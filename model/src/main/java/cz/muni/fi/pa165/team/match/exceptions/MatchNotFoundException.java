package cz.muni.fi.pa165.team.match.exceptions;

import java.util.UUID;

/**
 * @author Tomas Smid <smid.thomas@gmail.com>
 */
public class MatchNotFoundException extends RuntimeException
{

    private final UUID matchId;

    public MatchNotFoundException(final UUID matchId, Throwable cause)
    {
        super(String.format("Match %s not found", matchId.toString()), cause);
        this.matchId = matchId;
    }

    public UUID getMatchId()
    {
        return matchId;
    }
}
