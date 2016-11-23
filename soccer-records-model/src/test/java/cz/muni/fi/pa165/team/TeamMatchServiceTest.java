package cz.muni.fi.pa165.team;

import cz.muni.fi.pa165.team.exceptions.GoalWithSameParametersAlreadyExistsException;
import cz.muni.fi.pa165.team.exceptions.MatchTimeCollisionException;
import cz.muni.fi.pa165.team.exceptions.MatchWithSameParametersAlreadyExistsException;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

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
        Date startTime1 = new Date(time);
        Date startTime2 = new Date(time + 50000);
        Date endTime1 = new Date(time + 5500000);
        Date endTime2 = new Date(time + 5550000);
        Team team1 = new Team("Team1");
        Team team2 = new Team("Team2");
        Team team3 = new Team("Team3");
        TeamMatch match1 = new TeamMatch(team1, team2, startTime1, endTime1);
        List<TeamMatch> allMatchesOfHomeTeam = Arrays.asList(match1);
        List<TeamMatch> allMatchesOfAwayTeam = new ArrayList<>();

        TeamMatch newMatch = teamMatchService.createMatch(
            allMatchesOfHomeTeam,
            allMatchesOfAwayTeam,
            team1,
            team3,
            startTime2,
            endTime2
        );

        assertNotNull(newMatch);
        assertEquals(team1.getId(), newMatch.getHomeTeam().getId());
        assertEquals(team3.getId(), newMatch.getAwayTeam().getId());
        assertEquals(startTime2, newMatch.getStartTime());
        assertEquals(endTime2, newMatch.getEndTime());
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testCreateMatchWithNullAwayTeam()
    {
        TeamMatchService teamMatchService = new TeamMatchService();
        long time = System.currentTimeMillis();
        Date startTime1 = new Date(time);
        Date startTime2 = new Date(time + 50000);
        Date endTime1 = new Date(time + 5500000);
        Date endTime2 = new Date(time + 5550000);
        Team team1 = new Team("Team1");
        Team team2 = new Team("Team2");
        TeamMatch match1 = new TeamMatch(team1, team2, startTime1, endTime1);
        List<TeamMatch> allMatchesOfHomeTeam = Arrays.asList(match1);
        List<TeamMatch> allMatchesOfAwayTeam = new ArrayList<>();

        TeamMatch newMatch = teamMatchService.createMatch(
            allMatchesOfHomeTeam,
            allMatchesOfAwayTeam,
            team1,
            null,
            startTime2,
            endTime2
        );
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testCreateMatchWithSameHomeAndAwayTeam()
    {
        TeamMatchService teamMatchService = new TeamMatchService();
        long time = System.currentTimeMillis();
        Date startTime1 = new Date(time);
        Date startTime2 = new Date(time + 50000);
        Date endTime1 = new Date(time + 5500000);
        Date endTime2 = new Date(time + 5550000);
        Team team1 = new Team("Team1");
        Team team2 = new Team("Team2");
        TeamMatch match1 = new TeamMatch(team1, team2, startTime1, endTime1);
        List<TeamMatch> allMatchesOfHomeTeam = Arrays.asList(match1);
        List<TeamMatch> allMatchesOfAwayTeam = Arrays.asList(match1);

        TeamMatch newMatch = teamMatchService.createMatch(
            allMatchesOfHomeTeam,
            allMatchesOfAwayTeam,
            team1,
            team1,
            startTime2,
            endTime2
        );
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testCreateMatchWithNullStartTime()
    {
        TeamMatchService teamMatchService = new TeamMatchService();
        long time = System.currentTimeMillis();
        Date startTime1 = new Date(time);
        Date endTime1 = new Date(time + 5500000);
        Date endTime2 = new Date(time + 5550000);
        Team team1 = new Team("Team1");
        Team team2 = new Team("Team2");
        Team team3 = new Team("Team3");
        TeamMatch match1 = new TeamMatch(team1, team2, startTime1, endTime1);
        List<TeamMatch> allMatchesOfHomeTeam = Arrays.asList(match1);
        List<TeamMatch> allMatchesOfAwayTeam = new ArrayList<>();

        TeamMatch newMatch = teamMatchService.createMatch(
            allMatchesOfHomeTeam,
            allMatchesOfAwayTeam,
            team1,
            team3,
            null,
            endTime2
        );
    }

    @Test
    public void testCreateMatchWithNullEndTime()
    {
        TeamMatchService teamMatchService = new TeamMatchService();
        long time = System.currentTimeMillis();
        Date startTime1 = new Date(time);
        Date startTime2 = new Date(time + 50000);
        Date endTime1 = new Date(time + 5500000);
        Team team1 = new Team("Team1");
        Team team2 = new Team("Team2");
        Team team3 = new Team("Team3");
        TeamMatch match1 = new TeamMatch(team1, team2, startTime1, endTime1);
        List<TeamMatch> allMatchesOfHomeTeam = Arrays.asList(match1);
        List<TeamMatch> allMatchesOfAwayTeam = new ArrayList<>();

        TeamMatch newMatch = teamMatchService.createMatch(
            allMatchesOfHomeTeam,
            allMatchesOfAwayTeam,
            team1,
            team3,
            startTime2,
            null
        );

        assertNotNull(newMatch);
        assertEquals(team1.getId(), newMatch.getHomeTeam().getId());
        assertEquals(team3.getId(), newMatch.getAwayTeam().getId());
        assertEquals(startTime2, newMatch.getStartTime());
        assertNull(newMatch.getEndTime());
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testCreateMatchWithEndTimeBeforeStartTime()
    {
        TeamMatchService teamMatchService = new TeamMatchService();
        long time = System.currentTimeMillis();
        Date startTime1 = new Date(time);
        Date startTime2 = new Date(time + 50000);
        Date endTime1 = new Date(time + 5500000);
        Date endTime2 = new Date(time + 49999);
        Team team1 = new Team("Team1");
        Team team2 = new Team("Team2");
        Team team3 = new Team("Team3");
        TeamMatch match1 = new TeamMatch(team1, team2, startTime1, endTime1);
        List<TeamMatch> allMatchesOfHomeTeam = Arrays.asList(match1);
        List<TeamMatch> allMatchesOfAwayTeam = new ArrayList<>();

        TeamMatch newMatch = teamMatchService.createMatch(
            allMatchesOfHomeTeam,
            allMatchesOfAwayTeam,
            team1,
            team3,
            startTime2,
            endTime2
        );
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testCreateMatchWithStartTimeEqualToEndTime()
    {
        TeamMatchService teamMatchService = new TeamMatchService();
        long time = System.currentTimeMillis();
        Date startTime1 = new Date(time);
        Date startTime2 = new Date(time + 50000);
        Date endTime1 = new Date(time + 5500000);
        Date endTime2 = new Date(time + 50000);
        Team team1 = new Team("Team1");
        Team team2 = new Team("Team2");
        Team team3 = new Team("Team3");
        TeamMatch match1 = new TeamMatch(team1, team2, startTime1, endTime1);
        List<TeamMatch> allMatchesOfHomeTeam = Arrays.asList(match1);
        List<TeamMatch> allMatchesOfAwayTeam = new ArrayList<>();

        TeamMatch newMatch = teamMatchService.createMatch(
            allMatchesOfHomeTeam,
            allMatchesOfAwayTeam,
            team1,
            team3,
            startTime2,
            endTime2
        );
    }

    @Test
    public void testCreateMatchWithSomeHomeTeamMatchAndSameStartTime()
    {
        TeamMatchService teamMatchService = new TeamMatchService();
        long time = System.currentTimeMillis();
        Date startTime1 = new Date(time);
        Date startTime2 = new Date(time);
        Team team1 = new Team("Team1");
        Team team2 = new Team("Team2");
        Team team3 = new Team("Team3");
        TeamMatch match1 = new TeamMatch(team1, team2, startTime1);
        List<TeamMatch> allMatchesOfHomeTeam = Arrays.asList(match1);
        List<TeamMatch> allMatchesOfAwayTeam = new ArrayList<>();

        try {
            TeamMatch newMatch = teamMatchService.createMatch(
                allMatchesOfHomeTeam,
                allMatchesOfAwayTeam,
                team1,
                team3,
                startTime2,
                null
            );
            fail("Expected exception 'MatchWithSameParametersAlreadyExistsException'");
        } catch (MatchWithSameParametersAlreadyExistsException ex) {
            assertEquals(startTime2, ex.getStartTime());
            assertEquals(team1.getId(), ex.getTeamId());
        }
    }

    @Test
    public void testCreateMatchWithSomeAwayTeamMatchAndSameStartTime()
    {
        TeamMatchService teamMatchService = new TeamMatchService();
        long time = System.currentTimeMillis();
        Date startTime1 = new Date(time);
        Date startTime2 = new Date(time);
        Team team1 = new Team("Team1");
        Team team2 = new Team("Team2");
        Team team3 = new Team("Team3");
        TeamMatch match1 = new TeamMatch(team1, team2, startTime1);
        List<TeamMatch> allMatchesOfHomeTeam = new ArrayList<>();
        List<TeamMatch> allMatchesOfAwayTeam = Arrays.asList(match1);

        try {
            TeamMatch newMatch = teamMatchService.createMatch(
                allMatchesOfHomeTeam,
                allMatchesOfAwayTeam,
                team3,
                team2,
                startTime2,
                null
            );
            fail("Expected exception 'MatchWithSameParametersAlreadyExistsException'");
        } catch (MatchWithSameParametersAlreadyExistsException ex) {
            assertEquals(startTime2, ex.getStartTime());
            assertEquals(team2.getId(), ex.getTeamId());
        }
    }

    @Test
    public void testCreateMatchWithNoSameHomeTeamMatches()
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
        Team team4 = new Team("Team4");
        TeamMatch match1 = new TeamMatch(team1, team2, startTime1, endTime1);
        List<TeamMatch> allMatchesOfHomeTeam = new ArrayList<>();
        List<TeamMatch> allMatchesOfAwayTeam = new ArrayList<>();

        TeamMatch newMatch = teamMatchService.createMatch(
            allMatchesOfHomeTeam,
            allMatchesOfAwayTeam,
            team3,
            team4,
            startTime2,
            endTime2
        );

        assertNotNull(newMatch);
        assertEquals(team3.getId(), newMatch.getHomeTeam().getId());
        assertEquals(team4.getId(), newMatch.getAwayTeam().getId());
        assertEquals(startTime2, newMatch.getStartTime());
        assertEquals(endTime2, newMatch.getEndTime());
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
        Team team3 = new Team("Team3");
        Team team4 = new Team("Team4");
        TeamMatch match1 = new TeamMatch(team1, team2, startTime1, endTime1);
        TeamMatch match2 = new TeamMatch(team3, team4, startTime1);
        List<TeamMatch> sameStartTimeMatches = Arrays.asList(match1);

        teamMatchService.changeMatchTime(
            sameStartTimeMatches,
            match2,
            startTime2,
            endTime2
        );

        assertEquals(startTime2, match2.getStartTime());
        assertEquals(endTime2, match2.getEndTime());
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testChangeMatchTimeWithNullStartTime()
    {
        TeamMatchService teamMatchService = new TeamMatchService();
        long time = System.currentTimeMillis();
        Date startTime1 = new Date(time);
        Date endTime2 = new Date(time + 5550000);
        Team team3 = new Team("Team3");
        Team team4 = new Team("Team4");
        TeamMatch match2 = new TeamMatch(team3, team4, startTime1);
        List<TeamMatch> sameStartTimeMatches = new ArrayList<>();

        teamMatchService.changeMatchTime(
            sameStartTimeMatches,
            match2,
            null,
            endTime2
        );
    }

    @Test
    public void testChangeMatchTimeWithNullEndTime()
    {
        TeamMatchService teamMatchService = new TeamMatchService();
        long time = System.currentTimeMillis();
        Date startTime1 = new Date(time);
        Date startTime2 = new Date(time + 50000);
        Date endTime1 = new Date(time + 5500000);
        Team team1 = new Team("Team1");
        Team team2 = new Team("Team2");
        Team team3 = new Team("Team3");
        Team team4 = new Team("Team4");
        TeamMatch match1 = new TeamMatch(team1, team2, startTime1, endTime1);
        TeamMatch match2 = new TeamMatch(team3, team4, startTime1);
        List<TeamMatch> sameStartTimeMatches = Arrays.asList(match1);

        teamMatchService.changeMatchTime(
            sameStartTimeMatches,
            match2,
            startTime2,
            null
        );

        assertEquals(startTime2, match2.getStartTime());
        assertNull(match2.getEndTime());
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
        Team team3 = new Team("Team3");
        Team team4 = new Team("Team4");
        TeamMatch match1 = new TeamMatch(team1, team2, startTime1, endTime1);
        TeamMatch match2 = new TeamMatch(team3, team4, startTime1);
        List<TeamMatch> sameStartTimeMatches = Arrays.asList(match1);

        teamMatchService.changeMatchTime(
            sameStartTimeMatches,
            match2,
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
        Team team3 = new Team("Team3");
        Team team4 = new Team("Team4");
        TeamMatch match1 = new TeamMatch(team1, team2, startTime1, endTime1);
        TeamMatch match2 = new TeamMatch(team3, team4, startTime1);
        List<TeamMatch> sameStartTimeMatches = Arrays.asList(match1);

        teamMatchService.changeMatchTime(
            sameStartTimeMatches,
            match2,
            startTime2,
            endTime2
        );
    }

    @Test
    public void testChangeMatchTimeWithSameHomeTeamAndSameStartTime1()
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
        List<TeamMatch> sameStartTimeMatches = Arrays.asList(match1);

        try {
            teamMatchService.changeMatchTime(
                sameStartTimeMatches,
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
    public void testChangeMatchTimeWithSameHomeTeamAndSameStartTime2()
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
        TeamMatch match2 = new TeamMatch(team2, team3, startTime1);
        List<TeamMatch> sameStartTimeMatches = Arrays.asList(match1);

        try {
            teamMatchService.changeMatchTime(
                sameStartTimeMatches,
                match2,
                startTime2,
                endTime2
            );
            fail("Expected exception MatchTimeCollisionException");
        } catch (MatchTimeCollisionException ex) {
            assertEquals(match2.getId(), ex.getMatchPlannedToBeChangedId());
            assertEquals(match1.getId(), ex.getCollidingMatchId());
            assertEquals(startTime2, ex.getStartTime());
            assertEquals(team2.getId(), ex.getTeamId());
        }
    }

    @Test
    public void testChangeMatchTimeWithSameAwayTeamAndSameStartTime()
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
        List<TeamMatch> sameStartTimeMatches = Arrays.asList(match1);

        try {
            teamMatchService.changeMatchTime(
                sameStartTimeMatches,
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
    public void testChangeMatchTimeWithNoSameStartTimeMatches()
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
        TeamMatch match2 = new TeamMatch(team3, team2, startTime1);
        List<TeamMatch> sameStartTimeMatches = Arrays.asList(match1);

        try {
            teamMatchService.changeMatchTime(
                sameStartTimeMatches,
                match2,
                startTime2,
                endTime2
            );
            fail("Expected exception MatchTimeCollisionException");
        } catch (MatchTimeCollisionException ex) {
            assertEquals(match2.getId(), ex.getMatchPlannedToBeChangedId());
            assertEquals(match1.getId(), ex.getCollidingMatchId());
            assertEquals(startTime2, ex.getStartTime());
            assertEquals(team2.getId(), ex.getTeamId());
        }
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testChangeMatchTimeWithNullMatch()
    {
        TeamMatchService teamMatchService = new TeamMatchService();
        long time = System.currentTimeMillis();
        Date startTime1 = new Date(time + 50000);
        Date endTime1 = new Date(time + 5500000);
        Date endTime2 = new Date(time + 5550000);
        Team team1 = new Team("Team1");
        Team team2 = new Team("Team2");
        TeamMatch match1 = new TeamMatch(team1, team2, startTime1, endTime1);
        List<TeamMatch> sameStartTimeMatches = new ArrayList<>();

        teamMatchService.changeMatchTime(
            sameStartTimeMatches,
            null,
            startTime1,
            endTime2
        );
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
    public void testEndMatchWithNullStartTime()
    {
        TeamMatchService teamMatchService = new TeamMatchService();
        long time = System.currentTimeMillis();
        Date endTime = new Date(time + 5000000);
        Team team1 = new Team("Team1");
        Team team2 = new Team("Team2");
        TeamMatch match = new TeamMatch(team1, team2, null);

        teamMatchService.endMatch(match, endTime);
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

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testEndMatchWithNullMatch()
    {
        TeamMatchService teamMatchService = new TeamMatchService();
        long time = System.currentTimeMillis();
        Date endTime = new Date(time + 5000000);

        teamMatchService.endMatch(null, endTime);
    }

    @Test
    public void testAddNewScoredGoal()
    {
        TeamMatchService teamMatchService = new TeamMatchService();
        long time = System.currentTimeMillis();
        Date startTime = new Date(time);
        Date goalMatchTime1 = new Date(time + 15000);
        Date goalMatchTime2 = new Date(time + 31254);
        Team homeTeam = new Team("HomeTeam");
        Team awayTeam = new Team("AwayTeam");
        TeamMatch match = new TeamMatch(homeTeam, awayTeam, startTime);
        TeamPlayer scorer1 = new TeamPlayer("John", "Doe", 187, 90, homeTeam);
        TeamPlayer scorer2 = new TeamPlayer("Jack", "Smith", 178, 84, homeTeam);
        TeamPlayer assistant1 = new TeamPlayer("Alex", "Collina", 187, 90, homeTeam);
        TeamPlayer assistant2 = new TeamPlayer("Radan", "Spock", 186, 78, homeTeam);
        TeamMatchGoal goal1 = new TeamMatchGoal(scorer1, assistant1, match, goalMatchTime1);
        List<TeamMatchGoal> goalsInActualMatch = Arrays.asList(goal1);

        TeamMatchGoal newGoal = teamMatchService.addNewScoredGoal(
            goalsInActualMatch,
            scorer2,
            assistant2,
            match,
            goalMatchTime2
        );

        assertNotNull(newGoal);
        assertEquals(scorer2.getId(), newGoal.getScorer().getId());
        assertEquals(assistant2.getId(), newGoal.getAssistant().getId());
        assertEquals(match.getId(), newGoal.getMatch().getId());
        assertEquals(goalMatchTime2, newGoal.getMatchTime());
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testAddNewScoredGoalWithNullScorer()
    {
        TeamMatchService teamMatchService = new TeamMatchService();
        long time = System.currentTimeMillis();
        Date startTime = new Date(time);
        Date goalMatchTime1 = new Date(time + 15000);
        Date goalMatchTime2 = new Date(time + 31254);
        Team homeTeam = new Team("HomeTeam");
        Team awayTeam = new Team("AwayTeam");
        TeamMatch match = new TeamMatch(homeTeam, awayTeam, startTime);
        TeamPlayer scorer1 = new TeamPlayer("John", "Doe", 187, 90, homeTeam);
        TeamPlayer assistant1 = new TeamPlayer("Alex", "Collina", 187, 90, homeTeam);
        TeamPlayer assistant2 = new TeamPlayer("Radan", "Spock", 186, 78, homeTeam);
        TeamMatchGoal goal1 = new TeamMatchGoal(scorer1, assistant1, match, goalMatchTime1);
        List<TeamMatchGoal> goalsInActualMatch = Arrays.asList(goal1);

        TeamMatchGoal newGoal = teamMatchService.addNewScoredGoal(
            goalsInActualMatch,
            null,
            assistant2,
            match,
            goalMatchTime2
        );
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testAddNewScoredGoalWithNullAssistant()
    {
        TeamMatchService teamMatchService = new TeamMatchService();
        long time = System.currentTimeMillis();
        Date startTime = new Date(time);
        Date goalMatchTime1 = new Date(time + 15000);
        Date goalMatchTime2 = new Date(time + 31254);
        Team homeTeam = new Team("HomeTeam");
        Team awayTeam = new Team("AwayTeam");
        TeamMatch match = new TeamMatch(homeTeam, awayTeam, startTime);
        TeamPlayer scorer1 = new TeamPlayer("John", "Doe", 187, 90, homeTeam);
        TeamPlayer scorer2 = new TeamPlayer("Jack", "Smith", 178, 84, homeTeam);
        TeamPlayer assistant1 = new TeamPlayer("Alex", "Collina", 187, 90, homeTeam);
        TeamMatchGoal goal1 = new TeamMatchGoal(scorer1, assistant1, match, goalMatchTime1);
        List<TeamMatchGoal> goalsInActualMatch = Arrays.asList(goal1);

        TeamMatchGoal newGoal = teamMatchService.addNewScoredGoal(
            goalsInActualMatch,
            scorer2,
            null,
            match,
            goalMatchTime2
        );
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testAddNewScoredGoalWithSameScorerAndAssistant()
    {
        TeamMatchService teamMatchService = new TeamMatchService();
        long time = System.currentTimeMillis();
        Date startTime = new Date(time);
        Date goalMatchTime1 = new Date(time + 15000);
        Date goalMatchTime2 = new Date(time + 31254);
        Team homeTeam = new Team("HomeTeam");
        Team awayTeam = new Team("AwayTeam");
        TeamMatch match = new TeamMatch(homeTeam, awayTeam, startTime);
        TeamPlayer scorer1 = new TeamPlayer("John", "Doe", 187, 90, homeTeam);
        TeamPlayer scorer2 = new TeamPlayer("Jack", "Smith", 178, 84, homeTeam);
        TeamPlayer assistant1 = new TeamPlayer("Alex", "Collina", 187, 90, homeTeam);
        TeamMatchGoal goal1 = new TeamMatchGoal(scorer1, assistant1, match, goalMatchTime1);
        List<TeamMatchGoal> goalsInActualMatch = Arrays.asList(goal1);

        TeamMatchGoal newGoal = teamMatchService.addNewScoredGoal(
            goalsInActualMatch,
            scorer2,
            scorer2,
            match,
            goalMatchTime2
        );
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testAddNewScoredGoalWithNullGoalMatchTime()
    {
        TeamMatchService teamMatchService = new TeamMatchService();
        long time = System.currentTimeMillis();
        Date startTime = new Date(time);
        Date goalMatchTime1 = new Date(time + 15000);
        Team homeTeam = new Team("HomeTeam");
        Team awayTeam = new Team("AwayTeam");
        TeamMatch match = new TeamMatch(homeTeam, awayTeam, startTime);
        TeamPlayer scorer1 = new TeamPlayer("John", "Doe", 187, 90, homeTeam);
        TeamPlayer scorer2 = new TeamPlayer("Jack", "Smith", 178, 84, homeTeam);
        TeamPlayer assistant1 = new TeamPlayer("Alex", "Collina", 187, 90, homeTeam);
        TeamPlayer assistant2 = new TeamPlayer("Radan", "Spock", 186, 78, homeTeam);
        TeamMatchGoal goal1 = new TeamMatchGoal(scorer1, assistant1, match, goalMatchTime1);
        List<TeamMatchGoal> goalsInActualMatch = Arrays.asList(goal1);

        TeamMatchGoal newGoal = teamMatchService.addNewScoredGoal(
            goalsInActualMatch,
            scorer2,
            assistant2,
            match,
            null
        );
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testAddNewScoredGoalWithGoalMatchTimeBeforeMatchStartTime()
    {
        TeamMatchService teamMatchService = new TeamMatchService();
        long time = System.currentTimeMillis();
        Date startTime = new Date(time);
        Date goalMatchTime1 = new Date(time + 15000);
        Date goalMatchTime2 = new Date(time - 1);
        Team homeTeam = new Team("HomeTeam");
        Team awayTeam = new Team("AwayTeam");
        TeamMatch match = new TeamMatch(homeTeam, awayTeam, startTime);
        TeamPlayer scorer1 = new TeamPlayer("John", "Doe", 187, 90, homeTeam);
        TeamPlayer scorer2 = new TeamPlayer("Jack", "Smith", 178, 84, homeTeam);
        TeamPlayer assistant1 = new TeamPlayer("Alex", "Collina", 187, 90, homeTeam);
        TeamPlayer assistant2 = new TeamPlayer("Radan", "Spock", 186, 78, homeTeam);
        TeamMatchGoal goal1 = new TeamMatchGoal(scorer1, assistant1, match, goalMatchTime1);
        List<TeamMatchGoal> goalsInActualMatch = Arrays.asList(goal1);

        TeamMatchGoal newGoal = teamMatchService.addNewScoredGoal(
            goalsInActualMatch,
            scorer2,
            assistant2,
            match,
            goalMatchTime2
        );
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testAddNewScoredGoalWithGoalMatchTimeEqualToMatchStartTime()
    {
        TeamMatchService teamMatchService = new TeamMatchService();
        long time = System.currentTimeMillis();
        Date startTime = new Date(time);
        Date goalMatchTime1 = new Date(time + 15000);
        Date goalMatchTime2 = new Date(time);
        Team homeTeam = new Team("HomeTeam");
        Team awayTeam = new Team("AwayTeam");
        TeamMatch match = new TeamMatch(homeTeam, awayTeam, startTime);
        TeamPlayer scorer1 = new TeamPlayer("John", "Doe", 187, 90, homeTeam);
        TeamPlayer scorer2 = new TeamPlayer("Jack", "Smith", 178, 84, homeTeam);
        TeamPlayer assistant1 = new TeamPlayer("Alex", "Collina", 187, 90, homeTeam);
        TeamPlayer assistant2 = new TeamPlayer("Radan", "Spock", 186, 78, homeTeam);
        TeamMatchGoal goal1 = new TeamMatchGoal(scorer1, assistant1, match, goalMatchTime1);
        List<TeamMatchGoal> goalsInActualMatch = Arrays.asList(goal1);

        TeamMatchGoal newGoal = teamMatchService.addNewScoredGoal(
            goalsInActualMatch,
            scorer2,
            assistant2,
            match,
            goalMatchTime2
        );
    }

    @Test
    public void testAddNewScoredGoalWithGoalMatchTimeAfterMatchEndTime()
    {
        TeamMatchService teamMatchService = new TeamMatchService();
        long time = System.currentTimeMillis();
        Date startTime = new Date(time);
        Date goalMatchTime1 = new Date(time + 15000);
        Date goalMatchTime2 = new Date(time + 5500001);
        Team homeTeam = new Team("HomeTeam");
        Team awayTeam = new Team("AwayTeam");
        TeamMatch match = new TeamMatch(homeTeam, awayTeam, startTime);
        TeamPlayer scorer1 = new TeamPlayer("John", "Doe", 187, 90, homeTeam);
        TeamPlayer scorer2 = new TeamPlayer("Jack", "Smith", 178, 84, homeTeam);
        TeamPlayer assistant1 = new TeamPlayer("Alex", "Collina", 187, 90, homeTeam);
        TeamPlayer assistant2 = new TeamPlayer("Radan", "Spock", 186, 78, homeTeam);
        TeamMatchGoal goal1 = new TeamMatchGoal(scorer1, assistant1, match, goalMatchTime1);
        List<TeamMatchGoal> goalsInActualMatch = Arrays.asList(goal1);

        TeamMatchGoal newGoal = teamMatchService.addNewScoredGoal(
            goalsInActualMatch,
            scorer2,
            assistant2,
            match,
            goalMatchTime2
        );
    }

    @Test
    public void testAddNewScoredGoalWithGoalMatchTimeEqualToMatchEndTime()
    {
        TeamMatchService teamMatchService = new TeamMatchService();
        long time = System.currentTimeMillis();
        Date startTime = new Date(time);
        Date goalMatchTime1 = new Date(time + 15000);
        Date goalMatchTime2 = new Date(time + 5500000);
        Team homeTeam = new Team("HomeTeam");
        Team awayTeam = new Team("AwayTeam");
        TeamMatch match = new TeamMatch(homeTeam, awayTeam, startTime);
        TeamPlayer scorer1 = new TeamPlayer("John", "Doe", 187, 90, homeTeam);
        TeamPlayer scorer2 = new TeamPlayer("Jack", "Smith", 178, 84, homeTeam);
        TeamPlayer assistant1 = new TeamPlayer("Alex", "Collina", 187, 90, homeTeam);
        TeamPlayer assistant2 = new TeamPlayer("Radan", "Spock", 186, 78, homeTeam);
        TeamMatchGoal goal1 = new TeamMatchGoal(scorer1, assistant1, match, goalMatchTime1);
        List<TeamMatchGoal> goalsInActualMatch = Arrays.asList(goal1);

        TeamMatchGoal newGoal = teamMatchService.addNewScoredGoal(
            goalsInActualMatch,
            scorer2,
            assistant2,
            match,
            goalMatchTime2
        );
    }

    @Test
    public void testAddNewScoredGoalWithAlreadyExistingGoal()
    {
        TeamMatchService teamMatchService = new TeamMatchService();
        long time = System.currentTimeMillis();
        Date startTime = new Date(time);
        Date goalMatchTime1 = new Date(time + 15000);
        Team homeTeam = new Team("HomeTeam");
        Team awayTeam = new Team("AwayTeam");
        TeamMatch match = new TeamMatch(homeTeam, awayTeam, startTime);
        TeamPlayer scorer1 = new TeamPlayer("John", "Doe", 187, 90, homeTeam);
        TeamPlayer assistant1 = new TeamPlayer("Alex", "Collina", 187, 90, homeTeam);
        TeamMatchGoal goal1 = new TeamMatchGoal(scorer1, assistant1, match, goalMatchTime1);
        List<TeamMatchGoal> goalsInActualMatch = Arrays.asList(goal1);

        try {
            TeamMatchGoal newGoal = teamMatchService.addNewScoredGoal(
                goalsInActualMatch,
                scorer1,
                assistant1,
                match,
                goalMatchTime1
            );
            fail("Expected exception GoalWithSameParametersAlreadyExistsException");
        } catch (GoalWithSameParametersAlreadyExistsException ex) {
            assertEquals(scorer1.getId(), ex.getScorerId());
            assertEquals(assistant1.getId(), ex.getAssistantId());
            assertEquals(match.getId(), ex.getMatchId());
            assertEquals(goalMatchTime1, ex.getMatchTime());
        }
    }
}
