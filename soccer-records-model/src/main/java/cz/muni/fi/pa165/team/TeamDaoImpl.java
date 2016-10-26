package cz.muni.fi.pa165.team;

import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.UUID;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author Denis Galajda
 */
@Repository
public class TeamDaoImpl implements TeamDao
{
    private EntityManager em;

    @Override
    public Team findTeamByName(String name)
    {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Cannot search for null name");
        }

        try {
            return em
                .createQuery("SELECT t FROM Team t WHERE name = :name", Team.class)
                .setParameter("name", name)
                .getSingleResult();

        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public Team findById(UUID id)
    {
        return em.find(Team.class, id);
    }

    @PersistenceContext
    public void setEntityManager(EntityManager em)
    {
        this.em = em;
    }

    @Override
    public void update(Team t)
    {
        em.merge(t);
    }

    @Override
    public void delete(Team t)
    {
        em.remove(t);
    }

    @Override
    public Collection findAll()
    {
        TypedQuery<Team> query = em.createQuery("SELECT t FROM Team t", Team.class);
        return query.getResultList();
    }

    @Override
    public void create(Team t)
    {
         em.persist(t);
    }

}
