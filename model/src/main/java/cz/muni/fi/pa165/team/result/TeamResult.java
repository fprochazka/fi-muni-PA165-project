package cz.muni.fi.pa165.team.result;

import cz.muni.fi.pa165.team.Team;

/**
 * @author Denis Galajda <galajda.denis@gmail.com>
 */
public class TeamResult
{

    private Team team;
    private int matchesPlayedCnt;
    private int winsCnt;
    private int lossesCnt;
    private int drawsCnt;
    private int goalsScoredCnt;
    private int goalsConcededCnt;

    public TeamResult(Team team, int matchesPlayedCnt, int winsCnt, int lossesCnt, int drawsCnt, int goalsScoredCnt, int goalsConcededCnt)
    {
        this.team = team;
        this.matchesPlayedCnt = matchesPlayedCnt;
        this.winsCnt = winsCnt;
        this.lossesCnt = lossesCnt;
        this.drawsCnt = drawsCnt;
        this.goalsScoredCnt = goalsScoredCnt;
        this.goalsConcededCnt = goalsConcededCnt;
    }

    public Team getTeam()
    {
        return team;
    }

    public int getMatchesPlayedCnt()
    {
        return matchesPlayedCnt;
    }

    public int getWinsCnt()
    {
        return winsCnt;
    }

    public int getLossesCnt()
    {
        return lossesCnt;
    }

    public int getDrawsCnt()
    {
        return drawsCnt;
    }

    public int getGoalsScoredCnt()
    {
        return goalsScoredCnt;
    }

    public int getGoalsConcededCnt()
    {
        return goalsConcededCnt;
    }
}
