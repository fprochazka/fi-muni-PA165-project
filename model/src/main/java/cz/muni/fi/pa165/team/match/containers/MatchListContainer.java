package cz.muni.fi.pa165.team.match.containers;

import cz.muni.fi.pa165.team.match.TeamMatch;

import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Map;

/**
 * @author Tomas Smid <smid.thomas@gmail.com>
 */
public class MatchListContainer
{
    private Collection<TeamMatch> matches;

    private Map<TeamMatch, Long> homeGoals;

    private Map<TeamMatch, Long> awayGoals;

    private DateTimeFormatter formatter;

    public Collection<TeamMatch> getMatches()
    {
        return matches;
    }

    public void setMatches(Collection<TeamMatch> matches)
    {
        this.matches = matches;
    }

    public Map<TeamMatch, Long> getHomeGoals()
    {
        return homeGoals;
    }

    public void setHomeGoals(Map<TeamMatch, Long> homeGoals)
    {
        this.homeGoals = homeGoals;
    }

    public Map<TeamMatch, Long> getAwayGoals()
    {
        return awayGoals;
    }

    public void setAwayGoals(Map<TeamMatch, Long> awayGoals)
    {
        this.awayGoals = awayGoals;
    }

    public DateTimeFormatter getFormatter()
    {
        return formatter;
    }

    public void setFormatter(DateTimeFormatter formatter)
    {
        this.formatter = formatter;
    }
}
