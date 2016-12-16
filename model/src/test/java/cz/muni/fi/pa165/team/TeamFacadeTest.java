package cz.muni.fi.pa165.team;

import cz.muni.fi.pa165.config.ModelConfig;
import cz.muni.fi.pa165.team.match.TeamMatch;
import cz.muni.fi.pa165.team.match.TeamMatchGoal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

/**
 * @author Denis Galajda <galajda.denis@gmail.com>
 */
@ContextConfiguration(classes = ModelConfig.class)
public class TeamFacadeTest extends AbstractTransactionalTestNGSpringContextTests
{

    private LocalDateTime now = LocalDateTime.now();

    @Autowired
    public TeamFacade teamFacade;

    @PersistenceContext
    public EntityManager em;

    @Test
    public void testCreateTeam() throws Exception
    {
        Team createdTeam = teamFacade.createTeam("ManCity");
        em.clear();
        em.find(Team.class, createdTeam.getId());

        Team foundTeam = em.find(Team.class, createdTeam.getId());
        assertNotNull(foundTeam);
        assertEquals("ManCity", foundTeam.getName());
    }

    @Test
    public void testChangeTeamName() throws Exception
    {
        Team createdTeam = teamFacade.createTeam("ManCity");
        em.clear();

        Team foundTeam = em.find(Team.class, createdTeam.getId());
        assertNotNull(foundTeam);
        assertEquals("ManCity", foundTeam.getName());

        teamFacade.changeTeamName(foundTeam.getId(), "ManUtd");
        em.clear();

        Team foundTeam2 = em.find(Team.class, createdTeam.getId());
        assertNotNull(foundTeam2);
        assertEquals(foundTeam2.getName(), "ManUtd");
    }

    @Test
    public void testDeleteTeam() throws Exception
    {
        Team createdTeam = teamFacade.createTeam("FCB");
        Team secondTeam = teamFacade.createTeam("RMA");
        em.flush();

        TeamPlayer player1 = new TeamPlayer("John", "Doe", 187, 85, createdTeam);
        em.persist(player1);
        em.flush();

        TeamPlayer player2 = new TeamPlayer("Ctiziadoslav", "Tetrov", 187, 85, createdTeam);
        em.persist(player2);
        em.flush();

        long time = System.currentTimeMillis();
        TeamMatch match = new TeamMatch(createdTeam, secondTeam, now, now.plusMinutes(15));
        em.persist(match);
        em.flush();

        TeamMatchGoal goal = new TeamMatchGoal(player1, player2, match, now.plusSeconds(1));
        em.persist(goal);
        em.flush();

        Assert.assertNotNull(em.find(TeamMatchGoal.class, goal.getId()));
        Assert.assertNotNull(em.find(TeamMatch.class, match.getId()));
        Assert.assertNotNull(em.find(Team.class, createdTeam.getId()));
        Assert.assertNotNull(em.find(TeamPlayer.class, player1.getId()));
        Assert.assertNotNull(em.find(TeamPlayer.class, player2.getId()));

        em.clear();
        UUID tmpId = createdTeam.getId();
        teamFacade.deleteTeam(createdTeam.getId());
        em.find(Team.class, tmpId);

        Assert.assertNull(em.find(TeamMatchGoal.class, goal.getId()));
        Assert.assertNull(em.find(TeamMatch.class, match.getId()));
        Assert.assertNull(em.find(Team.class, createdTeam.getId()));
        Assert.assertNull(em.find(TeamPlayer.class, player1.getId()));
        Assert.assertNull(em.find(TeamPlayer.class, player2.getId()));
    }

}
