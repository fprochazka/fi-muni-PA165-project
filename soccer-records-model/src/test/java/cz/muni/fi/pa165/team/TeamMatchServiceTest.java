package cz.muni.fi.pa165.team;

import cz.muni.fi.pa165.team.exceptions.GoalWithSameParametersAlreadyExistsException;
import cz.muni.fi.pa165.team.exceptions.MatchTimeCollisionException;
import cz.muni.fi.pa165.team.exceptions.MatchWithSameParametersAlreadyExistsException;
import org.testng.annotations.Test;

import java.util.Date;

import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.fail;

/**
 * @author Tomas Smid <smid.thomas@gmail.com>
 */
public class TeamMatchServiceTest
{

    @Test
    public void testCreateMatch()
    {
        TeamMatchService teamMatchService = new TeamMatchService();
        long time = System.currentTimeMillis();
        Date startTime = new Date(time + 50000);
        Date endTime = new Date(time + 5550000);
        Team team1 = new Team("Team1");
        Team team2 = new Team("Team2");

        TeamMatch newMatch = teamMatchService.createMatch(
            null,
            null,
            team1,
            team2,
            startTime,
            endTime
        );

        assertNotNull(newMatch);
        assertEquals(team1.getId(), newMatch.getHomeTeam().getId());
        assertEquals(team2.getId(), newMatch.getAwayTeam().getId());
        assertEquals(startTime, newMatch.getStartTime());
        assertEquals(endTime, newMatch.getEndTime());
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testCreateMatchWithNullAwayTeam()
    {
        TeamMatchService teamMatchService = new TeamMatchService();
        long time = System.currentTimeMillis();
        Date startTime = new Date(time + 50000);
        Date endTime = new Date(time + 5550000);
        Team team1 = new Team("Team1");

        TeamMatch newMatch = teamMatchService.createMatch(
            null,
            null,
            team1,
            null,
            startTime,
            endTime
        );
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testCreateMatchWithNullHomeTeam()
    {
        TeamMatchService teamMatchService = new TeamMatchService();
        long time = System.currentTimeMillis();
        Date startTime = new Date(time + 50000);
        Date endTime = new Date(time + 5550000);
        Team team1 = new Team("Team1");

        TeamMatch newMatch = teamMatchService.createMatch(
            null,
            null,
            null,
            team1,
            startTime,
            endTime
        );
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testCreateMatchWithSameHomeAndAwayTeam()
    {
        TeamMatchService teamMatchService = new TeamMatchService();
        long time = System.currentTimeMillis();
        Date startTime = new Date(time + 50000);
        Date endTime = new Date(time + 5550000);
        Team team1 = new Team("Team1");

        TeamMatch newMatch = teamMatchService.createMatch(
            null,
            null,
            team1,
            team1,
            startTime,
            endTime
        );
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testCreateMatchWithNullStartTime()
    {
        TeamMatchService teamMatchService = new TeamMatchService();
        long time = System.currentTimeMillis();
        Date endTime = new Date(time + 5500000);
        Team team1 = new Team("Team1");
        Team team2 = new Team("Team2");

        TeamMatch newMatch = teamMatchService.createMatch(
            null,
            null,
            team1,
            team2,
            null,
            endTime
        );
    }

    @Test
    public void testCreateMatchWithNullEndTime()
    {
        TeamMatchService teamMatchService = new TeamMatchService();
        long time = System.currentTimeMillis();
        Date startTime = new Date(time);
        Team team1 = new Team("Team1");
        Team team2 = new Team("Team2");

        TeamMatch newMatch = teamMatchService.createMatch(
            null,
            null,
            team1,
            team2,
            startTime,
            null
        );

        assertNotNull(newMatch);
        assertEquals(team1.getId(), newMatch.getHomeTeam().getId());
        assertEquals(team2.getId(), newMatch.getAwayTeam().getId());
        assertEquals(startTime, newMatch.getStartTime());
        assertNull(newMatch.getEndTime());
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testCreateMatchWithEndTimeBeforeStartTime()
    {
        TeamMatchService teamMatchService = new TeamMatchService();
        long time = System.currentTimeMillis();
        Date startTime = new Date(time + 50000);
        Date endTime = new Date(time + 49999);
        Team team1 = new Team("Team1");
        Team team2 = new Team("Team2");

        TeamMatch newMatch = teamMatchService.createMatch(
            null,
            null,
            team1,
            team2,
            startTime,
            endTime
        );
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testCreateMatchWithStartTimeEqualToEndTime()
    {
        TeamMatchService teamMatchService = new TeamMatchService();
        long time = System.currentTimeMillis();
        Date startTime = new Date(time + 50000);
        Date endTime = new Date(time + 50000);
        Team team1 = new Team("Team1");
        Team team2 = new Team("Team2");

        TeamMatch newMatch = teamMatchService.createMatch(
            null,
            null,
            team1,
            team2,
            startTime,
            endTime
        );
    }

    @Test
    public void testCreateMatchWithConflictingMatchForHomeTeam()
    {
        TeamMatchService teamMatchService = new TeamMatchService();
        long time = System.currentTimeMillis();
        Date startTime = new Date(time);
        Team team1 = new Team("Team1");
        Team team2 = new Team("Team2");
        Team team3 = new Team("Team3");
        TeamMatch match = new TeamMatch(team3, team1, startTime);

        try {
            TeamMatch newMatch = teamMatchService.createMatch(
                match,
                null,
                team1,
                team2,
                startTime,
                null
            );
            fail("Expected exception 'MatchWithSameParametersAlreadyExistsException'");
        } catch (MatchWithSameParametersAlreadyExistsException ex) {
            assertEquals(startTime, ex.getStartTime());
            assertEquals(team1.getId(), ex.getTeamId());
        }
    }

    @Test
    public void testCreateMatchWithConflictingMatchForAwayTeam()
    {
        TeamMatchService teamMatchService = new TeamMatchService();
        long time = System.currentTimeMillis();
        Date startTime = new Date(time);
        Team team1 = new Team("Team1");
        Team team2 = new Team("Team2");
        Team team3 = new Team("Team3");
        TeamMatch match = new TeamMatch(team1, team2, startTime);

        try {
            teamMatchService.createMatch(
                null,
                match,
                team3,
                team2,
                startTime,
                null
            );
            fail("Expected exception 'MatchWithSameParametersAlreadyExistsException'");
        } catch (MatchWithSameParametersAlreadyExistsException ex) {
            assertEquals(startTime, ex.getStartTime());
            assertEquals(team2.getId(), ex.getTeamId());
        }
    }

    @Test
    public void testChangeMatchTime()
    {
        TeamMatchService teamMatchService = new TeamMatchService();
        long time = System.currentTimeMillis();
        Date startTime1 = new Date(time);
        Date startTime2 = new Date(time + 50000);
        Date endTime1 = new Date(time + 5500000);
        Date endTime2 = new Date(time + 5550000);
        Team team1 = new Team("Team1");
        Team team2 = new Team("Team2");
        TeamMatch match = new TeamMatch(team1, team2, startTime1, endTime1);

        teamMatchService.changeMatchTime(
            null,
            null,
            match,
            startTime2,
            endTime2
        );

        assertEquals(startTime2, match.getStartTime());
        assertEquals(endTime2, match.getEndTime());
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testChangeMatchTimeWithNullMatch()
    {
        TeamMatchService teamMatchService = new TeamMatchService();
        long time = System.currentTimeMillis();
        Date startTime = new Date(time + 50000);
        Date endTime = new Date(time + 5500000);

        teamMatchService.changeMatchTime(
            null,
            null,
            null,
            startTime,
            endTime
        );
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testChangeMatchTimeWithNullStartTime()
    {
        TeamMatchService teamMatchService = new TeamMatchService();
        long time = System.currentTimeMillis();
        Date startTime = new Date(time);
        Date endTime = new Date(time + 5550000);
        Team team3 = new Team("Team3");
        Team team4 = new Team("Team4");
        TeamMatch match2 = new TeamMatch(team3, team4, startTime);

        teamMatchService.changeMatchTime(
            null,
            null,
            match2,
            null,
            endTime
        );
    }

    @Test
    public void testChangeMatchTimeWithNullEndTime()
    {
        TeamMatchService teamMatchService = new TeamMatchService();
        long time = System.currentTimeMillis();
        Date startTime1 = new Date(time);
        Date startTime2 = new Date(time + 50000);
        Date endTime = new Date(time + 5500000);
        Team team1 = new Team("Team1");
        Team team2 = new Team("Team2");
        TeamMatch match = new TeamMatch(team1, team2, startTime1, endTime);

        teamMatchService.changeMatchTime(
            null,
            null,
            match,
            startTime2,
            null
        );

        assertEquals(startTime2, match.getStartTime());
        assertNull(match.getEndTime());
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testChangeMatchTimeWithEndTimeBeforeStartTime()
    {
        TeamMatchService teamMatchService = new TeamMatchService();
        long time = System.currentTimeMillis();
        Date startTime1 = new Date(time);
        Date startTime2 = new Date(time + 50000);
        Date endTime1 = new Date(time + 5500000);
        Date endTime2 = new Date(time + 49999);
        Team team1 = new Team("Team1");
        Team team2 = new Team("Team2");
        TeamMatch match = new TeamMatch(team1, team2, startTime1, endTime1);

        teamMatchService.changeMatchTime(
            null,
            null,
            match,
            startTime2,
            endTime2
        );
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testChangeMatchTimeWithEndTimeEqualToStartTime()
    {
        TeamMatchService teamMatchService = new TeamMatchService();
        long time = System.currentTimeMillis();
        Date startTime1 = new Date(time);
        Date startTime2 = new Date(time + 50000);
        Date endTime1 = new Date(time + 5500000);
        Date endTime2 = new Date(time + 50000);
        Team team1 = new Team("Team1");
        Team team2 = new Team("Team2");
        TeamMatch match = new TeamMatch(team1, team2, startTime1, endTime1);

        teamMatchService.changeMatchTime(
            null,
            null,
            match,
            startTime2,
            endTime2
        );
    }

    @Test
    public void testChangeMatchTimeWithConflictingMatchForMatchHomeTeam()
    {
        TeamMatchService teamMatchService = new TeamMatchService();
        long time = System.currentTimeMillis();
        Date startTime1 = new Date(time);
        Date startTime2 = new Date(time + 50000);
        Date endTime1 = new Date(time + 5500000);
        Date endTime2 = new Date(time + 5550000);
        Team team1 = new Team("Team1");
        Team team2 = new Team("Team2");
        Team team3 = new Team("Team3");
        TeamMatch match1 = new TeamMatch(team1, team2, startTime2, endTime1);
        TeamMatch match2 = new TeamMatch(team1, team3, startTime1);

        try {
            teamMatchService.changeMatchTime(
                match1,
                null,
                match2,
                startTime2,
                endTime2
            );
            fail("Expected exception MatchTimeCollisionException");
        } catch (MatchTimeCollisionException ex) {
            assertEquals(match2.getId(), ex.getMatchPlannedToBeChangedId());
            assertEquals(match1.getId(), ex.getCollidingMatchId());
            assertEquals(startTime2, ex.getStartTime());
            assertEquals(team1.getId(), ex.getTeamId());
        }
    }

    @Test
    public void testChangeMatchTimeWithConflictingMatchForMatchAwayTeam()
    {
        TeamMatchService teamMatchService = new TeamMatchService();
        long time = System.currentTimeMillis();
        Date startTime1 = new Date(time);
        Date startTime2 = new Date(time + 50000);
        Date endTime1 = new Date(time + 5500000);
        Date endTime2 = new Date(time + 5550000);
        Team team1 = new Team("Team1");
        Team team2 = new Team("Team2");
        Team team3 = new Team("Team3");
        TeamMatch match1 = new TeamMatch(team1, team2, startTime2, endTime1);
        TeamMatch match2 = new TeamMatch(team3, team1, startTime1);

        try {
            teamMatchService.changeMatchTime(
                null,
                match1,
                match2,
                startTime2,
                endTime2
            );
            fail("Expected exception MatchTimeCollisionException");
        } catch (MatchTimeCollisionException ex) {
            assertEquals(match2.getId(), ex.getMatchPlannedToBeChangedId());
            assertEquals(match1.getId(), ex.getCollidingMatchId());
            assertEquals(startTime2, ex.getStartTime());
            assertEquals(team1.getId(), ex.getTeamId());
        }
    }

    @Test
    public void testEndMatch()
    {
        TeamMatchService teamMatchService = new TeamMatchService();
        long time = System.currentTimeMillis();
        Date startTime = new Date(time);
        Date endTime = new Date(time + 5000000);
        Team team1 = new Team("Team1");
        Team team2 = new Team("Team2");
        TeamMatch match = new TeamMatch(team1, team2, startTime);

        assertNull(match.getEndTime());

        teamMatchService.endMatch(match, endTime);

        assertEquals(endTime, match.getEndTime());
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testEndMatchWithNullMatch()
    {
        TeamMatchService teamMatchService = new TeamMatchService();
        long time = System.currentTimeMillis();
        Date endTime = new Date(time + 5000000);

        teamMatchService.endMatch(null, endTime);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testEndMatchWithNullEndTime()
    {
        TeamMatchService teamMatchService = new TeamMatchService();
        long time = System.currentTimeMillis();
        Date startTime = new Date(time);
        Team team1 = new Team("Team1");
        Team team2 = new Team("Team2");
        TeamMatch match = new TeamMatch(team1, team2, startTime);

        teamMatchService.endMatch(match, null);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testEndMatchWithAlreadyEndedMatch()
    {
        TeamMatchService teamMatchService = new TeamMatchService();
        long time = System.currentTimeMillis();
        Date startTime = new Date(time);
        Date endTime = new Date(time + 5000000);
        Date endTime2 = new Date(time + 1);
        Team team1 = new Team("Team1");
        Team team2 = new Team("Team2");
        TeamMatch match = new TeamMatch(team1, team2, startTime, endTime);

        teamMatchService.endMatch(match, endTime2);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testEndMatchWithEndTimeBeforeStartTime()
    {
        TeamMatchService teamMatchService = new TeamMatchService();
        long time = System.currentTimeMillis();
        Date startTime = new Date(time);
        Date endTime = new Date(time - 1);
        Team team1 = new Team("Team1");
        Team team2 = new Team("Team2");
        TeamMatch match = new TeamMatch(team1, team2, startTime);

        teamMatchService.endMatch(match, endTime);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testEndMatchWithEndTimeEqualToStartTime()
    {
        TeamMatchService teamMatchService = new TeamMatchService();
        long time = System.currentTimeMillis();
        Date startTime = new Date(time);
        Date endTime = new Date(time);
        Team team1 = new Team("Team1");
        Team team2 = new Team("Team2");
        TeamMatch match = new TeamMatch(team1, team2, startTime);

        teamMatchService.endMatch(match, endTime);
    }

    @Test
    public void testAddNewScoredGoal()
    {
        TeamMatchService teamMatchService = new TeamMatchService();
        long time = System.currentTimeMillis();
        Date startTime = new Date(time);
        Date goalMatchTime = new Date(time + 15000);
        Team homeTeam = new Team("HomeTeam");
        Team awayTeam = new Team("AwayTeam");
        TeamMatch match = new TeamMatch(homeTeam, awayTeam, startTime);
        TeamPlayer scorer = new TeamPlayer("John", "Doe", 187, 90, homeTeam);
        TeamPlayer assistant = new TeamPlayer("Jack", "Smith", 178, 84, homeTeam);

        TeamMatchGoal newGoal = teamMatchService.addNewScoredGoal(
            scorer,
            assistant,
            match,
            goalMatchTime,
            null
        );

        assertNotNull(newGoal);
        assertEquals(scorer.getId(), newGoal.getScorer().getId());
        assertEquals(assistant.getId(), newGoal.getAssistant().getId());
        assertEquals(match.getId(), newGoal.getMatch().getId());
        assertEquals(goalMatchTime, newGoal.getMatchTime());
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testAddNewScoredGoalWithNullScorer()
    {
        TeamMatchService teamMatchService = new TeamMatchService();
        long time = System.currentTimeMillis();
        Date startTime = new Date(time);
        Date goalMatchTime = new Date(time + 15000);
        Team homeTeam = new Team("HomeTeam");
        Team awayTeam = new Team("AwayTeam");
        TeamMatch match = new TeamMatch(homeTeam, awayTeam, startTime);
        TeamPlayer assistant = new TeamPlayer("Alex", "Collina", 187, 90, homeTeam);

        TeamMatchGoal newGoal = teamMatchService.addNewScoredGoal(
            null,
            assistant,
            match,
            goalMatchTime,
            null
        );
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testAddNewScoredGoalWithNullAssistant()
    {
        TeamMatchService teamMatchService = new TeamMatchService();
        long time = System.currentTimeMillis();
        Date startTime = new Date(time);
        Date goalMatchTime = new Date(time + 15000);
        Team homeTeam = new Team("HomeTeam");
        Team awayTeam = new Team("AwayTeam");
        TeamMatch match = new TeamMatch(homeTeam, awayTeam, startTime);
        TeamPlayer scorer = new TeamPlayer("John", "Doe", 187, 90, homeTeam);

        TeamMatchGoal newGoal = teamMatchService.addNewScoredGoal(
            scorer,
            null,
            match,
            goalMatchTime,
            null
        );
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testAddNewScoredGoalWithSameScorerAndAssistant()
    {
        TeamMatchService teamMatchService = new TeamMatchService();
        long time = System.currentTimeMillis();
        Date startTime = new Date(time);
        Date goalMatchTime = new Date(time + 15000);
        Team homeTeam = new Team("HomeTeam");
        Team awayTeam = new Team("AwayTeam");
        TeamMatch match = new TeamMatch(homeTeam, awayTeam, startTime);
        TeamPlayer player = new TeamPlayer("John", "Doe", 187, 90, homeTeam);

        TeamMatchGoal newGoal = teamMatchService.addNewScoredGoal(
            player,
            player,
            match,
            goalMatchTime,
            null
        );
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testAddNewScoredGoalWithNullGoalMatchTime()
    {
        TeamMatchService teamMatchService = new TeamMatchService();
        long time = System.currentTimeMillis();
        Date startTime = new Date(time);
        Team homeTeam = new Team("HomeTeam");
        Team awayTeam = new Team("AwayTeam");
        TeamMatch match = new TeamMatch(homeTeam, awayTeam, startTime);
        TeamPlayer scorer = new TeamPlayer("John", "Doe", 187, 90, homeTeam);
        TeamPlayer assistant = new TeamPlayer("Alex", "Collina", 187, 90, homeTeam);

        TeamMatchGoal newGoal = teamMatchService.addNewScoredGoal(
            scorer,
            assistant,
            match,
            null,
            null
        );
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testAddNewScoredGoalWithGoalMatchTimeBeforeMatchStartTime()
    {
        TeamMatchService teamMatchService = new TeamMatchService();
        long time = System.currentTimeMillis();
        Date startTime = new Date(time);
        Date goalMatchTime = new Date(time - 1);
        Team homeTeam = new Team("HomeTeam");
        Team awayTeam = new Team("AwayTeam");
        TeamMatch match = new TeamMatch(homeTeam, awayTeam, startTime);
        TeamPlayer scorer = new TeamPlayer("John", "Doe", 187, 90, homeTeam);
        TeamPlayer assistant = new TeamPlayer("Radan", "Spock", 186, 78, homeTeam);

        TeamMatchGoal newGoal = teamMatchService.addNewScoredGoal(
            scorer,
            assistant,
            match,
            goalMatchTime,
            null
        );
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testAddNewScoredGoalWithGoalMatchTimeEqualToMatchStartTime()
    {
        TeamMatchService teamMatchService = new TeamMatchService();
        long time = System.currentTimeMillis();
        Date startTime = new Date(time);
        Date goalMatchTime = new Date(time);
        Team homeTeam = new Team("HomeTeam");
        Team awayTeam = new Team("AwayTeam");
        TeamMatch match = new TeamMatch(homeTeam, awayTeam, startTime);
        TeamPlayer scorer = new TeamPlayer("John", "Doe", 187, 90, homeTeam);
        TeamPlayer assistant = new TeamPlayer("Radan", "Spock", 186, 78, homeTeam);

        TeamMatchGoal newGoal = teamMatchService.addNewScoredGoal(
            scorer,
            assistant,
            match,
            goalMatchTime,
            null
        );
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testAddNewScoredGoalWithGoalMatchTimeAfterMatchEndTime()
    {
        TeamMatchService teamMatchService = new TeamMatchService();
        long time = System.currentTimeMillis();
        Date startTime = new Date(time);
        Date endTime = new Date(time + 5500000);
        Date goalMatchTime = new Date(time + 5500001);
        Team homeTeam = new Team("HomeTeam");
        Team awayTeam = new Team("AwayTeam");
        TeamMatch match = new TeamMatch(homeTeam, awayTeam, startTime, endTime);
        TeamPlayer scorer = new TeamPlayer("John", "Doe", 187, 90, homeTeam);
        TeamPlayer assistant = new TeamPlayer("Alex", "Collina", 187, 90, homeTeam);

        TeamMatchGoal newGoal = teamMatchService.addNewScoredGoal(
            scorer,
            assistant,
            match,
            goalMatchTime,
            null
        );
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testAddNewScoredGoalWithGoalMatchTimeEqualToMatchEndTime()
    {
        TeamMatchService teamMatchService = new TeamMatchService();
        long time = System.currentTimeMillis();
        Date startTime = new Date(time);
        Date endTime = new Date(time + 5500000);
        Date goalMatchTime = new Date(time + 5500000);
        Team homeTeam = new Team("HomeTeam");
        Team awayTeam = new Team("AwayTeam");
        TeamMatch match = new TeamMatch(homeTeam, awayTeam, startTime, endTime);
        TeamPlayer scorer = new TeamPlayer("John", "Doe", 187, 90, homeTeam);
        TeamPlayer assistant = new TeamPlayer("Alex", "Collina", 187, 90, homeTeam);

        TeamMatchGoal newGoal = teamMatchService.addNewScoredGoal(
            scorer,
            assistant,
            match,
            goalMatchTime,
            null
        );
    }

    @Test
    public void testAddNewScoredGoalWithSameGoal()
    {
        TeamMatchService teamMatchService = new TeamMatchService();
        long time = System.currentTimeMillis();
        Date startTime = new Date(time);
        Date goalMatchTime = new Date(time + 15000);
        Team homeTeam = new Team("HomeTeam");
        Team awayTeam = new Team("AwayTeam");
        TeamMatch match = new TeamMatch(homeTeam, awayTeam, startTime);
        TeamPlayer scorer = new TeamPlayer("John", "Doe", 187, 90, homeTeam);
        TeamPlayer assistant = new TeamPlayer("Alex", "Collina", 187, 90, homeTeam);
        TeamMatchGoal goal = new TeamMatchGoal(scorer, assistant, match, goalMatchTime);

        try {
            teamMatchService.addNewScoredGoal(
                scorer,
                assistant,
                match,
                goalMatchTime,
                goal
            );
            fail("Expected exception GoalWithSameParametersAlreadyExistsException");
        } catch (GoalWithSameParametersAlreadyExistsException ex) {
            assertEquals(scorer.getId(), ex.getScorerId());
            assertEquals(assistant.getId(), ex.getAssistantId());
            assertEquals(match.getId(), ex.getMatchId());
            assertEquals(goalMatchTime, ex.getMatchTime());
        }
    }
}
