package cz.muni.fi.pa165.team.match.auxobjects;

import cz.muni.fi.pa165.team.TeamPlayer;
import cz.muni.fi.pa165.team.match.TeamMatchGoal;

import java.util.Collection;

/**
 * @author Tomas Smid <smid.thomas@gmail.com>
 */
public class MatchDetailView
{
    private final Collection<TeamMatchGoal> homeGoals;

    private final Collection<TeamMatchGoal> awayGoals;

    private final Collection<TeamPlayer> homeRoster;

    private final Collection<TeamPlayer> awayRoster;

    public MatchDetailView(
        Collection<TeamMatchGoal> homeGoals,
        Collection<TeamMatchGoal> awayGoals,
        Collection<TeamPlayer> homeRoster,
        Collection<TeamPlayer> awayRoster
    )
    {
        this.homeGoals = homeGoals;
        this.awayGoals = awayGoals;
        this.homeRoster = homeRoster;
        this.awayRoster = awayRoster;
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
