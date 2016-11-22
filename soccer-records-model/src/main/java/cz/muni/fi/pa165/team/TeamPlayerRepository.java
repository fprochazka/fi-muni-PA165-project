package cz.muni.fi.pa165.team;

import java.util.Collection;
import java.util.UUID;

/**
 * This interface represents Repository for managing the Player entity.
 *
 * @author Libor Mühlpachr <libor.muhl@seznam.cz>
 */

public interface TeamPlayerRepository
{

    /**
     * This method finds player by his ID.
     *
     * @param id player's ID
     */
    TeamPlayer getTeamPlayerById(UUID id);

    /**
     * This method finds players by their firstname.
     *
     * @param firstname player's firstname
     */
    Collection<TeamPlayer> findTeamPlayerByFirstname(String firstname);

    /**
     * This method finds players by their surname.
     *
     * @param surname player's surname
     */
    Collection<TeamPlayer> findTeamPlayerBySurname(String surname);

    /**
     * This method finds players by their team.
     *
     * @param team player's team
     */
    Collection<TeamPlayer> findTeamPlayerByTeam(Team team);

    /**
     * This method finds all players.
     */
    Collection<TeamPlayer> findAllTeamPlayers();
}
