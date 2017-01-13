package cz.muni.fi.pa165.team.player;

import cz.muni.fi.pa165.response.RedirectResponse;
import cz.muni.fi.pa165.team.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collection;
import java.util.UUID;

/**
 * @author Libor Muhlpachr <libor.muhl@seznam.cz>
 */
@Controller
public class TeamPlayerController
{

    private final TeamPlayerFacade teamPlayerFacade;
    private final TeamPlayerRepository teamPlayerRepository;
    private final TeamRepository teamRepository;

    @Autowired
    public TeamPlayerController(
        TeamPlayerFacade teamPlayerFacade,
        TeamPlayerRepository teamPlayerRepository,
        TeamRepository teamRepository
    )
    {
        this.teamPlayerFacade = teamPlayerFacade;
        this.teamPlayerRepository = teamPlayerRepository;
        this.teamRepository = teamRepository;
    }

    @RequestMapping(value = "/team/{teamId}/players", method = RequestMethod.GET)
    public ModelAndView viewList(@PathVariable("teamId") UUID teamId) throws Exception
    {
        Team team = teamRepository.getTeamById(teamId);
        Collection<TeamPlayer> teamPlayers = teamPlayerRepository.findTeamPlayerByTeam(teamId);

        return new ModelAndView("team/player/list")
            .addObject("team", team)
            .addObject("teamPlayers", teamPlayers);
    }

    @RequestMapping(value = "/team/{teamId}/player/create", method = RequestMethod.GET)
    public ModelAndView viewNewTeamPlayer(@PathVariable("teamId") UUID teamId) throws Exception
    {
        Team team = teamRepository.getTeamById(teamId);

        return new ModelAndView("team/player/create")
            .addObject("team", team)
            .addObject("teamPlayerRequest", new TeamPlayerRequest());
    }

    @RequestMapping(value = "/team/{teamId}/player/create", method = RequestMethod.POST)
    public ModelAndView submitNewTeamPlayer(
        @PathVariable("teamId") UUID teamId,
        @ModelAttribute("teamPlayerRequest") TeamPlayerRequest teamPlayerRequest,
        BindingResult result
    ) throws Exception
    {
        Team team = teamRepository.getTeamById(teamId);

        if (result.hasErrors()) {
            return new ModelAndView("team/player/create")
                .addObject("team", team)
                .addObject("teamPlayerRequest", teamPlayerRequest);
        }

        teamPlayerFacade.createTeamPlayer(
            team.getId(),
            teamPlayerRequest.getFirstname(),
            teamPlayerRequest.getSurname(),
            teamPlayerRequest.getHeight(),
            teamPlayerRequest.getWeight()
        );

        return new RedirectResponse("/team/" + teamId.toString() + "/players");
    }

    @RequestMapping(value = "/team/{teamId}/player/{playerId}/edit", method = RequestMethod.GET)
    public ModelAndView viewEditTeamPlayer(
        @PathVariable("teamId") UUID teamId,
        @PathVariable("playerId") UUID playerId
    ) throws Exception
    {
        TeamPlayer teamPlayer = teamPlayerRepository.getTeamPlayerByTeamAndId(teamId, playerId);

        return new ModelAndView("team/player/edit")
            .addObject("team", teamPlayer.getTeam())
            .addObject("teamPlayerRequest", TeamPlayerRequest.fromTeamPlayer(teamPlayer));
    }

    @RequestMapping(value = "/team/{teamId}/player/{playerId}/edit", method = RequestMethod.POST)
    public ModelAndView submitEditTeamPlayer(
        @PathVariable("teamId") UUID teamId,
        @PathVariable("playerId") UUID playerId,
        @ModelAttribute("teamPlayerRequest") TeamPlayerRequest teamPlayerRequest,
        BindingResult result
    ) throws Exception
    {
        TeamPlayer teamPlayer = teamPlayerRepository.getTeamPlayerByTeamAndId(teamId, playerId);

        if (result.hasErrors()) {
            return new ModelAndView("team/player/edit")
                .addObject("team", teamPlayer.getTeam())
                .addObject("teamPlayerRequest", teamPlayerRequest);
        }

        teamPlayerFacade.changeTeamPlayerDetails(
            teamId,
            playerId,
            teamPlayerRequest.getFirstname(),
            teamPlayerRequest.getSurname(),
            teamPlayerRequest.getHeight(),
            teamPlayerRequest.getWeight()
        );

        return new RedirectResponse("/team/" + teamId.toString() + "/players");
    }

    @RequestMapping(value = "/team/{teamId}/player/{playerId}/delete", method = RequestMethod.POST)
    public ModelAndView submitDeleteTeamPlayer(
        @PathVariable("teamId") UUID teamId,
        @PathVariable("playerId") UUID playerId
    ) throws Exception
    {
        teamPlayerFacade.deleteTeamPlayer(teamId, playerId);

        return new RedirectResponse("/team/" + teamId.toString() + "/players");
    }

    @RequestMapping(value = "/team/{teamId}/player/{playerId}", method = RequestMethod.GET)
    public ModelAndView viewTeamPlayer(
        @PathVariable("teamId") UUID teamId,
        @PathVariable("playerId") UUID playerId
    )
    {
        TeamPlayer teamPlayer = teamPlayerRepository.getTeamPlayerByTeamAndId(teamId, playerId);

        return new ModelAndView("/team/player/detail")
            .addObject("team", teamPlayer.getTeam())
            .addObject("teamPlayer", teamPlayer);
    }
}
