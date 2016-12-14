package cz.muni.fi.pa165.team;

import cz.muni.fi.pa165.team.exceptions.TeamNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.Collection;
import java.util.UUID;

/**
 * This class implements the TeamDAO interface.
 *
 * @author Denis Galajda
 */
@Repository
public class TeamRepositoryImpl implements TeamRepository
{

    private EntityManager em;

    @Autowired
    public TeamRepositoryImpl(EntityManager em)
    {
        this.em = em;
    }

    @Override
    public Team findTeamByName(final String name)
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
    public Team getTeamById(final UUID teamId)
    {
        Assert.notNull(teamId, "Cannot search for a null teamId");

        try {
            return em
                .createQuery("SELECT t FROM Team t WHERE t.id = :teamId", Team.class)
                .setParameter("teamId", teamId)
                .getSingleResult();

        } catch (NoResultException e) {
            throw new TeamNotFoundException(teamId, e);
        }
    }

    @Override
    public Team findTeamByPlayer(final TeamPlayer tp)
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
    public Collection<Team> findAll()
    {
        TypedQuery<Team> query = em.createQuery("SELECT t FROM Team t", Team.class);
        return query.getResultList();
    }

}
