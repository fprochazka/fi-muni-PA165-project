package cz.muni.fi.pa165.team;

import org.springframework.stereotype.Repository;
import cz.muni.fi.pa165.team.exceptions.TeamPlayerNotFoundException;
import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.Collection;
import java.util.UUID;

/**
 * This class implements the TeamPlayerRepository interface.
 *
 * @author Libor Mühlpachr <libor.muhl@seznam.cz>
 */
@Repository
public class TeamPlayerRepositoryImpl implements TeamPlayerRepository
{

    private EntityManager em;

    public TeamPlayerRepositoryImpl(EntityManager em)
    {
        this.em = em;
    }

    @Override
    public Collection<TeamPlayer> findTeamPlayerByFirstname(String firstname)
    {
        if (firstname == null || firstname.isEmpty()) {
            throw new IllegalArgumentException("Cannot search for null firstname");
        }

        TypedQuery<TeamPlayer> query = em.createQuery("SELECT tp FROM TeamPlayer tp WHERE tp.firstname = :firstname", TeamPlayer.class)
            .setParameter("firstname", firstname);
        return query.getResultList();
    }

    @Override
    public Collection<TeamPlayer> findTeamPlayerBySurname(String surname)
    {
        if (surname == null || surname.isEmpty()) {
            throw new IllegalArgumentException("Cannot search for null surname");
        }
        TypedQuery<TeamPlayer> query = em.createQuery("SELECT tp FROM TeamPlayer tp WHERE tp.surname = :surname", TeamPlayer.class)
            .setParameter("surname", surname);
        return query.getResultList();
    }

    @Override
    public Collection<TeamPlayer> findTeamPlayerByTeam(Team team)
    {
        if (team == null) {
            throw new IllegalArgumentException("Cannot search for null team");
        }

        TypedQuery<TeamPlayer> query = em.createQuery("SELECT tp FROM TeamPlayer tp WHERE tp.team = :tid", TeamPlayer.class)
            .setParameter("tid", team);
        return query.getResultList();
    }

    @Override
    public TeamPlayer getTeamPlayerById(UUID teamPlayerId)
    {
        Assert.notNull(teamPlayerId, "Cannot search for null teamPlayerId");

        try {
            return em
                .createQuery("SELECT tp FROM TeamPlayer tp WHERE tp.id = :teamPlayerId", TeamPlayer.class)
                .setParameter("teamPlayerId", teamPlayerId)
                .getSingleResult();

        } catch (NoResultException e) {
            throw new TeamPlayerNotFoundException(teamPlayerId, e);
        }
    }

    @Override
    public Collection<TeamPlayer> findAllTeamPlayers()
    {
        TypedQuery<TeamPlayer> query = em.createQuery("SELECT tp FROM TeamPlayer tp", TeamPlayer.class);
        return query.getResultList();
    }
}
