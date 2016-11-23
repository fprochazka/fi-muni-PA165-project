package cz.muni.fi.pa165.team;

import org.testng.annotations.Test;
import java.util.Date;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;

/**
 * @author Tomas Smid <smid.thomas@gmail.com>
 */
public class TeamMatchTest
{
    @Test
    public void testChangeMatchTime(){
        TeamMatch teamMatch = getTeamMatch();
        Date newStartTime = new Date(teamMatch.getStartTime().getTime()+15000);
        Date newEndTime = new Date(newStartTime.getTime()+5420000);

        teamMatch.changeMatchTime(newStartTime,newEndTime);

        assertEquals(newStartTime,teamMatch.getStartTime());
        assertEquals(newEndTime,teamMatch.getEndTime());
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testChangeMatchTimeNullStartTime(){
        TeamMatch teamMatch = getTeamMatch();
        Date newEndTime = new Date(teamMatch.getStartTime().getTime()+50000);

        teamMatch.changeMatchTime(null, newEndTime);
    }

    @Test
    public void testChangeMatchTimeNullEndTime(){
        TeamMatch teamMatch = getTeamMatch();
        Date newStartTime = new Date(teamMatch.getStartTime().getTime()+2000);

        teamMatch.changeMatchTime(newStartTime, null);

        assertEquals(newStartTime, teamMatch.getStartTime());
        assertNull(teamMatch.getEndTime());
    }

    @Test
    public void testChangeMatchTimeValidEndTimeToNullEndTime(){
        long time = System.currentTimeMillis();
        TeamMatch teamMatch = getTeamMatch(new Date(time + 15000000));
        Date newStartTime = new Date();

        assertEquals(new Date(time + 15000000), teamMatch.getEndTime());

        teamMatch.changeMatchTime(newStartTime, null);

        assertEquals(newStartTime, teamMatch.getStartTime());
        assertNull(teamMatch.getEndTime());
    }

    @Test
    public void testEndMatch(){
        TeamMatch teamMatch = getTeamMatch();
        Date newEndTime = new Date(teamMatch.getStartTime().getTime() + 5400000);

        assertNull(teamMatch.getEndTime());

        teamMatch.endMatch(newEndTime);

        assertEquals(newEndTime, teamMatch.getEndTime());
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testEndMatchNullEndTimeToNullEndTime(){
        TeamMatch teamMatch = getTeamMatch();

        teamMatch.endMatch(null);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testEndMatchValidEndTimeToNullEndTime(){
        long time = System.currentTimeMillis();
        TeamMatch teamMatch = getTeamMatch(new Date(time + 1500000));

        assertEquals(new Date(time + 1500000), teamMatch.getEndTime());

        teamMatch.endMatch(null);
    }

    private TeamMatch getTeamMatch(){
        return getTeamMatch(null);
    }

    private TeamMatch getTeamMatch(Date endTime){
        return (endTime == null ?
            new TeamMatch(new Team("HomeTeam"), new Team("AwayTeam"), new Date()) :
            new TeamMatch(new Team("HomeTeam"), new Team("AwayTeam"), new Date(), new Date(endTime.getTime())));
    }
}
