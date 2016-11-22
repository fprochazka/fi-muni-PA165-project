package cz.muni.fi.pa165.team;

import cz.muni.fi.pa165.config.ApplicationConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.annotations.Test;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.UUID;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

/**
 * @author Denis Galajda <galajda.denis@gmail.com>
 */
@ContextConfiguration(classes = ApplicationConfig.class)
public class TeamFacadeTest extends AbstractTransactionalTestNGSpringContextTests
{

    @Autowired
    public TeamFacade teamFacade;

    @PersistenceContext
    public EntityManager em;

    @Test
    public void testCreateUser() throws Exception
    {
        Team createdTeam = teamFacade.createTeam("ManCity");
        em.clear();
        em.find(Team.class, createdTeam.getId());

        Team foundTeam = em.find(Team.class, createdTeam.getId());
        assertNotNull(foundTeam);
        assertEquals("ManCity", foundTeam.getName());
    }

    @Test
    public void testChangeTeamName() throws Exception
    {
        Team createdTeam = teamFacade.createTeam("ManCity");
        em.clear();

        Team foundTeam = em.find(Team.class, createdTeam.getId());
        assertNotNull(foundTeam);
        assertEquals("ManCity", foundTeam.getName());

        teamFacade.changeTeamName(foundTeam.getId(), "ManUtd");
        em.clear();

        Team foundTeam2 = em.find(Team.class, createdTeam.getId());
        assertNotNull(foundTeam2);
        assertEquals(foundTeam2.getName(), "ManUtd");
    }

    @Test
    public void testDeleteTeam() throws Exception
    {
        Team createdTeam = teamFacade.createTeam("FCB");
        em.clear();
        UUID tmpId = createdTeam.getId();
        teamFacade.deleteTeam(createdTeam.getId());
        em.find(Team.class, tmpId);
    }
}
