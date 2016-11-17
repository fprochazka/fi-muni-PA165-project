package cz.muni.fi.pa165.team;

import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * @author Denis Galajda <galajda.denis@gmail.com>
 */
public class TeamServiceTest
{

    @Test
    public void testCreateUser() throws Exception
    {
        TeamService teamService = new TeamService();

        Team team = teamService.createTeam("ManUtd", null);
        assertEquals("ManUtd", team.getName());
    }
}
