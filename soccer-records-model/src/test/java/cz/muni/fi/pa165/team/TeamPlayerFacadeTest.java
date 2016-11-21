package cz.muni.fi.pa165.team;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.annotations.Test;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.testng.Assert.*;

/**
 * @author Libor Mühlpachr <libor.muhl@seznam.cz>
 */
public class TeamPlayerFacadeTest extends AbstractTransactionalTestNGSpringContextTests
{

    public TeamPlayerFacade teamPlayerFacade;

    @Autowired
    public TeamPlayerService teamPlayerService;

    @PersistenceContext
    public EntityManager em;

    @Test
    public void testCreateTeamPlayer() throws Exception
    {
        TeamService teamService = new TeamService();
        Team team = teamService.createTeam("Liverpool", null);

        TeamPlayer teamPlayer = teamPlayerFacade.createTeamPlayer("Filip", "Procházka", 185, 80, team);
        em.clear();

        TeamPlayer foundPlayer = em.find(TeamPlayer.class, teamPlayer.getId());

        assertNotNull(foundPlayer);
        assertEquals(foundPlayer.getFirstname(), "Filip");
        assertEquals(foundPlayer.getSurname(), "Procházka");
        assertEquals(foundPlayer.getHeight(), 185);
        assertEquals(foundPlayer.getWeight(), 90);
        assertEquals(foundPlayer.getTeam(), team);
    }

    @Test
    public void testDeleteTeamPlayer() throws Exception
    {
        TeamService teamService = new TeamService();
        Team team = teamService.createTeam("Liverpool", null);

        TeamPlayer teamPlayer = teamPlayerFacade.createTeamPlayer("Filip", "Procházka", 185, 80, team);
        em.clear();

        TeamPlayer dbPlayer = em.find(TeamPlayer.class, teamPlayer.getId());
        assertNotNull(dbPlayer);

        teamPlayerFacade.deleteTeamPlayer(teamPlayer.getId());
        dbPlayer = em.find(TeamPlayer.class, teamPlayer.getId());

        assertNull(dbPlayer);
    }

    @Test
    public void testChangeTeamPlayerFirstname() throws Exception
    {
        TeamService teamService = new TeamService();
        Team team = teamService.createTeam("Liverpool", null);

        TeamPlayer teamPlayer = teamPlayerService.createTeamPlayer("Filip", "Procházka", 185, 80, team);
        em.persist(team);
        em.flush();
        em.clear();

        teamPlayerFacade.changeTeamPlayerFirstname(teamPlayer.getId(), "Tomáš");
        em.clear();

        TeamPlayer foundTeamPlayer = em.find(TeamPlayer.class, teamPlayer.getId());
        assertEquals(foundTeamPlayer.getFirstname(), "Tomáš");
    }

    @Test
    public void testChangeTeamPlayerSurname() throws Exception
    {
        TeamService teamService = new TeamService();
        Team team = teamService.createTeam("Liverpool", null);

        TeamPlayer teamPlayer = teamPlayerService.createTeamPlayer("Filip", "Procházka", 185, 80, team);
        em.persist(team);
        em.flush();
        em.clear();

        teamPlayerFacade.changeTeamPlayerSurname(teamPlayer.getId(), "Novák");
        em.clear();

        TeamPlayer foundTeamPlayer = em.find(TeamPlayer.class, teamPlayer.getId());
        assertEquals(foundTeamPlayer.getSurname(), "Novák");
    }

    @Test
    public void testChangeTeamPlayerHeight() throws Exception
    {
        TeamService teamService = new TeamService();
        Team team = teamService.createTeam("Liverpool", null);

        TeamPlayer teamPlayer = teamPlayerService.createTeamPlayer("Filip", "Procházka", 185, 80, team);
        em.persist(team);
        em.flush();
        em.clear();

        teamPlayerFacade.changeTeamPlayerHeight(teamPlayer.getId(), 190);
        em.clear();

        TeamPlayer foundTeamPlayer = em.find(TeamPlayer.class, teamPlayer.getId());
        assertEquals(foundTeamPlayer.getHeight(), 190);
    }

    @Test
    public void testChangeTeamPlayerWeight() throws Exception
    {
        TeamService teamService = new TeamService();
        Team team = teamService.createTeam("Liverpool", null);

        TeamPlayer teamPlayer = teamPlayerService.createTeamPlayer("Filip", "Procházka", 185, 80, team);
        em.persist(team);
        em.flush();
        em.clear();

        teamPlayerFacade.changeTeamPlayerWeight(teamPlayer.getId(), 70);
        em.clear();

        TeamPlayer foundTeamPlayer = em.find(TeamPlayer.class, teamPlayer.getId());
        assertEquals(foundTeamPlayer.getWeight(), 70);
    }
}
