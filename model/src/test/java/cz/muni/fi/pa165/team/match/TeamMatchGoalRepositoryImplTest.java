package cz.muni.fi.pa165.team.match;

import cz.muni.fi.pa165.config.ModelConfig;
import cz.muni.fi.pa165.team.Team;
import cz.muni.fi.pa165.team.TeamPlayer;
import cz.muni.fi.pa165.team.match.exceptions.GoalNotFoundException;
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
 * @author Denis Galajda <galajda.denis@gmail.com>
 */
@ContextConfiguration(classes = ModelConfig.class)
public class TeamMatchGoalRepositoryImplTest extends AbstractTransactionalTestNGSpringContextTests
{

    private LocalDateTime now = LocalDateTime.now();

    @Autowired
    public TeamMatchGoalRepositoryImpl teamMatchGoalRepository;

    @PersistenceContext
    public EntityManager em;

    @Test
    public void testGetGoalById()
    {
        Team homeTeam = new Team("homeTeam");
        Team awayTeam = new Team("awayTeam");
        em.persist(homeTeam);
        em.persist(awayTeam);

        TeamPlayer scorer = new TeamPlayer("John", "Doe", 187, 95, homeTeam);
        TeamPlayer assistant = new TeamPlayer("Jack", "Reacher", 179, 74, homeTeam);
        em.persist(scorer);
        em.persist(assistant);

        TeamMatch match = new TeamMatch(homeTeam, awayTeam, now.minusMinutes(10));
        em.persist(match);

        TeamMatchGoal goal = new TeamMatchGoal(scorer, assistant, match, now);
        em.persist(goal);
        em.flush();

        TeamMatchGoal dbGoal = teamMatchGoalRepository.getGoalById(goal.getId());

        Assert.assertEquals(dbGoal.getScorer(), goal.getScorer());
        Assert.assertEquals(dbGoal.getAssistant(), goal.getAssistant());
        Assert.assertEquals(dbGoal.getMatch(), goal.getMatch());
        Assert.assertEquals(dbGoal.getMatchTime(), goal.getMatchTime());
    }

    @Test(expectedExceptions = { IllegalArgumentException.class },
          expectedExceptionsMessageRegExp = "Cannot search for a null goal id")
    public void testGetGoalByNullId()
    {
        Team homeTeam = new Team("homeTeam");
        Team awayTeam = new Team("awayTeam");
        em.persist(homeTeam);
        em.persist(awayTeam);

        TeamPlayer scorer = new TeamPlayer("John", "Doe", 187, 95, homeTeam);
        TeamPlayer assistant = new TeamPlayer("Jack", "Reacher", 179, 74, homeTeam);
        em.persist(scorer);
        em.persist(assistant);

        TeamMatch match = new TeamMatch(homeTeam, awayTeam, now.minusMinutes(10));
        em.persist(match);

        TeamMatchGoal goal = new TeamMatchGoal(scorer, assistant, match, now);
        em.persist(goal);
        em.flush();

        TeamMatchGoal dbGoal = teamMatchGoalRepository.getGoalById(null);
    }

    @Test
    public void testFindGoalByNonexistentId()
    {
        Team homeTeam = new Team("homeTeam");
        Team awayTeam = new Team("awayTeam");
        em.persist(homeTeam);
        em.persist(awayTeam);

        TeamPlayer scorer = new TeamPlayer("John", "Doe", 187, 95, homeTeam);
        TeamPlayer assistant = new TeamPlayer("Jack", "Reacher", 179, 74, homeTeam);
        em.persist(scorer);
        em.persist(assistant);

        TeamMatch match = new TeamMatch(homeTeam, awayTeam, now.minusMinutes(10));
        em.persist(match);

        TeamMatchGoal goal = new TeamMatchGoal(scorer, assistant, match, now);
        em.persist(goal);
        em.flush();

        UUID badId = UUID.randomUUID();
        Assert.assertNotEquals(badId, goal.getId());

        try {
            TeamMatchGoal dbGoal = teamMatchGoalRepository.getGoalById(badId);
            Assert.fail("Expected exception GoalNotFoundException");
        } catch (GoalNotFoundException ex) {
            Assert.assertEquals(badId, ex.getGoalId());
        }
    }

    @Test
    public void testFindMatchByScorer()
    {
        Team homeTeam = new Team("homeTeam");
        Team awayTeam = new Team("awayTeam");
        em.persist(homeTeam);
        em.persist(awayTeam);

        TeamPlayer scorer = new TeamPlayer("John", "Doe", 187, 95, homeTeam);
        TeamPlayer assistant = new TeamPlayer("Jack", "Reacher", 179, 74, homeTeam);
        TeamPlayer scorer2 = new TeamPlayer("Bruno", "Fartis", 187, 95, homeTeam);
        TeamPlayer assistant2 = new TeamPlayer("Emil", "Hunter", 179, 74, homeTeam);
        em.persist(scorer);
        em.persist(assistant);
        em.persist(scorer2);
        em.persist(assistant2);

        TeamMatch match = new TeamMatch(homeTeam, awayTeam, now.minusMinutes(10));
        em.persist(match);

        TeamMatchGoal goal1 = new TeamMatchGoal(scorer, assistant, match, now);
        TeamMatchGoal goal2 = new TeamMatchGoal(scorer2, assistant, match, now.plusMinutes(15));
        TeamMatchGoal goal3 = new TeamMatchGoal(scorer, assistant2, match, now.plusMinutes(25));

        em.persist(goal1);
        em.persist(goal2);
        em.persist(goal3);
        em.flush();

        Collection<TeamMatchGoal> dbGoals = teamMatchGoalRepository.findGoalByScorer(scorer.getId());

        Assert.assertEquals(dbGoals.size(), 2);
        Assert.assertTrue(dbGoals.contains(goal1));
        Assert.assertTrue(dbGoals.contains(goal3));
        Assert.assertFalse(dbGoals.contains(goal2));
    }

    @Test(expectedExceptions = { IllegalArgumentException.class },
          expectedExceptionsMessageRegExp = "Cannot search for null scorer")
    public void testFindGoalByScorerNull()
    {
        Team homeTeam = new Team("homeTeam");
        Team awayTeam = new Team("awayTeam");
        em.persist(homeTeam);
        em.persist(awayTeam);

        TeamPlayer scorer = new TeamPlayer("John", "Doe", 187, 95, homeTeam);
        TeamPlayer assistant = new TeamPlayer("Jack", "Reacher", 179, 74, homeTeam);
        TeamPlayer scorer2 = new TeamPlayer("Bruno", "Fartis", 187, 95, homeTeam);
        TeamPlayer assistant2 = new TeamPlayer("Emil", "Hunter", 179, 74, homeTeam);
        em.persist(scorer);
        em.persist(assistant);
        em.persist(scorer2);
        em.persist(assistant2);

        TeamMatch match = new TeamMatch(homeTeam, awayTeam, now.minusMinutes(10));
        em.persist(match);

        TeamMatchGoal goal1 = new TeamMatchGoal(scorer, assistant, match, now);
        TeamMatchGoal goal2 = new TeamMatchGoal(scorer2, assistant, match, now.plusMinutes(15));
        TeamMatchGoal goal3 = new TeamMatchGoal(scorer, assistant2, match, now.plusMinutes(25));

        em.persist(goal1);
        em.persist(goal2);
        em.persist(goal3);
        em.flush();

        Collection<TeamMatchGoal> dbGoals = teamMatchGoalRepository.findGoalByScorer(null);
    }

    @Test
    public void testFindGoalByScorerNonexistent()
    {
        Team homeTeam = new Team("homeTeam");
        Team awayTeam = new Team("awayTeam");
        em.persist(homeTeam);
        em.persist(awayTeam);

        TeamPlayer scorer = new TeamPlayer("John", "Doe", 187, 95, homeTeam);
        TeamPlayer assistant = new TeamPlayer("Jack", "Reacher", 179, 74, homeTeam);
        TeamPlayer scorer2 = new TeamPlayer("Bruno", "Fartis", 187, 95, homeTeam);
        TeamPlayer assistant2 = new TeamPlayer("Emil", "Hunter", 179, 74, homeTeam);
        em.persist(scorer);
        em.persist(assistant);
        em.persist(scorer2);
        em.persist(assistant2);

        TeamMatch match = new TeamMatch(homeTeam, awayTeam, now.minusMinutes(10));
        em.persist(match);

        TeamMatchGoal goal1 = new TeamMatchGoal(scorer, assistant, match, now);
        TeamMatchGoal goal2 = new TeamMatchGoal(scorer2, assistant, match, now.plusMinutes(15));
        TeamMatchGoal goal3 = new TeamMatchGoal(scorer, assistant2, match, now.plusMinutes(25));

        em.persist(goal1);
        em.persist(goal2);
        em.persist(goal3);
        em.flush();

        TeamPlayer scorer3 = new TeamPlayer("Derek", "Lock", 187, 95, homeTeam);
        Collection<TeamMatchGoal> dbGoals = teamMatchGoalRepository.findGoalByScorer(scorer3.getId());

        Assert.assertTrue(dbGoals.isEmpty());
    }

    @Test
    public void testFindGoalByAssistant()
    {
        Team homeTeam = new Team("homeTeam");
        Team awayTeam = new Team("awayTeam");
        em.persist(homeTeam);
        em.persist(awayTeam);

        TeamPlayer scorer = new TeamPlayer("John", "Doe", 187, 95, homeTeam);
        TeamPlayer assistant = new TeamPlayer("Jack", "Reacher", 179, 74, homeTeam);
        TeamPlayer scorer2 = new TeamPlayer("Bruno", "Fartis", 187, 95, homeTeam);
        TeamPlayer assistant2 = new TeamPlayer("Emil", "Hunter", 179, 74, homeTeam);
        em.persist(scorer);
        em.persist(assistant);
        em.persist(scorer2);
        em.persist(assistant2);

        TeamMatch match = new TeamMatch(homeTeam, awayTeam, now.minusMinutes(10));
        em.persist(match);

        TeamMatchGoal goal1 = new TeamMatchGoal(scorer, assistant, match, now);
        TeamMatchGoal goal2 = new TeamMatchGoal(scorer2, assistant, match, now.plusMinutes(15));
        TeamMatchGoal goal3 = new TeamMatchGoal(scorer, assistant2, match, now.plusMinutes(25));

        em.persist(goal1);
        em.persist(goal2);
        em.persist(goal3);
        em.flush();

        Collection<TeamMatchGoal> dbGoals = teamMatchGoalRepository.findGoalByAssistant(assistant.getId());

        Assert.assertEquals(dbGoals.size(), 2);
        Assert.assertTrue(dbGoals.contains(goal1));
        Assert.assertTrue(dbGoals.contains(goal2));
        Assert.assertFalse(dbGoals.contains(goal3));
    }

    @Test(expectedExceptions = { IllegalArgumentException.class },
          expectedExceptionsMessageRegExp = "Cannot search for null assistant")
    public void testFindGoalByAssistantNull()
    {
        Team homeTeam = new Team("homeTeam");
        Team awayTeam = new Team("awayTeam");
        em.persist(homeTeam);
        em.persist(awayTeam);

        TeamPlayer scorer = new TeamPlayer("John", "Doe", 187, 95, homeTeam);
        TeamPlayer assistant = new TeamPlayer("Jack", "Reacher", 179, 74, homeTeam);
        TeamPlayer scorer2 = new TeamPlayer("Bruno", "Fartis", 187, 95, homeTeam);
        TeamPlayer assistant2 = new TeamPlayer("Emil", "Hunter", 179, 74, homeTeam);
        em.persist(scorer);
        em.persist(assistant);
        em.persist(scorer2);
        em.persist(assistant2);

        TeamMatch match = new TeamMatch(homeTeam, awayTeam, now.minusMinutes(10));
        em.persist(match);

        TeamMatchGoal goal1 = new TeamMatchGoal(scorer, assistant, match, now);
        TeamMatchGoal goal2 = new TeamMatchGoal(scorer2, assistant, match, now.plusMinutes(15));
        TeamMatchGoal goal3 = new TeamMatchGoal(scorer, assistant2, match, now.plusMinutes(25));

        em.persist(goal1);
        em.persist(goal2);
        em.persist(goal3);
        em.flush();

        Collection<TeamMatchGoal> dbGoals = teamMatchGoalRepository.findGoalByAssistant(null);
    }

    @Test
    public void testFindGoalByAssistantNonexistent()
    {
        Team homeTeam = new Team("homeTeam");
        Team awayTeam = new Team("awayTeam");
        em.persist(homeTeam);
        em.persist(awayTeam);

        TeamPlayer scorer = new TeamPlayer("John", "Doe", 187, 95, homeTeam);
        TeamPlayer assistant = new TeamPlayer("Jack", "Reacher", 179, 74, homeTeam);
        TeamPlayer scorer2 = new TeamPlayer("Bruno", "Fartis", 187, 95, homeTeam);
        TeamPlayer assistant2 = new TeamPlayer("Emil", "Hunter", 179, 74, homeTeam);
        em.persist(scorer);
        em.persist(assistant);
        em.persist(scorer2);
        em.persist(assistant2);

        TeamMatch match = new TeamMatch(homeTeam, awayTeam, now.minusMinutes(10));
        em.persist(match);

        TeamMatchGoal goal1 = new TeamMatchGoal(scorer, assistant, match, now);
        TeamMatchGoal goal2 = new TeamMatchGoal(scorer2, assistant, match, now.plusMinutes(15));
        TeamMatchGoal goal3 = new TeamMatchGoal(scorer, assistant2, match, now.plusMinutes(25));

        em.persist(goal1);
        em.persist(goal2);
        em.persist(goal3);
        em.flush();

        TeamPlayer assistant3 = new TeamPlayer("Derek", "Lock", 187, 95, homeTeam);
        Collection<TeamMatchGoal> dbGoals = teamMatchGoalRepository.findGoalByAssistant(assistant3.getId());

        Assert.assertTrue(dbGoals.isEmpty());
    }

    @Test
    public void testFindGoalByMatch()
    {
        Team homeTeam = new Team("homeTeam");
        Team awayTeam = new Team("awayTeam");
        Team awayTeam2 = new Team("awayTeam2");
        em.persist(homeTeam);
        em.persist(awayTeam);
        em.persist(awayTeam2);

        TeamPlayer scorer = new TeamPlayer("John", "Doe", 187, 95, homeTeam);
        TeamPlayer assistant = new TeamPlayer("Jack", "Reacher", 179, 74, homeTeam);
        TeamPlayer scorer2 = new TeamPlayer("Bruno", "Fartis", 187, 95, homeTeam);
        TeamPlayer assistant2 = new TeamPlayer("Emil", "Hunter", 179, 74, homeTeam);
        em.persist(scorer);
        em.persist(assistant);
        em.persist(scorer2);
        em.persist(assistant2);

        TeamMatch match = new TeamMatch(homeTeam, awayTeam, now.minusMinutes(10));
        TeamMatch match2 = new TeamMatch(homeTeam, awayTeam2, now.plusMinutes(25));
        em.persist(match);
        em.persist(match2);

        TeamMatchGoal goal1 = new TeamMatchGoal(scorer, assistant, match, now);
        TeamMatchGoal goal2 = new TeamMatchGoal(scorer2, assistant, match, now.plusMinutes(15));
        TeamMatchGoal goal3 = new TeamMatchGoal(scorer, assistant2, match2, now.plusMinutes(26));

        em.persist(goal1);
        em.persist(goal2);
        em.persist(goal3);
        em.flush();

        Collection<TeamMatchGoal> dbGoals = teamMatchGoalRepository.findGoalByMatch(match.getId());

        Assert.assertEquals(dbGoals.size(), 2);
        Assert.assertTrue(dbGoals.contains(goal1));
        Assert.assertTrue(dbGoals.contains(goal2));
        Assert.assertFalse(dbGoals.contains(goal3));
    }

    @Test(expectedExceptions = { IllegalArgumentException.class },
          expectedExceptionsMessageRegExp = "Cannot search for null match")
    public void testFindGoalByMatchNull()
    {
        Team homeTeam = new Team("homeTeam");
        Team awayTeam = new Team("awayTeam");
        Team awayTeam2 = new Team("awayTeam2");
        em.persist(homeTeam);
        em.persist(awayTeam);
        em.persist(awayTeam2);

        TeamPlayer scorer = new TeamPlayer("John", "Doe", 187, 95, homeTeam);
        TeamPlayer assistant = new TeamPlayer("Jack", "Reacher", 179, 74, homeTeam);
        TeamPlayer scorer2 = new TeamPlayer("Bruno", "Fartis", 187, 95, homeTeam);
        TeamPlayer assistant2 = new TeamPlayer("Emil", "Hunter", 179, 74, homeTeam);
        em.persist(scorer);
        em.persist(assistant);
        em.persist(scorer2);
        em.persist(assistant2);

        TeamMatch match = new TeamMatch(homeTeam, awayTeam, now.minusMinutes(10));
        TeamMatch match2 = new TeamMatch(homeTeam, awayTeam2, now.plusMinutes(25));
        em.persist(match);
        em.persist(match2);

        TeamMatchGoal goal1 = new TeamMatchGoal(scorer, assistant, match, now);
        TeamMatchGoal goal2 = new TeamMatchGoal(scorer2, assistant, match, now.plusMinutes(15));
        TeamMatchGoal goal3 = new TeamMatchGoal(scorer, assistant2, match2, now.plusMinutes(26));

        em.persist(goal1);
        em.persist(goal2);
        em.persist(goal3);
        em.flush();

        Collection<TeamMatchGoal> dbGoals = teamMatchGoalRepository.findGoalByMatch(null);
    }

    @Test
    public void testFindGoalByMatchNonexistent()
    {
        Team homeTeam = new Team("homeTeam");
        Team awayTeam = new Team("awayTeam");
        Team awayTeam2 = new Team("awayTeam2");
        Team awayTeam3 = new Team("awayTeam3");
        em.persist(homeTeam);
        em.persist(awayTeam);
        em.persist(awayTeam2);
        em.persist(awayTeam3);

        TeamPlayer scorer = new TeamPlayer("John", "Doe", 187, 95, homeTeam);
        TeamPlayer assistant = new TeamPlayer("Jack", "Reacher", 179, 74, homeTeam);
        TeamPlayer scorer2 = new TeamPlayer("Bruno", "Fartis", 187, 95, homeTeam);
        TeamPlayer assistant2 = new TeamPlayer("Emil", "Hunter", 179, 74, homeTeam);
        em.persist(scorer);
        em.persist(assistant);
        em.persist(scorer2);
        em.persist(assistant2);

        TeamMatch match = new TeamMatch(homeTeam, awayTeam, now.minusMinutes(10));
        TeamMatch match2 = new TeamMatch(homeTeam, awayTeam2, now.plusMinutes(5));
        em.persist(match);
        em.persist(match2);

        TeamMatchGoal goal1 = new TeamMatchGoal(scorer, assistant, match, now);
        TeamMatchGoal goal2 = new TeamMatchGoal(scorer2, assistant, match, now.plusMinutes(15));
        TeamMatchGoal goal3 = new TeamMatchGoal(scorer, assistant2, match2, now.plusMinutes(25));

        em.persist(goal1);
        em.persist(goal2);
        em.persist(goal3);
        em.flush();

        TeamMatch match3 = new TeamMatch(homeTeam, awayTeam3, now.plusMinutes(10));

        Collection<TeamMatchGoal> dbGoals = teamMatchGoalRepository.findGoalByMatch(match3.getId());

        Assert.assertTrue(dbGoals.isEmpty());
    }

    @Test
    public void testFindAllGoals()
    {
        Team homeTeam = new Team("homeTeam");
        Team awayTeam = new Team("awayTeam");
        Team awayTeam2 = new Team("awayTeam2");
        em.persist(homeTeam);
        em.persist(awayTeam);
        em.persist(awayTeam2);

        TeamPlayer scorer = new TeamPlayer("John", "Doe", 187, 95, homeTeam);
        TeamPlayer assistant = new TeamPlayer("Jack", "Reacher", 179, 74, homeTeam);
        TeamPlayer scorer2 = new TeamPlayer("Bruno", "Fartis", 187, 95, homeTeam);
        TeamPlayer assistant2 = new TeamPlayer("Emil", "Hunter", 179, 74, homeTeam);
        em.persist(scorer);
        em.persist(assistant);
        em.persist(scorer2);
        em.persist(assistant2);

        TeamMatch match = new TeamMatch(homeTeam, awayTeam, now.minusMinutes(10));
        TeamMatch match2 = new TeamMatch(homeTeam, awayTeam2, now.plusMinutes(5));
        em.persist(match);
        em.persist(match2);

        TeamMatchGoal goal1 = new TeamMatchGoal(scorer, assistant, match, now);
        TeamMatchGoal goal2 = new TeamMatchGoal(scorer2, assistant, match, now.plusMinutes(15));
        TeamMatchGoal goal3 = new TeamMatchGoal(scorer, assistant2, match2, now.plusMinutes(25));

        em.persist(goal1);
        em.persist(goal2);
        em.persist(goal3);
        em.flush();

        Collection<TeamMatchGoal> dbGoals = teamMatchGoalRepository.findAllGoals();

        Assert.assertEquals(dbGoals.size(), 3);
        Assert.assertTrue(dbGoals.contains(goal1));
        Assert.assertTrue(dbGoals.contains(goal2));
        Assert.assertTrue(dbGoals.contains(goal3));
    }

    @Test
    public void testFindConflictingGoal()
    {
        Team homeTeam = new Team("homeTeam");
        Team awayTeam = new Team("awayTeam");
        em.persist(homeTeam);
        em.persist(awayTeam);

        TeamPlayer scorer1 = new TeamPlayer("John", "Doe", 187, 95, homeTeam);
        TeamPlayer assistant1 = new TeamPlayer("Jack", "Reacher", 179, 74, homeTeam);
        TeamPlayer scorer2 = new TeamPlayer("Alex", "Franck", 187, 95, homeTeam);
        TeamPlayer assistant2 = new TeamPlayer("Rob", "McRub", 179, 74, homeTeam);
        em.persist(scorer1);
        em.persist(assistant1);
        em.persist(scorer2);
        em.persist(assistant2);

        TeamMatch match = new TeamMatch(homeTeam, awayTeam, now.minusMinutes(10));
        em.persist(match);

        TeamMatchGoal goal1 = new TeamMatchGoal(scorer1, assistant1, match, now);
        TeamMatchGoal goal2 = new TeamMatchGoal(scorer2, assistant2, match, now.plusMinutes(12));
        em.persist(goal1);
        em.persist(goal2);
        em.flush();

        TeamMatchGoal conflictingGoal = teamMatchGoalRepository.findConflictingGoal(
            match.getId(),
            scorer1.getId(),
            assistant1.getId(),
            now
        );

        Assert.assertNotNull(conflictingGoal);
        Assert.assertEquals(conflictingGoal.getScorer(), goal1.getScorer());
        Assert.assertEquals(conflictingGoal.getAssistant(), goal1.getAssistant());
        Assert.assertEquals(conflictingGoal.getMatch(), goal1.getMatch());
        Assert.assertEquals(conflictingGoal.getMatchTime(), goal1.getMatchTime());
    }

    @Test
    public void testFindConflictingGoalNotSameScorer()
    {
        Team homeTeam = new Team("homeTeam");
        Team awayTeam = new Team("awayTeam");
        em.persist(homeTeam);
        em.persist(awayTeam);

        TeamPlayer scorer1 = new TeamPlayer("John", "Doe", 187, 95, homeTeam);
        TeamPlayer assistant1 = new TeamPlayer("Jack", "Reacher", 179, 74, homeTeam);
        TeamPlayer scorer2 = new TeamPlayer("Alex", "Franck", 187, 95, homeTeam);
        TeamPlayer assistant2 = new TeamPlayer("Rob", "McRub", 179, 74, homeTeam);
        em.persist(scorer1);
        em.persist(assistant1);
        em.persist(scorer2);
        em.persist(assistant2);

        TeamMatch match = new TeamMatch(homeTeam, awayTeam, now.minusMinutes(10));
        em.persist(match);

        TeamMatchGoal goal1 = new TeamMatchGoal(scorer1, assistant1, match, now);
        TeamMatchGoal goal2 = new TeamMatchGoal(scorer2, assistant2, match, now.plusMinutes(12));
        em.persist(goal1);
        em.persist(goal2);
        em.flush();

        TeamMatchGoal conflictingGoal = teamMatchGoalRepository.findConflictingGoal(
            match.getId(),
            scorer2.getId(),
            assistant1.getId(),
            now
        );

        Assert.assertNull(conflictingGoal);
    }

    @Test
    public void testFindConflictingGoalNotSameAssistant()
    {
        Team homeTeam = new Team("homeTeam");
        Team awayTeam = new Team("awayTeam");
        em.persist(homeTeam);
        em.persist(awayTeam);

        TeamPlayer scorer1 = new TeamPlayer("John", "Doe", 187, 95, homeTeam);
        TeamPlayer assistant1 = new TeamPlayer("Jack", "Reacher", 179, 74, homeTeam);
        TeamPlayer scorer2 = new TeamPlayer("Alex", "Franck", 187, 95, homeTeam);
        TeamPlayer assistant2 = new TeamPlayer("Rob", "McRub", 179, 74, homeTeam);
        em.persist(scorer1);
        em.persist(assistant1);
        em.persist(scorer2);
        em.persist(assistant2);

        TeamMatch match = new TeamMatch(homeTeam, awayTeam, now.minusMinutes(10));
        em.persist(match);

        TeamMatchGoal goal1 = new TeamMatchGoal(scorer1, assistant1, match, now);
        TeamMatchGoal goal2 = new TeamMatchGoal(scorer2, assistant2, match, now.plusMinutes(12));
        em.persist(goal1);
        em.persist(goal2);
        em.flush();

        TeamMatchGoal conflictingGoal = teamMatchGoalRepository.findConflictingGoal(
            match.getId(),
            scorer1.getId(),
            assistant2.getId(),
            now
        );

        Assert.assertNull(conflictingGoal);
    }

    @Test
    public void testFindConflictingGoalNotSameMatchTime()
    {
        Team homeTeam = new Team("homeTeam");
        Team awayTeam = new Team("awayTeam");
        em.persist(homeTeam);
        em.persist(awayTeam);

        TeamPlayer scorer1 = new TeamPlayer("John", "Doe", 187, 95, homeTeam);
        TeamPlayer assistant1 = new TeamPlayer("Jack", "Reacher", 179, 74, homeTeam);
        TeamPlayer scorer2 = new TeamPlayer("Alex", "Franck", 187, 95, homeTeam);
        TeamPlayer assistant2 = new TeamPlayer("Rob", "McRub", 179, 74, homeTeam);
        em.persist(scorer1);
        em.persist(assistant1);
        em.persist(scorer2);
        em.persist(assistant2);

        TeamMatch match = new TeamMatch(homeTeam, awayTeam, now.minusMinutes(10));
        em.persist(match);

        TeamMatchGoal goal1 = new TeamMatchGoal(scorer1, assistant1, match, now);
        TeamMatchGoal goal2 = new TeamMatchGoal(scorer2, assistant2, match, now.plusMinutes(12));
        em.persist(goal1);
        em.persist(goal2);
        em.flush();

        TeamMatchGoal conflictingGoal = teamMatchGoalRepository.findConflictingGoal(
            match.getId(),
            scorer1.getId(),
            assistant1.getId(),
            now.plusMinutes(12)
        );

        Assert.assertNull(conflictingGoal);
    }

    @Test(expectedExceptions = { IllegalArgumentException.class },
          expectedExceptionsMessageRegExp = "Cannot search goal for a null match")
    public void testFindConflictingGoalWithNullMatch()
    {
        Team homeTeam = new Team("homeTeam");
        Team awayTeam = new Team("awayTeam");
        em.persist(homeTeam);
        em.persist(awayTeam);

        TeamPlayer scorer1 = new TeamPlayer("John", "Doe", 187, 95, homeTeam);
        TeamPlayer assistant1 = new TeamPlayer("Jack", "Reacher", 179, 74, homeTeam);
        TeamPlayer scorer2 = new TeamPlayer("Alex", "Franck", 187, 95, homeTeam);
        TeamPlayer assistant2 = new TeamPlayer("Rob", "McRub", 179, 74, homeTeam);
        em.persist(scorer1);
        em.persist(assistant1);
        em.persist(scorer2);
        em.persist(assistant2);

        TeamMatch match = new TeamMatch(homeTeam, awayTeam, now.minusMinutes(10));
        em.persist(match);

        TeamMatchGoal goal1 = new TeamMatchGoal(scorer1, assistant1, match, now);
        TeamMatchGoal goal2 = new TeamMatchGoal(scorer2, assistant2, match, now.plusMinutes(12));
        em.persist(goal1);
        em.persist(goal2);
        em.flush();

        TeamMatchGoal conflictingGoal = teamMatchGoalRepository.findConflictingGoal(
            null,
            scorer1.getId(),
            assistant1.getId(),
            now
        );
    }

    @Test(expectedExceptions = { IllegalArgumentException.class },
          expectedExceptionsMessageRegExp = "Cannot search goal for a null scorer")
    public void testFindConflictingGoalWithNullScorer()
    {
        Team homeTeam = new Team("homeTeam");
        Team awayTeam = new Team("awayTeam");
        em.persist(homeTeam);
        em.persist(awayTeam);

        TeamPlayer scorer1 = new TeamPlayer("John", "Doe", 187, 95, homeTeam);
        TeamPlayer assistant1 = new TeamPlayer("Jack", "Reacher", 179, 74, homeTeam);
        TeamPlayer scorer2 = new TeamPlayer("Alex", "Franck", 187, 95, homeTeam);
        TeamPlayer assistant2 = new TeamPlayer("Rob", "McRub", 179, 74, homeTeam);
        em.persist(scorer1);
        em.persist(assistant1);
        em.persist(scorer2);
        em.persist(assistant2);

        TeamMatch match = new TeamMatch(homeTeam, awayTeam, now.minusMinutes(10));
        em.persist(match);

        TeamMatchGoal goal1 = new TeamMatchGoal(scorer1, assistant1, match, now);
        TeamMatchGoal goal2 = new TeamMatchGoal(scorer2, assistant2, match, now.plusMinutes(12));
        em.persist(goal1);
        em.persist(goal2);
        em.flush();

        TeamMatchGoal conflictingGoal = teamMatchGoalRepository.findConflictingGoal(
            match.getId(),
            null,
            assistant1.getId(),
            now
        );
    }

    @Test(expectedExceptions = { IllegalArgumentException.class },
          expectedExceptionsMessageRegExp = "Cannot search goal for a null assistant")
    public void testFindConflictingGoalWithNullAssistant()
    {
        Team homeTeam = new Team("homeTeam");
        Team awayTeam = new Team("awayTeam");
        em.persist(homeTeam);
        em.persist(awayTeam);

        TeamPlayer scorer1 = new TeamPlayer("John", "Doe", 187, 95, homeTeam);
        TeamPlayer assistant1 = new TeamPlayer("Jack", "Reacher", 179, 74, homeTeam);
        TeamPlayer scorer2 = new TeamPlayer("Alex", "Franck", 187, 95, homeTeam);
        TeamPlayer assistant2 = new TeamPlayer("Rob", "McRub", 179, 74, homeTeam);
        em.persist(scorer1);
        em.persist(assistant1);
        em.persist(scorer2);
        em.persist(assistant2);

        TeamMatch match = new TeamMatch(homeTeam, awayTeam, now.minusMinutes(10));
        em.persist(match);

        TeamMatchGoal goal1 = new TeamMatchGoal(scorer1, assistant1, match, now);
        TeamMatchGoal goal2 = new TeamMatchGoal(scorer2, assistant2, match, now.plusMinutes(12));
        em.persist(goal1);
        em.persist(goal2);
        em.flush();

        TeamMatchGoal conflictingGoal = teamMatchGoalRepository.findConflictingGoal(
            match.getId(),
            scorer1.getId(),
            null,
            now
        );
    }

    @Test(expectedExceptions = { IllegalArgumentException.class },
          expectedExceptionsMessageRegExp = "Cannot search goal for a null goal match time")
    public void testFindConflictingGoalWithNullMatchTime()
    {
        Team homeTeam = new Team("homeTeam");
        Team awayTeam = new Team("awayTeam");
        em.persist(homeTeam);
        em.persist(awayTeam);

        TeamPlayer scorer1 = new TeamPlayer("John", "Doe", 187, 95, homeTeam);
        TeamPlayer assistant1 = new TeamPlayer("Jack", "Reacher", 179, 74, homeTeam);
        TeamPlayer scorer2 = new TeamPlayer("Alex", "Franck", 187, 95, homeTeam);
        TeamPlayer assistant2 = new TeamPlayer("Rob", "McRub", 179, 74, homeTeam);
        em.persist(scorer1);
        em.persist(assistant1);
        em.persist(scorer2);
        em.persist(assistant2);

        TeamMatch match = new TeamMatch(homeTeam, awayTeam, now.minusMinutes(10));
        em.persist(match);

        TeamMatchGoal goal1 = new TeamMatchGoal(scorer1, assistant1, match, now);
        TeamMatchGoal goal2 = new TeamMatchGoal(scorer2, assistant2, match, now.plusMinutes(12));
        em.persist(goal1);
        em.persist(goal2);
        em.flush();

        TeamMatchGoal conflictingGoal = teamMatchGoalRepository.findConflictingGoal(
            match.getId(),
            scorer1.getId(),
            assistant1.getId(),
            null
        );
    }

    @Test
    public void testFindLastGoalByMatch()
    {
        Team homeTeam = new Team("homeTeam");
        Team awayTeam = new Team("awayTeam");
        Team awayTeam2 = new Team("awayTeam2");
        em.persist(homeTeam);
        em.persist(awayTeam);
        em.persist(awayTeam2);

        TeamPlayer scorer = new TeamPlayer("John", "Doe", 187, 95, homeTeam);
        TeamPlayer assistant = new TeamPlayer("Jack", "Reacher", 179, 74, homeTeam);
        TeamPlayer scorer2 = new TeamPlayer("Bruno", "Fartis", 187, 95, homeTeam);
        TeamPlayer assistant2 = new TeamPlayer("Emil", "Hunter", 179, 74, homeTeam);
        em.persist(scorer);
        em.persist(assistant);
        em.persist(scorer2);
        em.persist(assistant2);

        TeamMatch match = new TeamMatch(homeTeam, awayTeam, now);
        TeamMatch match2 = new TeamMatch(homeTeam, awayTeam2, now.plusDays(3));
        em.persist(match);
        em.persist(match2);

        TeamMatchGoal goal1 = new TeamMatchGoal(scorer, assistant, match, now.plusMinutes(10));
        TeamMatchGoal goal2 = new TeamMatchGoal(scorer2, assistant, match, now.plusMinutes(55));
        TeamMatchGoal goal3 = new TeamMatchGoal(scorer, assistant2, match2, now.plusDays(3).plusMinutes(26));

        em.persist(goal1);
        em.persist(goal2);
        em.persist(goal3);
        em.flush();

        TeamMatchGoal dbGoal = teamMatchGoalRepository.findLastGoalByMatch(match.getId());

        Assert.assertNotNull(dbGoal);
        Assert.assertEquals(dbGoal.getId(), goal2.getId());
        Assert.assertEquals(dbGoal.getMatchTime(), goal2.getMatchTime());
        Assert.assertEquals(dbGoal.getScorer(), goal2.getScorer());
        Assert.assertEquals(dbGoal.getAssistant(), goal2.getAssistant());
    }

    @Test(expectedExceptions = {IllegalArgumentException.class},
        expectedExceptionsMessageRegExp = "Cannot search last scored goal for null match")
    public void testFindLastGoalByMatchNull()
    {
        Team homeTeam = new Team("homeTeam");
        Team awayTeam = new Team("awayTeam");
        Team awayTeam2 = new Team("awayTeam2");
        em.persist(homeTeam);
        em.persist(awayTeam);
        em.persist(awayTeam2);

        TeamPlayer scorer = new TeamPlayer("John", "Doe", 187, 95, homeTeam);
        TeamPlayer assistant = new TeamPlayer("Jack", "Reacher", 179, 74, homeTeam);
        TeamPlayer scorer2 = new TeamPlayer("Bruno", "Fartis", 187, 95, homeTeam);
        TeamPlayer assistant2 = new TeamPlayer("Emil", "Hunter", 179, 74, homeTeam);
        em.persist(scorer);
        em.persist(assistant);
        em.persist(scorer2);
        em.persist(assistant2);

        TeamMatch match = new TeamMatch(homeTeam, awayTeam, now);
        TeamMatch match2 = new TeamMatch(homeTeam, awayTeam2, now.plusDays(3));
        em.persist(match);
        em.persist(match2);

        TeamMatchGoal goal1 = new TeamMatchGoal(scorer, assistant, match, now.plusMinutes(10));
        TeamMatchGoal goal2 = new TeamMatchGoal(scorer2, assistant, match, now.plusMinutes(55));
        TeamMatchGoal goal3 = new TeamMatchGoal(scorer, assistant2, match2, now.plusDays(3).plusMinutes(26));

        em.persist(goal1);
        em.persist(goal2);
        em.persist(goal3);
        em.flush();

        TeamMatchGoal dbGoal = teamMatchGoalRepository.findLastGoalByMatch(null);
    }

    @Test
    public void testFindLastGoalByMatchNonexistent()
    {
        Team homeTeam = new Team("homeTeam");
        Team awayTeam = new Team("awayTeam");
        Team awayTeam2 = new Team("awayTeam2");
        Team awayTeam3 = new Team("awayTeam3");
        em.persist(homeTeam);
        em.persist(awayTeam);
        em.persist(awayTeam2);
        em.persist(awayTeam3);

        TeamPlayer scorer = new TeamPlayer("John", "Doe", 187, 95, homeTeam);
        TeamPlayer assistant = new TeamPlayer("Jack", "Reacher", 179, 74, homeTeam);
        TeamPlayer scorer2 = new TeamPlayer("Bruno", "Fartis", 187, 95, homeTeam);
        TeamPlayer assistant2 = new TeamPlayer("Emil", "Hunter", 179, 74, homeTeam);
        em.persist(scorer);
        em.persist(assistant);
        em.persist(scorer2);
        em.persist(assistant2);

        TeamMatch match = new TeamMatch(homeTeam, awayTeam, now);
        TeamMatch match2 = new TeamMatch(homeTeam, awayTeam2, now.plusDays(3));
        em.persist(match);
        em.persist(match2);

        TeamMatchGoal goal1 = new TeamMatchGoal(scorer, assistant, match, now.plusMinutes(10));
        TeamMatchGoal goal2 = new TeamMatchGoal(scorer2, assistant, match, now.plusMinutes(55));
        TeamMatchGoal goal3 = new TeamMatchGoal(scorer, assistant2, match2, now.plusDays(3).plusMinutes(26));

        em.persist(goal1);
        em.persist(goal2);
        em.persist(goal3);
        em.flush();

        TeamMatch match3 = new TeamMatch(homeTeam, awayTeam3, now.plusDays(8));

        TeamMatchGoal dbGoal = teamMatchGoalRepository.findLastGoalByMatch(match3.getId());

        Assert.assertNull(dbGoal);
    }

    @Test
    public void testFindLastGoalByMatchWithNoGoal()
    {
        Team homeTeam = new Team("homeTeam");
        Team awayTeam = new Team("awayTeam");
        Team awayTeam2 = new Team("awayTeam2");
        em.persist(homeTeam);
        em.persist(awayTeam);
        em.persist(awayTeam2);

        TeamPlayer scorer = new TeamPlayer("John", "Doe", 187, 95, homeTeam);
        TeamPlayer assistant = new TeamPlayer("Jack", "Reacher", 179, 74, homeTeam);
        TeamPlayer scorer2 = new TeamPlayer("Bruno", "Fartis", 187, 95, homeTeam);
        TeamPlayer assistant2 = new TeamPlayer("Emil", "Hunter", 179, 74, homeTeam);
        em.persist(scorer);
        em.persist(assistant);
        em.persist(scorer2);
        em.persist(assistant2);

        TeamMatch match = new TeamMatch(homeTeam, awayTeam, now);
        TeamMatch match2 = new TeamMatch(homeTeam, awayTeam2, now.plusDays(3));
        em.persist(match);
        em.persist(match2);

        TeamMatchGoal goal1 = new TeamMatchGoal(scorer, assistant, match, now.plusMinutes(10));
        TeamMatchGoal goal2 = new TeamMatchGoal(scorer2, assistant, match, now.plusMinutes(55));

        em.persist(goal1);
        em.persist(goal2);
        em.flush();

        TeamMatchGoal dbGoal = teamMatchGoalRepository.findLastGoalByMatch(match2.getId());

        Assert.assertNull(dbGoal);
    }

    @Test
    public void testFindAllGoalsByTeamInMatch()
    {
        Team homeTeam = new Team("homeTeam");
        Team awayTeam = new Team("awayTeam");
        em.persist(homeTeam);
        em.persist(awayTeam);

        TeamPlayer scorer = new TeamPlayer("John", "Doe", 187, 95, homeTeam);
        TeamPlayer assistant = new TeamPlayer("Jack", "Reacher", 179, 74, homeTeam);
        TeamPlayer scorer2 = new TeamPlayer("Bruno", "Fartis", 187, 95, homeTeam);
        TeamPlayer assistant2 = new TeamPlayer("Emil", "Hunter", 179, 74, homeTeam);
        TeamPlayer scorer3 = new TeamPlayer("Levin", "Korn", 187, 95, awayTeam);
        TeamPlayer assistant3 = new TeamPlayer("Dumbo", "Hlavka", 179, 74, awayTeam);
        em.persist(scorer);
        em.persist(assistant);
        em.persist(scorer2);
        em.persist(assistant2);
        em.persist(scorer3);
        em.persist(assistant3);

        TeamMatch match = new TeamMatch(homeTeam, awayTeam, now);
        em.persist(match);

        TeamMatchGoal goal1 = new TeamMatchGoal(scorer, assistant, match, now.plusMinutes(5));
        TeamMatchGoal goal2 = new TeamMatchGoal(scorer2, assistant2, match, now.plusMinutes(16));
        TeamMatchGoal goal3 = new TeamMatchGoal(scorer3, assistant3, match, now.plusMinutes(89));

        em.persist(goal1);
        em.persist(goal2);
        em.persist(goal3);
        em.flush();

        Collection<TeamMatchGoal> dbGoals = teamMatchGoalRepository.findAllGoalsByTeamInMatch(match.getId(), homeTeam.getId());

        Assert.assertFalse(dbGoals.isEmpty());
        Assert.assertTrue(dbGoals.contains(goal1));
        Assert.assertTrue(dbGoals.contains(goal2));
        Assert.assertFalse(dbGoals.contains(goal3));
    }

    @Test
    public void testFindAllGoalsByTeamInMatchWithNoGoalsOfRequiredTeam()
    {
        Team homeTeam = new Team("homeTeam");
        Team awayTeam = new Team("awayTeam");
        em.persist(homeTeam);
        em.persist(awayTeam);

        TeamPlayer scorer = new TeamPlayer("John", "Doe", 187, 95, homeTeam);
        TeamPlayer assistant = new TeamPlayer("Jack", "Reacher", 179, 74, homeTeam);
        TeamPlayer scorer2 = new TeamPlayer("Bruno", "Fartis", 187, 95, homeTeam);
        TeamPlayer assistant2 = new TeamPlayer("Emil", "Hunter", 179, 74, homeTeam);
        em.persist(scorer);
        em.persist(assistant);
        em.persist(scorer2);
        em.persist(assistant2);

        TeamMatch match = new TeamMatch(homeTeam, awayTeam, now);
        em.persist(match);

        TeamMatchGoal goal1 = new TeamMatchGoal(scorer, assistant, match, now.plusMinutes(5));
        TeamMatchGoal goal2 = new TeamMatchGoal(scorer2, assistant2, match, now.plusMinutes(16));

        em.persist(goal1);
        em.persist(goal2);
        em.flush();

        Collection<TeamMatchGoal> dbGoals = teamMatchGoalRepository.findAllGoalsByTeamInMatch(match.getId(), awayTeam.getId());

        Assert.assertTrue(dbGoals.isEmpty());
    }

    @Test(expectedExceptions = {IllegalArgumentException.class},
        expectedExceptionsMessageRegExp = "Cannot search all scored goals of null team")
    public void testFindAllGoalsByNullTeamInMatch()
    {
        Team homeTeam = new Team("homeTeam");
        Team awayTeam = new Team("awayTeam");
        em.persist(homeTeam);
        em.persist(awayTeam);

        TeamPlayer scorer = new TeamPlayer("John", "Doe", 187, 95, homeTeam);
        TeamPlayer assistant = new TeamPlayer("Jack", "Reacher", 179, 74, homeTeam);
        TeamPlayer scorer2 = new TeamPlayer("Bruno", "Fartis", 187, 95, homeTeam);
        TeamPlayer assistant2 = new TeamPlayer("Emil", "Hunter", 179, 74, homeTeam);
        TeamPlayer scorer3 = new TeamPlayer("Levin", "Korn", 187, 95, awayTeam);
        TeamPlayer assistant3 = new TeamPlayer("Dumbo", "Hlavka", 179, 74, awayTeam);
        em.persist(scorer);
        em.persist(assistant);
        em.persist(scorer2);
        em.persist(assistant2);
        em.persist(scorer3);
        em.persist(assistant3);

        TeamMatch match = new TeamMatch(homeTeam, awayTeam, now);
        em.persist(match);

        TeamMatchGoal goal1 = new TeamMatchGoal(scorer, assistant, match, now.plusMinutes(5));
        TeamMatchGoal goal2 = new TeamMatchGoal(scorer2, assistant2, match, now.plusMinutes(16));
        TeamMatchGoal goal3 = new TeamMatchGoal(scorer3, assistant3, match, now.plusMinutes(89));

        em.persist(goal1);
        em.persist(goal2);
        em.persist(goal3);
        em.flush();

        Collection<TeamMatchGoal> dbGoals = teamMatchGoalRepository.findAllGoalsByTeamInMatch(match.getId(), null);
    }

    @Test(expectedExceptions = {IllegalArgumentException.class},
        expectedExceptionsMessageRegExp = "Cannot search all scored goals of a team in null match")
    public void testFindAllGoalsByTeamInNullMatch()
    {
        Team homeTeam = new Team("homeTeam");
        Team awayTeam = new Team("awayTeam");
        em.persist(homeTeam);
        em.persist(awayTeam);

        TeamPlayer scorer = new TeamPlayer("John", "Doe", 187, 95, homeTeam);
        TeamPlayer assistant = new TeamPlayer("Jack", "Reacher", 179, 74, homeTeam);
        TeamPlayer scorer2 = new TeamPlayer("Bruno", "Fartis", 187, 95, homeTeam);
        TeamPlayer assistant2 = new TeamPlayer("Emil", "Hunter", 179, 74, homeTeam);
        TeamPlayer scorer3 = new TeamPlayer("Levin", "Korn", 187, 95, awayTeam);
        TeamPlayer assistant3 = new TeamPlayer("Dumbo", "Hlavka", 179, 74, awayTeam);
        em.persist(scorer);
        em.persist(assistant);
        em.persist(scorer2);
        em.persist(assistant2);
        em.persist(scorer3);
        em.persist(assistant3);

        TeamMatch match = new TeamMatch(homeTeam, awayTeam, now);
        em.persist(match);

        TeamMatchGoal goal1 = new TeamMatchGoal(scorer, assistant, match, now.plusMinutes(5));
        TeamMatchGoal goal2 = new TeamMatchGoal(scorer2, assistant2, match, now.plusMinutes(16));
        TeamMatchGoal goal3 = new TeamMatchGoal(scorer3, assistant3, match, now.plusMinutes(89));

        em.persist(goal1);
        em.persist(goal2);
        em.persist(goal3);
        em.flush();

        Collection<TeamMatchGoal> dbGoals = teamMatchGoalRepository.findAllGoalsByTeamInMatch(null, homeTeam.getId());
    }

    @Test
    public void testGetGoalsCountByTeamInMatch()
    {
        Team homeTeam = new Team("homeTeam");
        Team awayTeam = new Team("awayTeam");
        em.persist(homeTeam);
        em.persist(awayTeam);

        TeamPlayer scorer = new TeamPlayer("John", "Doe", 187, 95, homeTeam);
        TeamPlayer assistant = new TeamPlayer("Jack", "Reacher", 179, 74, homeTeam);
        TeamPlayer scorer2 = new TeamPlayer("Bruno", "Fartis", 187, 95, homeTeam);
        TeamPlayer assistant2 = new TeamPlayer("Emil", "Hunter", 179, 74, homeTeam);
        TeamPlayer scorer3 = new TeamPlayer("Levin", "Korn", 187, 95, awayTeam);
        TeamPlayer assistant3 = new TeamPlayer("Dumbo", "Hlavka", 179, 74, awayTeam);
        em.persist(scorer);
        em.persist(assistant);
        em.persist(scorer2);
        em.persist(assistant2);
        em.persist(scorer3);
        em.persist(assistant3);

        TeamMatch match = new TeamMatch(homeTeam, awayTeam, now);
        em.persist(match);

        TeamMatchGoal goal1 = new TeamMatchGoal(scorer, assistant, match, now.plusMinutes(5));
        TeamMatchGoal goal2 = new TeamMatchGoal(scorer2, assistant2, match, now.plusMinutes(16));
        TeamMatchGoal goal3 = new TeamMatchGoal(scorer3, assistant3, match, now.plusMinutes(89));

        em.persist(goal1);
        em.persist(goal2);
        em.persist(goal3);
        em.flush();

        long numGoals = teamMatchGoalRepository.getGoalsCountByTeamInMatch(match.getId(), homeTeam.getId());

        Assert.assertEquals(numGoals, 2);
    }

    @Test
    public void testGetGoalsCountByTeamInMatchWithNoGoalsOfRequiredTeam()
    {
        Team homeTeam = new Team("homeTeam");
        Team awayTeam = new Team("awayTeam");
        em.persist(homeTeam);
        em.persist(awayTeam);

        TeamPlayer scorer = new TeamPlayer("John", "Doe", 187, 95, homeTeam);
        TeamPlayer assistant = new TeamPlayer("Jack", "Reacher", 179, 74, homeTeam);
        TeamPlayer scorer2 = new TeamPlayer("Bruno", "Fartis", 187, 95, homeTeam);
        TeamPlayer assistant2 = new TeamPlayer("Emil", "Hunter", 179, 74, homeTeam);
        em.persist(scorer);
        em.persist(assistant);
        em.persist(scorer2);
        em.persist(assistant2);

        TeamMatch match = new TeamMatch(homeTeam, awayTeam, now);
        em.persist(match);

        TeamMatchGoal goal1 = new TeamMatchGoal(scorer, assistant, match, now.plusMinutes(5));
        TeamMatchGoal goal2 = new TeamMatchGoal(scorer2, assistant2, match, now.plusMinutes(16));

        em.persist(goal1);
        em.persist(goal2);
        em.flush();

        long numGoals = teamMatchGoalRepository.getGoalsCountByTeamInMatch(match.getId(), awayTeam.getId());

        Assert.assertEquals(numGoals, 0);
    }

    @Test(expectedExceptions = {IllegalArgumentException.class},
        expectedExceptionsMessageRegExp = "Cannot search all scored goals number of null team")
    public void testGetGoalsCountByNullTeamInMatch()
    {
        Team homeTeam = new Team("homeTeam");
        Team awayTeam = new Team("awayTeam");
        em.persist(homeTeam);
        em.persist(awayTeam);

        TeamPlayer scorer = new TeamPlayer("John", "Doe", 187, 95, homeTeam);
        TeamPlayer assistant = new TeamPlayer("Jack", "Reacher", 179, 74, homeTeam);
        TeamPlayer scorer2 = new TeamPlayer("Bruno", "Fartis", 187, 95, homeTeam);
        TeamPlayer assistant2 = new TeamPlayer("Emil", "Hunter", 179, 74, homeTeam);
        TeamPlayer scorer3 = new TeamPlayer("Levin", "Korn", 187, 95, awayTeam);
        TeamPlayer assistant3 = new TeamPlayer("Dumbo", "Hlavka", 179, 74, awayTeam);
        em.persist(scorer);
        em.persist(assistant);
        em.persist(scorer2);
        em.persist(assistant2);
        em.persist(scorer3);
        em.persist(assistant3);

        TeamMatch match = new TeamMatch(homeTeam, awayTeam, now);
        em.persist(match);

        TeamMatchGoal goal1 = new TeamMatchGoal(scorer, assistant, match, now.plusMinutes(5));
        TeamMatchGoal goal2 = new TeamMatchGoal(scorer2, assistant2, match, now.plusMinutes(16));
        TeamMatchGoal goal3 = new TeamMatchGoal(scorer3, assistant3, match, now.plusMinutes(89));

        em.persist(goal1);
        em.persist(goal2);
        em.persist(goal3);
        em.flush();

        long numGoals = teamMatchGoalRepository.getGoalsCountByTeamInMatch(match.getId(), null);
    }

    @Test(expectedExceptions = {IllegalArgumentException.class},
        expectedExceptionsMessageRegExp = "Cannot search all scored goals number of a team in null match")
    public void testGetGoalsCountByTeamInNullMatch()
    {
        Team homeTeam = new Team("homeTeam");
        Team awayTeam = new Team("awayTeam");
        em.persist(homeTeam);
        em.persist(awayTeam);

        TeamPlayer scorer = new TeamPlayer("John", "Doe", 187, 95, homeTeam);
        TeamPlayer assistant = new TeamPlayer("Jack", "Reacher", 179, 74, homeTeam);
        TeamPlayer scorer2 = new TeamPlayer("Bruno", "Fartis", 187, 95, homeTeam);
        TeamPlayer assistant2 = new TeamPlayer("Emil", "Hunter", 179, 74, homeTeam);
        TeamPlayer scorer3 = new TeamPlayer("Levin", "Korn", 187, 95, awayTeam);
        TeamPlayer assistant3 = new TeamPlayer("Dumbo", "Hlavka", 179, 74, awayTeam);
        em.persist(scorer);
        em.persist(assistant);
        em.persist(scorer2);
        em.persist(assistant2);
        em.persist(scorer3);
        em.persist(assistant3);

        TeamMatch match = new TeamMatch(homeTeam, awayTeam, now);
        em.persist(match);

        TeamMatchGoal goal1 = new TeamMatchGoal(scorer, assistant, match, now.plusMinutes(5));
        TeamMatchGoal goal2 = new TeamMatchGoal(scorer2, assistant2, match, now.plusMinutes(16));
        TeamMatchGoal goal3 = new TeamMatchGoal(scorer3, assistant3, match, now.plusMinutes(89));

        em.persist(goal1);
        em.persist(goal2);
        em.persist(goal3);
        em.flush();

        long numGoals = teamMatchGoalRepository.getGoalsCountByTeamInMatch(null, homeTeam.getId());
    }
    
}
