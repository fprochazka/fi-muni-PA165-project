package cz.muni.fi.pa165.teamPlayer;

import cz.muni.fi.pa165.config.ApplicationConfig;
import cz.muni.fi.pa165.team.Team;
import cz.muni.fi.pa165.team.TeamPlayer;
import cz.muni.fi.pa165.team.TeamPlayerDao;
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
 * @author Filip Prochazka <filip@prochazka.su>
 */

@ContextConfiguration(classes = ApplicationConfig.class)
public class TeamPlayerDaoTest extends AbstractTransactionalTestNGSpringContextTests
{

    @Autowired
    public TeamPlayerDao teamPlayerDao;

    @PersistenceContext
    public EntityManager em;

    private TeamPlayer tp1, tp2, tp3, tp4;
    private Team t1;

    public void createTeam()
    {
        t1 = new Team("Liverpool");
    }

    @BeforeMethod
    public void createPlayer()
    {
        tp1 = new TeamPlayer("Filip", "Procházka", 22, 185, 80, t1);
        tp2 = new TeamPlayer("Libor", "Mühlpachr", 24, 175, 85, t1);
        tp3 = new TeamPlayer("Denis", "Galajda", 21, 175, 80, t1);
        tp4 = new TeamPlayer("Tomáš", "Šmíd", 23, 180, 75, t1);

        teamPlayerDao.create(tp1);
        teamPlayerDao.create(tp2);
        teamPlayerDao.create(tp3);
        teamPlayerDao.create(tp4);
        em.flush();
    }

    @Test
    public void findPlayerByFirstname()
    {
        Assert.assertNotNull(teamPlayerDao.findPlayerByFirstname(tp1.getFirstname()));
        Assert.assertNotNull(teamPlayerDao.findPlayerByFirstname(tp2.getFirstname()));
    }

    @Test
    public void findPlayerBySurname()
    {
        Assert.assertNotNull(teamPlayerDao.findPlayerBySurname(tp1.getSurname()));
        Assert.assertNotNull(teamPlayerDao.findPlayerBySurname(tp2.getSurname()));
    }

    @Test
    public void findPlayerById()
    {
        Assert.assertNotNull(teamPlayerDao.findPlayerById(tp1.getId()));
        Assert.assertNotNull(teamPlayerDao.findPlayerById(tp2.getId()));
    }

    @Test
    public void findPlayerByTeam()
    {
        Assert.assertNotNull(teamPlayerDao.findPlayerByTeam(tp1.getTeam()));
        Assert.assertNotNull(teamPlayerDao.findPlayerByTeam(tp2.getTeam()));
    }

        @Test
    public void findPlayerByHeight()
    {
        Assert.assertNotNull(teamPlayerDao.findPlayerByHeight(tp1.getHeight()));
        Assert.assertNotNull(teamPlayerDao.findPlayerByHeight(tp2.getHeight()));
    }

    @Test
    public void findPlayerByWeight()
    {
        Assert.assertNotNull(teamPlayerDao.findPlayerByWeight(tp1.getWeight()));
        Assert.assertNotNull(teamPlayerDao.findPlayerByWeight(tp2.getWeight()));
    }

    @Test
    public void findAllPlayers()
    {
        Collection<TeamPlayer> teamPlayerList = teamPlayerDao.findAllPlayers();
        assertEquals(teamPlayerList.size(), 2);

        assertTrue(teamPlayerList.stream().filter(t -> t.getFirstname().equals("Filip")).count() == 1);
        assertTrue(teamPlayerList.stream().filter(t -> t.getFirstname().equals("Libor")).count() == 1);
    }

    @Test
    public void update()
    {
        String oldFirstname = tp1.getFirstname();
        String newFirstname = "Petr";
        tp1.setFirstname(newFirstname);
        teamPlayerDao.update(tp1);
        em.flush();

        Assert.assertNotNull(teamPlayerDao.findPlayerByFirstname(newFirstname));
        Assert.assertNull(teamPlayerDao.findPlayerByFirstname(oldFirstname));
    }

    public void delete()
    {
        TeamPlayer tp1Backup = tp1;
        teamPlayerDao.delete(tp1);
        em.flush();
        Assert.assertNull(teamPlayerDao.findPlayerById(tp1Backup.getId()));
    }
}
