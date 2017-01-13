package cz.muni.fi.pa165.team;

import cz.muni.fi.pa165.config.ModelConfig;
import cz.muni.fi.pa165.team.exceptions.TeamPlayerNotFoundException;
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
@ContextConfiguration(classes = ModelConfig.class)
public class TeamPlayerRepositoryImplTest extends AbstractTransactionalTestNGSpringContextTests
{

    @Autowired
    public TeamPlayerRepositoryImpl teamPlayerRepository;

    @PersistenceContext
    public EntityManager em;

    @Test
    public void testFindTeamPlayerByFirstname()
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

        em.flush();

        Collection<TeamPlayer> dbPlayers = teamPlayerRepository.findTeamPlayerByFirstname(player2.getFirstname());

        assertEquals(dbPlayers.size(), 2);
        assertTrue(dbPlayers.contains(player2));
        assertTrue(dbPlayers.contains(player3));
        assertFalse(dbPlayers.contains(player1));
        assertFalse(dbPlayers.contains(player4));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testFindTeamPlayerByFirstnameNull()
    {
        teamPlayerRepository.findTeamPlayerByFirstname(null);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testFindTeamPlayerByFirstnameEmpty()
    {
        teamPlayerRepository.findTeamPlayerByFirstname("");
    }

    @Test
    public void testFindTeamPlayerByFirstnameNonExistent()
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
        em.flush();

        Collection<TeamPlayer> dbPlayers = teamPlayerRepository.findTeamPlayerByFirstname("Lukáš");
        assertTrue(dbPlayers.isEmpty());
    }

    @Test
    public void testFindTeamPlayerBySurname()
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
        em.flush();

        Collection<TeamPlayer> dbPlayers = teamPlayerRepository.findTeamPlayerBySurname(player3.getSurname());

        assertEquals(dbPlayers.size(), 2);
        assertTrue(dbPlayers.contains(player3));
        assertTrue(dbPlayers.contains(player4));
        assertFalse(dbPlayers.contains(player1));
        assertFalse(dbPlayers.contains(player2));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testFindTeamPlayerBySurnameNull()
    {
        teamPlayerRepository.findTeamPlayerBySurname(null);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testFindTeamPlayerBySurnameEmpty()
    {
        teamPlayerRepository.findTeamPlayerBySurname("");
    }

    @Test
    public void testFindTeamPlayerBySurnameNonExistent()
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
        em.flush();

        Collection<TeamPlayer> dbPlayers = teamPlayerRepository.findTeamPlayerBySurname("Novák");
        assertTrue(dbPlayers.isEmpty());
    }

    @Test
    public void testFindTeamPlayerById()
    {
        Team team = new Team("Liverpool");
        em.persist(team);

        TeamPlayer player1 = new TeamPlayer("Filip", "Procházka", 185, 80, team);
        TeamPlayer player2 = new TeamPlayer("Libor", "Mühlpachr", 175, 85, team);
        em.persist(player1);
        em.persist(player2);
        em.flush();

        TeamPlayer dbPlayer = teamPlayerRepository.getTeamPlayerById(player1.getId());

        assertEquals(dbPlayer.getFirstname(), player1.getFirstname());
        assertEquals(dbPlayer.getSurname(), player1.getSurname());
        assertEquals(dbPlayer.getHeight(), player1.getHeight());
        assertEquals(dbPlayer.getWeight(), player1.getWeight());
        assertEquals(dbPlayer.getTeam(), player1.getTeam());
    }

    @Test
    public void testFindTeamPlayerByIdNonExistent()
    {
        UUID badId = UUID.randomUUID();
        try {
            teamPlayerRepository.getTeamPlayerById(badId);
            fail("Expected exception");
        } catch (TeamPlayerNotFoundException e) {
            assertEquals(badId, e.getTeamPlayerId());
        }
    }

    @Test
    public void testFindTeamPlayerByTeam()
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
        em.flush();

        Collection<TeamPlayer> dbPlayers = teamPlayerRepository.findTeamPlayerByTeam(team3.getId());

        assertEquals(dbPlayers.size(), 2);
        assertTrue(dbPlayers.contains(player1));
        assertTrue(dbPlayers.contains(player4));
        assertFalse(dbPlayers.contains(player2));
        assertFalse(dbPlayers.contains(player3));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testFindTeamPlayerByTeamNull()
    {
        teamPlayerRepository.findTeamPlayerByTeam(null);
    }

    @Test
    public void testFindTeamPlayerByTeamNonExistent()
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
        em.flush();

        Collection<TeamPlayer> dbPlayers = teamPlayerRepository.findTeamPlayerByTeam(new Team("Chelsea").getId());
        assertTrue(dbPlayers.isEmpty());
    }

}
