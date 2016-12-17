package cz.muni.fi.pa165.team.match;

import cz.muni.fi.pa165.response.RedirectResponse;
import cz.muni.fi.pa165.team.Team;
import cz.muni.fi.pa165.team.TeamPlayer;
import cz.muni.fi.pa165.team.TeamPlayerRepository;
import cz.muni.fi.pa165.team.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.time.format.DateTimeFormatter;
import java.util.*;

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
    public ModelAndView viewMatchList()
    {
        List<Integer> homeTeamsGoals = new ArrayList<>();
        List<Integer> awayTeamsGoals = new ArrayList<>();
        Collection<TeamMatch> playedMatches = teamMatchFacade.findAllPlayedMatches();
        Collection<TeamMatch> plannedMatches = teamMatchFacade.findAllPlannedMatches();
        ModelAndView modelAndView = new ModelAndView("team/match/list");

        for (TeamMatch pmatch : playedMatches) {
            homeTeamsGoals.add(teamMatchFacade.findAllGoalByHomeTeamInMatch(pmatch.getId()).size());
            awayTeamsGoals.add(teamMatchFacade.findAllGoalByAwayTeamInMatch(pmatch.getId()).size());
        }
        modelAndView.addObject("playedMatches", playedMatches);
        modelAndView.addObject("plannedMatches", plannedMatches);
        modelAndView.addObject("homeTeamsGoals", homeTeamsGoals);
        modelAndView.addObject("awayTeamsGoals", awayTeamsGoals);
        modelAndView.addObject("formatter", DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));

        return modelAndView;
    }

    @RequestMapping(value = "/match/new", method = RequestMethod.GET)
    public ModelAndView viewNewMatchForm()
    {
        MatchRequest matchRequest = new MatchRequest();
        ModelAndView modelAndView = new ModelAndView("team/match/mform");
        Collection<Team> allTeams = teamRepository.findAll();

        matchRequest.setTarget("/match/create");
        modelAndView.addObject("homeTeams", allTeams);
        modelAndView.addObject("awayTeams", allTeams);
        modelAndView.addObject("matchRequest", matchRequest);

        return modelAndView;
    }

    @RequestMapping(value = "/match/{id}/delete", method = RequestMethod.POST)
    public ModelAndView deleteMatch(@PathVariable("id") UUID id) throws Exception
    {
        teamMatchFacade.deleteMatch(id);
        return new RedirectResponse("/matches");
    }

    @RequestMapping(value = "/match/{id}")
    public ModelAndView viewMatchDetail(@PathVariable("id") UUID id)
    {
        TeamMatch match = teamMatchFacade.getMatchById(id);
        Collection<TeamMatchGoal> homeGoals = teamMatchGoalRepository.findAllGoalsByTeamInMatch(
            match.getId(), match.getHomeTeam().getId());
        Collection<TeamMatchGoal> awayGoals = teamMatchGoalRepository.findAllGoalsByTeamInMatch(
            match.getId(), match.getAwayTeam().getId());
        Collection<TeamPlayer> homePlayers = teamPlayerRepository.findTeamPlayerByTeam(match.getHomeTeam());
        Collection<TeamPlayer> awayPlayers = teamPlayerRepository.findTeamPlayerByTeam(match.getAwayTeam());
        ModelAndView modelAndView = new ModelAndView("team/match/detail");

        modelAndView.addObject("match", match);
        modelAndView.addObject("formatter", DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
        modelAndView.addObject("homeGoals", homeGoals);
        modelAndView.addObject("awayGoals", awayGoals);
        modelAndView.addObject("homePlayers", homePlayers);
        modelAndView.addObject("awayPlayers", awayPlayers);

        return modelAndView;
    }
}
