package cz.muni.fi.pa165.team;

import java.util.Collection;
import java.util.UUID;

/**
 * @author Libor Mühlpachr <libor.muhl@seznam.cz>
 */
public interface TeamPlayerDao
{

    /**
     * Find Player by his ID.
     */
    TeamPlayer findPlayerById(UUID id);

    /**
     * Find Player by his firstname.
     */
    Collection<TeamPlayer> findPlayerByFirstname(String firstname);

    /**
     * Find Player by his surname.
     */
    Collection<TeamPlayer> findPlayerBySurname(String surname);

    /**
     * Find Player by his Team.
     */

     Collection<TeamPlayer> findPlayerByTeam(Team team);

    /**
     * Find all Players.
     */
     Collection<TeamPlayer> findAllPlayers();

    /**
     * Create Player.
     */
     void create(TeamPlayer entity);

    /**
     * Update Player.
     */
     void update(TeamPlayer entity);

    /**
     * Delete Player.
     */
     void delete(TeamPlayer entity);

}
