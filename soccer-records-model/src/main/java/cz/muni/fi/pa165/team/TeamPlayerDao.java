package cz.muni.fi.pa165.team;

import java.util.Collection;
import java.util.UUID;

/**
 * This interface represents DAO for managing the Player entity.
 *
 * @author Libor Mühlpachr <libor.muhl@seznam.cz>
 */
public interface TeamPlayerDao
{

    /**
     * This method finds player by his ID.
     *
     * @param id player's ID
     */
    TeamPlayer findPlayerById(UUID id);

    /**
     * This method finds players by their firstname.
     *
     * @param firstname player's firstname
     */
    Collection<TeamPlayer> findPlayerByFirstname(String firstname);

    /**
     * This method finds players by their surname.
     *
     * @param surname player's surname
     */
    Collection<TeamPlayer> findPlayerBySurname(String surname);

    /**
     * This method finds players by their team.
     *
     * @param team player's team
     */
    Collection<TeamPlayer> findPlayerByTeam(Team team);

    /**
     * This method finds all players.
     */
    Collection<TeamPlayer> findAllPlayers();

    /**
     * This method creates a player.
     *
     * @param entity player which should be created
     */
    void createPlayer(TeamPlayer entity);

    /**
     * This method updates a player.
     *
     * @param entity player which should be updated
     */
    void updatePlayer(TeamPlayer entity);

    /**
     * This method deletes a player.
     *
     * @param entity player which should be deleted
     */
    void deletePlayer(TeamPlayer entity);
}
