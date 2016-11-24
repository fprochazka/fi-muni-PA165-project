package cz.muni.fi.pa165.team;

import java.util.Collection;
import java.util.Date;
import java.util.UUID;

/**
 * @author Tomas Smid <smid.thomas@gmail.com>
 */
public interface TeamMatchRepository
{

    /**
     * Retrieves the required match from the database by its id.
     *
     * @param matchId id of the match which should be found in database
     * @return match with the given id
     */
    TeamMatch getMatchById(final UUID matchId);

    /**
     * Retrieves the required match from the database according
     * to the given match, which should be in the role of home
     * team in match.
     *
     * @param teamId team which should be in role of home team in the found match
     * @return collection of all matches in which the given match is in the role
     * of home team, collection is empty if such match does not exist
     */
    Collection<TeamMatch> findMatchByHomeTeam(final UUID teamId);

    /**
     * Retrieves the required match from the database according
     * to the given match, which should be in the role of away
     * team in match.
     *
     * @param teamId team which should be in role of away team in the found match
     * @return collection of all matches in which the given match is in the role
     * of home team, collection is empty if such match does not exist
     */
    Collection<TeamMatch> findMatchByAwayTeam(final UUID teamId);

    /**
     * Retrieves the required match from database according to
     * the given time in which tha match should start.
     *
     * @param startTime time in which should the found match start
     * @return collection of all matches which start in the given
     * start time, collection is empty if such match does not exist
     */
    Collection<TeamMatch> findMatchByStartTime(final Date startTime);

    /**
     * Retrieves all matches stored in tha database.
     *
     * @return collection of all stored matches in database,
     * collection is empty if there is no stored match in database
     */
    Collection<TeamMatch> findAllMatches();

    /**
     * Retrieves all matches which have already been played.
     *
     * @return collection of all played matches
     */
    Collection<TeamMatch> findAllPlayedMatches();

    /**
     * Retrieves all matches which are planned to be played, but have not
     * already been played.
     *
     * @return collection of all planned but not played matches
     */
    Collection<TeamMatch> findAllPlannedMatches();

    /**
     * Retrieves all matches which have already been played and
     * one of the teams in those matches is the given one.
     *
     * @param teamId team for which are retrieved all its played matches
     * @return collection of all played matches of the given team
     */
    Collection<TeamMatch> findAllPlayedMatchesOfTeam(final UUID teamId);

    /**
     * Retrieves all the matches which are planned, but have not already
     * been played and one of the teams in those matches is the given one.
     *
     * @param teamId team for which are retrieved all its planned not played matches
     * @return collection of all planned not played matches of the given team
     */
    Collection<TeamMatch> findAllPlannedMatchesOfTeam(final UUID teamId);

    /**
     * Retrieves all the matches in which play the required team, does not
     * matter if it is in role of home team or away team.
     *
     * @param teamId team which matches are going to be retrieved
     * @return all matches with the given team
     */
    Collection<TeamMatch> findAllMatchesOfTeam(final UUID teamId);

    /**
     * Retrieves match which conflicts with another match, because of the same match
     * start time and a same team.
     *
     * @param teamId    id of a team which appears in both conflicting matches
     * @param startTime start time of both conflicting matches
     * @return found conflicting match if exists, null otherwise
     */
    TeamMatch findConflictingMatchByTeamAndStartTime(final UUID teamId, final Date startTime);
}
