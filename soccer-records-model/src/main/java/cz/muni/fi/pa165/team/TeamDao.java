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
    Team findTeamById(UUID id);

    /**
     * Find team by it's name.
     */
    Team findTeamByName(String name);

    /**
     * Find team by it's name.
     */
    Team findTeamByPlayer(TeamPlayer tp);

    /**
     * Find all teams
     */
    Collection<Team> findAll();

    /**
     * Persists the given team
     */
    void createTeam(Team entity);

    /**
     * Update the given team
     */
    void updateTeam(Team entity);

    /**
     * Delete the given team
     */
    void deleteTeam(Team entity);

}
