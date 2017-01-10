package cz.muni.fi.pa165.team.match;

import cz.muni.fi.pa165.response.RedirectResponse;
import cz.muni.fi.pa165.team.TeamPlayerRepository;
import cz.muni.fi.pa165.team.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.format.DateTimeFormatter;
import java.util.UUID;

/**
 * @author Tomas Smid <smid.thomas@gmail.com>
 */
@Controller
public class MatchController
{

    private final TeamMatchFacade teamMatchFacade;

    private final TeamMatchRepository teamMatchRepository;

    private final TeamRepository teamRepository;

    private final TeamMatchGoalRepository teamMatchGoalRepository;

    private final TeamPlayerRepository teamPlayerRepository;

    @Autowired
    public MatchController(
        TeamMatchFacade teamMatchFacade,
        TeamMatchRepository teamMatchRepository,
        TeamRepository teamRepository,
        TeamMatchGoalRepository teamMatchGoalRepository,
        TeamPlayerRepository teamPlayerRepository)
    {
        this.teamMatchFacade = teamMatchFacade;
        this.teamMatchRepository = teamMatchRepository;
        this.teamRepository = teamRepository;
        this.teamMatchGoalRepository = teamMatchGoalRepository;
        this.teamPlayerRepository = teamPlayerRepository;
    }

    @InitBinder("matchRequest")
    private void initBinder(WebDataBinder binder)
    {
        binder.setValidator(new MatchCreationValidator());
    }

    @RequestMapping(value = "/matches", method = RequestMethod.GET)
    public ModelAndView viewListPlayed()
    {
        return new ModelAndView("team/match/played")
            .addObject("matchResults", teamMatchFacade.getPlayedMatchesList())
            .addObject("formatter", DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
    }

    @RequestMapping(value = "/matches/upcomming", method = RequestMethod.GET)
    public ModelAndView viewListUpcomming()
    {
        return new ModelAndView("team/match/upcomming")
            .addObject("plannedMatches", teamMatchRepository.findAllPlannedMatches())
            .addObject("formatter", DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
    }

    @RequestMapping(value = "/match/create", method = RequestMethod.GET)
    public ModelAndView viewNewMatch()
    {
        return new ModelAndView("team/match/create")
            .addObject("teams", teamRepository.findAll())
            .addObject("matchRequest", new MatchRequest());
    }

    @RequestMapping(value = "/match/create", method = RequestMethod.POST)
    public ModelAndView submitNewMatch(
        @ModelAttribute("matchRequest") @Validated MatchRequest matchRequest,
        BindingResult result
    ) throws Exception
    {
        if (result.hasErrors()) {
            return new ModelAndView("team/match/create")
                .addObject("teams", teamRepository.findAll())
                .addObject("matchRequest", matchRequest);
        }

        TeamMatch teamMatch = teamMatchFacade.createMatch(
            matchRequest.getHomeTeamId(),
            matchRequest.getAwayTeamId(),
            matchRequest.getStartTime(),
            matchRequest.getEndTime()
        );

        return new RedirectResponse("/match/" + teamMatch.getId().toString());
    }

    @RequestMapping(value = "/match/{id}", method = RequestMethod.GET)
    public ModelAndView viewMatchDetail(@PathVariable("id") UUID id)
    {
        return new ModelAndView("team/match/detail")
            .addObject("matchResult", teamMatchFacade.getMatchResult(id))
            .addObject("matchDetail", teamMatchFacade.getMatchDetail(id))
            .addObject("formatter", DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
    }

    @RequestMapping(value = "/match/{id}/delete", method = RequestMethod.POST)
    public ModelAndView deleteMatch(@PathVariable("id") UUID id) throws Exception
    {
        teamMatchFacade.deleteMatch(id);

        return new RedirectResponse("/matches");
    }

    @RequestMapping(value = "/match/{mid}/goal/{gid}/delete", method = RequestMethod.POST)
    public ModelAndView deleteGoal(@PathVariable("mid") UUID mid, @PathVariable("gid") UUID gid)
    {
        teamMatchFacade.deleteMatchGoal(gid);

        return new RedirectResponse("/match/" + mid);
    }

    @RequestMapping(value = "/match/{id}/edit", method = RequestMethod.GET)
    public ModelAndView viewEditMatch(@PathVariable("id") UUID id)
    {
        MatchRequest matchRequest = MatchRequest.fromMatch(teamMatchRepository.getMatchById(id));

        return new ModelAndView("team/match/edit")
            .addObject("matchRequest", matchRequest)
            .addObject("teams", teamRepository.findAll());
    }

    @RequestMapping(value = "/match/{id}/edit", method = RequestMethod.POST)
    public ModelAndView submitEditMatch(
        @PathVariable("id") UUID id,
        @ModelAttribute("matchRequest") @Validated MatchRequest matchRequest,
        BindingResult result
    )
    {
        if (result.hasErrors()) {
            System.out.println("RESULT HAS ERROR, CYCLE INFINITE");
            return new ModelAndView("team/match/edit")
                .addObject("matchRequest", matchRequest)
                .addObject("teams", teamRepository.findAll());
        }

        System.out.println("CHANGE MATCH TIME");
        teamMatchFacade.changeMatchTime(id, matchRequest.getStartTime(), matchRequest.getEndTime());

        return new RedirectResponse("/matches");
    }

    /**
     * Validates match form data.
     */
    class MatchCreationValidator implements Validator
    {

        @Override
        public boolean supports(Class type)
        {
            return MatchRequest.class.isAssignableFrom(type);
        }

        @Override
        public void validate(Object target, Errors errors)
        {
            MatchRequest request = (MatchRequest) target;

            if (request.getHomeTeamId() == null) {
                errors.rejectValue("homeTeamId", "matchform.hometeamnotspecified");
            }

            if (request.getAwayTeamId() == null) {
                errors.rejectValue("awayTeamId", "matchform.awayteamnotspecified");
            }

            if (request.getHomeTeamId() != null && request.getAwayTeamId() != null &&
                request.getHomeTeamId().equals(request.getAwayTeamId()))
            {
                errors.rejectValue("awayTeamId", "matchform.sameteams");
            }

            if (request.getEndTime() != null && !request.getEndTime().isAfter(request.getStartTime()))
            {
                errors.rejectValue("endTime", "matchform.endtimenotafterstarttime");
            }
        }

    }
}
