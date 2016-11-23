package cz.muni.fi.pa165.team;

import cz.muni.fi.pa165.config.ApplicationConfig;
import cz.muni.fi.pa165.team.exceptions.MatchNotFoundException;
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
public class TeamMatchRepositoryImplTest extends AbstractTransactionalTestNGSpringContextTests
{

    @Autowired
    public TeamMatchRepositoryImpl teamMatchRepository;

    @PersistenceContext
    public EntityManager em;

    @Test
    public void testGetMatchById()
    {
        Team homeTeam = new Team("HomeTeam");
        Team awayTeam = new Team("AwayTeam");
        em.persist(homeTeam);
        em.persist(awayTeam);

        TeamMatch teamMatch = new TeamMatch(homeTeam, awayTeam, new Date());

        em.persist(teamMatch);
        em.flush();

        TeamMatch dbMatch = teamMatchRepository.getMatchById(teamMatch.getId());

        Assert.assertEquals(dbMatch.getAwayTeam(), teamMatch.getAwayTeam());
        Assert.assertEquals(dbMatch.getHomeTeam(), teamMatch.getHomeTeam());
        Assert.assertEquals(dbMatch.getStartTime(), dbMatch.getStartTime());
        Assert.assertEquals(dbMatch.getEndTime(), teamMatch.getEndTime());
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testGetMatchByNullId()
    {
        Team homeTeam = new Team("HomeTeam");
        Team awayTeam = new Team("AwayTeam");
        em.persist(homeTeam);
        em.persist(awayTeam);

        TeamMatch teamMatch = new TeamMatch(homeTeam, awayTeam, new Date());

        em.persist(teamMatch);
        em.flush();

        TeamMatch dbMatch = teamMatchRepository.getMatchById(null);
    }

    @Test(expectedExceptions = MatchNotFoundException.class)
    public void testGetMatchByNonexistentId()
    {
        Team homeTeam = new Team("HomeTeam");
        Team awayTeam = new Team("AwayTeam");
        em.persist(homeTeam);
        em.persist(awayTeam);

        TeamMatch teamMatch = new TeamMatch(homeTeam, awayTeam, new Date());

        em.persist(teamMatch);
        em.flush();

        UUID badId = UUID.randomUUID();
        Assert.assertNotEquals(badId, teamMatch.getId());

        teamMatchRepository.getMatchById(badId);
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
        em.flush();

        Collection<TeamMatch> dbMatches = teamMatchRepository.findMatchByHomeTeam(homeTeam1.getId());

        Assert.assertEquals(dbMatches.size(), 2);
        Assert.assertTrue(dbMatches.contains(teamMatch1));
        Assert.assertTrue(dbMatches.contains(teamMatch3));
        Assert.assertFalse(dbMatches.contains(teamMatch2));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testFindMatchByHomeTeamNull()
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
        em.flush();

        Collection<TeamMatch> dbMatches = teamMatchRepository.findMatchByHomeTeam(null);
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
        em.flush();

        Collection<TeamMatch> dbMatches = teamMatchRepository.findMatchByHomeTeam(new Team("homeTeam3").getId());

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
        em.flush();

        Collection<TeamMatch> dbMatches = teamMatchRepository.findMatchByAwayTeam(awayTeam1.getId());

        Assert.assertEquals(dbMatches.size(), 2);
        Assert.assertTrue(dbMatches.contains(teamMatch1));
        Assert.assertTrue(dbMatches.contains(teamMatch2));
        Assert.assertFalse(dbMatches.contains(teamMatch3));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testFindMatchByAwayTeamNull()
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
        em.flush();

        Collection<TeamMatch> dbMatches = teamMatchRepository.findMatchByAwayTeam(null);
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
        em.flush();

        Collection<TeamMatch> dbMatches = teamMatchRepository.findMatchByAwayTeam(new Team("awayTeam3").getId());

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
        em.flush();

        Collection<TeamMatch> dbMatches = teamMatchRepository.findMatchByStartTime(new Date(time + 1000));

        Assert.assertEquals(dbMatches.size(), 2);
        Assert.assertTrue(dbMatches.contains(teamMatch2));
        Assert.assertTrue(dbMatches.contains(teamMatch3));
        Assert.assertFalse(dbMatches.contains(teamMatch1));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testFindMatchByStartTimeNull()
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
        em.flush();

        Collection<TeamMatch> dbMatches = teamMatchRepository.findMatchByStartTime(null);
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
        em.flush();

        Collection<TeamMatch> dbMatches = teamMatchRepository.findAllMatches();

        Assert.assertEquals(dbMatches.size(), 3);
        Assert.assertTrue(dbMatches.contains(teamMatch1));
        Assert.assertTrue(dbMatches.contains(teamMatch2));
        Assert.assertTrue(dbMatches.contains(teamMatch3));
    }

    @Test
    public void testFindAllPlayedMatches()
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

        TeamMatch teamMatch1 = new TeamMatch(homeTeam1, awayTeam1, new Date(time), new Date(time + 5400000));
        TeamMatch teamMatch2 = new TeamMatch(homeTeam2, awayTeam1, new Date(time + 1000));
        TeamMatch teamMatch3 = new TeamMatch(homeTeam1, awayTeam2, new Date(time + 2500), new Date(time + 5520000));

        em.persist(teamMatch1);
        em.persist(teamMatch2);
        em.persist(teamMatch3);
        em.flush();

        Collection<TeamMatch> dbMatches = teamMatchRepository.findAllPlayedMatches();

        Assert.assertEquals(dbMatches.size(), 2);
        Assert.assertTrue(dbMatches.contains(teamMatch1));
        Assert.assertTrue(dbMatches.contains(teamMatch3));
        Assert.assertFalse(dbMatches.contains(teamMatch2));
    }

    @Test
    public void testFindAllPlannedMatches()
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

        TeamMatch teamMatch1 = new TeamMatch(homeTeam1, awayTeam1, new Date(time), new Date(time + 5400000));
        TeamMatch teamMatch2 = new TeamMatch(homeTeam2, awayTeam1, new Date(time + 1000));
        TeamMatch teamMatch3 = new TeamMatch(homeTeam1, awayTeam2, new Date(time + 2500), new Date(time + 5520000));

        em.persist(teamMatch1);
        em.persist(teamMatch2);
        em.persist(teamMatch3);
        em.flush();

        Collection<TeamMatch> dbMatches = teamMatchRepository.findAllPlannedMatches();

        Assert.assertEquals(dbMatches.size(), 1);
        Assert.assertTrue(dbMatches.contains(teamMatch2));
        Assert.assertFalse(dbMatches.contains(teamMatch1));
        Assert.assertFalse(dbMatches.contains(teamMatch3));
    }

    @Test
    public void testFindAllPlayedMatchesOfTeam()
    {
        long time = System.currentTimeMillis();
        Team team1 = new Team("Team1");
        Team team2 = new Team("Team2");
        Team team3 = new Team("Team3");
        Team team4 = new Team("Team4");
        em.persist(team1);
        em.persist(team2);
        em.persist(team3);
        em.persist(team4);

        TeamMatch teamMatch1 = new TeamMatch(team1, team2, new Date(time), new Date(time + 5400000));
        TeamMatch teamMatch2 = new TeamMatch(team2, team3, new Date(time + 1000));
        TeamMatch teamMatch3 = new TeamMatch(team3, team4, new Date(time + 2500), new Date(time + 5520000));
        TeamMatch teamMatch4 = new TeamMatch(team2, team1, new Date(time + 60000), new Date(time + 5920000));

        em.persist(teamMatch1);
        em.persist(teamMatch2);
        em.persist(teamMatch3);
        em.persist(teamMatch4);
        em.flush();

        Collection<TeamMatch> dbMatches = teamMatchRepository.findAllPlayedMatchesOfTeam(team2.getId());

        Assert.assertEquals(dbMatches.size(), 2);
        Assert.assertTrue(dbMatches.contains(teamMatch1));
        Assert.assertTrue(dbMatches.contains(teamMatch4));
        Assert.assertFalse(dbMatches.contains(teamMatch2));
        Assert.assertFalse(dbMatches.contains(teamMatch3));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testFindAllPlayedMatchesOfNullTeam()
    {
        long time = System.currentTimeMillis();
        Team team1 = new Team("Team1");
        Team team2 = new Team("Team2");
        Team team3 = new Team("Team3");
        Team team4 = new Team("Team4");
        em.persist(team1);
        em.persist(team2);
        em.persist(team3);
        em.persist(team4);

        TeamMatch teamMatch1 = new TeamMatch(team1, team2, new Date(time), new Date(time + 5400000));
        TeamMatch teamMatch2 = new TeamMatch(team2, team3, new Date(time + 1000));
        TeamMatch teamMatch3 = new TeamMatch(team3, team4, new Date(time + 2500), new Date(time + 5520000));
        TeamMatch teamMatch4 = new TeamMatch(team2, team1, new Date(time + 60000), new Date(time + 5920000));

        em.persist(teamMatch1);
        em.persist(teamMatch2);
        em.persist(teamMatch3);
        em.persist(teamMatch4);
        em.flush();

        Collection<TeamMatch> dbMatches = teamMatchRepository.findAllPlayedMatchesOfTeam(null);
    }

    @Test
    public void testFindAllPlayedMatchesOfNonexistentTeam()
    {
        long time = System.currentTimeMillis();
        Team team1 = new Team("Team1");
        Team team2 = new Team("Team2");
        Team team3 = new Team("Team3");
        Team team4 = new Team("Team4");
        em.persist(team1);
        em.persist(team2);
        em.persist(team3);
        em.persist(team4);

        TeamMatch teamMatch1 = new TeamMatch(team1, team2, new Date(time), new Date(time + 5400000));
        TeamMatch teamMatch2 = new TeamMatch(team2, team3, new Date(time + 1000));
        TeamMatch teamMatch3 = new TeamMatch(team3, team4, new Date(time + 2500), new Date(time + 5520000));
        TeamMatch teamMatch4 = new TeamMatch(team2, team1, new Date(time + 60000), new Date(time + 5920000));

        em.persist(teamMatch1);
        em.persist(teamMatch2);
        em.persist(teamMatch3);
        em.persist(teamMatch4);
        em.flush();

        Collection<TeamMatch> dbMatches = teamMatchRepository.findAllPlayedMatchesOfTeam(new Team("Team5").getId());

        Assert.assertTrue(dbMatches.isEmpty());
    }

    @Test
    public void testFindAllPlannedMatchesOfTeam()
    {
        long time = System.currentTimeMillis();
        Team team1 = new Team("Team1");
        Team team2 = new Team("Team2");
        Team team3 = new Team("Team3");
        Team team4 = new Team("Team4");
        em.persist(team1);
        em.persist(team2);
        em.persist(team3);
        em.persist(team4);

        TeamMatch teamMatch1 = new TeamMatch(team1, team2, new Date(time), new Date(time + 5400000));
        TeamMatch teamMatch2 = new TeamMatch(team2, team3, new Date(time + 1000));
        TeamMatch teamMatch3 = new TeamMatch(team3, team4, new Date(time + 2500), new Date(time + 5520000));
        TeamMatch teamMatch4 = new TeamMatch(team2, team1, new Date(time + 60000));

        em.persist(teamMatch1);
        em.persist(teamMatch2);
        em.persist(teamMatch3);
        em.persist(teamMatch4);
        em.flush();

        Collection<TeamMatch> dbMatches = teamMatchRepository.findAllPlannedMatchesOfTeam(team2.getId());

        Assert.assertEquals(dbMatches.size(), 2);
        Assert.assertTrue(dbMatches.contains(teamMatch2));
        Assert.assertTrue(dbMatches.contains(teamMatch4));
        Assert.assertFalse(dbMatches.contains(teamMatch1));
        Assert.assertFalse(dbMatches.contains(teamMatch3));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testFindAllPlannedMatchesOfNullTeam()
    {
        long time = System.currentTimeMillis();
        Team team1 = new Team("Team1");
        Team team2 = new Team("Team2");
        Team team3 = new Team("Team3");
        Team team4 = new Team("Team4");
        em.persist(team1);
        em.persist(team2);
        em.persist(team3);
        em.persist(team4);

        TeamMatch teamMatch1 = new TeamMatch(team1, team2, new Date(time), new Date(time + 5400000));
        TeamMatch teamMatch2 = new TeamMatch(team2, team3, new Date(time + 1000));
        TeamMatch teamMatch3 = new TeamMatch(team3, team4, new Date(time + 2500), new Date(time + 5520000));
        TeamMatch teamMatch4 = new TeamMatch(team2, team1, new Date(time + 60000));

        em.persist(teamMatch1);
        em.persist(teamMatch2);
        em.persist(teamMatch3);
        em.persist(teamMatch4);
        em.flush();

        Collection<TeamMatch> dbMatches = teamMatchRepository.findAllPlannedMatchesOfTeam(null);
    }

    @Test
    public void testFindAllPlannedMatchesOfNonexistentTeam()
    {
        long time = System.currentTimeMillis();
        Team team1 = new Team("Team1");
        Team team2 = new Team("Team2");
        Team team3 = new Team("Team3");
        Team team4 = new Team("Team4");
        em.persist(team1);
        em.persist(team2);
        em.persist(team3);
        em.persist(team4);

        TeamMatch teamMatch1 = new TeamMatch(team1, team2, new Date(time), new Date(time + 5400000));
        TeamMatch teamMatch2 = new TeamMatch(team2, team3, new Date(time + 1000));
        TeamMatch teamMatch3 = new TeamMatch(team3, team4, new Date(time + 2500), new Date(time + 5520000));
        TeamMatch teamMatch4 = new TeamMatch(team2, team1, new Date(time + 60000));

        em.persist(teamMatch1);
        em.persist(teamMatch2);
        em.persist(teamMatch3);
        em.persist(teamMatch4);
        em.flush();

        Collection<TeamMatch> dbMatches = teamMatchRepository.findAllPlannedMatchesOfTeam(new Team("Team5").getId());

        Assert.assertTrue(dbMatches.isEmpty());
    }

    @Test
    public void testFindAllMatchesOfTeam()
    {
        long time = System.currentTimeMillis();
        Team team1 = new Team("Team1");
        Team team2 = new Team("Team2");
        Team team3 = new Team("Team3");
        Team team4 = new Team("Team4");
        em.persist(team1);
        em.persist(team2);
        em.persist(team3);
        em.persist(team4);

        TeamMatch teamMatch1 = new TeamMatch(team1, team2, new Date(time), new Date(time + 5400000));
        TeamMatch teamMatch2 = new TeamMatch(team2, team3, new Date(time + 1000));
        TeamMatch teamMatch3 = new TeamMatch(team3, team4, new Date(time + 2500), new Date(time + 5520000));
        TeamMatch teamMatch4 = new TeamMatch(team2, team1, new Date(time + 60000));

        em.persist(teamMatch1);
        em.persist(teamMatch2);
        em.persist(teamMatch3);
        em.persist(teamMatch4);
        em.flush();

        Collection<TeamMatch> dbMatches = teamMatchRepository.findAllMatchesOfTeam(team2.getId());

        Assert.assertEquals(dbMatches.size(), 3);
        Assert.assertTrue(dbMatches.contains(teamMatch1));
        Assert.assertTrue(dbMatches.contains(teamMatch2));
        Assert.assertTrue(dbMatches.contains(teamMatch4));
        Assert.assertFalse(dbMatches.contains(teamMatch3));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testFindAllMatchesOfNullTeam()
    {
        long time = System.currentTimeMillis();
        Team team1 = new Team("Team1");
        Team team2 = new Team("Team2");
        Team team3 = new Team("Team3");
        Team team4 = new Team("Team4");
        em.persist(team1);
        em.persist(team2);
        em.persist(team3);
        em.persist(team4);

        TeamMatch teamMatch1 = new TeamMatch(team1, team2, new Date(time), new Date(time + 5400000));
        TeamMatch teamMatch2 = new TeamMatch(team2, team3, new Date(time + 1000));
        TeamMatch teamMatch3 = new TeamMatch(team3, team4, new Date(time + 2500), new Date(time + 5520000));
        TeamMatch teamMatch4 = new TeamMatch(team2, team1, new Date(time + 60000));

        em.persist(teamMatch1);
        em.persist(teamMatch2);
        em.persist(teamMatch3);
        em.persist(teamMatch4);
        em.flush();

        Collection<TeamMatch> dbMatches = teamMatchRepository.findAllPlannedMatchesOfTeam(null);
    }

    @Test
    public void testFindAllMatchesOfNonexistentTeam()
    {
        long time = System.currentTimeMillis();
        Team team1 = new Team("Team1");
        Team team2 = new Team("Team2");
        Team team3 = new Team("Team3");
        Team team4 = new Team("Team4");
        em.persist(team1);
        em.persist(team2);
        em.persist(team3);
        em.persist(team4);

        TeamMatch teamMatch1 = new TeamMatch(team1, team2, new Date(time), new Date(time + 5400000));
        TeamMatch teamMatch2 = new TeamMatch(team2, team3, new Date(time + 1000));
        TeamMatch teamMatch3 = new TeamMatch(team3, team4, new Date(time + 2500), new Date(time + 5520000));
        TeamMatch teamMatch4 = new TeamMatch(team2, team1, new Date(time + 60000));

        em.persist(teamMatch1);
        em.persist(teamMatch2);
        em.persist(teamMatch3);
        em.persist(teamMatch4);
        em.flush();

        Collection<TeamMatch> dbMatches = teamMatchRepository.findAllMatchesOfTeam(new Team("Team5").getId());

        Assert.assertTrue(dbMatches.isEmpty());
    }
}
