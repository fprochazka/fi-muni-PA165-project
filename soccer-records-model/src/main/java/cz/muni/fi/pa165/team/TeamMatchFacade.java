package cz.muni.fi.pa165.team;

import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @author Tomas Smid <smid.thomas@gmail.com>
 */
public class TeamMatchFacade
{
    private TeamMatchService teamMatchService;

    private TeamMatchRepository teamMatchRepository;

    private TeamMatchGoalRepository teamMatchGoalRepository;

    private TeamRepository teamRepository;

    private TeamPlayerRepository teamPlayerRepository;

    private EntityManager entityManager;

    public TeamMatchFacade(
        TeamMatchService teamMatchService,
        TeamMatchRepository teamMatchRepository,
        TeamMatchGoalRepository teamMatchGoalRepository,
        TeamRepository teamRepository,
        TeamPlayerRepository teamPlayerRepository,
        EntityManager entityManager
    )
    {
        this.teamMatchService = teamMatchService;
        this.teamMatchRepository = teamMatchRepository;
        this.teamMatchGoalRepository = teamMatchGoalRepository;
        this.teamRepository = teamRepository;
        this.teamPlayerRepository = teamPlayerRepository;
        this.entityManager = entityManager;
    }

    public TeamMatch createMatch(UUID homeTeamId, UUID awayTeamId, Date startTime, Date endTime)
    {
        Team homeTeam = teamRepository.getTeamById(homeTeamId);
        Team awayTeam = teamRepository.getTeamById(awayTeamId);
        List<TeamMatch> allMatchesOfHomeTeam =
            new ArrayList<>(teamMatchRepository.findAllMatchesOfTeam(homeTeam.getId()));
        List<TeamMatch> allMatchesOfAwayTeam =
            new ArrayList<>(teamMatchRepository.findAllMatchesOfTeam(awayTeam.getId()));

        TeamMatch teamMatch = teamMatchService.createMatch(
            allMatchesOfHomeTeam,
            allMatchesOfAwayTeam,
            homeTeam,
            awayTeam,
            startTime,
            endTime
        );

        entityManager.persist(teamMatch);
        entityManager.flush();

        return teamMatch;
    }

    public void deleteMatch(UUID matchId){

        TeamMatch teamMatch = teamMatchRepository.getMatchById(matchId);

        entityManager.createQuery("DELETE TeamMatchGoal tmg WHERE tmg.match = :teamMatch")
                     .setParameter("teamMatch", teamMatch)
                     .executeUpdate();

        entityManager.remove(teamMatch);
    }

    public void changeMatchTime(UUID matchId, Date startTime, Date endTime){

        TeamMatch teamMatch = teamMatchRepository.getMatchById(matchId);
        List<TeamMatch> sameStartTimeMatches = new ArrayList<>(teamMatchRepository.findMatchByStartTime(startTime));

        teamMatchService.changeMatchTime(sameStartTimeMatches,teamMatch,startTime,endTime);

        entityManager.flush();
    }

    public void endMatch(UUID matchId, Date endTime){

        TeamMatch teamMatch = teamMatchRepository.getMatchById(matchId);

        teamMatchService.endMatch(teamMatch,endTime);

        entityManager.flush();
    }

    public TeamMatchGoal addNewScoredGoal(UUID scorerId, UUID assistantId, UUID matchId, Date matchTime)
    {
        TeamPlayer scorer = teamPlayerRepository.getTeamPlayerById(scorerId);
        TeamPlayer assistant = teamPlayerRepository.getTeamPlayerById(assistantId);
        TeamMatch match = teamMatchRepository.getMatchById(matchId);
        List<TeamMatchGoal> goalsInActualMatch = new ArrayList<>(teamMatchGoalRepository.findGoalByMatch(match.getId()));

        TeamMatchGoal teamMatchGoal = teamMatchService.addNewScoredGoal(
            goalsInActualMatch,
            scorer,
            assistant,
            match,
            matchTime
        );

        entityManager.persist(teamMatchGoal);
        entityManager.flush();

        return teamMatchGoal;
    }

    public void deleteMatchGoal(UUID goalId){

        TeamMatchGoal teamMatchGoal = teamMatchGoalRepository.getGoalById(goalId);

        entityManager.remove(teamMatchGoal);
    }
}
