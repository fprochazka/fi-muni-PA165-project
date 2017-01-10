package cz.muni.fi.pa165.team.match.goal;

import org.hibernate.validator.constraints.NotEmpty;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * @author Tomas Smid <smid.thomas@gmail.com>
 */
public class GoalRequest
{
    @NotEmpty
    private UUID scorerId;

    @NotEmpty
    private UUID assistantId;

    @NotEmpty
    private LocalDateTime matchTime;

    public UUID getScorerId()
    {
        return scorerId;
    }

    public void setScorerId(UUID scorerId)
    {
        this.scorerId = scorerId;
    }

    public UUID getAssistantId()
    {
        return assistantId;
    }

    public void setAssistantId(UUID assistantId)
    {
        this.assistantId = assistantId;
    }

    public LocalDateTime getMatchTime()
    {
        return matchTime;
    }

    public void setMatchTime(LocalDateTime matchTime)
    {
        this.matchTime = matchTime;
    }
}
