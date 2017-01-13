package cz.muni.fi.pa165.team.result;

import cz.muni.fi.pa165.team.TeamPlayer;

/**
 * @author Denis Galajda <galajda.denis@gmail.com>
 */
public class TeamPlayerResult
{

    private TeamPlayer player;
    private int goals;
    private int assists;

    public TeamPlayerResult(TeamPlayer player, int goals, int assists)
    {
        this.player = player;
        this.goals = goals;
        this.assists = assists;
    }

    public TeamPlayer getPlayer()
    {
        return player;
    }

    public long getGoals()
    {
        return goals;
    }

    public long getAssists()
    {
        return assists;
    }
}
