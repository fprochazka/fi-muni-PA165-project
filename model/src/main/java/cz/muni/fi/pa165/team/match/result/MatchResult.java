package cz.muni.fi.pa165.team.match.result;

import cz.muni.fi.pa165.team.match.TeamMatch;

/**
 * @author Tomas Smid <smid.thomas@gmail.com>
 */
public class MatchResult
{
    private final TeamMatch match;

    private final Long homeGoals;

    private final Long awayGoals;

    public MatchResult(TeamMatch match, Long homeGoals, Long awayGoals)
    {
        this.match = match;
        this.homeGoals = homeGoals;
        this.awayGoals = awayGoals;
    }

    public TeamMatch getMatch()
    {
        return match;
    }

    public Long getHomeGoals()
    {
        return homeGoals;
    }

    public Long getAwayGoals()
    {
        return awayGoals;
    }

}
