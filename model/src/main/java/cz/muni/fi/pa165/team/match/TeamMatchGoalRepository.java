package cz.muni.fi.pa165.team.match;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.UUID;

/**
 * Represents Repository for managing (quering) the TeamMatchGoal entity.
 *
 * @author Tomas Smid <smid.thomas@gmail.com>
 */
public interface TeamMatchGoalRepository
{

    /**
     * Retrieves the goal from database according to
     * the given id.
     *
     * @param id id of the match which should be found in the database
     * @return the match with the given id, or null if such goal
     * does not exist
     */
    TeamMatchGoal getGoalById(final UUID id);

    /**
     * Retrieves the goal from the database according to
     * the given scorer.
     *
     * @param scorerId scorer whose goals should be retrieved from database
     * @return collection of the goals which was scored by the scorer,
     * collection is empty if such goal does not exist
     */
    Collection<TeamMatchGoal> findGoalByScorer(final UUID scorerId);

    /**
     * Retrieves the goal from the database according to
     * the given assistant.
     *
     * @param assistantId assistant of the found goal
     * @return collection of the goals associated with given assistant
     * as assistant of the goal, collection is empty if there is no such
     * goal
     */
    Collection<TeamMatchGoal> findGoalByAssistant(final UUID assistantId);

    /**
     * Retrieves the goal scored in the given match.
     *
     * @param matchId match in which the found goal should be scored
     * @return collection of goals scored in the given match, collection
     * is empty if such goal does not exist
     */
    Collection<TeamMatchGoal> findGoalByMatch(final UUID matchId);

    /**
     * Retrieves all scored goals.
     *
     * @return collection of all stored goals in database
     */
    Collection<TeamMatchGoal> findAllGoals();

    /**
     * Search if there exists a goal which has the same paramaters as the given.
     *
     * @param matchId     id of the match in which we want search same goal
     * @param scorerId    id of the scorer of goal
     * @param assistantId id of the assistant of goal
     * @param matchTime   time of the match in which goal is scored
     * @return found conflicting goal, null otherwise
     */
    TeamMatchGoal findConflictingGoal(final UUID matchId, final UUID scorerId, final UUID assistantId, final LocalDateTime matchTime);

    /**
     * Retrieves the last scored goal in the given match.
     *
     * @param matchId match in which thou found goal is scored as last one
     * @return the last scored goal in the required match if such goal exists,
     * otherwise null
     */
    TeamMatchGoal findLastGoalByMatch(final UUID matchId);

    /**
     * Retrieves all goal scored by the required team in the required match.
     *
     * @param matchId match in which the team should scored its goals
     * @param teamId  team which goals in the match should be retrieved
     * @return all scored goals by the team in the match
     */
    Collection<TeamMatchGoal> findAllGoalsByTeamInMatch(final UUID matchId, final UUID teamId);

    /**
     * Finds out the number scored goals by particular team in particular match.
     *
     * @param matchId match in which the particular team played and number of its
     *                scored goals should be retrieved
     * @param teamId  team which number of scored goals are required
     * @return number of scored goals by the given team in the given match
     */
    Long getGoalsCountByTeamInMatch(final UUID matchId, final UUID teamId);

}
