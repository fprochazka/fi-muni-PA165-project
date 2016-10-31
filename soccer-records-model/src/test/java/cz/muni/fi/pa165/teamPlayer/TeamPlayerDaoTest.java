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

    @BeforeMethod
    public void createData()
    {
        t1 = new Team("Liverpool");
        em.persist(t1);

        tp1 = new TeamPlayer("Filip", "Procházka", 185, 80, t1);
        tp2 = new TeamPlayer("Libor", "Mühlpachr", 175, 85, t1);
        tp3 = new TeamPlayer("Denis", "Galajda", 175, 80, t1);
        tp4 = new TeamPlayer("Tomáš", "Šmíd", 180, 75, t1);

        teamPlayerDao.create(tp1);
        teamPlayerDao.create(tp2);
        teamPlayerDao.create(tp3);
        teamPlayerDao.create(tp4);
        em.flush();
    }



    @Test
    public void findPlayerById()
    {
        Assert.assertNotNull(teamPlayerDao.findPlayerById(tp1.getId()));
        Assert.assertNotNull(teamPlayerDao.findPlayerById(tp2.getId()));
    }


    @Test
    public void findAllPlayers()
    {
        Collection<TeamPlayer> teamPlayerList = teamPlayerDao.findAllPlayers();
        assertEquals(teamPlayerList.size(), 4);

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
    }

    public void delete()
    {
        TeamPlayer tp1Backup = tp1;
        teamPlayerDao.delete(tp1);
        em.flush();
        Assert.assertNull(teamPlayerDao.findPlayerById(tp1Backup.getId()));
    }
}
