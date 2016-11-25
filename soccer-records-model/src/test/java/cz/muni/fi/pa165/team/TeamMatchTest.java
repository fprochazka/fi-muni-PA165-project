package cz.muni.fi.pa165.team;

import org.testng.annotations.Test;

import java.util.Date;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertNotNull;

/**
 * @author Tomas Smid <smid.thomas@gmail.com>
 */
public class TeamMatchTest
{

    @Test
    public void testChangeMatchTime()
    {
        TeamMatch teamMatch = getTeamMatch(0l);
        Date newStartTime = new Date(teamMatch.getStartTime().getTime() + 15000);
        Date newEndTime = new Date(newStartTime.getTime() + 5420000);

        teamMatch.changeMatchTime(newStartTime, newEndTime);

        assertEquals(newStartTime, teamMatch.getStartTime());
        assertEquals(newEndTime, teamMatch.getEndTime());
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testChangeMatchTimeNullStartTime()
    {
        TeamMatch teamMatch = getTeamMatch(0l);
        Date newEndTime = new Date(teamMatch.getStartTime().getTime() + 50000);

        teamMatch.changeMatchTime(null, newEndTime);
    }

    @Test
    public void testChangeMatchTimeNullEndTime()
    {
        TeamMatch teamMatch = getTeamMatch(0l);
        Date newStartTime = new Date(teamMatch.getStartTime().getTime() + 2000);

        teamMatch.changeMatchTime(newStartTime, null);

        assertEquals(newStartTime, teamMatch.getStartTime());
        assertNull(teamMatch.getEndTime());
    }

    @Test
    public void testChangeMatchTimeValidEndTimeToNullEndTime()
    {
        long time = System.currentTimeMillis();
        TeamMatch teamMatch = getTeamMatch(time, new Date(time + 1500000));
        Date newStartTime = new Date();

        assertEquals(new Date(time + 1500000), teamMatch.getEndTime());

        teamMatch.changeMatchTime(newStartTime, null);

        assertEquals(newStartTime, teamMatch.getStartTime());
        assertNull(teamMatch.getEndTime());
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testChangeMatchTimeWithEndTimeBeforeStartTime(){
        long time = System.currentTimeMillis();
        TeamMatch teamMatch = getTeamMatch(time, new Date(time + 1500000));
        Date newStartTime = new Date(time + 1000);

        assertEquals(new Date(time + 1500000), teamMatch.getEndTime());

        teamMatch.changeMatchTime(newStartTime, new Date(time));

        assertEquals(newStartTime, teamMatch.getStartTime());
        assertNull(teamMatch.getEndTime());
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testChangeMatchTimeWithEndTimeEqualToStartTime(){
        long time = System.currentTimeMillis();
        TeamMatch teamMatch = getTeamMatch(time, new Date(time + 1500000));
        Date newStartTime = new Date(time + 1000);

        assertEquals(new Date(time + 1500000), teamMatch.getEndTime());

        teamMatch.changeMatchTime(newStartTime, new Date(time + 1000));

        assertEquals(newStartTime, teamMatch.getStartTime());
        assertNull(teamMatch.getEndTime());
    }

    @Test
    public void testEndMatch()
    {
        TeamMatch teamMatch = getTeamMatch(0l);
        Date newEndTime = new Date(teamMatch.getStartTime().getTime() + 5400000);

        assertNull(teamMatch.getEndTime());

        teamMatch.endMatch(newEndTime);

        assertEquals(newEndTime, teamMatch.getEndTime());
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testEndMatchNullEndTimeToNullEndTime()
    {
        TeamMatch teamMatch = getTeamMatch(0l);

        teamMatch.endMatch(null);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testEndMatchValidEndTimeToNullEndTime()
    {
        long time = System.currentTimeMillis();
        TeamMatch teamMatch = getTeamMatch(time, new Date(time + 150000));

        assertEquals(new Date(time + 150000), teamMatch.getEndTime());

        teamMatch.endMatch(null);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testEndMatchWithEndTimeBeforeStartTime()
    {
        long time = System.currentTimeMillis();
        TeamMatch teamMatch = getTeamMatch(time, new Date(time + 150000));

        assertEquals(new Date(time + 150000), teamMatch.getEndTime());

        teamMatch.endMatch(new Date(time-1));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testEndMatchWithEndTimeEqualToStartTime()
    {
        long time = System.currentTimeMillis();
        TeamMatch teamMatch = getTeamMatch(time, new Date(time + 150000));

        assertEquals(new Date(time + 150000), teamMatch.getEndTime());

        teamMatch.endMatch(new Date(time));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testCreatingMatchWithNullHomeTeam(){
        TeamMatch teamMatch = new TeamMatch(
            null,
            new Team("AwayTeam"),
            new Date(15),
            new Date(1500)
        );
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testCreatingMatchWithNullAwayTeam(){
        TeamMatch teamMatch = new TeamMatch(
            new Team("HomeTeam"),
            null,
            new Date(15),
            new Date(1500)
        );
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testCreatingMatchWithNullStartTime(){
        TeamMatch teamMatch = new TeamMatch(
            new Team("HomeTeam"),
            new Team("AwayTeam"),
            null,
            new Date(1500)
        );
    }

    @Test
    public void testCreatingMatchWithNullEndTime(){
        Team homeTeam = new Team("HomeTeam");
        Team awayTeam = new Team("AwayTeam");
        Date startTime = new Date(15);

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

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testCreatingMatchWithEndTimeBeforeStartTime(){
        TeamMatch teamMatch = new TeamMatch(
            new Team("HomeTeam"),
            new Team("AwayTeam"),
            new Date(1501),
            new Date(1500)
        );
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testCreatingMatchWithEndTimeEqualToStartTime(){
        TeamMatch teamMatch = new TeamMatch(
            new Team("HomeTeam"),
            new Team("AwayTeam"),
            new Date(1500),
            new Date(1500)
        );
    }

    private TeamMatch getTeamMatch(long millis)
    {
        return getTeamMatch(millis, null);
    }

    private TeamMatch getTeamMatch(long millis, Date endTime)
    {
        return (endTime == null ?
            new TeamMatch(new Team("HomeTeam"), new Team("AwayTeam"), new Date(millis)) :
            new TeamMatch(new Team("HomeTeam"), new Team("AwayTeam"), new Date(millis), new Date(endTime.getTime())));
    }
}
