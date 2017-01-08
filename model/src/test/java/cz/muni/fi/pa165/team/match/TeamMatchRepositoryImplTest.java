package cz.muni.fi.pa165.team.match;

import cz.muni.fi.pa165.config.ModelConfig;
import cz.muni.fi.pa165.team.Team;
import cz.muni.fi.pa165.team.match.exceptions.MatchNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.UUID;

/**
 * @author Filip Procházka <filip@prochazka.su>
 */
@ContextConfiguration(classes = ModelConfig.class)
public class TeamMatchRepositoryImplTest extends AbstractTransactionalTestNGSpringContextTests
{

    private LocalDateTime now = LocalDateTime.now();

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

        TeamMatch teamMatch = new TeamMatch(homeTeam, awayTeam, now);

        em.persist(teamMatch);
        em.flush();

        TeamMatch dbMatch = teamMatchRepository.getMatchById(teamMatch.getId());

        Assert.assertEquals(dbMatch.getAwayTeam(), teamMatch.getAwayTeam());
        Assert.assertEquals(dbMatch.getHomeTeam(), teamMatch.getHomeTeam());
        Assert.assertEquals(dbMatch.getStartTime(), dbMatch.getStartTime());
        Assert.assertEquals(dbMatch.getEndTime(), teamMatch.getEndTime());
    }

    @Test(expectedExceptions = { IllegalArgumentException.class },
          expectedExceptionsMessageRegExp = "Cannot search for a null match id")
    public void testGetMatchByNullId()
    {
        Team homeTeam = new Team("HomeTeam");
        Team awayTeam = new Team("AwayTeam");
        em.persist(homeTeam);
        em.persist(awayTeam);

        TeamMatch teamMatch = new TeamMatch(homeTeam, awayTeam, now);

        em.persist(teamMatch);
        em.flush();

        TeamMatch dbMatch = teamMatchRepository.getMatchById(null);
    }

    @Test
    public void testGetMatchByNonexistentId()
    {
        Team homeTeam = new Team("HomeTeam");
        Team awayTeam = new Team("AwayTeam");
        em.persist(homeTeam);
        em.persist(awayTeam);

        TeamMatch teamMatch = new TeamMatch(homeTeam, awayTeam, now);

        em.persist(teamMatch);
        em.flush();

        UUID badId = UUID.randomUUID();
        Assert.assertNotEquals(badId, teamMatch.getId());

        try {
            teamMatchRepository.getMatchById(badId);
            Assert.fail("Expected exception MatchNotFoundException");
        } catch (MatchNotFoundException ex) {
            Assert.assertEquals(ex.getMatchId(), badId);
        }
    }

    @Test
    public void testFindMatchByHomeTeam()
    {
        Team homeTeam1 = new Team("HomeTeam1");
        Team awayTeam1 = new Team("AwayTeam1");
        Team homeTeam2 = new Team("HomeTeam2");
        Team awayTeam2 = new Team("AwayTeam2");
        em.persist(homeTeam1);
        em.persist(awayTeam1);
        em.persist(homeTeam2);
        em.persist(awayTeam2);

        TeamMatch teamMatch1 = new TeamMatch(homeTeam1, awayTeam1, now);
        TeamMatch teamMatch2 = new TeamMatch(homeTeam2, awayTeam1, now.plusMinutes(1));
        TeamMatch teamMatch3 = new TeamMatch(homeTeam1, awayTeam2, now.plusMinutes(2));

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

    @Test(expectedExceptions = { IllegalArgumentException.class },
          expectedExceptionsMessageRegExp = "Cannot search for null match home team")
    public void testFindMatchByHomeTeamNull()
    {
        Team homeTeam1 = new Team("HomeTeam1");
        Team awayTeam1 = new Team("AwayTeam1");
        Team homeTeam2 = new Team("HomeTeam2");
        Team awayTeam2 = new Team("AwayTeam2");
        em.persist(homeTeam1);
        em.persist(awayTeam1);
        em.persist(homeTeam2);
        em.persist(awayTeam2);

        TeamMatch teamMatch1 = new TeamMatch(homeTeam1, awayTeam1, now);
        TeamMatch teamMatch2 = new TeamMatch(homeTeam2, awayTeam1, now.plusMinutes(1));
        TeamMatch teamMatch3 = new TeamMatch(homeTeam1, awayTeam2, now.plusMinutes(2));

        em.persist(teamMatch1);
        em.persist(teamMatch2);
        em.persist(teamMatch3);
        em.flush();

        Collection<TeamMatch> dbMatches = teamMatchRepository.findMatchByHomeTeam(null);
    }

    @Test
    public void testFindMatchByHomeTeamNonexistent()
    {
        Team homeTeam1 = new Team("HomeTeam1");
        Team awayTeam1 = new Team("AwayTeam1");
        Team homeTeam2 = new Team("HomeTeam2");
        Team awayTeam2 = new Team("AwayTeam2");
        em.persist(homeTeam1);
        em.persist(awayTeam1);
        em.persist(homeTeam2);
        em.persist(awayTeam2);

        TeamMatch teamMatch1 = new TeamMatch(homeTeam1, awayTeam1, now);
        TeamMatch teamMatch2 = new TeamMatch(homeTeam2, awayTeam1, now.plusMinutes(1));
        TeamMatch teamMatch3 = new TeamMatch(homeTeam1, awayTeam2, now.plusMinutes(2));

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
        Team homeTeam1 = new Team("HomeTeam1");
        Team awayTeam1 = new Team("AwayTeam1");
        Team homeTeam2 = new Team("HomeTeam2");
        Team awayTeam2 = new Team("AwayTeam2");
        em.persist(homeTeam1);
        em.persist(awayTeam1);
        em.persist(homeTeam2);
        em.persist(awayTeam2);

        TeamMatch teamMatch1 = new TeamMatch(homeTeam1, awayTeam1, now);
        TeamMatch teamMatch2 = new TeamMatch(homeTeam2, awayTeam1, now.plusMinutes(1));
        TeamMatch teamMatch3 = new TeamMatch(homeTeam1, awayTeam2, now.plusMinutes(2));

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

    @Test(expectedExceptions = { IllegalArgumentException.class },
          expectedExceptionsMessageRegExp = "Cannot search for null match away team")
    public void testFindMatchByAwayTeamNull()
    {
        Team homeTeam1 = new Team("HomeTeam1");
        Team awayTeam1 = new Team("AwayTeam1");
        Team homeTeam2 = new Team("HomeTeam2");
        Team awayTeam2 = new Team("AwayTeam2");
        em.persist(homeTeam1);
        em.persist(awayTeam1);
        em.persist(homeTeam2);
        em.persist(awayTeam2);

        TeamMatch teamMatch1 = new TeamMatch(homeTeam1, awayTeam1, now);
        TeamMatch teamMatch2 = new TeamMatch(homeTeam2, awayTeam1, now.plusMinutes(1));
        TeamMatch teamMatch3 = new TeamMatch(homeTeam1, awayTeam2, now.plusMinutes(2));

        em.persist(teamMatch1);
        em.persist(teamMatch2);
        em.persist(teamMatch3);
        em.flush();

        Collection<TeamMatch> dbMatches = teamMatchRepository.findMatchByAwayTeam(null);
    }

    @Test
    public void testFindMatchByAwayTeamNonexistent()
    {
        Team homeTeam1 = new Team("HomeTeam1");
        Team awayTeam1 = new Team("AwayTeam1");
        Team homeTeam2 = new Team("HomeTeam2");
        Team awayTeam2 = new Team("AwayTeam2");
        em.persist(homeTeam1);
        em.persist(awayTeam1);
        em.persist(homeTeam2);
        em.persist(awayTeam2);

        TeamMatch teamMatch1 = new TeamMatch(homeTeam1, awayTeam1, now);
        TeamMatch teamMatch2 = new TeamMatch(homeTeam2, awayTeam1, now.plusMinutes(1));
        TeamMatch teamMatch3 = new TeamMatch(homeTeam1, awayTeam2, now.plusMinutes(2));

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
        Team homeTeam1 = new Team("HomeTeam1");
        Team awayTeam1 = new Team("AwayTeam1");
        Team homeTeam2 = new Team("HomeTeam2");
        Team awayTeam2 = new Team("AwayTeam2");
        em.persist(homeTeam1);
        em.persist(awayTeam1);
        em.persist(homeTeam2);
        em.persist(awayTeam2);

        TeamMatch teamMatch1 = new TeamMatch(homeTeam1, awayTeam1, now);
        TeamMatch teamMatch2 = new TeamMatch(homeTeam2, awayTeam1, now.plusMinutes(1));
        TeamMatch teamMatch3 = new TeamMatch(homeTeam1, awayTeam2, now.plusMinutes(1));

        em.persist(teamMatch1);
        em.persist(teamMatch2);
        em.persist(teamMatch3);
        em.flush();

        Collection<TeamMatch> dbMatches = teamMatchRepository.findMatchByStartTime(now.plusMinutes(1));

        Assert.assertEquals(dbMatches.size(), 2);
        Assert.assertTrue(dbMatches.contains(teamMatch2));
        Assert.assertTrue(dbMatches.contains(teamMatch3));
        Assert.assertFalse(dbMatches.contains(teamMatch1));
    }

    @Test(expectedExceptions = { IllegalArgumentException.class },
          expectedExceptionsMessageRegExp = "Cannot search for null match start time")
    public void testFindMatchByStartTimeNull()
    {
        Team homeTeam1 = new Team("HomeTeam1");
        Team awayTeam1 = new Team("AwayTeam1");
        Team homeTeam2 = new Team("HomeTeam2");
        Team awayTeam2 = new Team("AwayTeam2");
        em.persist(homeTeam1);
        em.persist(awayTeam1);
        em.persist(homeTeam2);
        em.persist(awayTeam2);

        TeamMatch teamMatch1 = new TeamMatch(homeTeam1, awayTeam1, now);
        TeamMatch teamMatch2 = new TeamMatch(homeTeam2, awayTeam1, now.plusMinutes(1));
        TeamMatch teamMatch3 = new TeamMatch(homeTeam1, awayTeam2, now.plusMinutes(1));

        em.persist(teamMatch1);
        em.persist(teamMatch2);
        em.persist(teamMatch3);
        em.flush();

        Collection<TeamMatch> dbMatches = teamMatchRepository.findMatchByStartTime(null);
    }

    @Test
    public void testFindAllMatches()
    {
        Team homeTeam1 = new Team("HomeTeam1");
        Team awayTeam1 = new Team("AwayTeam1");
        Team homeTeam2 = new Team("HomeTeam2");
        Team awayTeam2 = new Team("AwayTeam2");
        em.persist(homeTeam1);
        em.persist(awayTeam1);
        em.persist(homeTeam2);
        em.persist(awayTeam2);

        TeamMatch teamMatch1 = new TeamMatch(homeTeam1, awayTeam1, now);
        TeamMatch teamMatch2 = new TeamMatch(homeTeam2, awayTeam1, now.plusMinutes(1));
        TeamMatch teamMatch3 = new TeamMatch(homeTeam1, awayTeam2, now.plusMinutes(2));

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
        Team homeTeam1 = new Team("HomeTeam1");
        Team awayTeam1 = new Team("AwayTeam1");
        Team homeTeam2 = new Team("HomeTeam2");
        Team awayTeam2 = new Team("AwayTeam2");
        em.persist(homeTeam1);
        em.persist(awayTeam1);
        em.persist(homeTeam2);
        em.persist(awayTeam2);

        TeamMatch teamMatch1 = new TeamMatch(homeTeam1, awayTeam1, now, now.plusMinutes(60));
        TeamMatch teamMatch2 = new TeamMatch(homeTeam2, awayTeam1, now.plusMinutes(1));
        TeamMatch teamMatch3 = new TeamMatch(homeTeam1, awayTeam2, now.plusMinutes(2), now.plusMinutes(62));

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
        Team homeTeam1 = new Team("HomeTeam1");
        Team awayTeam1 = new Team("AwayTeam1");
        Team homeTeam2 = new Team("HomeTeam2");
        Team awayTeam2 = new Team("AwayTeam2");
        em.persist(homeTeam1);
        em.persist(awayTeam1);
        em.persist(homeTeam2);
        em.persist(awayTeam2);

        TeamMatch teamMatch1 = new TeamMatch(homeTeam1, awayTeam1, now, now.plusMinutes(60));
        TeamMatch teamMatch2 = new TeamMatch(homeTeam2, awayTeam1, now.plusMinutes(1));
        TeamMatch teamMatch3 = new TeamMatch(homeTeam1, awayTeam2, now.plusMinutes(2), now.plusMinutes(62));

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
        Team team1 = new Team("Team1");
        Team team2 = new Team("Team2");
        Team team3 = new Team("Team3");
        Team team4 = new Team("Team4");
        em.persist(team1);
        em.persist(team2);
        em.persist(team3);
        em.persist(team4);

        TeamMatch teamMatch1 = new TeamMatch(team1, team2, now, now.plusMinutes(60));
        TeamMatch teamMatch2 = new TeamMatch(team2, team3, now.plusMinutes(1));
        TeamMatch teamMatch3 = new TeamMatch(team3, team4, now.plusMinutes(2), now.plusMinutes(62));
        TeamMatch teamMatch4 = new TeamMatch(team2, team1, now.plusMinutes(6), now.plusMinutes(66));

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

    @Test(expectedExceptions = { IllegalArgumentException.class },
          expectedExceptionsMessageRegExp = "Cannot search all played matches for a null team")
    public void testFindAllPlayedMatchesOfNullTeam()
    {
        Team team1 = new Team("Team1");
        Team team2 = new Team("Team2");
        Team team3 = new Team("Team3");
        Team team4 = new Team("Team4");
        em.persist(team1);
        em.persist(team2);
        em.persist(team3);
        em.persist(team4);

        TeamMatch teamMatch1 = new TeamMatch(team1, team2, now, now.plusMinutes(60));
        TeamMatch teamMatch2 = new TeamMatch(team2, team3, now.plusMinutes(1));
        TeamMatch teamMatch3 = new TeamMatch(team3, team4, now.plusMinutes(2), now.plusMinutes(62));
        TeamMatch teamMatch4 = new TeamMatch(team2, team1, now.plusMinutes(6), now.plusMinutes(66));

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
        Team team1 = new Team("Team1");
        Team team2 = new Team("Team2");
        Team team3 = new Team("Team3");
        Team team4 = new Team("Team4");
        em.persist(team1);
        em.persist(team2);
        em.persist(team3);
        em.persist(team4);

        TeamMatch teamMatch1 = new TeamMatch(team1, team2, now, now.plusMinutes(60));
        TeamMatch teamMatch2 = new TeamMatch(team2, team3, now.plusMinutes(1));
        TeamMatch teamMatch3 = new TeamMatch(team3, team4, now.plusMinutes(2), now.plusMinutes(62));
        TeamMatch teamMatch4 = new TeamMatch(team2, team1, now.plusMinutes(6), now.plusMinutes(66));

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
        Team team1 = new Team("Team1");
        Team team2 = new Team("Team2");
        Team team3 = new Team("Team3");
        Team team4 = new Team("Team4");
        em.persist(team1);
        em.persist(team2);
        em.persist(team3);
        em.persist(team4);

        TeamMatch teamMatch1 = new TeamMatch(team1, team2, now, now.plusMinutes(60));
        TeamMatch teamMatch2 = new TeamMatch(team2, team3, now.plusMinutes(1));
        TeamMatch teamMatch3 = new TeamMatch(team3, team4, now.plusMinutes(2), now.plusMinutes(62));
        TeamMatch teamMatch4 = new TeamMatch(team2, team1, now.plusMinutes(6));

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

    @Test(expectedExceptions = { IllegalArgumentException.class },
          expectedExceptionsMessageRegExp = "Cannot search all planned matches for a null team")
    public void testFindAllPlannedMatchesOfNullTeam()
    {
        Team team1 = new Team("Team1");
        Team team2 = new Team("Team2");
        Team team3 = new Team("Team3");
        Team team4 = new Team("Team4");
        em.persist(team1);
        em.persist(team2);
        em.persist(team3);
        em.persist(team4);

        TeamMatch teamMatch1 = new TeamMatch(team1, team2, now, now.plusMinutes(60));
        TeamMatch teamMatch2 = new TeamMatch(team2, team3, now.plusMinutes(1));
        TeamMatch teamMatch3 = new TeamMatch(team3, team4, now.plusMinutes(2), now.plusMinutes(62));
        TeamMatch teamMatch4 = new TeamMatch(team2, team1, now.plusMinutes(6));

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
        Team team1 = new Team("Team1");
        Team team2 = new Team("Team2");
        Team team3 = new Team("Team3");
        Team team4 = new Team("Team4");
        em.persist(team1);
        em.persist(team2);
        em.persist(team3);
        em.persist(team4);

        TeamMatch teamMatch1 = new TeamMatch(team1, team2, now, now.plusMinutes(60));
        TeamMatch teamMatch2 = new TeamMatch(team2, team3, now.plusMinutes(1));
        TeamMatch teamMatch3 = new TeamMatch(team3, team4, now.plusMinutes(2), now.plusMinutes(62));
        TeamMatch teamMatch4 = new TeamMatch(team2, team1, now.plusMinutes(6));

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
        Team team1 = new Team("Team1");
        Team team2 = new Team("Team2");
        Team team3 = new Team("Team3");
        Team team4 = new Team("Team4");
        em.persist(team1);
        em.persist(team2);
        em.persist(team3);
        em.persist(team4);

        TeamMatch teamMatch1 = new TeamMatch(team1, team2, now, now.plusMinutes(60));
        TeamMatch teamMatch2 = new TeamMatch(team2, team3, now.plusMinutes(1));
        TeamMatch teamMatch3 = new TeamMatch(team3, team4, now.plusMinutes(2), now.plusMinutes(62));
        TeamMatch teamMatch4 = new TeamMatch(team2, team1, now.plusMinutes(6));

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

    @Test(expectedExceptions = { IllegalArgumentException.class },
          expectedExceptionsMessageRegExp = "Cannot search all matches for a null team")
    public void testFindAllMatchesOfNullTeam()
    {
        Team team1 = new Team("Team1");
        Team team2 = new Team("Team2");
        Team team3 = new Team("Team3");
        Team team4 = new Team("Team4");
        em.persist(team1);
        em.persist(team2);
        em.persist(team3);
        em.persist(team4);

        TeamMatch teamMatch1 = new TeamMatch(team1, team2, now, now.plusMinutes(60));
        TeamMatch teamMatch2 = new TeamMatch(team2, team3, now.plusMinutes(1));
        TeamMatch teamMatch3 = new TeamMatch(team3, team4, now.plusMinutes(2), now.plusMinutes(62));
        TeamMatch teamMatch4 = new TeamMatch(team2, team1, now.plusMinutes(6));

        em.persist(teamMatch1);
        em.persist(teamMatch2);
        em.persist(teamMatch3);
        em.persist(teamMatch4);
        em.flush();

        Collection<TeamMatch> dbMatches = teamMatchRepository.findAllMatchesOfTeam(null);
    }

    @Test
    public void testFindAllMatchesOfNonexistentTeam()
    {
        Team team1 = new Team("Team1");
        Team team2 = new Team("Team2");
        Team team3 = new Team("Team3");
        Team team4 = new Team("Team4");
        em.persist(team1);
        em.persist(team2);
        em.persist(team3);
        em.persist(team4);

        TeamMatch teamMatch1 = new TeamMatch(team1, team2, now, now.plusMinutes(60));
        TeamMatch teamMatch2 = new TeamMatch(team2, team3, now.plusMinutes(1));
        TeamMatch teamMatch3 = new TeamMatch(team3, team4, now.plusMinutes(2), now.plusMinutes(62));
        TeamMatch teamMatch4 = new TeamMatch(team2, team1, now.plusMinutes(6));

        em.persist(teamMatch1);
        em.persist(teamMatch2);
        em.persist(teamMatch3);
        em.persist(teamMatch4);
        em.flush();

        Collection<TeamMatch> dbMatches = teamMatchRepository.findAllMatchesOfTeam(new Team("Team5").getId());

        Assert.assertTrue(dbMatches.isEmpty());
    }

    @Test
    public void testFindConflictingMatchByTeamAndStartTimeAsAwayTeam()
    {
        Team team1 = new Team("Team1");
        Team team2 = new Team("Team2");
        Team team3 = new Team("Team3");
        Team team4 = new Team("Team4");
        em.persist(team1);
        em.persist(team2);
        em.persist(team3);
        em.persist(team4);

        TeamMatch teamMatch1 = new TeamMatch(team1, team2, now);
        TeamMatch teamMatch2 = new TeamMatch(team3, team4, now.plusMinutes(15));

        em.persist(teamMatch1);
        em.persist(teamMatch2);
        em.flush();

        TeamMatch conflictingMatch =
            teamMatchRepository.findConflictingMatchByTeamAndStartTime(team2.getId(), now);

        Assert.assertNotNull(conflictingMatch);
        Assert.assertEquals(conflictingMatch.getAwayTeam(), team2);
        Assert.assertEquals(conflictingMatch.getStartTime(), now);
    }

    @Test
    public void testFindConflictingMatchByTeamAndStartTimeAsHomeTeam()
    {
        Team team1 = new Team("Team1");
        Team team2 = new Team("Team2");
        Team team3 = new Team("Team3");
        Team team4 = new Team("Team4");
        em.persist(team1);
        em.persist(team2);
        em.persist(team3);
        em.persist(team4);

        TeamMatch teamMatch1 = new TeamMatch(team1, team2, now);
        TeamMatch teamMatch2 = new TeamMatch(team3, team4, now.plusMinutes(15));

        em.persist(teamMatch1);
        em.persist(teamMatch2);
        em.flush();

        TeamMatch conflictingMatch =
            teamMatchRepository.findConflictingMatchByTeamAndStartTime(team3.getId(), now.plusMinutes(15));

        Assert.assertNotNull(conflictingMatch);
        Assert.assertEquals(conflictingMatch.getHomeTeam(), team3);
        Assert.assertEquals(conflictingMatch.getStartTime(), now.plusMinutes(15));
    }

    @Test
    public void testFindConflictingMatchByTeamAndStartTimeNoConflictWithTeam()
    {
        Team team1 = new Team("Team1");
        Team team2 = new Team("Team2");
        Team team3 = new Team("Team3");
        Team team4 = new Team("Team4");
        em.persist(team1);
        em.persist(team2);
        em.persist(team3);
        em.persist(team4);

        TeamMatch teamMatch1 = new TeamMatch(team1, team2, now);
        TeamMatch teamMatch2 = new TeamMatch(team3, team4, now.plusMinutes(15));

        em.persist(teamMatch1);
        em.persist(teamMatch2);
        em.flush();

        TeamMatch conflictingMatch =
            teamMatchRepository.findConflictingMatchByTeamAndStartTime(new Team("Team5").getId(), now.plusMinutes(15));

        Assert.assertNull(conflictingMatch);
    }

    @Test
    public void testFindConflictingMatchByTeamAndStartTimeNoConflictWithTime()
    {
        Team team1 = new Team("Team1");
        Team team2 = new Team("Team2");
        Team team3 = new Team("Team3");
        Team team4 = new Team("Team4");
        em.persist(team1);
        em.persist(team2);
        em.persist(team3);
        em.persist(team4);

        TeamMatch teamMatch1 = new TeamMatch(team1, team2, now);
        TeamMatch teamMatch2 = new TeamMatch(team3, team4, now.plusMinutes(15));

        em.persist(teamMatch1);
        em.persist(teamMatch2);
        em.flush();

        TeamMatch conflictingMatch =
            teamMatchRepository.findConflictingMatchByTeamAndStartTime(team4.getId(), now);

        Assert.assertNull(conflictingMatch);
    }

    @Test(expectedExceptions = { IllegalArgumentException.class },
          expectedExceptionsMessageRegExp = "Cannot search for conflicting match with a null team")
    public void testFindConflictingMatchByNullTeamAndStartTime()
    {
        Team team1 = new Team("Team1");
        Team team2 = new Team("Team2");
        Team team3 = new Team("Team3");
        Team team4 = new Team("Team4");
        em.persist(team1);
        em.persist(team2);
        em.persist(team3);
        em.persist(team4);

        TeamMatch teamMatch1 = new TeamMatch(team1, team2, now);
        TeamMatch teamMatch2 = new TeamMatch(team3, team4, now.plusMinutes(15));

        em.persist(teamMatch1);
        em.persist(teamMatch2);
        em.flush();

        TeamMatch conflictingMatch =
            teamMatchRepository.findConflictingMatchByTeamAndStartTime(null, now);
    }

    @Test(expectedExceptions = { IllegalArgumentException.class },
          expectedExceptionsMessageRegExp = "Cannot search for conflicting match with a null match start time")
    public void testFindConflictingMatchByTeamAndNullStartTime()
    {
        Team team1 = new Team("Team1");
        Team team2 = new Team("Team2");
        Team team3 = new Team("Team3");
        Team team4 = new Team("Team4");
        em.persist(team1);
        em.persist(team2);
        em.persist(team3);
        em.persist(team4);

        TeamMatch teamMatch1 = new TeamMatch(team1, team2, now);
        TeamMatch teamMatch2 = new TeamMatch(team3, team4, now.plusMinutes(15));

        em.persist(teamMatch1);
        em.persist(teamMatch2);
        em.flush();

        TeamMatch conflictingMatch =
            teamMatchRepository.findConflictingMatchByTeamAndStartTime(team1.getId(), null);
    }
}
