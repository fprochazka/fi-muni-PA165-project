package cz.muni.fi.pa165.team.match;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * @author Tomas Smid <smid.thomas@gmail.com>
 */
public class MatchRequest
{
    @NotEmpty
    public String homeTeam;

    @NotEmpty
    public String awayTeam;

    @NotEmpty
    public String startTime;

    @NotEmpty
    public String endTime;

    public String getTarget()
    {
        return target;
    }

    public void setTarget(String target)
    {
        this.target = target;
    }

    public String target;

    public String getHomeTeam()
    {
        return homeTeam;
    }

    public void setHomeTeam(String homeTeam)
    {
        this.homeTeam = homeTeam;
    }

    public String getAwayTeam()
    {
        return awayTeam;
    }

    public void setAwayTeam(String awayTeam)
    {
        this.awayTeam = awayTeam;
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
