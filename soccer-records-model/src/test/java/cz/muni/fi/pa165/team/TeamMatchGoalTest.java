package cz.muni.fi.pa165.team;

import org.testng.annotations.Test;

import java.util.Date;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;

/**
 * @author Tomas Smid <smid.thomas@gmail.com>
 */
public class TeamMatchGoalTest
{
    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testCreatingGoalWithNullMatch(){
        Team team = new Team("Team");
        TeamMatchGoal teamMatchGoal = new TeamMatchGoal(
            new TeamPlayer("John", "Doe", 187, 90, team),
            new TeamPlayer("John", "Smith", 179, 84, team),
            null,
            new Date(1500)
        );
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testCreatingGoalWithNullScorer(){
        Team team = new Team("Team");
        TeamMatchGoal teamMatchGoal = new TeamMatchGoal(
            null,
            new TeamPlayer("John", "Smith", 179, 84, team),
            new TeamMatch(team,new Team("Team2"),new Date(0)),
            new Date(1500)
        );
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testCreatingGoalWithNullAssistant(){
        Team team = new Team("Team");
        TeamMatchGoal teamMatchGoal = new TeamMatchGoal(
            new TeamPlayer("John", "Smith", 179, 84, team),
            null,
            new TeamMatch(team,new Team("Team2"),new Date(0)),
            new Date(1500)
        );
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testCreatingGoalWithNullGoalMatchTime(){
        Team team = new Team("Team");
        TeamMatchGoal teamMatchGoal = new TeamMatchGoal(
            new TeamPlayer("John", "Doe", 187, 90, team),
            new TeamPlayer("John", "Smith", 179, 84, team),
            new TeamMatch(team,new Team("Team2"),new Date(0)),
            null
        );
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testCreatingGoalWithGoalMatchTimeBeforeMatchStartTime(){
        Team team = new Team("Team");
        TeamMatchGoal teamMatchGoal = new TeamMatchGoal(
            new TeamPlayer("John", "Doe", 187, 90, team),
            new TeamPlayer("John", "Smith", 179, 84, team),
            new TeamMatch(team,new Team("Team2"),new Date(1)),
            new Date(0)
        );
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testCreatingGoalWithGoalMatchTimeEqualToMatchStartTime(){
        Team team = new Team("Team");
        TeamMatchGoal teamMatchGoal = new TeamMatchGoal(
            new TeamPlayer("John", "Doe", 187, 90, team),
            new TeamPlayer("John", "Smith", 179, 84, team),
            new TeamMatch(team,new Team("Team2"),new Date(1)),
            new Date(1)
        );
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testCreatingGoalWithGoalMatchTimeAfterMatchEndTime(){
        Team team = new Team("Team");
        TeamMatchGoal teamMatchGoal = new TeamMatchGoal(
            new TeamPlayer("John", "Doe", 187, 90, team),
            new TeamPlayer("John", "Smith", 179, 84, team),
            new TeamMatch(team,new Team("Team2"),new Date(1), new Date(10000)),
            new Date(10001)
        );
    }
}
