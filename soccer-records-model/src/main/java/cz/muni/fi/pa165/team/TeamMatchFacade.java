package cz.muni.fi.pa165.team;

import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @author Tomas Smid <smid.thomas@gmail.com>
 */
@Component
public class TeamMatchFacade
{
    private TeamMatchService teamMatchService;

    private TeamMatchRepository teamMatchRepository;

    private TeamMatchGoalRepository teamMatchGoalRepository;

    private EntityManager entityManager;

    public TeamMatchFacade(
        TeamMatchService teamMatchService,
        TeamMatchRepository teamMatchRepository,
        TeamMatchGoalRepository teamMatchGoalRepository,
        EntityManager entityManager
    )
    {
        this.teamMatchService = teamMatchService;
        this.teamMatchRepository = teamMatchRepository;
        this.teamMatchGoalRepository = teamMatchGoalRepository;
        this.entityManager = entityManager;
    }

    public TeamMatch createMatch(Team homeTeam, Team awayTeam, Date startTime, Date endTime)
    {
        List<TeamMatch> sameHomeTeamMatches =
            new ArrayList<>(teamMatchRepository.findMatchByHomeTeam(homeTeam.getId()));

        TeamMatch teamMatch = teamMatchService.createMatch(
            sameHomeTeamMatches,
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

    public TeamMatchGoal addNewScoredGoal(TeamPlayer scorer, TeamPlayer assistant, TeamMatch match, Date matchTime)
    {
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
