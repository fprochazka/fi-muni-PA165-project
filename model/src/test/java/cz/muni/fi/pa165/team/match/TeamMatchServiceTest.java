package cz.muni.fi.pa165.team.match;

import cz.muni.fi.pa165.team.Team;
import cz.muni.fi.pa165.team.TeamPlayer;
import cz.muni.fi.pa165.team.match.exceptions.GoalWithSameParametersAlreadyExistsException;
import cz.muni.fi.pa165.team.match.exceptions.MatchTimeCollisionException;
import cz.muni.fi.pa165.team.match.exceptions.MatchWithSameParametersAlreadyExistsException;
import org.testng.annotations.Test;

import java.time.LocalDateTime;

import static org.testng.Assert.*;

/**
 * @author Tomas Smid <smid.thomas@gmail.com>
 */
public class TeamMatchServiceTest
{

    private LocalDateTime now = LocalDateTime.now();

    @Test
    public void testCreateMatch()
    {
        TeamMatchService teamMatchService = new TeamMatchService();

        LocalDateTime startTime = now.plusMinutes(5);
        LocalDateTime endTime = now.plusMinutes(55);
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
        assertEquals(newMatch.getHomeTeam().getId(), team1.getId());
        assertEquals(newMatch.getAwayTeam().getId(), team2.getId());
        assertEquals(newMatch.getStartTime(), startTime);
        assertEquals(newMatch.getEndTime(), endTime);
    }

    @Test(expectedExceptions = { IllegalArgumentException.class },
          expectedExceptionsMessageRegExp = "Match cannot be created with a null away team")
    public void testCreateMatchWithNullAwayTeam()
    {
        TeamMatchService teamMatchService = new TeamMatchService();

        LocalDateTime startTime = now.plusMinutes(5);
        LocalDateTime endTime = now.plusMinutes(55);
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

        LocalDateTime startTime = now.plusMinutes(5);
        LocalDateTime endTime = now.plusMinutes(55);
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

        LocalDateTime startTime = now.plusMinutes(5);
        LocalDateTime endTime = now.plusMinutes(55);
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

        LocalDateTime endTime = now.plusMinutes(55);
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

        LocalDateTime startTime = now;
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
        assertEquals(newMatch.getHomeTeam().getId(), team1.getId());
        assertEquals(newMatch.getAwayTeam().getId(), team2.getId());
        assertEquals(newMatch.getStartTime(), startTime);
        assertNull(newMatch.getEndTime());
    }

    @Test(expectedExceptions = { IllegalArgumentException.class },
          expectedExceptionsMessageRegExp = "Match end time is not after start time")
    public void testCreateMatchWithEndTimeBeforeStartTime()
    {
        TeamMatchService teamMatchService = new TeamMatchService();

        LocalDateTime startTime = now.plusMinutes(5);
        LocalDateTime endTime = now.plusMinutes(4);
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

        LocalDateTime startTime = now.plusMinutes(5);
        LocalDateTime endTime = now.plusMinutes(5);
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

        LocalDateTime startTime = now;
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
            assertEquals(ex.getStartTime(), startTime);
            assertEquals(ex.getTeamId(), team1.getId());
        }
    }

    @Test
    public void testCreateMatchWithConflictingMatchForAwayTeam()
    {
        TeamMatchService teamMatchService = new TeamMatchService();

        LocalDateTime startTime = now;
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
            assertEquals(ex.getStartTime(), startTime);
            assertEquals(ex.getTeamId(), team2.getId());
        }
    }

    @Test
    public void testCreateMatchWithNotNullConflictingMatchButNoRealConflictOnlySameStartTime()
    {
        TeamMatchService teamMatchService = new TeamMatchService();

        LocalDateTime startTime = now;
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
        assertEquals(newMatch.getHomeTeam().getId(), team3.getId());
        assertEquals(newMatch.getAwayTeam().getId(), team4.getId());
        assertEquals(newMatch.getStartTime(), startTime);
        assertNull(newMatch.getEndTime());

    }

    @Test
    public void testCreateMatchWithNotNullConflictingMatchButNoRealConflictOnlySameOneTeam()
    {
        TeamMatchService teamMatchService = new TeamMatchService();

        LocalDateTime startTime1 = now;
        LocalDateTime startTime2 = now.plusMinutes(15);
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
        assertEquals(newMatch.getHomeTeam().getId(), team3.getId());
        assertEquals(newMatch.getAwayTeam().getId(), team1.getId());
        assertEquals(newMatch.getStartTime(), startTime2);
        assertNull(newMatch.getEndTime());

    }

    @Test
    public void testCreateMatchWithNotNullConflictingMatchButNoRealConflictOnlySameBothTeam()
    {
        TeamMatchService teamMatchService = new TeamMatchService();

        LocalDateTime startTime1 = now;
        LocalDateTime startTime2 = now.plusMinutes(15);
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
        assertEquals(newMatch.getHomeTeam().getId(), team2.getId());
        assertEquals(newMatch.getAwayTeam().getId(), team1.getId());
        assertEquals(newMatch.getStartTime(), startTime2);
        assertNull(newMatch.getEndTime());

    }

    @Test
    public void testChangeMatchTime()
    {
        TeamMatchService teamMatchService = new TeamMatchService();

        LocalDateTime startTime1 = now;
        LocalDateTime startTime2 = now.plusMinutes(5);
        LocalDateTime endTime1 = now.plusMinutes(50);
        LocalDateTime endTime2 = now.plusMinutes(55);
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

        assertEquals(match.getStartTime(), startTime2);
        assertEquals(match.getEndTime(), endTime2);
    }

    @Test(expectedExceptions = { IllegalArgumentException.class },
          expectedExceptionsMessageRegExp = "Cannot change match times of null match")
    public void testChangeMatchTimeWithNullMatch()
    {
        TeamMatchService teamMatchService = new TeamMatchService();

        LocalDateTime startTime = now.plusMinutes(5);
        LocalDateTime endTime = now.plusMinutes(55);

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

        LocalDateTime startTime = now;
        LocalDateTime endTime = now.plusMinutes(55);
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

        LocalDateTime startTime1 = now;
        LocalDateTime startTime2 = now.plusMinutes(5);
        LocalDateTime endTime = now.plusMinutes(55);
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

        assertEquals(match.getStartTime(), startTime2);
        assertNull(match.getEndTime());
    }

    @Test(expectedExceptions = { IllegalArgumentException.class },
          expectedExceptionsMessageRegExp = "Match end time is not after start time")
    public void testChangeMatchTimeWithEndTimeBeforeStartTime()
    {
        TeamMatchService teamMatchService = new TeamMatchService();

        LocalDateTime startTime1 = now;
        LocalDateTime startTime2 = now.plusMinutes(5);
        LocalDateTime endTime1 = now.plusMinutes(55);
        LocalDateTime endTime2 = now.plusMinutes(4);
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

        LocalDateTime startTime1 = now;
        LocalDateTime startTime2 = now.plusMinutes(5);
        LocalDateTime endTime1 = now.plusMinutes(55);
        LocalDateTime endTime2 = now.plusMinutes(5);
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

        LocalDateTime startTime1 = now;
        LocalDateTime startTime2 = now.plusMinutes(5);
        LocalDateTime endTime1 = now.plusMinutes(50);
        LocalDateTime endTime2 = now.plusMinutes(55);
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
            assertEquals(ex.getMatchPlannedToBeChangedId(), match2.getId());
            assertEquals(ex.getCollidingMatchId(), match1.getId());
            assertEquals(ex.getStartTime(), startTime2);
            assertEquals(ex.getTeamId(), team1.getId());
        }
    }

    @Test
    public void testChangeMatchTimeWithConflictingMatchForMatchAwayTeam()
    {
        TeamMatchService teamMatchService = new TeamMatchService();

        LocalDateTime startTime1 = now;
        LocalDateTime startTime2 = now.plusMinutes(5);
        LocalDateTime endTime1 = now.plusMinutes(50);
        LocalDateTime endTime2 = now.plusMinutes(55);
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
            assertEquals(ex.getMatchPlannedToBeChangedId(), match2.getId());
            assertEquals(ex.getCollidingMatchId(), match1.getId());
            assertEquals(ex.getStartTime(), startTime2);
            assertEquals(ex.getTeamId(), team1.getId());
        }
    }

    @Test
    public void testChangeMatchTimeWithNotNullConflictingMatchButNoRealConflictOnlySameStartTime()
    {
        TeamMatchService teamMatchService = new TeamMatchService();

        LocalDateTime startTime = now;
        LocalDateTime endTime1 = now.plusMinutes(50);
        LocalDateTime endTime2 = now.plusMinutes(55);
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

        assertEquals(match2.getStartTime(), startTime);
        assertEquals(match2.getEndTime(), endTime2);
    }

    @Test
    public void testChangeMatchTimeWithNotNullConflictingMatchButNoRealConflictOnlySameOneTeam()
    {
        TeamMatchService teamMatchService = new TeamMatchService();

        LocalDateTime startTime1 = now;
        LocalDateTime startTime2 = now.plusMinutes(1);
        LocalDateTime endTime1 = now.plusMinutes(50);
        LocalDateTime endTime2 = now.plusMinutes(55);
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

        assertEquals(match2.getStartTime(), startTime2);
        assertEquals(match2.getEndTime(), endTime2);
    }

    @Test
    public void testChangeMatchTimeWithNotNullConflictingMatchButNoRealConflictOnlySameBothTeam()
    {
        TeamMatchService teamMatchService = new TeamMatchService();

        LocalDateTime startTime1 = now;
        LocalDateTime startTime2 = now.plusMinutes(1);
        LocalDateTime endTime1 = now.plusMinutes(50);
        LocalDateTime endTime2 = now.plusMinutes(55);
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

        assertEquals(match2.getStartTime(), startTime2);
        assertEquals(match2.getEndTime(), endTime2);
    }

    @Test
    public void testChangeMatchTimeWithNotNullConflictingMatchButNoRealConflictJustOneSameMatchAndNoRealChange()
    {
        TeamMatchService teamMatchService = new TeamMatchService();

        LocalDateTime startTime = now;
        Team team1 = new Team("Team1");
        Team team2 = new Team("Team2");
        TeamMatch match = new TeamMatch(team1, team2, startTime);


        teamMatchService.changeMatchTime(
            match,
            match,
            match,
            startTime,
            null
        );

        assertEquals(match.getStartTime(), startTime);
        assertNull(match.getEndTime());
    }

    @Test
    public void testChangeMatchTimeWithNotNullConflictingMatchButNoRealConflictJustOneSameMatchAndEndTimeChange()
    {
        TeamMatchService teamMatchService = new TeamMatchService();

        LocalDateTime startTime = now;
        LocalDateTime endTime = now.plusMinutes(50);
        Team team1 = new Team("Team1");
        Team team2 = new Team("Team2");
        TeamMatch match = new TeamMatch(team1, team2, startTime);


        teamMatchService.changeMatchTime(
            match,
            match,
            match,
            startTime,
            endTime
        );

        assertEquals(match.getStartTime(), startTime);
        assertEquals(match.getEndTime(), endTime);
    }

    @Test
    public void testEndMatch()
    {
        TeamMatchService teamMatchService = new TeamMatchService();

        LocalDateTime startTime = now;
        LocalDateTime endTime = now.plusMinutes(50);
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

        LocalDateTime endTime = now.plusMinutes(50);

        teamMatchService.endMatch(null, endTime);
    }

    @Test(expectedExceptions = { IllegalArgumentException.class },
          expectedExceptionsMessageRegExp = "Cannot end the match with a null end time")
    public void testEndMatchWithNullEndTime()
    {
        TeamMatchService teamMatchService = new TeamMatchService();

        LocalDateTime startTime = now;
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

        LocalDateTime startTime = now;
        LocalDateTime endTime = now.plusMinutes(50);
        LocalDateTime endTime2 = now.plusSeconds(1);
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

        LocalDateTime startTime = now;
        LocalDateTime endTime = now.minusSeconds(1);
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

        LocalDateTime startTime = now;
        LocalDateTime endTime = now;
        Team team1 = new Team("Team1");
        Team team2 = new Team("Team2");
        TeamMatch match = new TeamMatch(team1, team2, startTime);

        teamMatchService.endMatch(match, endTime);
    }

    @Test
    public void testAddNewScoredGoal()
    {
        TeamMatchService teamMatchService = new TeamMatchService();

        LocalDateTime startTime = now;
        LocalDateTime goalMatchTime = now.plusMinutes(15);
        Team homeTeam = new Team("HomeTeam");
        Team awayTeam = new Team("AwayTeam");
        TeamMatch match = new TeamMatch(homeTeam, awayTeam, startTime);
        TeamPlayer scorer = new TeamPlayer("John", "Doe", 187, 90, homeTeam);
        TeamPlayer assistant = new TeamPlayer("Jack", "Smith", 178, 84, homeTeam);

        TeamMatchGoal newGoal = teamMatchService.addNewScoredGoal(
            scorer,
            assistant,
            match,
            homeTeam,
            goalMatchTime,
            null
        );

        assertNotNull(newGoal);
        assertEquals(newGoal.getScorer().getId(), scorer.getId());
        assertEquals(newGoal.getAssistant().getId(), assistant.getId());
        assertEquals(newGoal.getMatch().getId(), match.getId());
        assertEquals(newGoal.getMatchTime(), goalMatchTime);
    }

    @Test(expectedExceptions = { IllegalArgumentException.class },
          expectedExceptionsMessageRegExp = "Cannot create new goal with a null scorer")
    public void testAddNewScoredGoalWithNullScorer()
    {
        TeamMatchService teamMatchService = new TeamMatchService();

        LocalDateTime startTime = now;
        LocalDateTime goalMatchTime = now.plusMinutes(15);
        Team homeTeam = new Team("HomeTeam");
        Team awayTeam = new Team("AwayTeam");
        TeamMatch match = new TeamMatch(homeTeam, awayTeam, startTime);
        TeamPlayer assistant = new TeamPlayer("Alex", "Collina", 187, 90, homeTeam);

        TeamMatchGoal newGoal = teamMatchService.addNewScoredGoal(
            null,
            assistant,
            match,
            homeTeam,
            goalMatchTime,
            null
        );
    }

    @Test(expectedExceptions = { IllegalArgumentException.class },
          expectedExceptionsMessageRegExp = "Cannot create new goal with a null assistant")
    public void testAddNewScoredGoalWithNullAssistant()
    {
        TeamMatchService teamMatchService = new TeamMatchService();

        LocalDateTime startTime = now;
        LocalDateTime goalMatchTime = now.plusMinutes(15);
        Team homeTeam = new Team("HomeTeam");
        Team awayTeam = new Team("AwayTeam");
        TeamMatch match = new TeamMatch(homeTeam, awayTeam, startTime);
        TeamPlayer scorer = new TeamPlayer("John", "Doe", 187, 90, homeTeam);

        TeamMatchGoal newGoal = teamMatchService.addNewScoredGoal(
            scorer,
            null,
            match,
            homeTeam,
            goalMatchTime,
            null
        );
    }

    @Test(expectedExceptions = { IllegalArgumentException.class },
          expectedExceptionsMessageRegExp = "Cannot create new goal with scorer and assistant who are the same player")
    public void testAddNewScoredGoalWithSameScorerAndAssistant()
    {
        TeamMatchService teamMatchService = new TeamMatchService();

        LocalDateTime startTime = now;
        LocalDateTime goalMatchTime = now.plusMinutes(15);
        Team homeTeam = new Team("HomeTeam");
        Team awayTeam = new Team("AwayTeam");
        TeamMatch match = new TeamMatch(homeTeam, awayTeam, startTime);
        TeamPlayer player = new TeamPlayer("John", "Doe", 187, 90, homeTeam);

        TeamMatchGoal newGoal = teamMatchService.addNewScoredGoal(
            player,
            player,
            match,
            homeTeam,
            goalMatchTime,
            null
        );
    }

    @Test(expectedExceptions = { IllegalArgumentException.class },
          expectedExceptionsMessageRegExp = "Given a null goal match time")
    public void testAddNewScoredGoalWithNullGoalMatchTime()
    {
        TeamMatchService teamMatchService = new TeamMatchService();

        LocalDateTime startTime = now;
        Team homeTeam = new Team("HomeTeam");
        Team awayTeam = new Team("AwayTeam");
        TeamMatch match = new TeamMatch(homeTeam, awayTeam, startTime);
        TeamPlayer scorer = new TeamPlayer("John", "Doe", 187, 90, homeTeam);
        TeamPlayer assistant = new TeamPlayer("Alex", "Collina", 187, 90, homeTeam);

        TeamMatchGoal newGoal = teamMatchService.addNewScoredGoal(
            scorer,
            assistant,
            match,
            homeTeam,
            null,
            null
        );
    }

    @Test(expectedExceptions = { IllegalArgumentException.class },
          expectedExceptionsMessageRegExp = "Goal match time is not after match start time")
    public void testAddNewScoredGoalWithGoalMatchTimeBeforeMatchStartTime()
    {
        TeamMatchService teamMatchService = new TeamMatchService();

        LocalDateTime startTime = now;
        LocalDateTime goalMatchTime = now.minusSeconds(1);
        Team homeTeam = new Team("HomeTeam");
        Team awayTeam = new Team("AwayTeam");
        TeamMatch match = new TeamMatch(homeTeam, awayTeam, startTime);
        TeamPlayer scorer = new TeamPlayer("John", "Doe", 187, 90, homeTeam);
        TeamPlayer assistant = new TeamPlayer("Radan", "Spock", 186, 78, homeTeam);

        TeamMatchGoal newGoal = teamMatchService.addNewScoredGoal(
            scorer,
            assistant,
            match,
            homeTeam,
            goalMatchTime,
            null
        );
    }

    @Test(expectedExceptions = { IllegalArgumentException.class },
          expectedExceptionsMessageRegExp = "Goal match time is not after match start time")
    public void testAddNewScoredGoalWithGoalMatchTimeEqualToMatchStartTime()
    {
        TeamMatchService teamMatchService = new TeamMatchService();

        LocalDateTime startTime = now;
        LocalDateTime goalMatchTime = now;
        Team homeTeam = new Team("HomeTeam");
        Team awayTeam = new Team("AwayTeam");
        TeamMatch match = new TeamMatch(homeTeam, awayTeam, startTime);
        TeamPlayer scorer = new TeamPlayer("John", "Doe", 187, 90, homeTeam);
        TeamPlayer assistant = new TeamPlayer("Radan", "Spock", 186, 78, homeTeam);

        TeamMatchGoal newGoal = teamMatchService.addNewScoredGoal(
            scorer,
            assistant,
            match,
            homeTeam,
            goalMatchTime,
            null
        );
    }

    @Test(expectedExceptions = { IllegalArgumentException.class },
          expectedExceptionsMessageRegExp = "Goal match time is not before match end time")
    public void testAddNewScoredGoalWithGoalMatchTimeAfterMatchEndTime()
    {
        TeamMatchService teamMatchService = new TeamMatchService();

        LocalDateTime startTime = now;
        LocalDateTime endTime = now.plusMinutes(55);
        LocalDateTime goalMatchTime = now.plusMinutes(55).plusSeconds(1);
        Team homeTeam = new Team("HomeTeam");
        Team awayTeam = new Team("AwayTeam");
        TeamMatch match = new TeamMatch(homeTeam, awayTeam, startTime, endTime);
        TeamPlayer scorer = new TeamPlayer("John", "Doe", 187, 90, homeTeam);
        TeamPlayer assistant = new TeamPlayer("Alex", "Collina", 187, 90, homeTeam);

        TeamMatchGoal newGoal = teamMatchService.addNewScoredGoal(
            scorer,
            assistant,
            match,
            homeTeam,
            goalMatchTime,
            null
        );
    }

    @Test(expectedExceptions = { IllegalArgumentException.class },
          expectedExceptionsMessageRegExp = "Goal match time is not before match end time")
    public void testAddNewScoredGoalWithGoalMatchTimeEqualToMatchEndTime()
    {
        TeamMatchService teamMatchService = new TeamMatchService();

        LocalDateTime startTime = now;
        LocalDateTime endTime = now.plusMinutes(55);
        LocalDateTime goalMatchTime = now.plusMinutes(55);
        Team homeTeam = new Team("HomeTeam");
        Team awayTeam = new Team("AwayTeam");
        TeamMatch match = new TeamMatch(homeTeam, awayTeam, startTime, endTime);
        TeamPlayer scorer = new TeamPlayer("John", "Doe", 187, 90, homeTeam);
        TeamPlayer assistant = new TeamPlayer("Alex", "Collina", 187, 90, homeTeam);

        TeamMatchGoal newGoal = teamMatchService.addNewScoredGoal(
            scorer,
            assistant,
            match,
            homeTeam,
            goalMatchTime,
            null
        );
    }

    @Test
    public void testAddNewScoredGoalWithSameGoal()
    {
        TeamMatchService teamMatchService = new TeamMatchService();

        LocalDateTime startTime = now;
        LocalDateTime goalMatchTime = now.plusMinutes(15);
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
                homeTeam,
                goalMatchTime,
                goal
            );
            fail("Expected exception GoalWithSameParametersAlreadyExistsException");
        } catch (GoalWithSameParametersAlreadyExistsException ex) {
            assertEquals(ex.getScorerId(), scorer.getId());
            assertEquals(ex.getAssistantId(), assistant.getId());
            assertEquals(ex.getMatchId(), match.getId());
            assertEquals(ex.getMatchTime(), goalMatchTime);
        }
    }

    @Test
    public void testAddNewScoredGoalWithNotNullSameGoalButDifferentMatch()
    {
        TeamMatchService teamMatchService = new TeamMatchService();

        LocalDateTime startTime1 = now;
        LocalDateTime startTime2 = now.plusMinutes(10);
        LocalDateTime goalMatchTime = now.plusMinutes(15);
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
            homeTeam,
            goalMatchTime,
            goal
        );

        assertNotNull(newGoal);
        assertEquals(newGoal.getScorer().getId(), scorer.getId());
        assertEquals(newGoal.getAssistant().getId(), assistant.getId());
        assertEquals(newGoal.getMatch().getId(), match2.getId());
        assertEquals(newGoal.getMatchTime(), goalMatchTime);
    }

    @Test
    public void testAddNewScoredGoalWithNotNullSameGoalButDifferentScorer()
    {
        TeamMatchService teamMatchService = new TeamMatchService();

        LocalDateTime startTime = now;
        LocalDateTime goalMatchTime = now.plusMinutes(15);
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
            homeTeam,
            goalMatchTime,
            goal
        );

        assertNotNull(newGoal);
        assertEquals(newGoal.getScorer().getId(), scorer2.getId());
        assertEquals(newGoal.getAssistant().getId(), assistant.getId());
        assertEquals(newGoal.getMatch().getId(), match.getId());
        assertEquals(newGoal.getMatchTime(), goalMatchTime);
    }

    @Test
    public void testAddNewScoredGoalWithNotNullSameGoalButDifferentAssistant()
    {
        TeamMatchService teamMatchService = new TeamMatchService();

        LocalDateTime startTime = now;
        LocalDateTime goalMatchTime = now.plusMinutes(15);
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
            homeTeam,
            goalMatchTime,
            goal
        );

        assertNotNull(newGoal);
        assertEquals(newGoal.getScorer().getId(), scorer.getId());
        assertEquals(newGoal.getAssistant().getId(), assistant2.getId());
        assertEquals(newGoal.getMatch().getId(), match.getId());
        assertEquals(newGoal.getMatchTime(), goalMatchTime);
    }

    @Test
    public void testAddNewScoredGoalWithNotNullSameGoalButDifferentGoalMatchTime()
    {
        TeamMatchService teamMatchService = new TeamMatchService();

        LocalDateTime startTime = now;
        LocalDateTime goalMatchTime1 = now.plusMinutes(15);
        LocalDateTime goalMatchTime2 = now.plusMinutes(15).plusSeconds(1);
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
            homeTeam,
            goalMatchTime2,
            goal
        );

        assertNotNull(newGoal);
        assertEquals(newGoal.getScorer().getId(), scorer.getId());
        assertEquals(newGoal.getAssistant().getId(), assistant.getId());
        assertEquals(newGoal.getMatch().getId(), match.getId());
        assertEquals(newGoal.getMatchTime(), goalMatchTime2);
    }

    @Test(expectedExceptions = { IllegalArgumentException.class },
    expectedExceptionsMessageRegExp = "Cannot create new goal with a null match")
    public void testAddNewScoredGoalWithNullMatch()
    {
        TeamMatchService teamMatchService = new TeamMatchService();

        LocalDateTime startTime = now;
        LocalDateTime goalMatchTime1 = now.plusMinutes(15);
        LocalDateTime goalMatchTime2 = now.plusMinutes(15).plusSeconds(1);
        Team homeTeam = new Team("HomeTeam");
        Team awayTeam = new Team("AwayTeam");
        TeamMatch match = new TeamMatch(homeTeam, awayTeam, startTime);
        TeamPlayer scorer = new TeamPlayer("John", "Doe", 187, 90, homeTeam);
        TeamPlayer assistant = new TeamPlayer("Alex", "Collina", 187, 90, homeTeam);
        TeamMatchGoal goal = new TeamMatchGoal(scorer, assistant, match, goalMatchTime1);

        TeamMatchGoal newGoal = teamMatchService.addNewScoredGoal(
            scorer,
            assistant,
            null,
            homeTeam,
            goalMatchTime2,
            goal
        );
    }

    @Test(expectedExceptions = { IllegalArgumentException.class },
        expectedExceptionsMessageRegExp = "Cannot create new goal with a null scoring team")
    public void testAddNewScoredGoalWithNullScoringTeam()
    {
        TeamMatchService teamMatchService = new TeamMatchService();

        LocalDateTime startTime = now;
        LocalDateTime goalMatchTime1 = now.plusMinutes(15);
        LocalDateTime goalMatchTime2 = now.plusMinutes(15).plusSeconds(1);
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
            null,
            goalMatchTime2,
            goal
        );
    }

    @Test(expectedExceptions = { IllegalArgumentException.class },
        expectedExceptionsMessageRegExp = "Cannot create new goal with scorer who is not from scoring team")
    public void testAddNewScoredGoalWithScorerNotFromScoringTeam()
    {
        TeamMatchService teamMatchService = new TeamMatchService();

        LocalDateTime startTime = now;
        LocalDateTime goalMatchTime1 = now.plusMinutes(15);
        LocalDateTime goalMatchTime2 = now.plusMinutes(15).plusSeconds(1);
        Team homeTeam = new Team("HomeTeam");
        Team awayTeam = new Team("AwayTeam");
        TeamMatch match = new TeamMatch(homeTeam, awayTeam, startTime);
        TeamPlayer scorer = new TeamPlayer("John", "Doe", 187, 90, awayTeam);
        TeamPlayer assistant = new TeamPlayer("Alex", "Collina", 187, 90, homeTeam);
        TeamMatchGoal goal = new TeamMatchGoal(scorer, assistant, match, goalMatchTime1);

        TeamMatchGoal newGoal = teamMatchService.addNewScoredGoal(
            scorer,
            assistant,
            match,
            homeTeam,
            goalMatchTime2,
            goal
        );
    }

    @Test(expectedExceptions = { IllegalArgumentException.class },
        expectedExceptionsMessageRegExp = "Cannot create new goal with assistant who is not from scoring team")
    public void testAddNewScoredGoalWithAssistantNotFromScoringTeam()
    {
        TeamMatchService teamMatchService = new TeamMatchService();

        LocalDateTime startTime = now;
        LocalDateTime goalMatchTime1 = now.plusMinutes(15);
        LocalDateTime goalMatchTime2 = now.plusMinutes(15).plusSeconds(1);
        Team homeTeam = new Team("HomeTeam");
        Team awayTeam = new Team("AwayTeam");
        TeamMatch match = new TeamMatch(homeTeam, awayTeam, startTime);
        TeamPlayer scorer = new TeamPlayer("John", "Doe", 187, 90, homeTeam);
        TeamPlayer assistant = new TeamPlayer("Alex", "Collina", 187, 90, awayTeam);
        TeamMatchGoal goal = new TeamMatchGoal(scorer, assistant, match, goalMatchTime1);

        TeamMatchGoal newGoal = teamMatchService.addNewScoredGoal(
            scorer,
            assistant,
            match,
            homeTeam,
            goalMatchTime2,
            goal
        );
    }
}
