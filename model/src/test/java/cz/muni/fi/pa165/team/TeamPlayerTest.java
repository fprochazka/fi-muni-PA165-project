package cz.muni.fi.pa165.team;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.Test;

/**
 * @author Libor Mühlpachr <libor.muhl@seznam.cz>
 */
public class TeamPlayerTest
{

    @Test
    public void changeFirstname()
    {
        TeamPlayer teamPlayer = getTeamPlayer();

        teamPlayer.changeFirstname("Karel");

        assertEquals("Karel", teamPlayer.getFirstname());
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testChangeTeamPlayerFirstnameToNull()
    {
        TeamPlayer teamPlayer = getTeamPlayer();

        teamPlayer.changeFirstname(null);
    }

    @Test
    public void changeSurname()
    {
        TeamPlayer teamPlayer = getTeamPlayer();

        teamPlayer.changeSurname("Novák");

        assertEquals("Novák", teamPlayer.getSurname());
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testChangeTeamPlayerSurnameToNull()
    {
        TeamPlayer teamPlayer = getTeamPlayer();

        teamPlayer.changeSurname(null);
    }

    @Test
    public void changeHeight()
    {
        TeamPlayer teamPlayer = getTeamPlayer();

        teamPlayer.changeHeight(200);

        assertEquals(200, teamPlayer.getHeight());
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testChangeTeamPlayerHeightToNull()
    {
        TeamPlayer teamPlayer = getTeamPlayer();

        teamPlayer.changeHeight(0);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testChangeTeamPlayerHeightToNegativeValue()
    {
        TeamPlayer teamPlayer = getTeamPlayer();

        teamPlayer.changeHeight(-180);
    }

    @Test
    public void changeWeight()
    {
        TeamPlayer teamPlayer = getTeamPlayer();

        teamPlayer.changeWeight(70);

        assertEquals(70, teamPlayer.getWeight());
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testChangeTeamPlayerWeightToNull()
    {
        TeamPlayer teamPlayer = getTeamPlayer();

        teamPlayer.changeWeight(0);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testChangeTeamPlayerWeightToNegativeValue()
    {
        TeamPlayer teamPlayer = getTeamPlayer();

        teamPlayer.changeWeight(-70);
    }

    private TeamPlayer getTeamPlayer()
    {
        TeamService teamService = new TeamService();
        Team team = teamService.createTeam("Liverpool", null);

        return new TeamPlayer("Test", "Player", 180, 80, team);
    }
}
