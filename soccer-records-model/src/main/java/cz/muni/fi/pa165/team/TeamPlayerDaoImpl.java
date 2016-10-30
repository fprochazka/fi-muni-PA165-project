package cz.muni.fi.pa165.team;

import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.UUID;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 * @author Libor Mühlpachr <libor.muhl@seznam.cz>
 */

@Repository
public class TeamPlayerDaoImpl implements TeamPlayerDao
{

    private EntityManager em;

    @Override
    public TeamPlayer findPlayerByFirstname(String firstname)
    {
        if (firstname == null || firstname.isEmpty()) {
            throw new IllegalArgumentException("Cannot search for null name");
        }

        try {
            return em
                    .createQuery("SELECT tp FROM TeamPlayer tp WHERE firstname = :firstname", TeamPlayer.class)
                    .setParameter("firstname", firstname)
                    .getSingleResult();

        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public TeamPlayer findPlayerBySurname(String surname)
    {
        if (surname == null || surname.isEmpty()) {
            throw new IllegalArgumentException("Cannot search for null name");
        }

        try {
            return em
                    .createQuery("SELECT tp FROM TeamPlayer tp WHERE surname = :surname", TeamPlayer.class)
                    .setParameter("surname", surname)
                    .getSingleResult();

        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public TeamPlayer findPlayerByAge(int age)
    {
        if (age == null) {
            throw new IllegalArgumentException("Cannot search for null age");
        }

        try {
            return em
                    .createQuery("SELECT tp FROM TeamPlayer tp WHERE age = :age", TeamPlayer.class)
                    .setParameter("age", age)
                    .getSingleResult();

        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public TeamPlayer findPlayerByTeam(Team team)
    {
        if (team == null) {
            throw new IllegalArgumentException("Cannot search for null team");
        }

        try {
            return em
                    .createQuery("SELECT tp FROM TeamPlayer tp WHERE team = :team", TeamPlayer.class)
                    .setParameter("team", team)
                    .getSingleResult();

        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public TeamPlayer findPlayerByHeight(int height)
    {
        if (height == null) {
            throw new IllegalArgumentException("Cannot search for null height");
        }

        try {
            return em
                    .createQuery("SELECT tp FROM TeamPlayer tp WHERE height = :height", TeamPlayer.class)
                    .setParameter("height", height)
                    .getSingleResult();

        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public TeamPlayer findPlayerByWeight(int weight)
    {
        if (weight == null) {
            throw new IllegalArgumentException("Cannot search for null weight");
        }

        try {
            return em
                    .createQuery("SELECT tp FROM TeamPlayer tp WHERE weight = :weight", TeamPlayer.class)
                    .setParameter("weight", weight)
                    .getSingleResult();

        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public TeamPlayer findById(UUID id)
    {
        return em.find(TeamPlayer.class, id);
    }

    @PersistenceContext
    public void setEntityManager(EntityManager em)
    {
        this.em = em;
    }

    @Override
    public void create(TeamPlayer p)
    {
        em.persist(p);
    }

    @Override
    public void update(TeamPlayer p)
    {
        em.merge(p);
    }

    @Override
    public void delete(TeamPlayer p)
    {
        em.remove(p);
    }

    @Override
    public Collection findAll()
    {
        TypedQuery<TeamPlayer> query = em.createQuery("SELECT p FROM TeamPlayer p", TeamPlayer.class);
        return query.getResultList();
    }
}