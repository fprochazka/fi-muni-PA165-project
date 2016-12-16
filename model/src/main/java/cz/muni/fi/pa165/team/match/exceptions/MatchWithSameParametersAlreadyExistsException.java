package cz.muni.fi.pa165.team.match.exceptions;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * @author Tomas Smid <smid.thomas@gmail.com>
 */
public class MatchWithSameParametersAlreadyExistsException extends RuntimeException
{

    private final UUID teamId;

    private final LocalDateTime startTime;

    public MatchWithSameParametersAlreadyExistsException(UUID teamId, LocalDateTime startTime)
    {
        super(String.format(
            "There already exists match with team %s with start time %s",
            teamId.toString(),
            startTime.toString()
        ));

        this.teamId = teamId;
        this.startTime = startTime;
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
