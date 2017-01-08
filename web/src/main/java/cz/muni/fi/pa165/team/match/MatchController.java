package cz.muni.fi.pa165.team.match;

import cz.muni.fi.pa165.team.TeamPlayerRepository;
import cz.muni.fi.pa165.team.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author Tomas Smid <smid.thomas@gmail.com>
 */
@Controller
public class MatchController
{

    private final TeamMatchFacade teamMatchFacade;

    private final TeamRepository teamRepository;

    private final TeamMatchGoalRepository teamMatchGoalRepository;

    private final TeamPlayerRepository teamPlayerRepository;

    @Autowired
    public MatchController(
        TeamMatchFacade teamMatchFacade,
        TeamRepository teamRepository,
        TeamMatchGoalRepository teamMatchGoalRepository,
        TeamPlayerRepository teamPlayerRepository)
    {
        this.teamMatchFacade = teamMatchFacade;
        this.teamRepository = teamRepository;
        this.teamMatchGoalRepository = teamMatchGoalRepository;
        this.teamPlayerRepository = teamPlayerRepository;
    }

    @RequestMapping(value = "/matches", method = RequestMethod.GET)
    public ModelAndView viewListPlayed()
    {
        return new ModelAndView("team/match/played_list")
            .addObject("pmContainer", teamMatchFacade.preparePlayedMatchesForList());
    }

    @RequestMapping(value = "/matches/upcomming", method = RequestMethod.GET)
    public ModelAndView viewListPlanned()
    {
        return new ModelAndView("team/match/upcomming_list")
            .addObject("pmContainer", teamMatchFacade.preparePlayedMatchesForList());
    }

}
