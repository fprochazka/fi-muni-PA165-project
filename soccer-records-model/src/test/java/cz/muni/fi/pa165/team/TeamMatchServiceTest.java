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

    @Test(expectedExceptions = { IllegalArgumentException.class },
          expectedExceptionsMessageRegExp = "Match cannot be created with a null away team")
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

    @Test(expectedExceptions = { IllegalArgumentException.class },
          expectedExceptionsMessageRegExp = "Match cannot be created with a null home team")
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

    @Test(expectedExceptions = { IllegalArgumentException.class },
          expectedExceptionsMessageRegExp = "Match cannot be created for home and away teams which are same")
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

    @Test(expectedExceptions = { IllegalArgumentException.class },
          expectedExceptionsMessageRegExp = "Match start time is null")
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

    @Test(expectedExceptions = { IllegalArgumentException.class },
          expectedExceptionsMessageRegExp = "Match end time is not after start time")
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

    @Test(expectedExceptions = { IllegalArgumentException.class },
          expectedExceptionsMessageRegExp = "Match end time is not after start time")
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
    public void testCreateMatchWithNotNullConflictingMatchButNoRealConflictOnlySameStartTime()
    {
        TeamMatchService teamMatchService = new TeamMatchService();
        long time = System.currentTimeMillis();
        Date startTime = new Date(time);
        Team team1 = new Team("Team1");
        Team team2 = new Team("Team2");
        Team team3 = new Team("Team3");
        Team team4 = new Team("Team4");
        TeamMatch match = new TeamMatch(team1, team2, startTime);


        TeamMatch newMatch = teamMatchService.createMatch(
            null,
            match,
            team3,
            team4,
            startTime,
            null
        );

        assertNotNull(newMatch);
        assertEquals(team3.getId(), newMatch.getHomeTeam().getId());
        assertEquals(team4.getId(),newMatch.getAwayTeam().getId());
        assertEquals(startTime, newMatch.getStartTime());
        assertNull(newMatch.getEndTime());

    }

    @Test
    public void testCreateMatchWithNotNullConflictingMatchButNoRealConflictOnlySameOneTeam()
    {
        TeamMatchService teamMatchService = new TeamMatchService();
        long time = System.currentTimeMillis();
        Date startTime1 = new Date(time);
        Date startTime2 = new Date(time+1500);
        Team team1 = new Team("Team1");
        Team team2 = new Team("Team2");
        Team team3 = new Team("Team3");
        TeamMatch match = new TeamMatch(team1, team2, startTime1);


        TeamMatch newMatch = teamMatchService.createMatch(
            null,
            match,
            team3,
            team1,
            startTime2,
            null
        );

        assertNotNull(newMatch);
        assertEquals(team3.getId(), newMatch.getHomeTeam().getId());
        assertEquals(team1.getId(),newMatch.getAwayTeam().getId());
        assertEquals(startTime2, newMatch.getStartTime());
        assertNull(newMatch.getEndTime());

    }

    @Test
    public void testCreateMatchWithNotNullConflictingMatchButNoRealConflictOnlySameBothTeam()
    {
        TeamMatchService teamMatchService = new TeamMatchService();
        long time = System.currentTimeMillis();
        Date startTime1 = new Date(time);
        Date startTime2 = new Date(time+1500);
        Team team1 = new Team("Team1");
        Team team2 = new Team("Team2");
        TeamMatch match = new TeamMatch(team1, team2, startTime1);


        TeamMatch newMatch = teamMatchService.createMatch(
            match,
            match,
            team2,
            team1,
            startTime2,
            null
        );

        assertNotNull(newMatch);
        assertEquals(team2.getId(), newMatch.getHomeTeam().getId());
        assertEquals(team1.getId(),newMatch.getAwayTeam().getId());
        assertEquals(startTime2, newMatch.getStartTime());
        assertNull(newMatch.getEndTime());

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

    @Test(expectedExceptions = { IllegalArgumentException.class },
          expectedExceptionsMessageRegExp = "Cannot change match times of null match")
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

    @Test(expectedExceptions = { IllegalArgumentException.class },
          expectedExceptionsMessageRegExp = "Match start time is null")
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

    @Test(expectedExceptions = { IllegalArgumentException.class },
          expectedExceptionsMessageRegExp = "Match end time is not after start time")
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

    @Test(expectedExceptions = { IllegalArgumentException.class },
          expectedExceptionsMessageRegExp = "Match end time is not after start time")
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
    public void testChangeMatchTimeWithNotNullConflictingMatchButNoRealConflictOnlySameStartTime()
    {
        TeamMatchService teamMatchService = new TeamMatchService();
        long time = System.currentTimeMillis();
        Date startTime = new Date(time);
        Date endTime1 = new Date(time + 5500000);
        Date endTime2 = new Date(time + 5550000);
        Team team1 = new Team("Team1");
        Team team2 = new Team("Team2");
        Team team3 = new Team("Team3");
        Team team4 = new Team("Team4");
        TeamMatch match1 = new TeamMatch(team1, team2, startTime, endTime1);
        TeamMatch match2 = new TeamMatch(team3, team4, startTime);


        teamMatchService.changeMatchTime(
            null,
            match1,
            match2,
            startTime,
            endTime2
        );

        assertEquals(startTime, match2.getStartTime());
        assertEquals(endTime2, match2.getEndTime());
    }

    @Test
    public void testChangeMatchTimeWithNotNullConflictingMatchButNoRealConflictOnlySameOneTeam()
    {
        TeamMatchService teamMatchService = new TeamMatchService();
        long time = System.currentTimeMillis();
        Date startTime1 = new Date(time);
        Date startTime2 = new Date(time+1500);
        Date endTime1 = new Date(time + 5500000);
        Date endTime2 = new Date(time + 5550000);
        Team team1 = new Team("Team1");
        Team team2 = new Team("Team2");
        Team team3 = new Team("Team3");
        TeamMatch match1 = new TeamMatch(team1, team2, startTime1, endTime1);
        TeamMatch match2 = new TeamMatch(team3, team2, startTime1);


        teamMatchService.changeMatchTime(
            match1,
            null,
            match2,
            startTime2,
            endTime2
        );

        assertEquals(startTime2, match2.getStartTime());
        assertEquals(endTime2, match2.getEndTime());
    }

    @Test
    public void testChangeMatchTimeWithNotNullConflictingMatchButNoRealConflictOnlySameBothTeam()
    {
        TeamMatchService teamMatchService = new TeamMatchService();
        long time = System.currentTimeMillis();
        Date startTime1 = new Date(time);
        Date startTime2 = new Date(time+1500);
        Date endTime1 = new Date(time + 5500000);
        Date endTime2 = new Date(time + 5550000);
        Team team1 = new Team("Team1");
        Team team2 = new Team("Team2");
        TeamMatch match1 = new TeamMatch(team1, team2, startTime1, endTime1);
        TeamMatch match2 = new TeamMatch(team1, team2, startTime1);


        teamMatchService.changeMatchTime(
            match1,
            null,
            match2,
            startTime2,
            endTime2
        );

        assertEquals(startTime2, match2.getStartTime());
        assertEquals(endTime2, match2.getEndTime());
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

    @Test(expectedExceptions = { IllegalArgumentException.class },
          expectedExceptionsMessageRegExp = "Cannot end the null match")
    public void testEndMatchWithNullMatch()
    {
        TeamMatchService teamMatchService = new TeamMatchService();
        long time = System.currentTimeMillis();
        Date endTime = new Date(time + 5000000);

        teamMatchService.endMatch(null, endTime);
    }

    @Test(expectedExceptions = { IllegalArgumentException.class },
          expectedExceptionsMessageRegExp = "Cannot end the match with a null end time")
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

    @Test(expectedExceptions = { IllegalArgumentException.class },
          expectedExceptionsMessageRegExp = "Cannot end already ended match")
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

    @Test(expectedExceptions = { IllegalArgumentException.class },
          expectedExceptionsMessageRegExp = "Match end time is not after start time")
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

    @Test(expectedExceptions = { IllegalArgumentException.class },
          expectedExceptionsMessageRegExp = "Match end time is not after start time")
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

    @Test(expectedExceptions = { IllegalArgumentException.class },
          expectedExceptionsMessageRegExp = "Cannot create new goal with a null scorer")
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

    @Test(expectedExceptions = { IllegalArgumentException.class },
          expectedExceptionsMessageRegExp = "Cannot create new goal with a null assistant")
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

    @Test(expectedExceptions = { IllegalArgumentException.class },
          expectedExceptionsMessageRegExp = "Cannot create new goal with scorer and assistant who are the same player")
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

    @Test(expectedExceptions = { IllegalArgumentException.class },
          expectedExceptionsMessageRegExp = "Given a null goal match time")
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

    @Test(expectedExceptions = { IllegalArgumentException.class },
          expectedExceptionsMessageRegExp = "Goal match time is not after match start time")
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

    @Test(expectedExceptions = { IllegalArgumentException.class },
          expectedExceptionsMessageRegExp = "Goal match time is not after match start time")
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

    @Test(expectedExceptions = { IllegalArgumentException.class },
          expectedExceptionsMessageRegExp = "Goal match time is not before match end time")
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

    @Test(expectedExceptions = { IllegalArgumentException.class },
          expectedExceptionsMessageRegExp = "Goal match time is not before match end time")
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

    @Test
    public void testAddNewScoredGoalWithNotNullSameGoalButDifferentMatch()
    {
        TeamMatchService teamMatchService = new TeamMatchService();
        long time = System.currentTimeMillis();
        Date startTime1 = new Date(time);
        Date startTime2 = new Date(time+10000);
        Date goalMatchTime = new Date(time + 15000);
        Team homeTeam = new Team("HomeTeam");
        Team awayTeam = new Team("AwayTeam");
        TeamMatch match1 = new TeamMatch(homeTeam, awayTeam, startTime1);
        TeamMatch match2 = new TeamMatch(awayTeam, homeTeam, startTime2);
        TeamPlayer scorer = new TeamPlayer("John", "Doe", 187, 90, homeTeam);
        TeamPlayer assistant = new TeamPlayer("Alex", "Collina", 187, 90, homeTeam);
        TeamMatchGoal goal = new TeamMatchGoal(scorer, assistant, match1, goalMatchTime);


        TeamMatchGoal newGoal = teamMatchService.addNewScoredGoal(
            scorer,
            assistant,
            match2,
            goalMatchTime,
            goal
        );

        assertNotNull(newGoal);
        assertEquals(scorer.getId(), newGoal.getScorer().getId());
        assertEquals(assistant.getId(), newGoal.getAssistant().getId());
        assertEquals(match2.getId(), newGoal.getMatch().getId());
        assertEquals(goalMatchTime, newGoal.getMatchTime());
    }

    @Test
    public void testAddNewScoredGoalWithNotNullSameGoalButDifferentScorer()
    {
        TeamMatchService teamMatchService = new TeamMatchService();
        long time = System.currentTimeMillis();
        Date startTime = new Date(time);
        Date goalMatchTime = new Date(time + 15000);
        Team homeTeam = new Team("HomeTeam");
        Team awayTeam = new Team("AwayTeam");
        TeamMatch match = new TeamMatch(homeTeam, awayTeam, startTime);
        TeamPlayer scorer1 = new TeamPlayer("John", "Doe", 187, 90, homeTeam);
        TeamPlayer scorer2 = new TeamPlayer("Kevin", "McAllister", 187, 90, homeTeam);
        TeamPlayer assistant = new TeamPlayer("Alex", "Collina", 187, 90, homeTeam);
        TeamMatchGoal goal = new TeamMatchGoal(scorer1, assistant, match, goalMatchTime);


        TeamMatchGoal newGoal = teamMatchService.addNewScoredGoal(
            scorer2,
            assistant,
            match,
            goalMatchTime,
            goal
        );

        assertNotNull(newGoal);
        assertEquals(scorer2.getId(), newGoal.getScorer().getId());
        assertEquals(assistant.getId(), newGoal.getAssistant().getId());
        assertEquals(match.getId(), newGoal.getMatch().getId());
        assertEquals(goalMatchTime, newGoal.getMatchTime());
    }

    @Test
    public void testAddNewScoredGoalWithNotNullSameGoalButDifferentAssistant()
    {
        TeamMatchService teamMatchService = new TeamMatchService();
        long time = System.currentTimeMillis();
        Date startTime = new Date(time);
        Date goalMatchTime = new Date(time + 15000);
        Team homeTeam = new Team("HomeTeam");
        Team awayTeam = new Team("AwayTeam");
        TeamMatch match = new TeamMatch(homeTeam, awayTeam, startTime);
        TeamPlayer scorer = new TeamPlayer("John", "Doe", 187, 90, homeTeam);
        TeamPlayer assistant1 = new TeamPlayer("Alex", "Collina", 187, 90, homeTeam);
        TeamPlayer assistant2 = new TeamPlayer("Rob", "Bulk", 187, 90, homeTeam);
        TeamMatchGoal goal = new TeamMatchGoal(scorer, assistant1, match, goalMatchTime);


        TeamMatchGoal newGoal = teamMatchService.addNewScoredGoal(
            scorer,
            assistant2,
            match,
            goalMatchTime,
            goal
        );

        assertNotNull(newGoal);
        assertEquals(scorer.getId(), newGoal.getScorer().getId());
        assertEquals(assistant2.getId(), newGoal.getAssistant().getId());
        assertEquals(match.getId(), newGoal.getMatch().getId());
        assertEquals(goalMatchTime, newGoal.getMatchTime());
    }

    @Test
    public void testAddNewScoredGoalWithNotNullSameGoalButDifferentGoalMatchTime()
    {
        TeamMatchService teamMatchService = new TeamMatchService();
        long time = System.currentTimeMillis();
        Date startTime = new Date(time);
        Date goalMatchTime1 = new Date(time + 15000);
        Date goalMatchTime2 = new Date(time + 15001);
        Team homeTeam = new Team("HomeTeam");
        Team awayTeam = new Team("AwayTeam");
        TeamMatch match = new TeamMatch(homeTeam, awayTeam, startTime);
        TeamPlayer scorer = new TeamPlayer("John", "Doe", 187, 90, homeTeam);
        TeamPlayer assistant = new TeamPlayer("Alex", "Collina", 187, 90, homeTeam);
        TeamMatchGoal goal = new TeamMatchGoal(scorer, assistant, match, goalMatchTime1);


        TeamMatchGoal newGoal = teamMatchService.addNewScoredGoal(
            scorer,
            assistant,
            match,
            goalMatchTime2,
            goal
        );

        assertNotNull(newGoal);
        assertEquals(scorer.getId(), newGoal.getScorer().getId());
        assertEquals(assistant.getId(), newGoal.getAssistant().getId());
        assertEquals(match.getId(), newGoal.getMatch().getId());
        assertEquals(goalMatchTime2, newGoal.getMatchTime());
    }
}
