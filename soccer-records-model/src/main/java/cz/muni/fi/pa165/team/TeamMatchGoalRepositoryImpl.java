package cz.muni.fi.pa165.team;

import cz.muni.fi.pa165.team.exceptions.GoalNotFoundException;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.Collection;
import java.util.UUID;

/**
 * This class implements the TeamMatchGoalRepository interface.
 *
 * @author Tomas Smid <smid.thomas@gmail.com>
 */
@Repository
public class TeamMatchGoalRepositoryImpl implements TeamMatchGoalRepository
{

    private EntityManager em;

    public TeamMatchGoalRepositoryImpl(EntityManager em)
    {
        this.em = em;
    }

    @Override
    public TeamMatchGoal getGoalById(final UUID goalId)
    {
        if(goalId == null){
            throw new IllegalArgumentException("Cannot search for a null goal id");
        }

        try {
            return em.createQuery("SELECT g FROM TeamMatchGoal g WHERE g.id = :goalId",
                TeamMatchGoal.class).setParameter("goalId", goalId).getSingleResult();
        }catch (NoResultException ex){
            throw new GoalNotFoundException(goalId, ex);
        }
    }

    @Override
    public Collection<TeamMatchGoal> findGoalByScorer(final UUID playerId)
    {
        if (playerId == null) {
            throw new IllegalArgumentException("Cannot search for null scorer");
        }

        TypedQuery<TeamMatchGoal> query = em
            .createQuery("SELECT g FROM TeamMatchGoal g WHERE g.scorer = :scorerid",
                TeamMatchGoal.class)
            .setParameter("scorerid", playerId);
        return query.getResultList();
    }

    @Override
    public Collection<TeamMatchGoal> findGoalByAssistant(final UUID playerId)
    {
        if (playerId == null) {
            throw new IllegalArgumentException("Cannot search for null assistant");
        }

        TypedQuery<TeamMatchGoal> query = em
            .createQuery("SELECT g FROM TeamMatchGoal g WHERE g.assistant = :assistantid",
                TeamMatchGoal.class)
            .setParameter("assistantid", playerId);
        return query.getResultList();
    }

    @Override
    public Collection<TeamMatchGoal> findGoalByMatch(final UUID matchId)
    {
        if (matchId == null) {
            throw new IllegalArgumentException("Cannot search for null match");
        }

        TypedQuery<TeamMatchGoal> query = em
            .createQuery("SELECT g FROM TeamMatchGoal g WHERE g.match = :matchid",
                TeamMatchGoal.class)
            .setParameter("matchid", matchId);
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
