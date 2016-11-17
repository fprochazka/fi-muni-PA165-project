package cz.muni.fi.pa165.team;

import cz.muni.fi.pa165.config.ApplicationConfig;
import cz.muni.fi.pa165.team.exceptions.TeamNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.annotations.Test;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.UUID;

import static org.testng.Assert.*;

/**
 * @author Denis Galajda <galajda.denis@gmail.com>
 */
@ContextConfiguration(classes = ApplicationConfig.class)
public class TeamFacadeTest extends AbstractTransactionalTestNGSpringContextTests
{

    @Autowired
    public TeamRepository teamRepository;

    @Autowired
    public TeamFacade teamFacade;

    @Autowired
    public TeamService teamService;

    @PersistenceContext
    public EntityManager em;

    @Test
    public void testCreateUser() throws Exception
    {
        Team createdTeam = teamFacade.createTeam("ManCity");
        em.clear();

        Team foundTeam = teamRepository.getTeamById(createdTeam.getId());
        assertNotNull(foundTeam);
        assertEquals("ManCity", foundTeam.getName());
    }

    @Test
    public void testChangeTeamName() throws Exception
    {
        Team createdTeam = teamFacade.createTeam("ManCity");
        em.clear();
        Team foundTeam = teamRepository.getTeamById(createdTeam.getId());
        assertNotNull(foundTeam);
        assertEquals("ManCity", foundTeam.getName());

        teamFacade.changeTeamName(foundTeam.getId(), "ManUtd");
        em.clear();
        Team foundTeam2 = teamRepository.findTeamByName("ManUtd");
        assertNotNull(foundTeam2);

        em.clear();
        Team foundTeam3 = teamRepository.findTeamByName("ManCity");
        assertNull(foundTeam3);
    }

    @Test
    public void testDeleteTeam() throws Exception
    {
        Team createdTeam = teamFacade.createTeam("FCB");
        em.clear();
        UUID tmpId = createdTeam.getId();
        teamFacade.deleteTeam(createdTeam.getId());
        try {
            Team foundTeam = teamRepository.getTeamById(tmpId);
            fail("Expected exception");
        } catch (TeamNotFoundException e) {
            assertEquals(tmpId, e.getTeamId());
        }
    }
}
