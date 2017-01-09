package cz.muni.fi.pa165.team.match;

import org.hibernate.validator.constraints.NotEmpty;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * @author Tomas Smid <smid.thomas@gmail.com>
 */
public class MatchRequest
{
    @NotEmpty
    public UUID homeTeamId;

    @NotEmpty
    public UUID awayTeamId;

    @NotEmpty
    public LocalDateTime startTime;

    @NotEmpty
    public LocalDateTime endTime;

    public UUID getHomeTeamId()
    {
        return homeTeamId;
    }

    public void setHomeTeamId(UUID homeTeamId)
    {
        this.homeTeamId = homeTeamId;
    }

    public UUID getAwayTeamId()
    {
        return awayTeamId;
    }

    public void setAwayTeamId(UUID awayTeamId)
    {
        this.awayTeamId = awayTeamId;
    }

    public LocalDateTime getStartTime()
    {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime)
    {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime()
    {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime)
    {
        this.endTime = endTime;
    }
}
