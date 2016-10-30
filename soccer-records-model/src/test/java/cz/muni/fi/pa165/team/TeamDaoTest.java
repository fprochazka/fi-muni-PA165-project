package cz.muni.fi.pa165.team;

import cz.muni.fi.pa165.config.ApplicationConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collection;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

/**
 * @author Libor Mühlpachr <libor.muhl@seznam.cz>
 */
@ContextConfiguration(classes = ApplicationConfig.class)
public class TeamDaoTest extends AbstractTransactionalTestNGSpringContextTests
{

    @Autowired
    public TeamDao teamDao;

    @PersistenceContext
    public EntityManager em;

    private Team t1, t2, t3, t4;

    @BeforeMethod
    public void createTeam()
    {
        t1 = new Team("MAN UTD");
        t2 = new Team("MAN CITY");
        t3 = new Team("FCB");
        t4 = new Team("RLM");

        teamDao.createTeam(t1);
        teamDao.createTeam(t2);
        teamDao.createTeam(t3);
        teamDao.createTeam(t4);
        em.flush();
    }

    @Test
    public void findByName()
    {
        Assert.assertNotNull(teamDao.findTeamByName(t1.getName()));
    }

    @Test
    public void findById()
    {
        Assert.assertNotNull(teamDao.findTeamById(t2.getId()));
    }

    @Test
    public void findAll()
    {
        Collection<Team> teamList = teamDao.findAll();
        assertEquals(teamList.size(), 4);

        assertTrue(teamList.stream().filter(t -> t.getName().equals("MAN UTD")).count() == 1);
        assertTrue(teamList.stream().filter(t -> t.getName().equals("MAN CITY")).count() == 1);
        assertTrue(teamList.stream().filter(t -> t.getName().equals("FCB")).count() == 1);
        assertTrue(teamList.stream().filter(t -> t.getName().equals("RLM")).count() == 1);
    }

    @Test
    public void update()
    {
        String oldName = t3.getName();
        String newName = t3.getName() + "3";
        t3.setName(newName);
        teamDao.updateTeam(t3);
        em.flush();

        Assert.assertNotNull(teamDao.findTeamByName(newName));
        Assert.assertNull(teamDao.findTeamByName(oldName));
    }

    @Test
    public void delete()
    {
        teamDao.deleteTeam(t4);
        em.flush();
    }
}
