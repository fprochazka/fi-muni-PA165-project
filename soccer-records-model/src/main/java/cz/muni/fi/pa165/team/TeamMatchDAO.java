package cz.muni.fi.pa165.team;

import java.util.Collection;
import java.util.Date;
import java.util.UUID;

/**
 * Represents DAO for managing entities of type TeamMatch.
 *
 * @author Tomas Smid <smid.thomas@gmail.com>
 */
public interface TeamMatchDAO
{

    /**
     * Stored the match into the database.
     *
     * @param teamMatch match which is going to be stored
     */
    void createMatch(TeamMatch teamMatch);

    /**
     * Updates the state of the match stored in the database.
     *
     * @param teamMatch match which should be updated
     */
    void updateMatch(TeamMatch teamMatch);

    /**
     * Deletes the match from database and all its related goals too.
     *
     * @param teamMatch match which should be deleted
     */
    void deleteMatch(TeamMatch teamMatch);

    /**
     * Retrieves the required match from the database by its id.
     *
     * @param id id of the match which should be found in database
     * @return match with the given id, or null if there is no match
     * with the given id in the database
     */
    TeamMatch findMatchById(UUID id);

    /**
     * Retrieves the required match from the database according
     * to the given match, which should be in the role of home
     * team in match.
     *
     * @param team team which should be in role of home team in the found match
     * @return collection of all matches in which the given match is in the role
     * of home team, collection is empty if such match does not exist
     */
    Collection<TeamMatch> findMatchByHomeTeam(Team team);

    /**
     * Retrieves the required match from the database according
     * to the given match, which should be in the role of away
     * team in match.
     *
     * @param team team which should be in role of away team in the found match
     * @return collection of all matches in which the given match is in the role
     * of home team, collection is empty if such match does not exist
     */
    Collection<TeamMatch> findMatchByAwayTeam(Team team);

    /**
     * Retrieves the required match from database according to
     * the given time in which tha match should start.
     *
     * @param startTime time in which should the found match start
     * @return collection of all matches which start in the given
     * start time, collection is empty if such match does not exist
     */
    Collection<TeamMatch> findMatchByStartTime(Date startTime);

    /**
     * Retrieves all matches stored in tha database.
     *
     * @return collection of all stored matches in database,
     * collection is empty if there is no stored match in database
     */
    Collection<TeamMatch> findAllMatches();
}
