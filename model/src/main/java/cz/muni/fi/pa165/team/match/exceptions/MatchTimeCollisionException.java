package cz.muni.fi.pa165.team.match.exceptions;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * @author Tomas Smid <smid.thomas@gmail.com>
 */
public class MatchTimeCollisionException extends RuntimeException
{

    private final UUID matchPlannedToBeChangedId;

    private final UUID teamId;

    private final Date startTime;

    private final UUID collidingMatchId;

    public MatchTimeCollisionException(
        UUID matchPlannedToBeChangedId,
        UUID collidingMatchId,
        UUID teamId,
        Date startTime
    )
    {
        super(String.format("There cannot be changed the start time of the match %s with teamId %s to new value %s, " +
                "because there already exists another match %s with that same start time and same teamId",
            matchPlannedToBeChangedId.toString(),
            teamId.toString(),
            new SimpleDateFormat("dd/MM/YYYY hh:mm:ss").format(startTime),
            collidingMatchId));

        this.matchPlannedToBeChangedId = matchPlannedToBeChangedId;
        this.collidingMatchId = collidingMatchId;
        this.teamId = teamId;
        this.startTime = new Date(startTime.getTime());
    }

    public UUID getMatchPlannedToBeChangedId()
    {
        return matchPlannedToBeChangedId;
    }

    public UUID getCollidingMatchId()
    {
        return collidingMatchId;
    }

    public UUID getTeamId()
    {
        return teamId;
    }

    public Date getStartTime()
    {
        return new Date(startTime.getTime());
    }
}
