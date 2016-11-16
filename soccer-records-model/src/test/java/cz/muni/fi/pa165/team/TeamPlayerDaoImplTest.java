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
public class TeamPlayerDaoImplTest extends AbstractTransactionalTestNGSpringContextTests
{

    @Autowired
    public TeamPlayerDaoImpl teamPlayerDao;

    @PersistenceContext
    public EntityManager em;

    @Test
    public void testCreatePlayer()
    {
        Team team = new Team("Liverpool");
        em.persist(team);

        TeamPlayer player = new TeamPlayer("Filip", "Procházka", 185, 80, team);
        em.persist(player);

        TeamPlayer dbPlayer = em.find(TeamPlayer.class, player.getId());
        em.flush();

        assertNotNull(dbPlayer);
        assertEquals(dbPlayer.getFirstname(), player.getFirstname());
        assertEquals(dbPlayer.getSurname(), player.getSurname());
        assertEquals(dbPlayer.getHeight(), player.getHeight());
        assertEquals(dbPlayer.getWeight(), player.getWeight());
        assertEquals(dbPlayer.getTeam(), player.getTeam());
    }

    @Test
    public void testDeletePlayer()
    {
        Team team = new Team("Liverpool");
        em.persist(team);

        TeamPlayer player = new TeamPlayer("Filip", "Procházka", 185, 80, team);
        em.persist(player);

        TeamPlayer dbPlayer = em.find(TeamPlayer.class, player.getId());
        assertNotNull(dbPlayer);

        teamPlayerDao.deletePlayer(player);
        dbPlayer = em.find(TeamPlayer.class, player.getId());
        em.flush();

        assertNull(dbPlayer);
    }

    @Test
    public void testFindPlayerByFirstname()
    {
        Team team = new Team("Liverpool");
        em.persist(team);

        TeamPlayer player1 = new TeamPlayer("Filip", "Procházka", 185, 80, team);
        TeamPlayer player2 = new TeamPlayer("Libor", "Mühlpachr", 175, 85, team);
        TeamPlayer player3 = new TeamPlayer("Libor", "Galajda", 175, 80, team);
        TeamPlayer player4 = new TeamPlayer("Tomáš", "Šmíd", 180, 75, team);
        em.persist(player1);
        em.persist(player2);
        em.persist(player3);
        em.persist(player4);

        Collection<TeamPlayer> dbPlayers = teamPlayerDao.findPlayerByFirstname(player2.getFirstname());

        em.flush();

        assertEquals(dbPlayers.size(), 2);
        assertTrue(dbPlayers.contains(player2));
        assertTrue(dbPlayers.contains(player3));
        assertFalse(dbPlayers.contains(player1));
        assertFalse(dbPlayers.contains(player4));
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
        Team team = new Team("Liverpool");
        em.persist(team);

        TeamPlayer player1 = new TeamPlayer("Filip", "Procházka", 185, 80, team);
        TeamPlayer player2 = new TeamPlayer("Libor", "Mühlpachr", 175, 85, team);
        TeamPlayer player3 = new TeamPlayer("Libor", "Galajda", 175, 80, team);
        TeamPlayer player4 = new TeamPlayer("Tomáš", "Šmíd", 180, 75, team);
        em.persist(player1);
        em.persist(player2);
        em.persist(player3);
        em.persist(player4);

        Collection<TeamPlayer> dbPlayers = teamPlayerDao.findPlayerByFirstname("Lukáš");

        em.flush();

        assertTrue(dbPlayers.isEmpty());

    }

    @Test
    public void testFindPlayerBySurname()
    {
        Team team = new Team("Liverpool");
        em.persist(team);

        TeamPlayer player1 = new TeamPlayer("Filip", "Procházka", 185, 80, team);
        TeamPlayer player2 = new TeamPlayer("Libor", "Mühlpachr", 175, 85, team);
        TeamPlayer player3 = new TeamPlayer("Denis", "Šmíd", 175, 80, team);
        TeamPlayer player4 = new TeamPlayer("Tomáš", "Šmíd", 180, 75, team);
        em.persist(player1);
        em.persist(player2);
        em.persist(player3);
        em.persist(player4);

        Collection<TeamPlayer> dbPlayers = teamPlayerDao.findPlayerBySurname(player3.getSurname());

        em.flush();

        assertEquals(dbPlayers.size(), 2);
        assertTrue(dbPlayers.contains(player3));
        assertTrue(dbPlayers.contains(player4));
        assertFalse(dbPlayers.contains(player1));
        assertFalse(dbPlayers.contains(player2));
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
        Team team = new Team("Liverpool");
        em.persist(team);

        TeamPlayer player1 = new TeamPlayer("Filip", "Procházka", 185, 80, team);
        TeamPlayer player2 = new TeamPlayer("Libor", "Mühlpachr", 175, 85, team);
        TeamPlayer player3 = new TeamPlayer("Denis", "Galajda", 175, 80, team);
        TeamPlayer player4 = new TeamPlayer("Tomáš", "Šmíd", 180, 75, team);
        em.persist(player1);
        em.persist(player2);
        em.persist(player3);
        em.persist(player4);

        Collection<TeamPlayer> dbPlayers = teamPlayerDao.findPlayerBySurname("Novák");

        em.flush();

        assertTrue(dbPlayers.isEmpty());
    }

    @Test
    public void testFindPlayerById()
    {
        Team team = new Team("Liverpool");
        em.persist(team);

        TeamPlayer player1 = new TeamPlayer("Filip", "Procházka", 185, 80, team);
        TeamPlayer player2 = new TeamPlayer("Libor", "Mühlpachr", 175, 85, team);
        em.persist(player1);
        em.persist(player2);

        TeamPlayer dbPlayer = teamPlayerDao.findPlayerById(player1.getId());
        em.flush();

        assertEquals(dbPlayer.getFirstname(), player1.getFirstname());
        assertEquals(dbPlayer.getSurname(), player1.getSurname());
        assertEquals(dbPlayer.getHeight(), player1.getHeight());
        assertEquals(dbPlayer.getWeight(), player1.getWeight());
        assertEquals(dbPlayer.getTeam(), player1.getTeam());
    }

    @Test
    public void testFindPlayerByIdNonExistent()
    {
        Team team = new Team("Liverpool");
        em.persist(team);

        TeamPlayer player = new TeamPlayer("Filip", "Procházka", 185, 80, team);
        em.persist(player);

        UUID badId = UUID.randomUUID();
        assertNotEquals(player.getId(), badId);

        TeamPlayer dbPlayer = teamPlayerDao.findPlayerById(badId);
        em.flush();

        assertNull(dbPlayer);
    }

    @Test
    public void testFindPlayerByTeam()
    {
        Team team1 = new Team("Liverpool");
        Team team2 = new Team("Arsenal");
        Team team3 = new Team("Manchester");
        em.persist(team1);
        em.persist(team2);
        em.persist(team3);

        TeamPlayer player1 = new TeamPlayer("Filip", "Procházka", 185, 80, team3);
        TeamPlayer player2 = new TeamPlayer("Libor", "Mühlpachr", 175, 85, team1);
        TeamPlayer player3 = new TeamPlayer("Libor", "Galajda", 175, 80, team2);
        TeamPlayer player4 = new TeamPlayer("Tomáš", "Šmíd", 180, 75, team3);
        em.persist(player1);
        em.persist(player2);
        em.persist(player3);
        em.persist(player4);

        Collection<TeamPlayer> dbPlayers = teamPlayerDao.findPlayerByTeam(team3);

        em.flush();

        assertEquals(dbPlayers.size(), 2);
        assertTrue(dbPlayers.contains(player1));
        assertTrue(dbPlayers.contains(player4));
        assertFalse(dbPlayers.contains(player2));
        assertFalse(dbPlayers.contains(player3));
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
        Team team = new Team("Liverpool");
        em.persist(team);

        TeamPlayer player1 = new TeamPlayer("Filip", "Procházka", 185, 80, team);
        TeamPlayer player2 = new TeamPlayer("Libor", "Mühlpachr", 175, 85, team);
        TeamPlayer player3 = new TeamPlayer("Libor", "Galajda", 175, 80, team);
        TeamPlayer player4= new TeamPlayer("Tomáš", "Šmíd", 180, 75, team);
        em.persist(player1);
        em.persist(player2);
        em.persist(player3);
        em.persist(player4);

        Collection<TeamPlayer> dbPlayers = teamPlayerDao.findPlayerByTeam(new Team("Chelsea"));

        em.flush();

        assertTrue(dbPlayers.isEmpty());

    }

    @Test
    public void testFindAllPlayers()
    {
        Team team1 = new Team("Liverpool");
        Team team2 = new Team("Arsenal");
        Team team3 = new Team("Manchester");
        em.persist(team1);
        em.persist(team2);
        em.persist(team3);

        TeamPlayer player1 = new TeamPlayer("Filip", "Procházka", 185, 80, team3);
        TeamPlayer player2 = new TeamPlayer("Libor", "Mühlpachr", 175, 85, team1);
        TeamPlayer player3 = new TeamPlayer("Libor", "Galajda", 175, 80, team2);
        TeamPlayer player4 = new TeamPlayer("Tomáš", "Šmíd", 180, 75, team3);
        em.persist(player1);
        em.persist(player2);
        em.persist(player3);
        em.persist(player4);

        Collection<TeamPlayer> dbPlayers = teamPlayerDao.findAllPlayers();

        em.flush();

        assertEquals(dbPlayers.size(), 4);
        assertTrue(dbPlayers.contains(player1));
        assertTrue(dbPlayers.contains(player2));
        assertTrue(dbPlayers.contains(player3));
        assertTrue(dbPlayers.contains(player4));
    }

}
