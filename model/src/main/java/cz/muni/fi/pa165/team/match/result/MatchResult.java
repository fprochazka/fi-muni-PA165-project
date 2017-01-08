package cz.muni.fi.pa165.team.match.result;

import cz.muni.fi.pa165.team.match.TeamMatch;

/**
 * @author Tomas Smid <smid.thomas@gmail.com>
 */
public class MatchResult
{
    private TeamMatch match;

    private Long homeGoals;

    private Long awayGoals;

    public TeamMatch getMatch()
    {
        return match;
    }

    public void setMatch(TeamMatch match)
    {
        this.match = match;
    }

    public Long getHomeGoals()
    {
        return homeGoals;
    }

    public void setHomeGoals(Long homeGoals)
    {
        this.homeGoals = homeGoals;
    }

    public Long getAwayGoals()
    {
        return awayGoals;
    }

    public void setAwayGoals(Long awayGoals)
    {
        this.awayGoals = awayGoals;
    }
}
