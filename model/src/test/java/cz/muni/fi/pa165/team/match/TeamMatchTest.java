package cz.muni.fi.pa165.team.match;

import cz.muni.fi.pa165.team.Team;
import org.testng.annotations.Test;

import java.time.LocalDateTime;

import static org.testng.Assert.*;

/**
 * @author Tomas Smid <smid.thomas@gmail.com>
 */
public class TeamMatchTest
{

    private LocalDateTime now = LocalDateTime.now();

    @Test
    public void testChangeMatchTime()
    {
        TeamMatch teamMatch = new TeamMatch(new Team("HomeTeam"), new Team("AwayTeam"), now);
        LocalDateTime newStartTime = now.plusMinutes(15);
        LocalDateTime newEndTime = now.plusMinutes(75);

        teamMatch.changeMatchTime(newStartTime, newEndTime);

        assertEquals(newStartTime, teamMatch.getStartTime());
        assertEquals(newEndTime, teamMatch.getEndTime());
    }

    @Test(expectedExceptions = {IllegalArgumentException.class},
        expectedExceptionsMessageRegExp = "Match start time is null")
    public void testChangeMatchTimeNullStartTime()
    {
        TeamMatch teamMatch = new TeamMatch(new Team("HomeTeam"), new Team("AwayTeam"), now);
        LocalDateTime newEndTime = now.plusMinutes(5);

        teamMatch.changeMatchTime(null, newEndTime);
    }

    @Test
    public void testChangeMatchTimeNullEndTime()
    {
        TeamMatch teamMatch = new TeamMatch(new Team("HomeTeam"), new Team("AwayTeam"), now);
        LocalDateTime newStartTime = now.plusMinutes(5);

        teamMatch.changeMatchTime(newStartTime, null);

        assertEquals(newStartTime, teamMatch.getStartTime());
        assertNull(teamMatch.getEndTime());
    }

    @Test
    public void testChangeMatchTimeValidEndTimeToNullEndTime()
    {
        TeamMatch teamMatch = new TeamMatch(new Team("HomeTeam"), new Team("AwayTeam"), now, now.plusMinutes(15));
        LocalDateTime newStartTime = now;

        assertEquals(now.plusMinutes(15), teamMatch.getEndTime());

        teamMatch.changeMatchTime(newStartTime, null);

        assertEquals(newStartTime, teamMatch.getStartTime());
        assertNull(teamMatch.getEndTime());
    }

    @Test(expectedExceptions = {IllegalArgumentException.class},
        expectedExceptionsMessageRegExp = "Match end time is not after start time")
    public void testChangeMatchTimeWithEndTimeBeforeStartTime()
    {
        TeamMatch teamMatch = new TeamMatch(new Team("HomeTeam"), new Team("AwayTeam"), now, now.plusMinutes(15));
        LocalDateTime newStartTime = now.plusMinutes(1);

        assertEquals(now.plusMinutes(15), teamMatch.getEndTime());

        teamMatch.changeMatchTime(newStartTime, now);

        assertEquals(newStartTime, teamMatch.getStartTime());
        assertNull(teamMatch.getEndTime());
    }

    @Test(expectedExceptions = {IllegalArgumentException.class},
        expectedExceptionsMessageRegExp = "Match end time is not after start time")
    public void testChangeMatchTimeWithEndTimeEqualToStartTime()
    {
        TeamMatch teamMatch = new TeamMatch(new Team("HomeTeam"), new Team("AwayTeam"), now, now.plusMinutes(15));
        LocalDateTime newStartTime = now.plusMinutes(1);

        assertEquals(now.plusMinutes(15), teamMatch.getEndTime());

        teamMatch.changeMatchTime(newStartTime, now.plusMinutes(1));

        assertEquals(newStartTime, teamMatch.getStartTime());
        assertNull(teamMatch.getEndTime());
    }

    @Test
    public void testEndMatch()
    {
        TeamMatch teamMatch = new TeamMatch(new Team("HomeTeam"), new Team("AwayTeam"), now);
        LocalDateTime newEndTime = now.plusMinutes(55);

        assertNull(teamMatch.getEndTime());

        teamMatch.endMatch(newEndTime);

        assertEquals(newEndTime, teamMatch.getEndTime());
    }

    @Test(expectedExceptions = {IllegalArgumentException.class},
        expectedExceptionsMessageRegExp = "Match end time is not after start time")
    public void testEndMatchWithEndTimeBeforeStartTime()
    {
        TeamMatch teamMatch = new TeamMatch(new Team("HomeTeam"), new Team("AwayTeam"), now, now.plusMinutes(15));

        assertEquals(now.plusMinutes(15), teamMatch.getEndTime());

        teamMatch.endMatch(now.minusSeconds(1));
    }

    @Test(expectedExceptions = {IllegalArgumentException.class},
        expectedExceptionsMessageRegExp = "Match end time is not after start time")
    public void testEndMatchWithEndTimeEqualToStartTime()
    {
        TeamMatch teamMatch = new TeamMatch(new Team("HomeTeam"), new Team("AwayTeam"), now, now.plusMinutes(15));

        assertEquals(now.plusMinutes(15), teamMatch.getEndTime());

        teamMatch.endMatch(now);
    }

    @Test(expectedExceptions = {IllegalArgumentException.class},
        expectedExceptionsMessageRegExp = "Cannot create match with a null home team")
    public void testCreatingMatchWithNullHomeTeam()
    {
        TeamMatch teamMatch = new TeamMatch(
            null,
            new Team("AwayTeam"),
            now.plusSeconds(1),
            now.plusSeconds(15)
        );
    }

    @Test(expectedExceptions = {IllegalArgumentException.class},
        expectedExceptionsMessageRegExp = "Cannot create match with a null away team")
    public void testCreatingMatchWithNullAwayTeam()
    {
        TeamMatch teamMatch = new TeamMatch(
            new Team("HomeTeam"),
            null,
            now.plusSeconds(1),
            now.plusSeconds(15)
        );
    }

    @Test(expectedExceptions = {IllegalArgumentException.class},
        expectedExceptionsMessageRegExp = "Match start time is null")
    public void testCreatingMatchWithNullStartTime()
    {
        TeamMatch teamMatch = new TeamMatch(
            new Team("HomeTeam"),
            new Team("AwayTeam"),
            null,
            now.plusSeconds(15)
        );
    }

    @Test
    public void testCreatingMatchWithNullEndTime()
    {
        Team homeTeam = new Team("HomeTeam");
        Team awayTeam = new Team("AwayTeam");
        LocalDateTime startTime = now.plusSeconds(1);

        TeamMatch teamMatch = new TeamMatch(
            homeTeam,
            awayTeam,
            startTime,
            null
        );

        assertNotNull(teamMatch);
        assertEquals(homeTeam, teamMatch.getHomeTeam());
        assertEquals(awayTeam, teamMatch.getAwayTeam());
        assertEquals(startTime, teamMatch.getStartTime());
        assertNull(teamMatch.getEndTime());
    }

    @Test(expectedExceptions = {IllegalArgumentException.class},
        expectedExceptionsMessageRegExp = "Match end time is not after start time")
    public void testCreatingMatchWithEndTimeBeforeStartTime()
    {
        TeamMatch teamMatch = new TeamMatch(
            new Team("HomeTeam"),
            new Team("AwayTeam"),
            now.plusSeconds(16),
            now.plusSeconds(15)
        );
    }

    @Test(expectedExceptions = {IllegalArgumentException.class},
        expectedExceptionsMessageRegExp = "Match end time is not after start time")
    public void testCreatingMatchWithEndTimeEqualToStartTime()
    {
        TeamMatch teamMatch = new TeamMatch(
            new Team("HomeTeam"),
            new Team("AwayTeam"),
            now.plusSeconds(15),
            now.plusSeconds(15)
        );
    }
}
