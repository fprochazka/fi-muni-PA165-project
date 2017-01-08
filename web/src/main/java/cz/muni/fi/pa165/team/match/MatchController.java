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
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;
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

        LocalDateTime startTime = getMVCLocalDateTime(matchRequest.getStartTime());
        LocalDateTime endTime = getMVCLocalDateTime(matchRequest.getEndTime());

        TeamMatch teamMatch = teamMatchFacade.createMatch(
            UUID.fromString(matchRequest.getHomeTeamId()),
            UUID.fromString(matchRequest.getAwayTeamId()),
            startTime,
            endTime
        );

        return new RedirectResponse("/match/" + teamMatch.getId().toString());
    }

    private LocalDateTime getMVCLocalDateTime(String formInputDate)
    {
        if (formInputDate == null || formInputDate.isEmpty()){
            return null;
        }

        String[] splittedFormDate = formInputDate.split("T");
        String dateToBeParsed = splittedFormDate[0] + " " + splittedFormDate[1];
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        return LocalDateTime.parse(dateToBeParsed, formatter);
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
        }

    }
}
