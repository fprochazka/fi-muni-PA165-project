package cz.muni.fi.pa165.team.match;

import cz.muni.fi.pa165.team.Team;
import cz.muni.fi.pa165.team.TeamPlayer;
import cz.muni.fi.pa165.team.TeamPlayerRepository;
import cz.muni.fi.pa165.team.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Date;
import java.util.UUID;

/**
 * @author Tomas Smid <smid.thomas@gmail.com>
 */
@Service
@Transactional
public class TeamMatchFacade
{

    private TeamMatchService teamMatchService;

    private TeamMatchRepository teamMatchRepository;

    private TeamMatchGoalRepository teamMatchGoalRepository;

    private TeamRepository teamRepository;

    private TeamPlayerRepository teamPlayerRepository;

    private EntityManager entityManager;

    @Autowired
    public TeamMatchFacade(
        TeamMatchService teamMatchService,
        TeamMatchRepository teamMatchRepository,
        TeamMatchGoalRepository teamMatchGoalRepository,
        TeamRepository teamRepository,
        TeamPlayerRepository teamPlayerRepository
    )
    {
        this.teamMatchService = teamMatchService;
        this.teamMatchRepository = teamMatchRepository;
        this.teamMatchGoalRepository = teamMatchGoalRepository;
        this.teamRepository = teamRepository;
        this.teamPlayerRepository = teamPlayerRepository;
    }

    @PersistenceContext
    public void setEntityManager(EntityManager entityManager)
    {
        this.entityManager = entityManager;
    }

    /**
     * This method creates and stored a match in DB.
     *
     * @param homeTeamId id of home team in the match
     * @param awayTeamId id of away team in the match
     * @param startTime  start time of the match
     * @param endTime    end time of the match, can be null
     * @return newly created match
     */
    public TeamMatch createMatch(UUID homeTeamId, UUID awayTeamId, Date startTime, Date endTime)
    {
        Team homeTeam = teamRepository.getTeamById(homeTeamId);
        Team awayTeam = teamRepository.getTeamById(awayTeamId);
        TeamMatch conflictingMatchForHomeTeam = teamMatchRepository
            .findConflictingMatchByTeamAndStartTime(homeTeam.getId(), startTime);
        TeamMatch conflictingMatchForAwayTeam = teamMatchRepository
            .findConflictingMatchByTeamAndStartTime(awayTeam.getId(), startTime);

        TeamMatch teamMatch = teamMatchService.createMatch(
            conflictingMatchForHomeTeam,
            conflictingMatchForAwayTeam,
            homeTeam,
            awayTeam,
            startTime,
            endTime
        );

        entityManager.persist(teamMatch);
        entityManager.flush();

        return teamMatch;
    }

    /**
     * This method deletes match from DB.
     *
     * @param matchId id of match which should be deleted
     */
    public void deleteMatch(UUID matchId)
    {

        TeamMatch teamMatch = teamMatchRepository.getMatchById(matchId);

        entityManager.createQuery("DELETE TeamMatchGoal tmg WHERE tmg.match = :teamMatch")
            .setParameter("teamMatch", teamMatch)
            .executeUpdate();

        entityManager.remove(teamMatch);
        entityManager.flush();
    }

    /**
     * This method changes start time and end time of the match.
     *
     * @param matchId   id of the match which times should be updated
     * @param startTime new start time of the match
     * @param endTime   new end time of the match
     */
    public void changeMatchTime(UUID matchId, Date startTime, Date endTime)
    {
        TeamMatch teamMatch = teamMatchRepository.getMatchById(matchId);
        TeamMatch conflictingMatchForHomeTeam =
            teamMatchRepository.findConflictingMatchByTeamAndStartTime(teamMatch.getHomeTeam().getId(), startTime);
        TeamMatch conflictingMatchForAwayTeam =
            teamMatchRepository.findConflictingMatchByTeamAndStartTime(teamMatch.getAwayTeam().getId(), startTime);

        teamMatchService.changeMatchTime(
            conflictingMatchForHomeTeam,
            conflictingMatchForAwayTeam,
            teamMatch,
            startTime,
            endTime
        );

        entityManager.flush();
    }

    /**
     * This methods ends the match, respectively sets non-null end time value.
     *
     * @param matchId id of the match which end time should be updated
     * @param endTime new end time of the match
     */
    public void endMatch(UUID matchId, Date endTime)
    {
        TeamMatch teamMatch = teamMatchRepository.getMatchById(matchId);

        teamMatchService.endMatch(teamMatch, endTime);

        entityManager.flush();
    }

    /**
     * This method creates and stores new goal in DB.
     *
     * @param scorerId    id of the scorer of the goal
     * @param assistantId id of the assistant of the goal
     * @param matchId     id of the match in which the goal is scored
     * @param matchTime   time in which the goal is scored
     * @return newly created goal
     */
    public TeamMatchGoal addNewScoredGoal(UUID scorerId, UUID assistantId, UUID matchId, Date matchTime)
    {
        TeamPlayer scorer = teamPlayerRepository.getTeamPlayerById(scorerId);
        TeamPlayer assistant = teamPlayerRepository.getTeamPlayerById(assistantId);
        TeamMatch match = teamMatchRepository.getMatchById(matchId);
        TeamMatchGoal sameGoal = teamMatchGoalRepository.findConflictingGoal(
            match.getId(),
            scorer.getId(),
            assistant.getId(),
            matchTime
        );

        TeamMatchGoal teamMatchGoal = teamMatchService.addNewScoredGoal(
            scorer,
            assistant,
            match,
            matchTime,
            sameGoal
        );

        entityManager.persist(teamMatchGoal);
        entityManager.flush();

        return teamMatchGoal;
    }

    /**
     * This method deletes the goal from DB.
     *
     * @param goalId if of the goal which should be deleted
     */
    public void deleteMatchGoal(UUID goalId)
    {
        TeamMatchGoal teamMatchGoal = teamMatchGoalRepository.getGoalById(goalId);

        entityManager.remove(teamMatchGoal);
        entityManager.flush();
    }

}
