package cz.muni.fi.pa165.team;

import cz.muni.fi.pa165.team.exceptions.TeamPlayerNotFoundException;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
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

    private EntityManager entityManager;

    @PersistenceContext
    public void setEntityManager(EntityManager entityManager)
    {
        this.entityManager = entityManager;
    }

    @Override
    public Collection<TeamPlayer> findTeamPlayerByFirstname(final String firstname)
    {
        if (firstname == null || firstname.isEmpty()) {
            throw new IllegalArgumentException("Cannot search for null firstname");
        }

        TypedQuery<TeamPlayer> query = entityManager.createQuery("SELECT tp FROM TeamPlayer tp WHERE tp.firstname = :firstname", TeamPlayer.class)
            .setParameter("firstname", firstname);
        return query.getResultList();
    }

    @Override
    public Collection<TeamPlayer> findTeamPlayerBySurname(final String surname)
    {
        if (surname == null || surname.isEmpty()) {
            throw new IllegalArgumentException("Cannot search for null surname");
        }
        TypedQuery<TeamPlayer> query = entityManager.createQuery("SELECT tp FROM TeamPlayer tp WHERE tp.surname = :surname", TeamPlayer.class)
            .setParameter("surname", surname);
        return query.getResultList();
    }

    @Override
    public Collection<TeamPlayer> findTeamPlayerByTeam(final UUID teamId)
    {
        if (teamId == null) {
            throw new IllegalArgumentException("Cannot search for null team");
        }

        TypedQuery<TeamPlayer> query = entityManager.createQuery("SELECT tp FROM TeamPlayer tp WHERE tp.team.id = :teamId", TeamPlayer.class)
            .setParameter("teamId", teamId);
        return query.getResultList();
    }

    @Override
    public TeamPlayer getTeamPlayerById(final UUID teamPlayerId)
    {
        Assert.notNull(teamPlayerId, "Cannot search for null teamPlayerId");

        try {
            return entityManager
                .createQuery("SELECT tp FROM TeamPlayer tp WHERE tp.id = :teamPlayerId", TeamPlayer.class)
                .setParameter("teamPlayerId", teamPlayerId)
                .getSingleResult();

        } catch (NoResultException e) {
            throw new TeamPlayerNotFoundException(teamPlayerId, e);
        }
    }

    @Override
    public TeamPlayer getTeamPlayerByTeamAndId(UUID teamId, UUID playerId)
    {
        Assert.notNull(teamId, "Cannot search for null teamId");
        Assert.notNull(playerId, "Cannot search for null playerId");

        try {
            return entityManager
                .createQuery("SELECT tp FROM TeamPlayer tp WHERE tp.id = :playerId AND tp.team.id = :teamId", TeamPlayer.class)
                .setParameter("teamId", teamId)
                .setParameter("playerId", playerId)
                .getSingleResult();

        } catch (NoResultException e) {
            throw new TeamPlayerNotFoundException(playerId, e);
        }
    }
}
