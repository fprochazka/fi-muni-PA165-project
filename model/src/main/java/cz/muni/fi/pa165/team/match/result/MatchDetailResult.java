package cz.muni.fi.pa165.team.match.result;

import cz.muni.fi.pa165.team.TeamPlayer;
import cz.muni.fi.pa165.team.match.TeamMatchGoal;

import java.util.Collection;
import java.util.Collections;

/**
 * @author Tomas Smid <smid.thomas@gmail.com>
 */
public class MatchDetailResult
{

    private final Collection<TeamMatchGoal> homeGoals;

    private final Collection<TeamMatchGoal> awayGoals;

    private final Collection<TeamPlayer> homeRoster;

    private final Collection<TeamPlayer> awayRoster;

    public MatchDetailResult(
        Collection<TeamMatchGoal> homeGoals,
        Collection<TeamMatchGoal> awayGoals,
        Collection<TeamPlayer> homeRoster,
        Collection<TeamPlayer> awayRoster
    )
    {
        this.homeGoals = Collections.unmodifiableCollection(homeGoals);
        this.awayGoals = Collections.unmodifiableCollection(awayGoals);
        this.homeRoster = Collections.unmodifiableCollection(homeRoster);
        this.awayRoster = Collections.unmodifiableCollection(awayRoster);
    }

    public Collection<TeamMatchGoal> getHomeGoals()
    {
        return homeGoals;
    }

    public Collection<TeamMatchGoal> getAwayGoals()
    {
        return awayGoals;
    }

    public Collection<TeamPlayer> getHomeRoster()
    {
        return homeRoster;
    }

    public Collection<TeamPlayer> getAwayRoster()
    {
        return awayRoster;
    }
}
