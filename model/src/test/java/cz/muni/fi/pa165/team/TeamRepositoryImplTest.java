package cz.muni.fi.pa165.team;

import cz.muni.fi.pa165.config.ModelConfig;
import cz.muni.fi.pa165.team.exceptions.TeamNotFoundException;
import cz.muni.fi.pa165.team.match.TeamMatch;
import cz.muni.fi.pa165.team.match.TeamMatchGoal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.UUID;

import static org.testng.Assert.*;

/**
 * @author Libor Mühlpachr <libor.muhl@seznam.cz>
 */
@ContextConfiguration(classes = ModelConfig.class)
public class TeamRepositoryImplTest extends AbstractTransactionalTestNGSpringContextTests
{

    private LocalDateTime now = LocalDateTime.now();

    @Autowired
    public TeamRepositoryImpl teamRepository;

    @PersistenceContext
    public EntityManager em;

    private Team t1, t2, t3, t4;
    private TeamMatch m1, m2, m3, m4;
    private TeamMatchGoal g1, g2, g3, g4;
    private TeamPlayer p1, p2, p3, p4, p12, p22, p32, p42;

    @BeforeMethod
    public void createTeam()
    {
        t1 = new Team("MAN UTD");
        t2 = new Team("MAN CITY");
        t3 = new Team("FCB");
        t4 = new Team("RLM");
        em.persist(t1);
        em.persist(t2);
        em.persist(t3);
        em.persist(t4);
        em.flush();

        long time = System.currentTimeMillis();
        m1 = new TeamMatch(t1, t2, now, now.plusMinutes(55));
        m2 = new TeamMatch(t2, t3, now, now.plusMinutes(55));
        m3 = new TeamMatch(t3, t4, now, now.plusMinutes(55));
        m4 = new TeamMatch(t4, t1, now, now.plusMinutes(55));
        em.persist(m1);
        em.persist(m2);
        em.persist(m3);
        em.persist(m4);
        em.flush();

        p1 = new TeamPlayer("John", "Doe", 187, 85, t1);
        p2 = new TeamPlayer("Jozef", "Mrkvicka", 188, 86, t2);
        p3 = new TeamPlayer("Johny", "Kentus", 189, 87, t3);
        p4 = new TeamPlayer("Smajdalf", "Sedy", 190, 88, t1);
        p12 = new TeamPlayer("Ctiziadoslav", "Tetrov", 187, 85, t1);
        p22 = new TeamPlayer("Ctibor", "Mocnar", 188, 86, t2);
        p32 = new TeamPlayer("Evzen", "Loveczen", 189, 87, t3);
        p42 = new TeamPlayer("Libor", "Muhlpachr", 190, 88, t1);
        em.persist(p1);
        em.persist(p2);
        em.persist(p3);
        em.persist(p4);
        em.persist(p12);
        em.persist(p22);
        em.persist(p32);
        em.persist(p42);
        em.flush();

        g1 = new TeamMatchGoal(p1, p12, m1, now.plusMinutes(25));
        g2 = new TeamMatchGoal(p2, p22, m2, now.plusMinutes(25));
        g3 = new TeamMatchGoal(p3, p32, m3, now.plusMinutes(25));
        g4 = new TeamMatchGoal(p4, p42, m4, now.plusMinutes(25));
        em.persist(g1);
        em.persist(g2);
        em.persist(g3);
        em.persist(g4);
        em.flush();
    }

    @Test
    public void findTeamByName()
    {
        Assert.assertNotNull(teamRepository.findTeamByName(t1.getName()));
    }

    @Test
    public void getTeamById()
    {
        Assert.assertNotNull(teamRepository.getTeamById(t2.getId()));
    }

    @Test
    public void testGetByNonexistentIdThrows() throws Exception
    {
        UUID teamId = UUID.randomUUID();
        try {
            teamRepository.getTeamById(teamId);
            fail("Expected exception");

        } catch (TeamNotFoundException e) {
            assertEquals(teamId, e.getTeamId());
        }
    }

    @Test
    public void findTeamByPlayer()
    {
        Team tmpTeam = new Team("bla");
        em.persist(tmpTeam);
        em.flush();

        TeamPlayer player = new TeamPlayer("John", "Doe", 187, 85, tmpTeam);
        em.persist(player);
        em.flush();

        Assert.assertTrue(teamRepository.findTeamByPlayer(player).equals(tmpTeam));
    }

    @Test
    public void findAll()
    {
        Collection<Team> teamList = teamRepository.findAll();
        assertEquals(teamList.size(), 4);

        assertTrue(teamList.stream().filter(t -> t.getName().equals("MAN UTD")).count() == 1);
        assertTrue(teamList.stream().filter(t -> t.getName().equals("MAN CITY")).count() == 1);
        assertTrue(teamList.stream().filter(t -> t.getName().equals("FCB")).count() == 1);
        assertTrue(teamList.stream().filter(t -> t.getName().equals("RLM")).count() == 1);
    }

}
