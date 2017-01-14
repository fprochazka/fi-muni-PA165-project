package cz.muni.fi.pa165.team;

import cz.muni.fi.pa165.response.RedirectResponse;
import cz.muni.fi.pa165.team.exceptions.TeamWithSameNameAlreadyExistsException;
import cz.muni.fi.pa165.team.match.TeamMatchRepository;
import cz.muni.fi.pa165.team.result.TeamPlayerResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
    public TeamController(
        TeamFacade teamFacade,
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

    @RequestMapping(value = "/teams", method = RequestMethod.GET)
    public ModelAndView viewTeamsList() throws Exception
    {
        return new ModelAndView("team/list")
            .addObject("teams", teamFacade.getAllTeamStatistics());
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
            Team team = teamFacade.createTeam(teamDetailsRequest.getName());
            return new RedirectResponse("/team/" + team.getId().toString());
        } catch (TeamWithSameNameAlreadyExistsException e) {
            result.rejectValue("name", "teamform.namealreadyused");
            return new ModelAndView("team/create")
                .addObject("teamDetailsRequest", teamDetailsRequest);
        }
    }

    @RequestMapping(value = "/team/create", method = RequestMethod.GET)
    public ModelAndView viewNewTeam() throws Exception
    {
        return new ModelAndView("team/create")
            .addObject("teamDetailsRequest", new TeamDetailsRequest());
    }

    @RequestMapping(value = "/team/{teamId}/delete", method = RequestMethod.POST)
    public ModelAndView deleteTeam(@PathVariable("teamId") UUID teamId) throws Exception
    {
        teamFacade.deleteTeam(teamId);

        return new RedirectResponse("/teams");
    }

    @RequestMapping(value = "/team/{teamId}/edit", method = RequestMethod.POST)
    public ModelAndView submitEditTeam(
        @PathVariable("teamId") UUID teamId,
        @ModelAttribute("teamDetailsRequest") @Validated TeamDetailsRequest teamDetailsRequest,
        BindingResult result
    ) throws Exception
    {
        if (result.hasErrors()) {
            return new ModelAndView("team/edit")
                .addObject("teamDetailsRequest", teamDetailsRequest);
        }

        try {
            teamFacade.changeTeamName(teamId, teamDetailsRequest.getName());
            return new RedirectResponse("/team/" + teamId.toString());
        } catch (TeamWithSameNameAlreadyExistsException e) {
            result.rejectValue("name", "teamform.namealreadyused");
            return new ModelAndView("team/edit")
                .addObject("teamDetailsRequest", teamDetailsRequest);
        }
    }

    @RequestMapping(value = "/team/{teamId}/edit", method = RequestMethod.GET)
    public ModelAndView viewEditTeam(
        @PathVariable("teamId") UUID teamId
    ) throws Exception
    {
        TeamDetailsRequest teamDetailsRequest = TeamDetailsRequest.fromTeam(teamRepository.getTeamById(teamId));

        return new ModelAndView("team/edit")
            .addObject("teamDetailsRequest", teamDetailsRequest)
            .addObject("teamId", teamId.toString());
    }

    @RequestMapping(value = "/team/{teamId}", method = RequestMethod.GET)
    public ModelAndView viewTeamOverview(
        @PathVariable("teamId") UUID teamId
    )
    {
        Collection<TeamPlayer> teamPlayers = teamPlayerRepository.findTeamPlayerByTeam(teamRepository.getTeamById(teamId).getId());
        Collection<TeamPlayerResult> playersStatistics = teamPlayers.stream().map(player ->
            teamPlayerFacade.getPlayerStatistics(player.getId())).collect(Collectors.toCollection(ArrayList::new));
        return new ModelAndView("team/detail/overview")
            .addObject("teamStats", teamFacade.getTeamStatistics(teamId))
            .addObject("playersStats", playersStatistics);
    }

    @RequestMapping(value = "/team/{teamId}/results", method = RequestMethod.GET)
    public ModelAndView viewTeamResults(
        @PathVariable("teamId") UUID teamId
    )
    {
        return new ModelAndView("team/detail/results")
            .addObject("team", teamRepository.getTeamById(teamId))
            .addObject("matchResults", teamFacade.getPlayedTeamMatchesList(teamId))
            .addObject("formatter", DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
    }

    @RequestMapping(value = "/team/{teamId}/schedule", method = RequestMethod.GET)
    public ModelAndView viewTeamProgram(
        @PathVariable("teamId") UUID teamId
    )
    {
        return new ModelAndView("team/detail/schedule")
            .addObject("team", teamRepository.getTeamById(teamId))
            .addObject("plannedMatches", teamMatchRepository.findAllPlannedMatches())
            .addObject("formatter", DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
    }

}
