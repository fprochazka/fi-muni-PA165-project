package cz.muni.fi.pa165.team.exceptions;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * @author Tomas Smid <smid.thomas@gmail.com>
 */
public class GoalWithSameParametersAlreadyExistsException extends RuntimeException
{

    private final UUID scorerId;

    private final UUID assistantId;

    private final UUID matchId;

    private final Date matchTime;

    public GoalWithSameParametersAlreadyExistsException(
        UUID scorerId,
        UUID assistantId,
        UUID matchId,
        Date matchTime
    )
    {
        super(String.format("Goal from match %s scored by %s with assistant %s and scored in match time %s already exists",
            matchId.toString(),
            scorerId.toString(),
            assistantId.toString(),
            new SimpleDateFormat("dd/MM/YYYY hh:mm:ss").format(matchTime)));

        this.scorerId = scorerId;
        this.assistantId = assistantId;
        this.matchId = matchId;
        this.matchTime = new Date(matchTime.getTime());
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

    public Date getMatchTime()
    {
        return new Date(matchTime.getTime());
    }
}
