package cz.muni.fi.pa165.team;

import cz.muni.fi.pa165.config.ApplicationConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collection;
import java.util.Date;
import java.util.UUID;

/**
 * @author Filip Procházka <filip@prochazka.su>
 */
@ContextConfiguration(classes = ApplicationConfig.class)
public class TeamMatchDaoImplTest extends AbstractTransactionalTestNGSpringContextTests
{

    @Autowired
    public TeamMatchRepositoryImpl teamMatchDao;

    @PersistenceContext
    public EntityManager em;

    @Test
    public void testCreateMatch()
    {
        long time = System.currentTimeMillis();
        Team homeTeam = new Team("HomeTeam");
        Team awayTeam = new Team("AwayTeam");
        em.persist(homeTeam);
        em.persist(awayTeam);

        TeamMatch teamMatch = new TeamMatch(homeTeam, awayTeam, new Date(time), new Date(time + 5520000));

        teamMatchDao.createMatch(teamMatch);

        em.flush();

        Assert.assertNotNull(em.find(TeamMatch.class, teamMatch.getId()));
    }

    @Test
    public void testUpdateMatch()
    {
        long time1 = System.currentTimeMillis();
        Team homeTeam = new Team("HomeTeam");
        Team awayTeam = new Team("AwayTeam");
        em.persist(homeTeam);
        em.persist(awayTeam);

        TeamMatch teamMatch = new TeamMatch(homeTeam, awayTeam, new Date(time1));
        UUID id = teamMatch.getId();

        em.persist(teamMatch);

        long time2 = time1 + 1000;
        long time3 = time1 + 5520000;

        teamMatch.setStartTime(new Date(time2));
        teamMatch.setEndTime(new Date(time3));

        teamMatchDao.updateMatch(teamMatch);

        TeamMatch dbMatch = em.find(TeamMatch.class, id);

        em.flush();

        Assert.assertEquals(dbMatch.getStartTime().getTime(), time2);
        Assert.assertEquals(dbMatch.getEndTime().getTime(), time3);
    }

    @Test
    public void testDeleteMatch()
    {
        long time = System.currentTimeMillis();

        Team homeTeam = new Team("HomeTeam");
        Team awayTeam = new Team("AwayTeam");
        em.persist(homeTeam);
        em.persist(awayTeam);
        em.flush();

        TeamPlayer scorer1 = new TeamPlayer("Jozko", "Silny", 180, 80, homeTeam);
        TeamPlayer scorer2 = new TeamPlayer("Bohdan", "Kerekes", 194, 86, homeTeam);
        TeamPlayer assistant = new TeamPlayer("Lex", "Stroub", 181, 81, homeTeam);
        em.persist(scorer1);
        em.persist(scorer2);
        em.persist(assistant);

        TeamMatch teamMatch = new TeamMatch(homeTeam, awayTeam, new Date(time));
        UUID id = teamMatch.getId();
        TeamMatchGoal teamMatchGoal1 = new TeamMatchGoal(scorer1, assistant, teamMatch, new Date(time + 15000));
        TeamMatchGoal teamMatchGoal2 = new TeamMatchGoal(scorer2, assistant, teamMatch, new Date(time + 25000));

        em.persist(teamMatch);
        em.persist(teamMatchGoal1);
        em.persist(teamMatchGoal2);
        em.flush();

        TeamMatch dbMatch = em.find(TeamMatch.class, id);
        Assert.assertNotNull(dbMatch);

        teamMatchDao.deleteMatch(teamMatch);
        em.flush();
        em.clear();

        Team dbHomeTeam = em.find(Team.class, homeTeam.getId());
        Team dbAwayTeam = em.find(Team.class, awayTeam.getId());
        TeamMatchGoal dbTMG1 = em.find(TeamMatchGoal.class, teamMatchGoal1.getId());
        TeamMatchGoal dbTMG2 = em.find(TeamMatchGoal.class, teamMatchGoal2.getId());
        dbMatch = em.find(TeamMatch.class, id);

        Assert.assertNull(dbMatch);
        Assert.assertNull(dbTMG1);
        Assert.assertNull(dbTMG2);
        Assert.assertNotNull(dbHomeTeam);
        Assert.assertNotNull(dbAwayTeam);
    }

    @Test
    public void testFindMatchById()
    {
        long time = System.currentTimeMillis();
        Team homeTeam = new Team("HomeTeam");
        Team awayTeam = new Team("AwayTeam");
        em.persist(homeTeam);
        em.persist(awayTeam);

        em.flush();

        TeamMatch teamMatch = new TeamMatch(homeTeam, awayTeam, new Date(time));

        em.persist(teamMatch);

        TeamMatch dbMatch = teamMatchDao.findMatchById(teamMatch.getId());

        em.flush();

        Assert.assertEquals(dbMatch.getAwayTeam(), teamMatch.getAwayTeam());
        Assert.assertEquals(dbMatch.getHomeTeam(), teamMatch.getHomeTeam());
        Assert.assertEquals(dbMatch.getStartTime().getTime(), dbMatch.getStartTime().getTime());
        Assert.assertEquals(dbMatch.getEndTime(), teamMatch.getEndTime());
    }

    @Test
    public void testFindMatchByNonexistentId()
    {
        long time = System.currentTimeMillis();
        Team homeTeam = new Team("HomeTeam");
        Team awayTeam = new Team("AwayTeam");
        em.persist(homeTeam);
        em.persist(awayTeam);

        TeamMatch teamMatch = new TeamMatch(homeTeam, awayTeam, new Date(time));

        em.persist(teamMatch);

        UUID badId = UUID.randomUUID();
        Assert.assertNotEquals(badId, teamMatch.getId());

        TeamMatch dbMatch = teamMatchDao.findMatchById(badId);

        em.flush();

        Assert.assertNull(dbMatch);
    }

    @Test
    public void testFindMatchByHomeTeam()
    {
        long time = System.currentTimeMillis();
        Team homeTeam1 = new Team("HomeTeam1");
        Team awayTeam1 = new Team("AwayTeam1");
        Team homeTeam2 = new Team("HomeTeam2");
        Team awayTeam2 = new Team("AwayTeam2");
        em.persist(homeTeam1);
        em.persist(awayTeam1);
        em.persist(homeTeam2);
        em.persist(awayTeam2);

        TeamMatch teamMatch1 = new TeamMatch(homeTeam1, awayTeam1, new Date(time));
        TeamMatch teamMatch2 = new TeamMatch(homeTeam2, awayTeam1, new Date(time + 1000));
        TeamMatch teamMatch3 = new TeamMatch(homeTeam1, awayTeam2, new Date(time + 2500));

        em.persist(teamMatch1);
        em.persist(teamMatch2);
        em.persist(teamMatch3);

        Collection<TeamMatch> dbMatches = teamMatchDao.findMatchByHomeTeam(homeTeam1);

        em.flush();

        Assert.assertEquals(dbMatches.size(), 2);
        Assert.assertTrue(dbMatches.contains(teamMatch1));
        Assert.assertTrue(dbMatches.contains(teamMatch3));
        Assert.assertFalse(dbMatches.contains(teamMatch2));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testFindMatchByHomeTeamNull()
    {
        Collection<TeamMatch> dbMatches = teamMatchDao.findMatchByHomeTeam(null);
    }

    @Test
    public void testFindMatchByHomeTeamNonexistent()
    {
        long time = System.currentTimeMillis();
        Team homeTeam1 = new Team("HomeTeam1");
        Team awayTeam1 = new Team("AwayTeam1");
        Team homeTeam2 = new Team("HomeTeam2");
        Team awayTeam2 = new Team("AwayTeam2");
        em.persist(homeTeam1);
        em.persist(awayTeam1);
        em.persist(homeTeam2);
        em.persist(awayTeam2);

        TeamMatch teamMatch1 = new TeamMatch(homeTeam1, awayTeam1, new Date(time));
        TeamMatch teamMatch2 = new TeamMatch(homeTeam2, awayTeam1, new Date(time + 1000));
        TeamMatch teamMatch3 = new TeamMatch(homeTeam1, awayTeam2, new Date(time + 2500));

        em.persist(teamMatch1);
        em.persist(teamMatch2);
        em.persist(teamMatch3);

        Collection<TeamMatch> dbMatches = teamMatchDao.findMatchByHomeTeam(new Team("homeTeam3"));

        em.flush();

        Assert.assertTrue(dbMatches.isEmpty());
    }

    @Test
    public void testFindMatchByAwayTeam()
    {
        long time = System.currentTimeMillis();
        Team homeTeam1 = new Team("HomeTeam1");
        Team awayTeam1 = new Team("AwayTeam1");
        Team homeTeam2 = new Team("HomeTeam2");
        Team awayTeam2 = new Team("AwayTeam2");
        em.persist(homeTeam1);
        em.persist(awayTeam1);
        em.persist(homeTeam2);
        em.persist(awayTeam2);

        TeamMatch teamMatch1 = new TeamMatch(homeTeam1, awayTeam1, new Date(time));
        TeamMatch teamMatch2 = new TeamMatch(homeTeam2, awayTeam1, new Date(time + 1000));
        TeamMatch teamMatch3 = new TeamMatch(homeTeam1, awayTeam2, new Date(time + 2500));

        em.persist(teamMatch1);
        em.persist(teamMatch2);
        em.persist(teamMatch3);

        Collection<TeamMatch> dbMatches = teamMatchDao.findMatchByAwayTeam(awayTeam1);

        em.flush();

        Assert.assertEquals(dbMatches.size(), 2);
        Assert.assertTrue(dbMatches.contains(teamMatch1));
        Assert.assertTrue(dbMatches.contains(teamMatch2));
        Assert.assertFalse(dbMatches.contains(teamMatch3));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testFindMatchByAwayTeamNull()
    {
        Collection<TeamMatch> dbMatches = teamMatchDao.findMatchByAwayTeam(null);
        em.flush();
    }

    @Test
    public void testFindMatchByAwayTeamNonexistent()
    {
        long time = System.currentTimeMillis();
        Team homeTeam1 = new Team("HomeTeam1");
        Team awayTeam1 = new Team("AwayTeam1");
        Team homeTeam2 = new Team("HomeTeam2");
        Team awayTeam2 = new Team("AwayTeam2");
        em.persist(homeTeam1);
        em.persist(awayTeam1);
        em.persist(homeTeam2);
        em.persist(awayTeam2);

        TeamMatch teamMatch1 = new TeamMatch(homeTeam1, awayTeam1, new Date(time));
        TeamMatch teamMatch2 = new TeamMatch(homeTeam2, awayTeam1, new Date(time + 1000));
        TeamMatch teamMatch3 = new TeamMatch(homeTeam1, awayTeam2, new Date(time + 2500));

        em.persist(teamMatch1);
        em.persist(teamMatch2);
        em.persist(teamMatch3);

        Collection<TeamMatch> dbMatches = teamMatchDao.findMatchByAwayTeam(new Team("awayTeam3"));

        em.flush();

        Assert.assertTrue(dbMatches.isEmpty());
    }

    @Test
    public void testFindMatchByStartTime()
    {
        long time = System.currentTimeMillis();
        Team homeTeam1 = new Team("HomeTeam1");
        Team awayTeam1 = new Team("AwayTeam1");
        Team homeTeam2 = new Team("HomeTeam2");
        Team awayTeam2 = new Team("AwayTeam2");
        em.persist(homeTeam1);
        em.persist(awayTeam1);
        em.persist(homeTeam2);
        em.persist(awayTeam2);

        TeamMatch teamMatch1 = new TeamMatch(homeTeam1, awayTeam1, new Date(time));
        TeamMatch teamMatch2 = new TeamMatch(homeTeam2, awayTeam1, new Date(time + 1000));
        TeamMatch teamMatch3 = new TeamMatch(homeTeam1, awayTeam2, new Date(time + 1000));

        em.persist(teamMatch1);
        em.persist(teamMatch2);
        em.persist(teamMatch3);

        Collection<TeamMatch> dbMatches = teamMatchDao.findMatchByStartTime(new Date(time + 1000));

        em.flush();

        Assert.assertEquals(dbMatches.size(), 2);
        Assert.assertTrue(dbMatches.contains(teamMatch2));
        Assert.assertTrue(dbMatches.contains(teamMatch3));
        Assert.assertFalse(dbMatches.contains(teamMatch1));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testFindMatchByStartTimeNull()
    {
        Collection<TeamMatch> dbMatches = teamMatchDao.findMatchByStartTime(null);
        em.flush();
    }

    @Test
    public void testFindAllMatches()
    {
        long time = System.currentTimeMillis();
        Team homeTeam1 = new Team("HomeTeam1");
        Team awayTeam1 = new Team("AwayTeam1");
        Team homeTeam2 = new Team("HomeTeam2");
        Team awayTeam2 = new Team("AwayTeam2");
        em.persist(homeTeam1);
        em.persist(awayTeam1);
        em.persist(homeTeam2);
        em.persist(awayTeam2);

        TeamMatch teamMatch1 = new TeamMatch(homeTeam1, awayTeam1, new Date(time));
        TeamMatch teamMatch2 = new TeamMatch(homeTeam2, awayTeam1, new Date(time + 1000));
        TeamMatch teamMatch3 = new TeamMatch(homeTeam1, awayTeam2, new Date(time + 2500));

        em.persist(teamMatch1);
        em.persist(teamMatch2);
        em.persist(teamMatch3);

        Collection<TeamMatch> dbMatches = teamMatchDao.findAllMatches();

        em.flush();

        Assert.assertEquals(dbMatches.size(), 3);
        Assert.assertTrue(dbMatches.contains(teamMatch1));
        Assert.assertTrue(dbMatches.contains(teamMatch2));
        Assert.assertTrue(dbMatches.contains(teamMatch3));
    }

}
