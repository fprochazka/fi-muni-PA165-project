package cz.muni.fi.pa165.team.match.exceptions;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * @author Tomas Smid <smid.thomas@gmail.com>
 */
public class GoalWithSameParametersAlreadyExistsException extends RuntimeException
{

    private final UUID scorerId;

    private final UUID assistantId;

    private final UUID matchId;

    private final LocalDateTime matchTime;

    public GoalWithSameParametersAlreadyExistsException(
        UUID scorerId,
        UUID assistantId,
        UUID matchId,
        LocalDateTime matchTime
    )
    {
        super(String.format(
            "Goal from match %s scored by %s with assistant %s and scored in match time %s already exists",
            matchId.toString(),
            scorerId.toString(),
            assistantId.toString(),
            matchTime.toString()
        ));

        this.scorerId = scorerId;
        this.assistantId = assistantId;
        this.matchId = matchId;
        this.matchTime = matchTime;
    }

    public UUID getScorerId()
    {
        return scorerId;
    }

    public UUID getAssistantId()
    {
        return assistantId;
    }

    public UUID getMatchId()
    {
        return matchId;
    }

    public LocalDateTime getMatchTime()
    {
        return matchTime;
    }
}
