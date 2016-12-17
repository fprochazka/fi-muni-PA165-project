package cz.muni.fi.pa165.team;

import cz.muni.fi.pa165.response.RedirectResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.UUID;

/**
 * @author Denis Galajda <galajda.denis@gmail.com>
 */

@Controller
public class TeamController
{

    private final TeamFacade teamFacade;
    private final TeamRepository teamRepository;

    @Autowired
    public TeamController(TeamFacade teamFacade, TeamRepository teamRepository)
    {
        this.teamFacade = teamFacade;
        this.teamRepository = teamRepository;
    }

    @RequestMapping(value = "/teams", method = RequestMethod.GET)
    public ModelAndView viewList() throws Exception
    {
        return new ModelAndView("team/list")
            .addObject("teams", teamRepository.findAll());
    }

    @RequestMapping(value = "/team/create", method = RequestMethod.POST)
    public ModelAndView submitNewTeam(
        @ModelAttribute("teamDetailsRequest") TeamDetailsRequest teamDetailsRequest,
        BindingResult result
    ) throws Exception
    {
        if (result.hasErrors()) {
            return new ModelAndView("team/create")
                .addObject("teamDetailsRequest", teamDetailsRequest);
        }

        Team team = teamFacade.createTeam(teamDetailsRequest.name);

        return new RedirectResponse("/team/" + team.getId().toString());
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

        teamFacade.changeTeamName(id, teamDetailsRequest.getName());

        return new RedirectResponse("/teams");
    }

    @RequestMapping(value = "/team/{id}/edit", method = RequestMethod.GET)
    public ModelAndView editTeamLoad(
        @PathVariable("id") UUID id
    ) throws Exception
    {
        TeamDetailsRequest teamDetailsRequest = TeamDetailsRequest.fromTeam(teamRepository.getTeamById(id));

        return new ModelAndView("team/edit")
            .addObject("teamDetailsRequest", teamDetailsRequest);
    }

    @RequestMapping(value = "/team/{id}", method = RequestMethod.GET)
    public ModelAndView showTeamOverview(
        @PathVariable("id") UUID id
    )
    {
        return new ModelAndView("team/detail/overview")
            .addObject("team", teamRepository.getTeamById(id));
    }

    @RequestMapping(value = "/team/{id}/results", method = RequestMethod.GET)
    public ModelAndView showTeamResults(
        @PathVariable("id") UUID id
    )
    {
        return new ModelAndView("team/detail/results")
            .addObject("team", teamRepository.getTeamById(id));
    }

    @RequestMapping(value = "/team/{id}/schedule", method = RequestMethod.GET)
    public ModelAndView showTeamProgram(
        @PathVariable("id") UUID id
    )
    {
        return new ModelAndView("team/detail/schedule")
            .addObject("team", teamRepository.getTeamById(id));
    }

}
