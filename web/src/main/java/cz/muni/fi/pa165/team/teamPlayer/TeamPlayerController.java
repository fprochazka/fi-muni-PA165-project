package cz.muni.fi.pa165.team.teamPlayer;

import cz.muni.fi.pa165.response.RedirectResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import cz.muni.fi.pa165.team.TeamPlayerRepository;
import cz.muni.fi.pa165.team.TeamPlayerFacade;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.UUID;

import static javax.swing.text.StyleConstants.ModelAttribute;

/**
 * @author Libor Muhlpachr <libor.muhl@seznam.cz>
 */
@Controller
public class TeamPlayerController
{

    private final TeamPlayerFacade teamPlayerFacade;
    private final TeamPlayerRepository teamPlayerRepository;

    @Autowired
    public TeamPlayerController(TeamPlayerFacade teamPlayerFacade, TeamPlayerRepository teamPlayerRepository)
    {
        this.teamPlayerFacade = teamPlayerFacade;
        this.teamPlayerRepository = teamPlayerRepository;
    }

    @RequestMapping(value = "/teamplayers", method = RequestMethod.GET)
    public ModelAndView viewList() throws Exception
    {
        return new ModelAndView("/teamplayer/teamplayers").addObject("teamplayers", teamPlayerRepository.findAll());
    }

    @RequestMapping(value = "/teamplayer/create", method = RequestMethod.POST)
    public ModelAndView submitNewTeamPlayer(
        @ModelAttribute("teamPlayerRequest") TeamPlayerRequest teamPlayerRequest,
        BindingResult result
    ) throws Exception
    {
        if (result.hasErrors()) {
            return new ModelAndView("teamplayer/teamplayerform")
                .addObject("teamPlayerRequest", teamPlayerRequest);
        }

        teamPlayerFacade.createTeamPlayer(teamPlayerRequest.firstname);
        teamPlayerFacade.createTeamPlayer(teamPlayerRequest.surname);
        teamPlayerFacade.createTeamPlayer(teamPlayerRequest.height);
        teamPlayerFacade.createTeamPlayer(teamPlayerRequest.weight);
        teamPlayerFacade.createTeamPlayer(teamPlayerRequest.team);

        return new RedirectResponse("/teamplayers");
    }

    @RequestMapping(value = "/teamplayer/create", method = RequestMethod.GET)
    public ModelAndView viewNewTeamPlayer() throws Exception
    {
        TeamPlayerRequest teamPlayerRequest = new TeamPlayerRequest();
        teamPlayerRequest.setTarget("/teamplayer/create");
        return new ModelAndView("teamplayer/teamplayerform")
            .addObject("teamPlayerRequest", teamPlayerRequest);
    }

    @RequestMapping(value = "/teamplayer/{id}/delete", method = RequestMethod.POST)
    public ModelAndView deleteTeamPlayer(@PathVariable("id") UUID id) throws Exception
    {
        teamPlayerFacade.deleteTeamPlayer(id);
        return new RedirectResponse("/teamplayers");
    }

    @RequestMapping(value = "/teamplayer/{id}/edit", method = RequestMethod.POST)
    public ModelAndView editTeamPlayerPost(
        @PathVariable("id") UUID id,
        @ModelAttribute("teamPlayerRequest") TeamPlayerRequest teamPlayerRequest,
        BindingResult result
    ) throws Exception
    {
        if (result.hasErrors()) {
            return new ModelAndView("teamplayer/teamplayerform")
                .addObject("teamPlayerRequest", teamPlayerRequest);
        }
        teamPlayerFacade.changeTeamPlayerFirstname(id, teamPlayerRequest.getFirstname());
        teamPlayerFacade.changeTeamPlayerSurname(id, teamPlayerRequest.getSurname());
        teamPlayerFacade.changeTeamPlayerHeight(id, teamPlayerRequest.getHeight());
        teamPlayerFacade.changeTeamPlayerWeight(id, teamPlayerRequest.getWeight());
        return new RedirectResponse("/teamplayers");
    }

    @RequestMapping(value = "/teamplayer/{id}/edit", method = RequestMethod.GET)
    public ModelAndView editTeamPlayerLoad(@PathVariable("id") UUID id) throws Exception
    {
        TeamPlayerRequest teamPlayerRequest = new TeamPlayerRequest();
        teamPlayerRequest.setFirstname(teamPlayerRepository.getTeamPlayerById(id).getFirstname());
        teamPlayerRequest.setSurname(teamPlayerRepository.getTeamPlayerById(id).getSurname());
        teamPlayerRequest.setHeight(teamPlayerRepository.getTeamPlayerById(id).getHeight());
        teamPlayerRequest.setWeight(teamPlayerRepository.getTeamPlayerById(id).getWeight());
        teamPlayerRequest.setTeam(teamPlayerRepository.getTeamPlayerById(id).getTeam());
        teamPlayerRequest.setTarget("/teamplayer/" + id + "/edit");
        return new ModelAndView("/teamplayer/teamplayerform")
            .addObject("teamPlayerRequest", teamPlayerRequest);
    }

    @RequestMapping(value = "/teamplayer/{id}", method = RequestMethod.GET)
    public ModelAndView listNotes(@PathVariable("id") UUID id)
    {

        return new ModelAndView("/teamplayer/teamplayerdetail")
            .addObject("teamplayer", teamPlayerRepository.getTeamPlayerById(id));
    }
}
