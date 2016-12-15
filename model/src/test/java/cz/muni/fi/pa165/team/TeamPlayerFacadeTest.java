package cz.muni.fi.pa165.team;

import cz.muni.fi.pa165.config.ModelConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.annotations.Test;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.testng.Assert.*;

/**
 * @author Libor Mühlpachr <libor.muhl@seznam.cz>
 */
@ContextConfiguration(classes= ModelConfig.class)
public class TeamPlayerFacadeTest extends AbstractTransactionalTestNGSpringContextTests
{

    @Autowired
    public TeamPlayerFacade teamPlayerFacade;

    @Autowired
    public TeamPlayerService teamPlayerService;

    @Autowired
    public TeamService teamService;

    @PersistenceContext
    public EntityManager em;

    @Test
    public void testCreateTeamPlayer() throws Exception
    {
        Team team = teamService.createTeam("Liverpool", null);
        em.persist(team);

        TeamPlayer teamPlayer = teamPlayerFacade.createTeamPlayer("Filip", "Procházka", 185, 80, team);
        em.clear();

        TeamPlayer foundPlayer = em.find(TeamPlayer.class, teamPlayer.getId());

        assertNotNull(foundPlayer);
        assertEquals(foundPlayer.getFirstname(), "Filip");
        assertEquals(foundPlayer.getSurname(), "Procházka");
        assertEquals(foundPlayer.getHeight(), 185);
        assertEquals(foundPlayer.getWeight(), 80);
        assertEquals(foundPlayer.getTeam().getId(), team.getId()); // em.clear => different ref
    }

    @Test
    public void testDeleteTeamPlayer() throws Exception
    {
        Team team = teamService.createTeam("Liverpool", null);
        em.persist(team);

        TeamPlayer teamPlayer = teamPlayerService.createTeamPlayer("Filip", "Procházka", 185, 80, team);
        em.persist(teamPlayer);
        em.flush();
        em.clear();

        teamPlayerFacade.deleteTeamPlayer(teamPlayer.getId());
        em.clear();

        TeamPlayer dbPlayer = em.find(TeamPlayer.class, teamPlayer.getId());
        assertNull(dbPlayer);
    }

    @Test
    public void testChangeTeamPlayerFirstname() throws Exception
    {
        Team team = teamService.createTeam("Liverpool", null);
        em.persist(team);

        TeamPlayer teamPlayer = teamPlayerService.createTeamPlayer("Filip", "Procházka", 185, 80, team);
        em.persist(teamPlayer);
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
        Team team = teamService.createTeam("Liverpool", null);
        em.persist(team);

        TeamPlayer teamPlayer = teamPlayerService.createTeamPlayer("Filip", "Procházka", 185, 80, team);
        em.persist(teamPlayer);
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
        Team team = teamService.createTeam("Liverpool", null);
        em.persist(team);

        TeamPlayer teamPlayer = teamPlayerService.createTeamPlayer("Filip", "Procházka", 185, 80, team);
        em.persist(teamPlayer);
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
        Team team = teamService.createTeam("Liverpool", null);
        em.persist(team);

        TeamPlayer teamPlayer = teamPlayerService.createTeamPlayer("Filip", "Procházka", 185, 80, team);
        em.persist(teamPlayer);
        em.flush();
        em.clear();

        teamPlayerFacade.changeTeamPlayerWeight(teamPlayer.getId(), 70);
        em.clear();

        TeamPlayer foundTeamPlayer = em.find(TeamPlayer.class, teamPlayer.getId());
        assertEquals(foundTeamPlayer.getWeight(), 70);
    }
}
