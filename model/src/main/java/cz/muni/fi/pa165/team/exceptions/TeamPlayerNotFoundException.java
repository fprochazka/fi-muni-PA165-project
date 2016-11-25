package cz.muni.fi.pa165.team.exceptions;

import java.util.UUID;

/**
 * @author Libor Mühlpachr <libor.muhl@seznam.cz>
 */
public class TeamPlayerNotFoundException extends RuntimeException
{

    private final UUID teamPlayerId;

    public TeamPlayerNotFoundException(final UUID teamPlayerId, Throwable cause)
    {
        super(String.format("TeamPlayer %s not found", teamPlayerId.toString()), cause);
        this.teamPlayerId = teamPlayerId;
    }

    public UUID getTeamPlayerId()
    {
        return teamPlayerId;
    }
}
