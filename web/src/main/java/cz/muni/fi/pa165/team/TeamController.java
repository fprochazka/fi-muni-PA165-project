package cz.muni.fi.pa165.team;

import cz.muni.fi.pa165.response.RedirectResponse;
import cz.muni.fi.pa165.team.auxobjects.TeamStatistics;
import cz.muni.fi.pa165.team.exceptions.TeamWithSameNameAlreadyExistsException;
import cz.muni.fi.pa165.team.match.TeamMatchRepository;
import cz.muni.fi.pa165.team.player.auxobjects.TeamPlayerStatistics;
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
import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author Denis Galajda <galajda.denis@gmail.com>
 */

@Controller
public class TeamController
{

    private final TeamFacade teamFacade;
    private final TeamPlayerFacade teamPlayerFacade;
    private final TeamRepository teamRepository;
    private final TeamMatchRepository teamMatchRepository;
    private final TeamPlayerRepository teamPlayerRepository;

    @Autowired
    public TeamController(TeamFacade teamFacade,
                          TeamPlayerFacade teamPlayerFacade,
                          TeamRepository teamRepository,
                          TeamMatchRepository teamMatchRepository,
                          TeamPlayerRepository teamPlayerRepository
    )
    {
        this.teamFacade = teamFacade;
        this.teamPlayerFacade = teamPlayerFacade;
        this.teamRepository = teamRepository;
        this.teamMatchRepository = teamMatchRepository;
        this.teamPlayerRepository = teamPlayerRepository;
    }

    @InitBinder("teamDetailsRequest")
    private void initBinder(WebDataBinder binder)
    {
        binder.setValidator(new TeamCreationValidator());
    }

    @RequestMapping(value = "/teams", method = RequestMethod.GET)
    public ModelAndView viewList() throws Exception
    {
        Collection<TeamStatistics> teamsStatistics = new ArrayList<>();
        Collection<Team> teamsList = teamRepository.findAll();
        teamsStatistics.addAll(teamsList.stream().map(team -> teamFacade.getTeamStatistics(team.getId())).collect(Collectors.toList()));
        return new ModelAndView("team/list")
            .addObject("teams", teamsStatistics);
    }

    @RequestMapping(value = "/team/create", method = RequestMethod.POST)
    public ModelAndView submitNewTeam(
        @ModelAttribute("teamDetailsRequest") @Validated TeamDetailsRequest teamDetailsRequest,
        BindingResult result
    ) throws Exception
    {
        if (result.hasErrors()) {
            return new ModelAndView("team/create")
                .addObject("teamDetailsRequest", teamDetailsRequest);
        }

        try {
            teamFacade.createTeam(teamDetailsRequest.getName());
        } catch (TeamWithSameNameAlreadyExistsException e) {
            result.rejectValue("name", "teamform.namealreadyused");
            return new ModelAndView("team/create")
                .addObject("teamDetailsRequest", teamDetailsRequest);
        }
        return new RedirectResponse("/teams");
    }

    @RequestMapping(value = "/team/create", method = RequestMethod.GET)
    public ModelAndView viewNewTeam() throws Exception
    {
        return new ModelAndView("team/create")
            .addObject("teamDetailsRequest", new TeamDetailsRequest());
    }

    @RequestMapping(value = "/team/{id}/delete", method = RequestMethod.POST)
    public ModelAndView deleteTeam(@PathVariable("id") UUID id) throws Exception
    {
        teamFacade.deleteTeam(id);

        return new RedirectResponse("/teams");
    }

    @RequestMapping(value = "/team/{id}/edit", method = RequestMethod.POST)
    public ModelAndView editTeamPost(
        @PathVariable("id") UUID id,
        @ModelAttribute("teamDetailsRequest") @Validated TeamDetailsRequest teamDetailsRequest,
        BindingResult result
    ) throws Exception
    {
        if (result.hasErrors()) {
            return new ModelAndView("team/edit")
                .addObject("teamDetailsRequest", teamDetailsRequest);
        }

        try {
            teamFacade.changeTeamName(id, teamDetailsRequest.getName());
        } catch (TeamWithSameNameAlreadyExistsException e) {
            result.rejectValue("name", "teamform.namealreadyused");
            return new ModelAndView("team/edit")
                .addObject("teamDetailsRequest", teamDetailsRequest);
        }
        return new RedirectResponse("/team/" + id.toString());
    }

    @RequestMapping(value = "/team/{id}/edit", method = RequestMethod.GET)
    public ModelAndView editTeamLoad(
        @PathVariable("id") UUID id
    ) throws Exception
    {
        TeamDetailsRequest teamDetailsRequest = TeamDetailsRequest.fromTeam(teamRepository.getTeamById(id));

        return new ModelAndView("team/edit")
            .addObject("teamDetailsRequest", teamDetailsRequest)
            .addObject("teamId", id.toString());
    }

    @RequestMapping(value = "/team/{id}", method = RequestMethod.GET)
    public ModelAndView showTeamOverview(
        @PathVariable("id") UUID id
    )
    {
        Collection<TeamPlayer> teamPlayers = teamPlayerRepository.findTeamPlayerByTeam(teamRepository.getTeamById(id));
        Collection<TeamPlayerStatistics> playersStatistics = teamPlayers.stream().map(player ->
            teamPlayerFacade.getPlayerStatistics(player.getId())).collect(Collectors.toCollection(ArrayList::new));
        return new ModelAndView("team/detail/overview")
            .addObject("teamStats", teamFacade.getTeamStatistics(id))
            .addObject("playersStats", playersStatistics);
    }

    @RequestMapping(value = "/team/{id}/results", method = RequestMethod.GET)
    public ModelAndView showTeamResults(
        @PathVariable("id") UUID id
    )
    {
        return new ModelAndView("team/detail/results")
            .addObject("team", teamRepository.getTeamById(id))
            .addObject("matchResults", teamFacade.getPlayedTeamMatchesList(id))
            .addObject("formatter", DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
    }

    @RequestMapping(value = "/team/{id}/schedule", method = RequestMethod.GET)
    public ModelAndView showTeamProgram(
        @PathVariable("id") UUID id
    )
    {
        return new ModelAndView("team/detail/schedule")
            .addObject("team", teamRepository.getTeamById(id))
            .addObject("plannedMatches", teamMatchRepository.findAllPlannedMatches())
            .addObject("formatter", DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
    }


    /**
     * Validates team form data.
     */
    private class TeamCreationValidator implements Validator
    {

        @Override
        public boolean supports(Class type)
        {
            return TeamDetailsRequest.class.isAssignableFrom(type);
        }

        @Override
        public void validate(Object target, Errors errors)
        {
            TeamDetailsRequest request = (TeamDetailsRequest) target;

            if (request.getName().equals("")) {
                errors.rejectValue("name", "teamform.emptyname");
            }
        }

    }

}
