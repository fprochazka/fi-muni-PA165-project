package cz.muni.fi.pa165.team;

import java.util.Collection;
import java.util.UUID;

/**
 * Represents DAO for managing the Team entity.
 *
 * @author Denis Galajda
 */
public interface TeamRepository
{

    /**
     * Find Team by it's id.
     *
     * @param id id of the team which should be found in the database
     * @return the team with the given id,
     * or throws {@link cz.muni.fi.pa165.team.exceptions.TeamNotFoundException}
     */
    Team getTeamById(final UUID id);

    /**
     * Find Team by it's id.
     *
     * @param id id of the team which should be found in the database
     * @return the team with the given id, or null if such team
     * does not exist
     */
    Team findTeamById(final UUID id);

    /**
     * Find team by it's name.
     *
     * @param name name of the team which should be found in the database
     * @return the team with the given name, or null if such team
     * does not exist
     */
    Team findTeamByName(final String name);

    /**
     * Find team by it's player.
     *
     * @param tp player of the team which should be found in the database
     * @return the team with the given player, or null if such team
     * does not exist
     */
    Team findTeamByPlayer(final TeamPlayer tp);

    /**
     * Find all teams
     *
     * @return collection of all stored teams in database
     */
    Collection<Team> findAll();

}
