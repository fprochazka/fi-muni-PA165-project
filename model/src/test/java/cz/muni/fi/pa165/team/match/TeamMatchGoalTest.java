package cz.muni.fi.pa165.team.match;

import cz.muni.fi.pa165.team.Team;
import cz.muni.fi.pa165.team.TeamPlayer;
import org.testng.annotations.Test;

import java.util.Date;

/**
 * @author Tomas Smid <smid.thomas@gmail.com>
 */
public class TeamMatchGoalTest
{

    @Test(expectedExceptions = { IllegalArgumentException.class },
          expectedExceptionsMessageRegExp = "Cannot create goal for a null match")
    public void testCreatingGoalWithNullMatch()
    {
        Team team = new Team("Team");
        TeamMatchGoal teamMatchGoal = new TeamMatchGoal(
            new TeamPlayer("John", "Doe", 187, 90, team),
            new TeamPlayer("John", "Smith", 179, 84, team),
            null,
            new Date(1500)
        );
    }

    @Test(expectedExceptions = { IllegalArgumentException.class },
          expectedExceptionsMessageRegExp = "Cannot create goal for a null scorer")
    public void testCreatingGoalWithNullScorer()
    {
        Team team = new Team("Team");
        TeamMatchGoal teamMatchGoal = new TeamMatchGoal(
            null,
            new TeamPlayer("John", "Smith", 179, 84, team),
            new TeamMatch(team, new Team("Team2"), new Date(0)),
            new Date(1500)
        );
    }

    @Test(expectedExceptions = { IllegalArgumentException.class },
          expectedExceptionsMessageRegExp = "Cannot create goal for a null assistant")
    public void testCreatingGoalWithNullAssistant()
    {
        Team team = new Team("Team");
        TeamMatchGoal teamMatchGoal = new TeamMatchGoal(
            new TeamPlayer("John", "Smith", 179, 84, team),
            null,
            new TeamMatch(team, new Team("Team2"), new Date(0)),
            new Date(1500)
        );
    }

    @Test(expectedExceptions = { IllegalArgumentException.class },
          expectedExceptionsMessageRegExp = "Given a null goal match time")
    public void testCreatingGoalWithNullGoalMatchTime()
    {
        Team team = new Team("Team");
        TeamMatchGoal teamMatchGoal = new TeamMatchGoal(
            new TeamPlayer("John", "Doe", 187, 90, team),
            new TeamPlayer("John", "Smith", 179, 84, team),
            new TeamMatch(team, new Team("Team2"), new Date(0)),
            null
        );
    }

    @Test(expectedExceptions = { IllegalArgumentException.class },
          expectedExceptionsMessageRegExp = "Goal match time is not after match start time")
    public void testCreatingGoalWithGoalMatchTimeBeforeMatchStartTime()
    {
        Team team = new Team("Team");
        TeamMatchGoal teamMatchGoal = new TeamMatchGoal(
            new TeamPlayer("John", "Doe", 187, 90, team),
            new TeamPlayer("John", "Smith", 179, 84, team),
            new TeamMatch(team, new Team("Team2"), new Date(1)),
            new Date(0)
        );
    }

    @Test(expectedExceptions = { IllegalArgumentException.class },
          expectedExceptionsMessageRegExp = "Goal match time is not after match start time")
    public void testCreatingGoalWithGoalMatchTimeEqualToMatchStartTime()
    {
        Team team = new Team("Team");
        TeamMatchGoal teamMatchGoal = new TeamMatchGoal(
            new TeamPlayer("John", "Doe", 187, 90, team),
            new TeamPlayer("John", "Smith", 179, 84, team),
            new TeamMatch(team, new Team("Team2"), new Date(1)),
            new Date(1)
        );
    }

    @Test(expectedExceptions = { IllegalArgumentException.class },
          expectedExceptionsMessageRegExp = "Goal match time is not before match end time")
    public void testCreatingGoalWithGoalMatchTimeAfterMatchEndTime()
    {
        Team team = new Team("Team");
        TeamMatchGoal teamMatchGoal = new TeamMatchGoal(
            new TeamPlayer("John", "Doe", 187, 90, team),
            new TeamPlayer("John", "Smith", 179, 84, team),
            new TeamMatch(team, new Team("Team2"), new Date(1), new Date(10000)),
            new Date(10001)
        );
    }
}
