package cz.muni.fi.pa165.team;

import cz.muni.fi.pa165.config.ApplicationConfig;
import cz.muni.fi.pa165.team.exceptions.GoalNotFoundException;
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
 * @author Denis Galajda <galajda.denis@gmail.com>
 */
@ContextConfiguration(classes = ApplicationConfig.class)
public class TeamMatchGoalRepositoryImplTest extends AbstractTransactionalTestNGSpringContextTests
{

    @Autowired
    public TeamMatchGoalRepositoryImpl teamMatchGoalRepository;

    @PersistenceContext
    public EntityManager em;

    @Test
    public void testGetGoalById()
    {
        long time = System.currentTimeMillis();
        Team homeTeam = new Team("homeTeam");
        Team awayTeam = new Team("awayTeam");
        em.persist(homeTeam);
        em.persist(awayTeam);

        TeamPlayer scorer = new TeamPlayer("John", "Doe", 187, 95, homeTeam);
        TeamPlayer assistant = new TeamPlayer("Jack", "Reacher", 179, 74, homeTeam);
        em.persist(scorer);
        em.persist(assistant);

        TeamMatch match = new TeamMatch(homeTeam, awayTeam, new Date(time - 10000));
        em.persist(match);

        TeamMatchGoal goal = new TeamMatchGoal(scorer, assistant, match, new Date(time));
        em.persist(goal);
        em.flush();

        TeamMatchGoal dbGoal = teamMatchGoalRepository.getGoalById(goal.getId());

        Assert.assertEquals(dbGoal.getScorer(), goal.getScorer());
        Assert.assertEquals(dbGoal.getAssistant(), goal.getAssistant());
        Assert.assertEquals(dbGoal.getMatch(), goal.getMatch());
        Assert.assertEquals(dbGoal.getMatchTime(), goal.getMatchTime());
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testGetGoalByNullId()
    {
        long time = System.currentTimeMillis();
        Team homeTeam = new Team("homeTeam");
        Team awayTeam = new Team("awayTeam");
        em.persist(homeTeam);
        em.persist(awayTeam);

        TeamPlayer scorer = new TeamPlayer("John", "Doe", 187, 95, homeTeam);
        TeamPlayer assistant = new TeamPlayer("Jack", "Reacher", 179, 74, homeTeam);
        em.persist(scorer);
        em.persist(assistant);

        TeamMatch match = new TeamMatch(homeTeam, awayTeam, new Date(time - 10000));
        em.persist(match);

        TeamMatchGoal goal = new TeamMatchGoal(scorer, assistant, match, new Date(time));
        em.persist(goal);
        em.flush();

        TeamMatchGoal dbGoal = teamMatchGoalRepository.getGoalById(null);
    }

    @Test(expectedExceptions = GoalNotFoundException.class)
    public void testFindGoalByNonexistentId()
    {
        long time = System.currentTimeMillis();
        Team homeTeam = new Team("homeTeam");
        Team awayTeam = new Team("awayTeam");
        em.persist(homeTeam);
        em.persist(awayTeam);

        TeamPlayer scorer = new TeamPlayer("John", "Doe", 187, 95, homeTeam);
        TeamPlayer assistant = new TeamPlayer("Jack", "Reacher", 179, 74, homeTeam);
        em.persist(scorer);
        em.persist(assistant);

        TeamMatch match = new TeamMatch(homeTeam, awayTeam, new Date(time - 10000));
        em.persist(match);

        TeamMatchGoal goal = new TeamMatchGoal(scorer, assistant, match, new Date(time));
        em.persist(goal);
        em.flush();

        UUID badId = UUID.randomUUID();
        Assert.assertNotEquals(badId, goal.getId());

        TeamMatchGoal dbGoal = teamMatchGoalRepository.getGoalById(badId);
    }

    @Test
    public void testFindMatchByScorer()
    {
        long time = System.currentTimeMillis();
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

        TeamMatch match = new TeamMatch(homeTeam, awayTeam, new Date(time - 10000));
        em.persist(match);

        TeamMatchGoal goal1 = new TeamMatchGoal(scorer, assistant, match, new Date(time));
        TeamMatchGoal goal2 = new TeamMatchGoal(scorer2, assistant, match, new Date(time + 15000));
        TeamMatchGoal goal3 = new TeamMatchGoal(scorer, assistant2, match, new Date(time + 25000));

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

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testFindGoalByScorerNull()
    {
        long time = System.currentTimeMillis();
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

        TeamMatch match = new TeamMatch(homeTeam, awayTeam, new Date(time - 10000));
        em.persist(match);

        TeamMatchGoal goal1 = new TeamMatchGoal(scorer, assistant, match, new Date(time));
        TeamMatchGoal goal2 = new TeamMatchGoal(scorer2, assistant, match, new Date(time + 15000));
        TeamMatchGoal goal3 = new TeamMatchGoal(scorer, assistant2, match, new Date(time + 25000));

        em.persist(goal1);
        em.persist(goal2);
        em.persist(goal3);
        em.flush();

        Collection<TeamMatchGoal> dbGoals = teamMatchGoalRepository.findGoalByScorer(null);
    }

    @Test
    public void testFindGoalByScorerNonexistent()
    {
        long time = System.currentTimeMillis();
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

        TeamMatch match = new TeamMatch(homeTeam, awayTeam, new Date(time - 10000));
        em.persist(match);

        TeamMatchGoal goal1 = new TeamMatchGoal(scorer, assistant, match, new Date(time));
        TeamMatchGoal goal2 = new TeamMatchGoal(scorer2, assistant, match, new Date(time + 15000));
        TeamMatchGoal goal3 = new TeamMatchGoal(scorer, assistant2, match, new Date(time + 25000));

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
        long time = System.currentTimeMillis();
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

        TeamMatch match = new TeamMatch(homeTeam, awayTeam, new Date(time - 10000));
        em.persist(match);

        TeamMatchGoal goal1 = new TeamMatchGoal(scorer, assistant, match, new Date(time));
        TeamMatchGoal goal2 = new TeamMatchGoal(scorer2, assistant, match, new Date(time + 15000));
        TeamMatchGoal goal3 = new TeamMatchGoal(scorer, assistant2, match, new Date(time + 25000));

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

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testFindGoalByAssistantNull()
    {
        long time = System.currentTimeMillis();
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

        TeamMatch match = new TeamMatch(homeTeam, awayTeam, new Date(time - 10000));
        em.persist(match);

        TeamMatchGoal goal1 = new TeamMatchGoal(scorer, assistant, match, new Date(time));
        TeamMatchGoal goal2 = new TeamMatchGoal(scorer2, assistant, match, new Date(time + 15000));
        TeamMatchGoal goal3 = new TeamMatchGoal(scorer, assistant2, match, new Date(time + 25000));

        em.persist(goal1);
        em.persist(goal2);
        em.persist(goal3);
        em.flush();

        Collection<TeamMatchGoal> dbGoals = teamMatchGoalRepository.findGoalByAssistant(null);
    }

    @Test
    public void testFindGoalByAssistantNonexistent()
    {
        long time = System.currentTimeMillis();
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

        TeamMatch match = new TeamMatch(homeTeam, awayTeam, new Date(time - 10000));
        em.persist(match);

        TeamMatchGoal goal1 = new TeamMatchGoal(scorer, assistant, match, new Date(time));
        TeamMatchGoal goal2 = new TeamMatchGoal(scorer2, assistant, match, new Date(time + 15000));
        TeamMatchGoal goal3 = new TeamMatchGoal(scorer, assistant2, match, new Date(time + 25000));

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
        long time = System.currentTimeMillis();
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

        TeamMatch match = new TeamMatch(homeTeam, awayTeam, new Date(time - 10000));
        TeamMatch match2 = new TeamMatch(homeTeam, awayTeam2, new Date(time + 25000));
        em.persist(match);
        em.persist(match2);

        TeamMatchGoal goal1 = new TeamMatchGoal(scorer, assistant, match, new Date(time));
        TeamMatchGoal goal2 = new TeamMatchGoal(scorer2, assistant, match, new Date(time + 15000));
        TeamMatchGoal goal3 = new TeamMatchGoal(scorer, assistant2, match2, new Date(time + 26000));

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

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testFindGoalByMatchNull()
    {
        long time = System.currentTimeMillis();
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

        TeamMatch match = new TeamMatch(homeTeam, awayTeam, new Date(time - 10000));
        TeamMatch match2 = new TeamMatch(homeTeam, awayTeam2, new Date(time + 25000));
        em.persist(match);
        em.persist(match2);

        TeamMatchGoal goal1 = new TeamMatchGoal(scorer, assistant, match, new Date(time));
        TeamMatchGoal goal2 = new TeamMatchGoal(scorer2, assistant, match, new Date(time + 15000));
        TeamMatchGoal goal3 = new TeamMatchGoal(scorer, assistant2, match2, new Date(time + 25000));

        em.persist(goal1);
        em.persist(goal2);
        em.persist(goal3);
        em.flush();

        Collection<TeamMatchGoal> dbGoals = teamMatchGoalRepository.findGoalByMatch(null);
    }

    @Test
    public void testFindGoalByMatchNonexistent()
    {
        long time = System.currentTimeMillis();
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

        TeamMatch match = new TeamMatch(homeTeam, awayTeam, new Date(time - 10000));
        TeamMatch match2 = new TeamMatch(homeTeam, awayTeam2, new Date(time + 5000));
        em.persist(match);
        em.persist(match2);

        TeamMatchGoal goal1 = new TeamMatchGoal(scorer, assistant, match, new Date(time));
        TeamMatchGoal goal2 = new TeamMatchGoal(scorer2, assistant, match, new Date(time + 15000));
        TeamMatchGoal goal3 = new TeamMatchGoal(scorer, assistant2, match2, new Date(time + 25000));

        em.persist(goal1);
        em.persist(goal2);
        em.persist(goal3);
        em.flush();

        TeamMatch match3 = new TeamMatch(homeTeam, awayTeam3, new Date(time + 10000));

        Collection<TeamMatchGoal> dbGoals = teamMatchGoalRepository.findGoalByMatch(match3.getId());

        Assert.assertTrue(dbGoals.isEmpty());
    }

    @Test
    public void testFindAllGoals()
    {
        long time = System.currentTimeMillis();
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

        TeamMatch match = new TeamMatch(homeTeam, awayTeam, new Date(time - 10000));
        TeamMatch match2 = new TeamMatch(homeTeam, awayTeam2, new Date(time + 5000));
        em.persist(match);
        em.persist(match2);

        TeamMatchGoal goal1 = new TeamMatchGoal(scorer, assistant, match, new Date(time));
        TeamMatchGoal goal2 = new TeamMatchGoal(scorer2, assistant, match, new Date(time + 15000));
        TeamMatchGoal goal3 = new TeamMatchGoal(scorer, assistant2, match2, new Date(time + 25000));

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
        long time = System.currentTimeMillis();
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

        TeamMatch match = new TeamMatch(homeTeam, awayTeam, new Date(time-10000));
        em.persist(match);

        TeamMatchGoal goal1 = new TeamMatchGoal(scorer1, assistant1, match, new Date(time));
        TeamMatchGoal goal2 = new TeamMatchGoal(scorer2, assistant2, match, new Date(time+12000));
        em.persist(goal1);
        em.persist(goal2);
        em.flush();

        TeamMatchGoal conflictingGoal = teamMatchGoalRepository.findConflictingGoal(
            match.getId(),
            scorer1.getId(),
            assistant1.getId(),
            new Date(time)
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
        long time = System.currentTimeMillis();
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

        TeamMatch match = new TeamMatch(homeTeam, awayTeam, new Date(time-10000));
        em.persist(match);

        TeamMatchGoal goal1 = new TeamMatchGoal(scorer1, assistant1, match, new Date(time));
        TeamMatchGoal goal2 = new TeamMatchGoal(scorer2, assistant2, match, new Date(time+12000));
        em.persist(goal1);
        em.persist(goal2);
        em.flush();

        TeamMatchGoal conflictingGoal = teamMatchGoalRepository.findConflictingGoal(
            match.getId(),
            scorer2.getId(),
            assistant1.getId(),
            new Date(time)
        );

        Assert.assertNull(conflictingGoal);
    }

    @Test
    public void testFindConflictingGoalNotSameAssistant()
    {
        long time = System.currentTimeMillis();
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

        TeamMatch match = new TeamMatch(homeTeam, awayTeam, new Date(time-10000));
        em.persist(match);

        TeamMatchGoal goal1 = new TeamMatchGoal(scorer1, assistant1, match, new Date(time));
        TeamMatchGoal goal2 = new TeamMatchGoal(scorer2, assistant2, match, new Date(time+12000));
        em.persist(goal1);
        em.persist(goal2);
        em.flush();

        TeamMatchGoal conflictingGoal = teamMatchGoalRepository.findConflictingGoal(
            match.getId(),
            scorer1.getId(),
            assistant2.getId(),
            new Date(time)
        );

        Assert.assertNull(conflictingGoal);
    }

    @Test
    public void testFindConflictingGoalNotSameMatchTime()
    {
        long time = System.currentTimeMillis();
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

        TeamMatch match = new TeamMatch(homeTeam, awayTeam, new Date(time-10000));
        em.persist(match);

        TeamMatchGoal goal1 = new TeamMatchGoal(scorer1, assistant1, match, new Date(time));
        TeamMatchGoal goal2 = new TeamMatchGoal(scorer2, assistant2, match, new Date(time+12000));
        em.persist(goal1);
        em.persist(goal2);
        em.flush();

        TeamMatchGoal conflictingGoal = teamMatchGoalRepository.findConflictingGoal(
            match.getId(),
            scorer1.getId(),
            assistant1.getId(),
            new Date(time+12000)
        );

        Assert.assertNull(conflictingGoal);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testFindConflictingGoalWithNullMatch()
    {
        long time = System.currentTimeMillis();
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

        TeamMatch match = new TeamMatch(homeTeam, awayTeam, new Date(time-10000));
        em.persist(match);

        TeamMatchGoal goal1 = new TeamMatchGoal(scorer1, assistant1, match, new Date(time));
        TeamMatchGoal goal2 = new TeamMatchGoal(scorer2, assistant2, match, new Date(time+12000));
        em.persist(goal1);
        em.persist(goal2);
        em.flush();

        TeamMatchGoal conflictingGoal = teamMatchGoalRepository.findConflictingGoal(
            null,
            scorer1.getId(),
            assistant1.getId(),
            new Date(time)
        );
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testFindConflictingGoalWithNullScorer()
    {
        long time = System.currentTimeMillis();
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

        TeamMatch match = new TeamMatch(homeTeam, awayTeam, new Date(time-10000));
        em.persist(match);

        TeamMatchGoal goal1 = new TeamMatchGoal(scorer1, assistant1, match, new Date(time));
        TeamMatchGoal goal2 = new TeamMatchGoal(scorer2, assistant2, match, new Date(time+12000));
        em.persist(goal1);
        em.persist(goal2);
        em.flush();

        TeamMatchGoal conflictingGoal = teamMatchGoalRepository.findConflictingGoal(
            match.getId(),
            null,
            assistant1.getId(),
            new Date(time)
        );
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testFindConflictingGoalWithNullAssistant()
    {
        long time = System.currentTimeMillis();
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

        TeamMatch match = new TeamMatch(homeTeam, awayTeam, new Date(time-10000));
        em.persist(match);

        TeamMatchGoal goal1 = new TeamMatchGoal(scorer1, assistant1, match, new Date(time));
        TeamMatchGoal goal2 = new TeamMatchGoal(scorer2, assistant2, match, new Date(time+12000));
        em.persist(goal1);
        em.persist(goal2);
        em.flush();

        TeamMatchGoal conflictingGoal = teamMatchGoalRepository.findConflictingGoal(
            match.getId(),
            scorer1.getId(),
            null,
            new Date(time)
        );
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testFindConflictingGoalWithNullMatchTime()
    {
        long time = System.currentTimeMillis();
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

        TeamMatch match = new TeamMatch(homeTeam, awayTeam, new Date(time-10000));
        em.persist(match);

        TeamMatchGoal goal1 = new TeamMatchGoal(scorer1, assistant1, match, new Date(time));
        TeamMatchGoal goal2 = new TeamMatchGoal(scorer2, assistant2, match, new Date(time+12000));
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
}
