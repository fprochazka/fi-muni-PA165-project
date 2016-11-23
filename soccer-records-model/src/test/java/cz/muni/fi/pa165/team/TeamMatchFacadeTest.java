package cz.muni.fi.pa165.team;

import cz.muni.fi.pa165.config.ApplicationConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.annotations.Test;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Date;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;

/**
 * @author Tomas Smid <smid.thomas@gmail.com>
 */
@ContextConfiguration(classes = ApplicationConfig.class)
public class TeamMatchFacadeTest extends AbstractTransactionalTestNGSpringContextTests
{

    @Autowired
    public TeamMatchFacade teamMatchFacade;

    @PersistenceContext
    public EntityManager em;

    @Test
    public void testCreateMatch()
    {
        long time = System.currentTimeMillis();
        Date startTime = new Date(time);
        Date endTime = new Date(time + 5520000);
        Team homeTeam = new Team("HomeTeam");
        Team awayTeam = new Team("AwayTeam");
        em.persist(homeTeam);
        em.persist(awayTeam);
        em.flush();

        TeamMatch teamMatch = teamMatchFacade.createMatch(
            homeTeam.getId(),
            awayTeam.getId(),
            startTime,
            endTime
        );
        em.clear();

        TeamMatch dbMatch = em.find(TeamMatch.class, teamMatch.getId());

        assertNotNull(dbMatch);
        assertEquals(homeTeam.getId(), dbMatch.getHomeTeam().getId());
        assertEquals(awayTeam.getId(), dbMatch.getAwayTeam().getId());
        assertEquals(startTime, dbMatch.getStartTime());
        assertEquals(endTime, dbMatch.getEndTime());
    }

    @Test
    public void testDeleteMatch()
    {
        long time = System.currentTimeMillis();

        Team homeTeam = new Team("HomeTeam");
        Team awayTeam = new Team("AwayTeam");
        em.persist(homeTeam);
        em.persist(awayTeam);

        TeamPlayer scorer1 = new TeamPlayer("Jozko", "Silny", 180, 80, homeTeam);
        TeamPlayer scorer2 = new TeamPlayer("Bohdan", "Kerekes", 194, 86, homeTeam);
        TeamPlayer assistant = new TeamPlayer("Lex", "Stroub", 181, 81, homeTeam);
        em.persist(scorer1);
        em.persist(scorer2);
        em.persist(assistant);

        TeamMatch teamMatch = new TeamMatch(homeTeam, awayTeam, new Date(time));
        TeamMatchGoal teamMatchGoal1 = new TeamMatchGoal(scorer1, assistant, teamMatch, new Date(time + 15000));
        TeamMatchGoal teamMatchGoal2 = new TeamMatchGoal(scorer2, assistant, teamMatch, new Date(time + 25000));

        em.persist(teamMatch);
        em.persist(teamMatchGoal1);
        em.persist(teamMatchGoal2);
        em.flush();

        assertNotNull(em.find(TeamMatch.class, teamMatch.getId()));

        teamMatchFacade.deleteMatch(teamMatch.getId());
        em.flush();
        em.clear();

        Team dbHomeTeam = em.find(Team.class, homeTeam.getId());
        Team dbAwayTeam = em.find(Team.class, awayTeam.getId());
        TeamMatchGoal dbTMG1 = em.find(TeamMatchGoal.class, teamMatchGoal1.getId());
        TeamMatchGoal dbTMG2 = em.find(TeamMatchGoal.class, teamMatchGoal2.getId());

        assertNull(em.find(TeamMatch.class, teamMatch.getId()));
        assertNull(dbTMG1);
        assertNull(dbTMG2);
        assertNotNull(dbHomeTeam);
        assertNotNull(dbAwayTeam);
    }

    @Test
    public void testAddNewScoredGoal()
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
        em.flush();

        TeamMatchGoal goal = teamMatchFacade.addNewScoredGoal(
            scorer.getId(),
            assistant.getId(),
            match.getId(),
            new Date(time)
        );
        em.clear();

        TeamMatchGoal dbGoal = em.find(TeamMatchGoal.class, goal.getId());

        assertNotNull(dbGoal);
        assertEquals(dbGoal.getScorer().getId(), goal.getScorer().getId());
        assertEquals(dbGoal.getAssistant().getId(), goal.getAssistant().getId());
        assertEquals(dbGoal.getMatch().getId(),goal.getMatch().getId());
        assertEquals(dbGoal.getMatchTime(),goal.getMatchTime());
    }

    @Test
    public void testDeleteMatchGoal()
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

        assertNotNull(em.find(TeamMatchGoal.class, goal.getId()));

        teamMatchFacade.deleteMatchGoal(goal.getId());

        assertNull(em.find(TeamMatchGoal.class, goal.getId()));
    }

    @Test
    public void testChangeMatchTime(){
        long time = System.currentTimeMillis();
        Date startTime = new Date(time);
        Date endTime = new Date(time + 5520000);
        Team homeTeam = new Team("HomeTeam");
        Team awayTeam = new Team("AwayTeam");
        em.persist(homeTeam);
        em.persist(awayTeam);

        TeamMatch teamMatch = new TeamMatch(homeTeam,awayTeam,startTime,endTime);
        em.persist(teamMatch);
        em.flush();
        em.clear();

        TeamMatch dbMatch = em.find(TeamMatch.class, teamMatch.getId());

        assertNotNull(dbMatch);
        assertEquals(startTime, dbMatch.getStartTime());
        assertEquals(endTime, dbMatch.getEndTime());

        Date newStartTime = new Date(startTime.getTime() + 9548700);
        Date newEndTime = new Date(endTime.getTime() + 9600000);

        teamMatchFacade.changeMatchTime(teamMatch.getId(), newStartTime, newEndTime);

        dbMatch = em.find(TeamMatch.class, teamMatch.getId());
        assertNotNull(dbMatch);
        assertEquals(newStartTime, dbMatch.getStartTime());
        assertEquals(newEndTime, dbMatch.getEndTime());
    }

    @Test
    public void testEndMatch(){
        long time = System.currentTimeMillis();
        Date startTime = new Date(time);
        Team homeTeam = new Team("HomeTeam");
        Team awayTeam = new Team("AwayTeam");
        em.persist(homeTeam);
        em.persist(awayTeam);

        TeamMatch teamMatch = new TeamMatch(homeTeam,awayTeam,startTime);
        em.persist(teamMatch);
        em.flush();
        em.clear();

        TeamMatch dbMatch = em.find(TeamMatch.class, teamMatch.getId());

        assertNotNull(dbMatch);
        assertEquals(startTime, dbMatch.getStartTime());
        assertNull(dbMatch.getEndTime());

        Date newEndTime = new Date(dbMatch.getStartTime().getTime() + 9600000);

        teamMatchFacade.endMatch(teamMatch.getId(), newEndTime);

        dbMatch = em.find(TeamMatch.class, teamMatch.getId());
        assertNotNull(dbMatch);
        assertEquals(newEndTime, dbMatch.getEndTime());
    }
}
