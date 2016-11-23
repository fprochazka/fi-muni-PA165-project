package cz.muni.fi.pa165.team.exceptions;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * @author Tomas Smid <smid.thomas@gmail.com>
 */
public class MatchTimeCollisionException extends RuntimeException
{
    private final UUID matchId;

    private final UUID team;

    private final Date startTime;

    public MatchTimeCollisionException(UUID matchId, UUID team, Date startTime)
    {
        super(String.format("There cannot be changed the start time of the match %s with team %s to new value %s, " +
                "because there already exists another match with that same start time and same team",
            matchId.toString(),
            team.toString(),
            new SimpleDateFormat("dd/MM/YYYY hh:mm:ss").format(startTime)));

        this.matchId = matchId;
        this.team = team;
        this.startTime = new Date(startTime.getTime());
    }

    public UUID getMatchId()
    {
        return matchId;
    }

    public UUID getTeam()
    {
        return team;
    }

    public Date getStartTime()
    {
        return new Date(startTime.getTime());
    }
}
