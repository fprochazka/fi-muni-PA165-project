package cz.muni.fi.pa165.team.match.exceptions;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * @author Tomas Smid <smid.thomas@gmail.com>
 */
public class MatchTimeCollisionException extends RuntimeException
{

    private final UUID matchPlannedToBeChangedId;

    private final UUID teamId;

    private final LocalDateTime startTime;

    private final UUID collidingMatchId;

    public MatchTimeCollisionException(
        UUID matchPlannedToBeChangedId,
        UUID collidingMatchId,
        UUID teamId,
        LocalDateTime startTime
    )
    {
        super(String.format("There cannot be changed the start time of the match %s with teamId %s to new value %s, " +
                "because there already exists another match %s with that same start time and same teamId",
            matchPlannedToBeChangedId.toString(),
            teamId.toString(),
            startTime.toString(),
            collidingMatchId));

        this.matchPlannedToBeChangedId = matchPlannedToBeChangedId;
        this.collidingMatchId = collidingMatchId;
        this.teamId = teamId;
        this.startTime = startTime;
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

    public LocalDateTime getStartTime()
    {
        return startTime;
    }
}
