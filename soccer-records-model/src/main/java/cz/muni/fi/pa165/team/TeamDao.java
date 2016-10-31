package cz.muni.fi.pa165.team;

import java.util.Collection;
import java.util.UUID;

/**
 * Represents DAO for managing the Team entity.
 *
 * @author Denis Galajda
 */
public interface TeamDao
{

    /**
     * Find Team by it's id.
     *
     * @param id id of the team which should be found in the database
     * @return the team with the given id, or null if such team
     * does not exist
     */
    Team findTeamById(UUID id);

    /**
     * Find team by it's name.
     *
     * @param name name of the team which should be found in the database
     * @return the team with the given name, or null if such team
     * does not exist
     */
    Team findTeamByName(String name);

    /**
     * Find team by it's player.
     *
     * @param tp player of the team which should be found in the database
     * @return the team with the given player, or null if such team
     * does not exist
     */
    Team findTeamByPlayer(TeamPlayer tp);

    /**
     * Find all teams
     *
     * @return collection of all stored teams in database
     */
    Collection<Team> findAll();

    /**
     * Stores the given team into the database.
     *
     * @param entity team which should be created
     */
    void createTeam(Team entity);

    /**
     * Update the given team in the database.
     *
     * @param entity team which should be update
     */
    void updateTeam(Team entity);

    /**
     * Deletes the given team into the database.
     *
     * @param entity team which should be deleted
     */
    void deleteTeam(Team entity);

}
