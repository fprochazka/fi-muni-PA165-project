package cz.muni.fi.pa165.team;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.Collection;
import java.util.UUID;

/**
 * This class implements the TeamMatchGoalDAO interface.
 *
 * @author Tomas Smid <smid.thomas@gmail.com>
 */
@Repository
public class TeamMatchGoalDAOImpl implements TeamMatchGoalDAO
{

    private EntityManager em;

    @PersistenceContext
    public void setEntityManager(EntityManager em)
    {
        this.em = em;
    }

    @Override
    public void createGoal(TeamMatchGoal goal)
    {
        em.persist(goal);
    }

    @Override
    public void updateGoal(TeamMatchGoal goal)
    {
        em.merge(goal);
    }

    @Override
    public void deleteGoal(TeamMatchGoal goal)
    {
        em.remove(goal);
    }

    @Override
    public TeamMatchGoal findGoalById(UUID id)
    {
        return em.find(TeamMatchGoal.class, id);
    }

    @Override
    public Collection<TeamMatchGoal> findGoalByScorer(TeamPlayer scorer)
    {
        if (scorer == null) {
            throw new IllegalArgumentException("Cannot search for null scorer");
        }

        TypedQuery<TeamMatchGoal> query = em
            .createQuery("SELECT g FROM TeamMatchGoal g WHERE g.scorer = :scorerid",
                TeamMatchGoal.class)
            .setParameter("scorerid", scorer);
        return query.getResultList();
    }

    @Override
    public Collection<TeamMatchGoal> findGoalByAssistant(TeamPlayer assistant)
    {
        if (assistant == null) {
            throw new IllegalArgumentException("Cannot search for null assistant");
        }

        TypedQuery<TeamMatchGoal> query = em
            .createQuery("SELECT g FROM TeamMatchGoal g WHERE g.assistant = :assistantid",
                TeamMatchGoal.class)
            .setParameter("assistantid", assistant);
        return query.getResultList();
    }

    @Override
    public Collection<TeamMatchGoal> findGoalByMatch(TeamMatch match)
    {
        if (match == null) {
            throw new IllegalArgumentException("Cannot search for null match");
        }

        TypedQuery<TeamMatchGoal> query = em
            .createQuery("SELECT g FROM TeamMatchGoal g WHERE g.match = :matchid",
                TeamMatchGoal.class)
            .setParameter("matchid", match);
        return query.getResultList();
    }

    @Override
    public Collection<TeamMatchGoal> findAllGoals()
    {
        TypedQuery<TeamMatchGoal> query = em.createQuery("SELECT g FROM TeamMatchGoal g",
            TeamMatchGoal.class);
        return query.getResultList();
    }
}
