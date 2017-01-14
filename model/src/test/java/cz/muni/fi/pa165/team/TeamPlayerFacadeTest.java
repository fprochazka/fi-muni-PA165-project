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

        TeamPlayer teamPlayer = teamPlayerFacade.createTeamPlayer(team.getId(), "Filip", "Procházka", 185, 80);
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

        teamPlayerFacade.deleteTeamPlayer(team.getId(), teamPlayer.getId());
        em.clear();

        TeamPlayer dbPlayer = em.find(TeamPlayer.class, teamPlayer.getId());
        assertNull(dbPlayer);
    }

    @Test
    public void testChangeTeamPlayerDetails() throws Exception
    {
        Team team = teamService.createTeam("Liverpool", null);
        em.persist(team);

        TeamPlayer teamPlayer = teamPlayerService.createTeamPlayer("Filip", "Procházka", 185, 80, team);
        em.persist(teamPlayer);
        em.flush();
        em.clear();

        teamPlayerFacade.changeTeamPlayerDetails(
            team.getId(),
            teamPlayer.getId(),
            "Tomáš",
            "Novák",
            190,
            70
        );
        em.clear();

        TeamPlayer foundTeamPlayer = em.find(TeamPlayer.class, teamPlayer.getId());
        assertEquals(foundTeamPlayer.getFirstname(), "Tomáš");
        assertEquals(foundTeamPlayer.getSurname(), "Novák");
        assertEquals(foundTeamPlayer.getHeight(), 190);
        assertEquals(foundTeamPlayer.getWeight(), 70);
    }

}
