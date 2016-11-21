package cz.muni.fi.pa165.team;

import java.util.Collection;
import java.util.UUID;

/**
 * Represents DAO for managing the TeamMatchGoal entity.
 *
 * @author Tomas Smid <smid.thomas@gmail.com>
 */
interface TeamMatchGoalRepository
{

    /**
     * Retrieves the goal from database according to
     * the given id.
     *
     * @param id id of the match which should be found in the database
     * @return the match with the given id, or null if such goal
     * does not exist
     */
    TeamMatchGoal getGoalById(UUID id);

    /**
     * Retrieves the goal from the database according to
     * the given scorer.
     *
     * @param scorer scorer whose goals should be retrieved from database
     * @return collection of the goals which was scored by the scorer,
     * collection is empty if such goal does not exist
     */
    Collection<TeamMatchGoal> findGoalByScorer(TeamPlayer scorer);

    /**
     * Retrieves the goal from the database according to
     * the given assistant.
     *
     * @param assistant assistant of the found goal
     * @return collection of the goals associated with given assistant
     * as assistant of the goal, collection is empty if there is no such
     * goal
     */
    Collection<TeamMatchGoal> findGoalByAssistant(TeamPlayer assistant);

    /**
     * Retrieves the goal scored in the given match.
     *
     * @param match match in which the found goal should be scored
     * @return collection of goals scored in the given match, collection
     * is empty if such goal does not exist
     */
    Collection<TeamMatchGoal> findGoalByMatch(TeamMatch match);

    /**
     * Retrieves all scored goals.
     *
     * @return collection of all stored goals in database
     */
    Collection<TeamMatchGoal> findAllGoals();
}
