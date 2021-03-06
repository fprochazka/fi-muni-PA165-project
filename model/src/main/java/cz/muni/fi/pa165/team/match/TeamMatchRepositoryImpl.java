package cz.muni.fi.pa165.team.match;

import cz.muni.fi.pa165.team.match.exceptions.MatchNotFoundException;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.UUID;

/**
 * This class implements the TeamMatchRepository interface.
 *
 * @author Tomas Smid <smid.thomas@gmail.com>
 */
@Repository
public class TeamMatchRepositoryImpl implements TeamMatchRepository
{

    private EntityManager entityManager;

    @PersistenceContext
    public void setEntityManager(EntityManager entityManager)
    {
        this.entityManager = entityManager;
    }

    @Override
    public TeamMatch getMatchById(final UUID matchId)
    {
        Assert.notNull(matchId, "Cannot search for a null match id");

        try {
            return entityManager.createQuery("SELECT m FROM TeamMatch m WHERE m.id = :matchId", TeamMatch.class)
                .setParameter("matchId", matchId)
                .getSingleResult();
        } catch (NoResultException ex) {
            throw new MatchNotFoundException(matchId, ex);
        }
    }

    @Override
    public Collection<TeamMatch> findMatchByHomeTeam(final UUID teamId)
    {
        Assert.notNull(teamId, "Cannot search for null match home team");

        return entityManager.createQuery("SELECT m FROM TeamMatch m WHERE m.homeTeam.id = :teamId", TeamMatch.class)
            .setParameter("teamId", teamId)
            .getResultList();
    }

    @Override
    public Collection<TeamMatch> findMatchByAwayTeam(final UUID teamId)
    {
        Assert.notNull(teamId, "Cannot search for null match away team");

        return entityManager.createQuery("SELECT m FROM TeamMatch m WHERE m.awayTeam.id = :teamId", TeamMatch.class)
            .setParameter("teamId", teamId)
            .getResultList();
    }


    @Override
    public Collection<TeamMatch> findMatchByStartTime(final LocalDateTime startTime)
    {
        Assert.notNull(startTime, "Cannot search for null match start time");

        return entityManager.createQuery("SELECT m FROM TeamMatch m WHERE m.startTime = :startTime", TeamMatch.class)
            .setParameter("startTime", startTime)
            .getResultList();
    }

    @Override
    public Collection<TeamMatch> findAllMatches()
    {
        return entityManager.createQuery("SELECT m FROM TeamMatch m", TeamMatch.class).getResultList();
    }

    @Override
    public Collection<TeamMatch> findAllPlayedMatches()
    {
        return entityManager.createQuery("SELECT m FROM TeamMatch m WHERE m.endTime IS NOT NULL", TeamMatch.class).getResultList();
    }

    @Override
    public Collection<TeamMatch> findAllPlannedMatches()
    {
        return entityManager.createQuery("SELECT m FROM TeamMatch m WHERE m.endTime IS NULL", TeamMatch.class).getResultList();
    }

    @Override
    public Collection<TeamMatch> findAllPlayedMatchesOfTeam(final UUID teamId)
    {
        Assert.notNull(teamId, "Cannot search all played matches for a null team");

        return entityManager.createQuery("SELECT m FROM TeamMatch m WHERE m.endTime IS NOT NULL AND (m.homeTeam.id = :teamId " +
            "OR m.awayTeam.id = :teamId)", TeamMatch.class)
            .setParameter("teamId", teamId)
            .getResultList();
    }

    @Override
    public Collection<TeamMatch> findAllPlannedMatchesOfTeam(final UUID teamId)
    {
        Assert.notNull(teamId, "Cannot search all planned matches for a null team");

        return entityManager.createQuery("SELECT m FROM TeamMatch m WHERE m.endTime IS NULL AND (m.homeTeam.id = :teamId " +
            "OR m.awayTeam.id = :teamId)", TeamMatch.class)
            .setParameter("teamId", teamId)
            .getResultList();
    }

    @Override
    public Collection<TeamMatch> findAllMatchesOfTeam(final UUID teamId)
    {
        Assert.notNull(teamId, "Cannot search all matches for a null team");

        return entityManager.createQuery("SELECT m FROM TeamMatch m WHERE m.homeTeam.id = :teamId " +
            "OR m.awayTeam.id = :teamId", TeamMatch.class)
            .setParameter("teamId", teamId)
            .getResultList();
    }

    @Override
    public TeamMatch findConflictingMatchByTeamAndStartTime(final UUID teamId, final LocalDateTime startTime)
    {
        Assert.notNull(teamId, "Cannot search for conflicting match with a null team");
        Assert.notNull(startTime, "Cannot search for conflicting match with a null match start time");

        try {
            return entityManager.createQuery("SELECT m FROM TeamMatch m WHERE m.startTime = :startTime AND " +
                "(m.homeTeam.id = :teamId OR m.awayTeam.id = :teamId)", TeamMatch.class)
                .setParameter("startTime", startTime)
                .setParameter("teamId", teamId)
                .getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }
}
