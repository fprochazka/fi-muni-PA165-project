package cz.muni.fi.pa165.team.exceptions;

import java.util.UUID;

/**
 * @author Denis Galajda <galajda.denis@gmail.com>
 */
public class TeamNotFoundException extends RuntimeException
{

    private final UUID teamId;

    public TeamNotFoundException(final UUID teamId, Throwable cause)
    {
        super(String.format("Team %s not found", teamId.toString()), cause);
        this.teamId = teamId;
    }

    public UUID getTeamId()
    {
        return teamId;
    }
}
