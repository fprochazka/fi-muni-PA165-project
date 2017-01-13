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
     * Finds player by his ID.
     *
     * @param id player's ID
     */
    TeamPlayer getTeamPlayerById(final UUID id);

    /**
     * Finds player by his ID.
     */
    TeamPlayer getTeamPlayerByTeamAndId(final UUID teamId, final UUID playerId);

    /**
     * Finds players by their firstname.
     *
     * @param firstname player's firstname
     */
    Collection<TeamPlayer> findTeamPlayerByFirstname(final String firstname);

    /**
     * Finds players by their surname.
     *
     * @param surname player's surname
     */
    Collection<TeamPlayer> findTeamPlayerBySurname(final String surname);

    /**
     * Finds players by their team.
     *
     * @param teamId player's team
     */
    Collection<TeamPlayer> findTeamPlayerByTeam(final UUID teamId);

}
