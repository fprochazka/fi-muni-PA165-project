package cz.muni.fi.pa165.team.match;

import cz.muni.fi.pa165.config.ModelConfig;
import cz.muni.fi.pa165.team.Team;
import cz.muni.fi.pa165.team.TeamPlayer;
import cz.muni.fi.pa165.team.match.containers.MatchListContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.annotations.Test;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;

import static org.testng.Assert.*;

/**
 * @author Tomas Smid <smid.thomas@gmail.com>
 */
@ContextConfiguration(classes = ModelConfig.class)
public class TeamMatchFacadeTest extends AbstractTransactionalTestNGSpringContextTests
{

    private LocalDateTime now = LocalDateTime.now();

    @Autowired
    public TeamMatchFacade teamMatchFacade;

    @PersistenceContext
    public EntityManager em;

    @Test
    public void testCreateMatch()
    {
        Team homeTeam = new Team("HomeTeam");
        Team awayTeam = new Team("AwayTeam");
        em.persist(homeTeam);
        em.persist(awayTeam);
        em.flush();

        TeamMatch teamMatch = teamMatchFacade.createMatch(
            homeTeam.getId(),
            awayTeam.getId(),
            now,
            now.plusMinutes(60)
        );
        em.clear();

        TeamMatch dbMatch = em.find(TeamMatch.class, teamMatch.getId());

        assertNotNull(dbMatch);
        assertEquals(dbMatch.getHomeTeam().getId(), homeTeam.getId());
        assertEquals(dbMatch.getAwayTeam().getId(), awayTeam.getId());
        assertEquals(dbMatch.getStartTime(), teamMatch.getStartTime());
        assertEquals(dbMatch.getEndTime(), teamMatch.getEndTime());
    }

    @Test
    public void testDeleteMatch()
    {
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

        TeamMatch teamMatch = new TeamMatch(homeTeam, awayTeam, now);
        TeamMatchGoal teamMatchGoal1 = new TeamMatchGoal(scorer1, assistant, teamMatch, now.plusMinutes(15));
        TeamMatchGoal teamMatchGoal2 = new TeamMatchGoal(scorer2, assistant, teamMatch, now.plusMinutes(25));

        em.persist(teamMatch);
        em.persist(teamMatchGoal1);
        em.persist(teamMatchGoal2);
        em.flush();

        assertNotNull(em.find(TeamMatch.class, teamMatch.getId()));

        teamMatchFacade.deleteMatch(teamMatch.getId());
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
        em.flush();

        TeamMatchGoal goal = teamMatchFacade.addNewScoredGoal(
            scorer.getId(),
            assistant.getId(),
            match.getId(),
            now
        );
        em.clear();

        TeamMatchGoal dbGoal = em.find(TeamMatchGoal.class, goal.getId());

        assertNotNull(dbGoal);
        assertEquals(dbGoal.getScorer().getId(), goal.getScorer().getId());
        assertEquals(dbGoal.getAssistant().getId(), goal.getAssistant().getId());
        assertEquals(dbGoal.getMatch().getId(), goal.getMatch().getId());
        assertEquals(dbGoal.getMatchTime(), goal.getMatchTime());
    }

    @Test
    public void testDeleteMatchGoal()
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

        assertNotNull(em.find(TeamMatchGoal.class, goal.getId()));

        teamMatchFacade.deleteMatchGoal(goal.getId());

        assertNull(em.find(TeamMatchGoal.class, goal.getId()));
    }

    @Test
    public void testChangeMatchTime()
    {
        Team homeTeam = new Team("HomeTeam");
        Team awayTeam = new Team("AwayTeam");
        em.persist(homeTeam);
        em.persist(awayTeam);

        TeamMatch teamMatch = new TeamMatch(homeTeam, awayTeam, now, now.plusMinutes(60));
        em.persist(teamMatch);
        em.flush();
        em.clear();

        TeamMatch dbMatch = em.find(TeamMatch.class, teamMatch.getId());

        assertNotNull(dbMatch);
        assertEquals(dbMatch.getStartTime(), teamMatch.getStartTime());
        assertEquals(dbMatch.getEndTime(), teamMatch.getEndTime());

        LocalDateTime newStartTime = now.plusMinutes(15);
        LocalDateTime newEndTime = now.plusMinutes(75);

        teamMatchFacade.changeMatchTime(teamMatch.getId(), newStartTime, newEndTime);

        dbMatch = em.find(TeamMatch.class, teamMatch.getId());
        assertNotNull(dbMatch);
        assertEquals(dbMatch.getStartTime(), newStartTime);
        assertEquals(dbMatch.getEndTime(), newEndTime);
    }

    @Test
    public void testEndMatch()
    {
        Team homeTeam = new Team("HomeTeam");
        Team awayTeam = new Team("AwayTeam");
        em.persist(homeTeam);
        em.persist(awayTeam);

        TeamMatch teamMatch = new TeamMatch(homeTeam, awayTeam, now);
        em.persist(teamMatch);

        TeamPlayer player1 = new TeamPlayer("Josef", "Fracek", 185, 84, homeTeam);
        TeamPlayer player2 = new TeamPlayer("Leopold", "Petarda", 174, 65, homeTeam);
        em.persist(player1);
        em.persist(player2);

        TeamMatchGoal goal1 = new TeamMatchGoal(player1, player2, teamMatch, teamMatch.getStartTime().plusMinutes(10));
        TeamMatchGoal goal2 = new TeamMatchGoal(player2, player1, teamMatch, teamMatch.getStartTime().plusMinutes(15));
        em.persist(goal1);
        em.persist(goal2);
        em.flush();
        em.clear();

        TeamMatch dbMatch = em.find(TeamMatch.class, teamMatch.getId());

        assertNotNull(dbMatch);
        assertEquals(dbMatch.getStartTime(), teamMatch.getStartTime());
        assertNull(dbMatch.getEndTime());

        LocalDateTime newEndTime = now.plusMinutes(67);

        teamMatchFacade.endMatch(teamMatch.getId(), newEndTime);

        dbMatch = em.find(TeamMatch.class, teamMatch.getId());
        assertNotNull(dbMatch);
        assertEquals(dbMatch.getEndTime(), newEndTime);
    }

    @Test
    public void testPreparePlayedMatchesForList()
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

        TeamMatch match = new TeamMatch(homeTeam, awayTeam, now, now.plusMinutes(94));
        em.persist(match);

        TeamMatchGoal goal1 = new TeamMatchGoal(scorer, assistant, match, now.plusMinutes(5));
        TeamMatchGoal goal2 = new TeamMatchGoal(scorer2, assistant2, match, now.plusMinutes(16));
        TeamMatchGoal goal3 = new TeamMatchGoal(scorer3, assistant3, match, now.plusMinutes(89));

        em.persist(goal1);
        em.persist(goal2);
        em.persist(goal3);
        em.flush();

        MatchListContainer mlc = teamMatchFacade.preparePlayedMatchesForList();

        assertEquals(mlc.getMatches().size(), 1);
        assertTrue(mlc.getMatches().contains(match));
        assertEquals(mlc.getHomeGoals().get(match).longValue(), 2);
        assertEquals(mlc.getAwayGoals().get(match).longValue(), 1);
    }

    @Test
    public void testPreparePlannedMatchesForList()
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

        MatchListContainer mlc = teamMatchFacade.preparePlannedMatchesForList();

        assertEquals(mlc.getMatches().size(), 1);
        assertTrue(mlc.getMatches().contains(match));
        assertEquals(mlc.getHomeGoals().get(match).longValue(), 2);
        assertEquals(mlc.getAwayGoals().get(match).longValue(), 1);
    }

}
