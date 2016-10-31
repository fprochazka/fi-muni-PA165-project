package cz.muni.fi.pa165.team;

import cz.muni.fi.pa165.config.ApplicationConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.annotations.Test;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collection;
import java.util.UUID;

import static org.testng.Assert.*;

/**
 * @author Tomas Smid <smid.thomas@gmail.com>
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

    @Test
    public void testCreatePlayer()
    {
        t1 = new Team("Liverpool");
        em.persist(t1);

        tp1 = new TeamPlayer("Filip", "Procházka", 185, 80, t1);
        em.persist(tp1);

        TeamPlayer dbPlayer = em.find(TeamPlayer.class, tp1.getId());
        em.flush();

        assertNotNull(dbPlayer);
        assertEquals(dbPlayer.getFirstname(), tp1.getFirstname());
        assertEquals(dbPlayer.getSurname(), tp1.getSurname());
        assertEquals(dbPlayer.getHeight(), tp1.getHeight());
        assertEquals(dbPlayer.getWeight(), tp1.getWeight());
        assertEquals(dbPlayer.getTeam(), tp1.getTeam());
    }

    @Test
    public void testDeletePlayer()
    {
        t1 = new Team("Liverpool");
        em.persist(t1);

        tp1 = new TeamPlayer("Filip", "Procházka", 185, 80, t1);
        em.persist(tp1);

        TeamPlayer dbPlayer = em.find(TeamPlayer.class, tp1.getId());
        assertNotNull(dbPlayer);

        teamPlayerDao.deletePlayer(tp1);
        dbPlayer = em.find(TeamPlayer.class, tp1.getId());
        em.flush();

        assertNull(dbPlayer);
    }

    @Test
    public void testFindPlayerByFirstname()
    {
        t1 = new Team("Liverpool");
        em.persist(t1);

        tp1 = new TeamPlayer("Filip", "Procházka", 185, 80, t1);
        tp2 = new TeamPlayer("Libor", "Mühlpachr", 175, 85, t1);
        tp3 = new TeamPlayer("Libor", "Galajda", 175, 80, t1);
        tp4 = new TeamPlayer("Tomáš", "Šmíd", 180, 75, t1);
        em.persist(tp1);
        em.persist(tp2);
        em.persist(tp3);
        em.persist(tp4);

        Collection<TeamPlayer> dbPlayers = teamPlayerDao.findPlayerByFirstname(tp2.getFirstname());

        em.flush();

        assertEquals(dbPlayers.size(), 2);
        assertTrue(dbPlayers.contains(tp2));
        assertTrue(dbPlayers.contains(tp3));
        assertFalse(dbPlayers.contains(tp1));
        assertFalse(dbPlayers.contains(tp4));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testFindPlayerByFirstnameNull()
    {
        Collection<TeamPlayer> dbPlayers = teamPlayerDao.findPlayerByFirstname(null);
        em.flush();
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testFindPlayerByFirstnameEmpty()
    {
        Collection<TeamPlayer> dbPlayers = teamPlayerDao.findPlayerByFirstname("");
        em.flush();
    }

    @Test
    public void testFindPlayerByFirstnameNonExistent()
    {
        t1 = new Team("Liverpool");
        em.persist(t1);

        tp1 = new TeamPlayer("Filip", "Procházka", 185, 80, t1);
        tp2 = new TeamPlayer("Libor", "Mühlpachr", 175, 85, t1);
        tp3 = new TeamPlayer("Libor", "Galajda", 175, 80, t1);
        tp4 = new TeamPlayer("Tomáš", "Šmíd", 180, 75, t1);
        em.persist(tp1);
        em.persist(tp2);
        em.persist(tp3);
        em.persist(tp4);

        Collection<TeamPlayer> dbPlayers = teamPlayerDao.findPlayerByFirstname("Lukáš");

        em.flush();

        assertTrue(dbPlayers.isEmpty());

    }

    @Test
    public void testFindPlayerBySurname()
    {
        t1 = new Team("Liverpool");
        em.persist(t1);

        tp1 = new TeamPlayer("Filip", "Procházka", 185, 80, t1);
        tp2 = new TeamPlayer("Libor", "Mühlpachr", 175, 85, t1);
        tp3 = new TeamPlayer("Denis", "Šmíd", 175, 80, t1);
        tp4 = new TeamPlayer("Tomáš", "Šmíd", 180, 75, t1);
        em.persist(tp1);
        em.persist(tp2);
        em.persist(tp3);
        em.persist(tp4);

        Collection<TeamPlayer> dbPlayers = teamPlayerDao.findPlayerBySurname(tp3.getSurname());

        em.flush();

        assertEquals(dbPlayers.size(), 2);
        assertTrue(dbPlayers.contains(tp3));
        assertTrue(dbPlayers.contains(tp4));
        assertFalse(dbPlayers.contains(tp1));
        assertFalse(dbPlayers.contains(tp2));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testFindPlayerBySurnameNull()
    {
        Collection<TeamPlayer> dbPlayers = teamPlayerDao.findPlayerBySurname(null);
        em.flush();
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testFindPlayerBySurnameEmpty()
    {
        Collection<TeamPlayer> dbPlayers = teamPlayerDao.findPlayerBySurname("");
        em.flush();
    }

    @Test
    public void testFindPlayerBySurnameNonExistent()
    {
        t1 = new Team("Liverpool");
        em.persist(t1);

        tp1 = new TeamPlayer("Filip", "Procházka", 185, 80, t1);
        tp2 = new TeamPlayer("Libor", "Mühlpachr", 175, 85, t1);
        tp3 = new TeamPlayer("Denis", "Galajda", 175, 80, t1);
        tp4 = new TeamPlayer("Tomáš", "Šmíd", 180, 75, t1);
        em.persist(tp1);
        em.persist(tp2);
        em.persist(tp3);
        em.persist(tp4);

        Collection<TeamPlayer> dbPlayers = teamPlayerDao.findPlayerBySurname("Novák");

        em.flush();

        assertTrue(dbPlayers.isEmpty());
    }

    @Test
    public void testFindPlayerById()
    {
        t1 = new Team("Liverpool");
        em.persist(t1);

        tp1 = new TeamPlayer("Filip", "Procházka", 185, 80, t1);
        tp2 = new TeamPlayer("Libor", "Mühlpachr", 175, 85, t1);
        em.persist(tp1);
        em.persist(tp2);

        TeamPlayer dbPlayer = teamPlayerDao.findPlayerById(tp1.getId());
        em.flush();

        assertEquals(dbPlayer.getFirstname(), tp1.getFirstname());
        assertEquals(dbPlayer.getSurname(), tp1.getSurname());
        assertEquals(dbPlayer.getHeight(), tp1.getHeight());
        assertEquals(dbPlayer.getWeight(), tp1.getWeight());
        assertEquals(dbPlayer.getTeam(), tp1.getTeam());
    }

    @Test
    public void testFindPlayerByIdNonExistent()
    {
        t1 = new Team("Liverpool");
        em.persist(t1);

        tp1 = new TeamPlayer("Filip", "Procházka", 185, 80, t1);
        em.persist(tp1);

        UUID badId = UUID.randomUUID();
        assertNotEquals(tp1.getId(), badId);

        TeamPlayer dbPlayer = teamPlayerDao.findPlayerById(badId);
        em.flush();

        assertNull(dbPlayer);
    }

    @Test
    public void testFindPlayerByTeam()
    {
        Team t1 = new Team("Liverpool");
        Team t2 = new Team("Arsenal");
        Team t3 = new Team("Manchester");
        em.persist(t1);
        em.persist(t2);
        em.persist(t3);

        tp1 = new TeamPlayer("Filip", "Procházka", 185, 80, t3);
        tp2 = new TeamPlayer("Libor", "Mühlpachr", 175, 85, t1);
        tp3 = new TeamPlayer("Libor", "Galajda", 175, 80, t2);
        tp4 = new TeamPlayer("Tomáš", "Šmíd", 180, 75, t3);
        em.persist(tp1);
        em.persist(tp2);
        em.persist(tp3);
        em.persist(tp4);

        Collection<TeamPlayer> dbPlayers = teamPlayerDao.findPlayerByTeam(t3);

        em.flush();

        assertEquals(dbPlayers.size(), 2);
        assertTrue(dbPlayers.contains(tp1));
        assertTrue(dbPlayers.contains(tp4));
        assertFalse(dbPlayers.contains(tp2));
        assertFalse(dbPlayers.contains(tp3));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testFindPlayerByTeamNull()
    {
        Collection<TeamPlayer> dbPlayers = teamPlayerDao.findPlayerByTeam(null);
        em.flush();
    }


    @Test
    public void testFindPlayerByTeamNonExistent()
    {
        t1 = new Team("Liverpool");
        em.persist(t1);

        tp1 = new TeamPlayer("Filip", "Procházka", 185, 80, t1);
        tp2 = new TeamPlayer("Libor", "Mühlpachr", 175, 85, t1);
        tp3 = new TeamPlayer("Libor", "Galajda", 175, 80, t1);
        tp4 = new TeamPlayer("Tomáš", "Šmíd", 180, 75, t1);
        em.persist(tp1);
        em.persist(tp2);
        em.persist(tp3);
        em.persist(tp4);

        Collection<TeamPlayer> dbPlayers = teamPlayerDao.findPlayerByTeam(new Team("Chelsea"));

        em.flush();

        assertTrue(dbPlayers.isEmpty());

    }

    @Test
    public void testFindAllPlayers()
    {
        Team t1 = new Team("Liverpool");
        Team t2 = new Team("Arsenal");
        Team t3 = new Team("Manchester");
        em.persist(t1);
        em.persist(t2);
        em.persist(t3);

        tp1 = new TeamPlayer("Filip", "Procházka", 185, 80, t3);
        tp2 = new TeamPlayer("Libor", "Mühlpachr", 175, 85, t1);
        tp3 = new TeamPlayer("Libor", "Galajda", 175, 80, t2);
        tp4 = new TeamPlayer("Tomáš", "Šmíd", 180, 75, t3);
        em.persist(tp1);
        em.persist(tp2);
        em.persist(tp3);
        em.persist(tp4);

        Collection<TeamPlayer> dbPlayers = teamPlayerDao.findAllPlayers();

        em.flush();

        assertEquals(dbPlayers.size(), 4);
        assertTrue(dbPlayers.contains(tp1));
        assertTrue(dbPlayers.contains(tp2));
        assertTrue(dbPlayers.contains(tp3));
        assertTrue(dbPlayers.contains(tp4));
    }

}
