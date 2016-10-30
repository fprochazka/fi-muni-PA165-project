package cz.muni.fi.pa165.team;

import java.util.Collection;
import java.util.UUID;

/**
 * @author Denis Galajda
 */
public interface TeamDao
{

    /**
     * Find Team by it's id.
     */
    Team findById(UUID id);

    /**
     * Find team by it's name.
     */
    Team findTeamByName(String name);

    /**
     * Find all teams
     */
    Collection<Team> findAll();

    /**
     * Persists the given team
     */
    void create(Team entity);

    /**
     * Update the given team
     */
    void update(Team entity);

    /**
     * Delete the given team
     */
    void delete(Team entity);

}
