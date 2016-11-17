package cz.muni.fi.pa165.team;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.Collection;
import java.util.UUID;

/**
 * This class implements the TeamDAO interface.
 *
 * @author Denis Galajda
 */
@Repository
public class TeamDaoImpl implements TeamDao
{

    private EntityManager em;

    public TeamDaoImpl(EntityManager em)
    {
        this.em = em;
    }

    @Override
    public Team findTeamByName(String name)
    {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Cannot search for null name");
        }

        try {
            return em
                .createQuery("SELECT t FROM Team t WHERE t.name = :name", Team.class)
                .setParameter("name", name)
                .getSingleResult();

        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public Team findTeamById(UUID id)
    {
        return em.find(Team.class, id);
    }

    @Override
    public Team findTeamByPlayer(TeamPlayer tp)
    {
        if (tp == null) {
            throw new IllegalArgumentException("Cannot search for null name");
        }

        try {
            return em
                .createQuery("SELECT t FROM Team t WHERE t IN (SELECT tp.team FROM TeamPlayer tp WHERE tp.team = t.id AND tp.id = :tpid)", Team.class)
                .setParameter("tpid", tp.getId())
                .getSingleResult();

        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public void updateTeam(Team t)
    {
        em.merge(t);
    }

    @Override
    public void deleteTeam(Team t)
    {
        Query query = em.createQuery("DELETE FROM TeamMatchGoal tmg WHERE tmg.match IN " +
            "(SELECT tm FROM TeamMatch tm WHERE tm.awayTeam = :tid OR tm.homeTeam = :tid)")
            .setParameter("tid", t);
        query.executeUpdate();

        query = em.createQuery("DELETE FROM TeamMatch tm WHERE tm.awayTeam = :tid OR " +
            "tm.homeTeam = :tid")
            .setParameter("tid", t);
        query.executeUpdate();

        query = em.createQuery("DELETE FROM TeamPlayer tp WHERE tp.team = :tid")
            .setParameter("tid", t);
        query.executeUpdate();

        em.remove(t);
    }

    @Override
    public Collection<Team> findAll()
    {
        TypedQuery<Team> query = em.createQuery("SELECT t FROM Team t", Team.class);
        return query.getResultList();
    }

    @Override
    public void createTeam(Team t)
    {
        em.persist(t);
    }

}
