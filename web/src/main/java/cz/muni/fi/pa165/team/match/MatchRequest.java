package cz.muni.fi.pa165.team.match;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * @author Tomas Smid <smid.thomas@gmail.com>
 */
public class MatchRequest
{
    @NotEmpty
    public String homeTeamId;

    @NotEmpty
    public String awayTeamId;

    @NotEmpty
    public String startTime;

    @NotEmpty
    public String endTime;

    public String getHomeTeamId()
    {
        return homeTeamId;
    }

    public void setHomeTeamId(String homeTeamId)
    {
        this.homeTeamId = homeTeamId;
    }

    public String getAwayTeamId()
    {
        return awayTeamId;
    }

    public void setAwayTeamId(String awayTeamId)
    {
        this.awayTeamId = awayTeamId;
    }

    public String getStartTime()
    {
        return startTime;
    }

    public void setStartTime(String startTime)
    {
        this.startTime = startTime;
    }

    public String getEndTime()
    {
        return endTime;
    }

    public void setEndTime(String endTime)
    {
        this.endTime = endTime;
    }
}
