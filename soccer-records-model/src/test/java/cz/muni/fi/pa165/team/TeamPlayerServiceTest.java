package cz.muni.fi.pa165.team;

import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

/**
 * @author Libor Mühlpachr <libor.muhl@seznam.cz>
 */
public class TeamPlayerServiceTest
{

    @Test
    public void testCreateTeamPlayer() throws Exception
    {
        TeamPlayerService teamPlayerService = new TeamPlayerService();

        TeamService teamService = new TeamService();
        Team team = teamService.createTeam("Liverpool", null);

        TeamPlayer teamPlayer = teamPlayerService.createTeamPlayer("Filip", "Procházka", 185, 80, team);

        assertEquals("Filip", teamPlayer.getFirstname());
        assertEquals("Procházka", teamPlayer.getSurname());
        assertEquals(185, teamPlayer.getHeight());
        assertEquals(80, teamPlayer.getWeight());
        assertEquals(team, teamPlayer.getTeam());
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testCreateTeamPlayerWithNullFirstname()
    {
        TeamPlayerService teamPlayerService = new TeamPlayerService();

        TeamService teamService = new TeamService();
        Team team = teamService.createTeam("Liverpool", null);

        TeamPlayer newTeamPlayer = teamPlayerService.createTeamPlayer(null, "Procházka", 185, 80, team);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testCreateTeamPlayerWithNullSurname()
    {
        TeamPlayerService teamPlayerService = new TeamPlayerService();

        TeamService teamService = new TeamService();
        Team team = teamService.createTeam("Liverpool", null);

        TeamPlayer newTeamPlayer = teamPlayerService.createTeamPlayer("Filip", null, 185, 80, team);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testCreateTeamPlayerWithNullHeight()
    {
        TeamPlayerService teamPlayerService = new TeamPlayerService();

        TeamService teamService = new TeamService();
        Team team = teamService.createTeam("Liverpool", null);

        TeamPlayer newTeamPlayer = teamPlayerService.createTeamPlayer("Filip", "Procházka", 0, 80, team);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testCreateTeamPlayerWithNullWeight()
    {
        TeamPlayerService teamPlayerService = new TeamPlayerService();

        TeamService teamService = new TeamService();
        Team team = teamService.createTeam("Liverpool", null);

        TeamPlayer newTeamPlayer = teamPlayerService.createTeamPlayer("Filip", "Procházka", 185, 0, team);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testCreateTeamPlayerWithNullTeam()
    {
        TeamPlayerService teamPlayerService = new TeamPlayerService();

        TeamPlayer newTeamPlayer = teamPlayerService.createTeamPlayer("Filip", "Procházka", 185, 80, null);
    }

    @Test
    public void testCreateTeamPlayerWithNegativeHeight() throws Exception
    {
        TeamPlayerService teamPlayerService = new TeamPlayerService();

        TeamService teamService = new TeamService();
        Team team = teamService.createTeam("Liverpool", null);

        TeamPlayer newTeamPlayer = teamPlayerService.createTeamPlayer("Filip", "Procházka", -185, 80, team);

        assertNotNull(newTeamPlayer);
        assertTrue(newTeamPlayer.getHeight() < 0);
    }

    @Test
    public void testCreateTeamPlayerWithNegativeWeight() throws Exception
    {
        TeamPlayerService teamPlayerService = new TeamPlayerService();

        TeamService teamService = new TeamService();
        Team team = teamService.createTeam("Liverpool", null);

        TeamPlayer newTeamPlayer = teamPlayerService.createTeamPlayer("Filip", "Procházka", 185, -80, team);

        assertNotNull(newTeamPlayer);
        assertTrue(newTeamPlayer.getWeight() < 0);
    }
}
