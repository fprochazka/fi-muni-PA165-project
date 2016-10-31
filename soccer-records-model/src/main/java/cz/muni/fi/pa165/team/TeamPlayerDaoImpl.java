package cz.muni.fi.pa165.team;

import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.UUID;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 * @author Libor Mühlpachr <libor.muhl@seznam.cz>
 */

@Repository
public class TeamPlayerDaoImpl implements TeamPlayerDao
{

    private EntityManager em;

    @PersistenceContext
    public void setEntityManager(EntityManager entityManager)
    {
        this.em = entityManager;
    }

    @Override
    public Collection<TeamPlayer> findPlayerByFirstname(String firstname)
    {
        if (firstname == null || firstname.isEmpty()) {
            throw new IllegalArgumentException("Cannot search for null firstname");
        }

        TypedQuery<TeamPlayer> query = em.createQuery("SELECT tp FROM TeamPlayer tp WHERE tp.firstname = :firstname", TeamPlayer.class)
            .setParameter("firstname", firstname);
        return query.getResultList();

    }

    @Override
    public Collection<TeamPlayer> findPlayerBySurname(String surname)
    {
        if (surname == null || surname.isEmpty()) {
            throw new IllegalArgumentException("Cannot search for null surname");
        }
        TypedQuery<TeamPlayer> query = em.createQuery("SELECT tp FROM TeamPlayer tp WHERE tp.surname = :surname", TeamPlayer.class)
            .setParameter("surname", surname);
        return query.getResultList();
    }

    @Override
    public Collection<TeamPlayer> findPlayerByTeam(Team team)
    {
        if (team == null) {
            throw new IllegalArgumentException("Cannot search for null team");
        }

        TypedQuery<TeamPlayer> query = em.createQuery("SELECT tp FROM TeamPlayer tp WHERE tp.team = :tid", TeamPlayer.class)
            .setParameter("tid", team);
        return query.getResultList();
    }

    @Override
    public TeamPlayer findPlayerById(UUID id)
    {
        return em.find(TeamPlayer.class, id);
    }

    @Override
    public void createPlayer(TeamPlayer tp)
    {
        em.persist(tp);
    }

    @Override
    public void updatePlayer(TeamPlayer tp)
    {
        em.merge(tp);
    }

    @Override
    public void deletePlayer(TeamPlayer tp)
    {
        em.remove(tp);
    }

    @Override
    public Collection<TeamPlayer> findAllPlayers()
    {
        TypedQuery<TeamPlayer> query = em.createQuery("SELECT tp FROM TeamPlayer tp", TeamPlayer.class);
        return query.getResultList();
    }
}
