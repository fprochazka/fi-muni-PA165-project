package cz.muni.fi.pa165.team;

import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * @author Denis Galajda <galajda.denis@gmail.com>
 */
public class TeamServiceTest
{

    @Test
    public void testCreateTeam() throws Exception
    {
        TeamService teamService = new TeamService();

        Team team = teamService.createTeam("ManUtd", null);
        assertEquals("ManUtd", team.getName());
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testCreateTeamWithNullNameThrows() throws Exception
    {
        TeamService teamService = new TeamService();
        teamService.createTeam(null, null);
    }
}
