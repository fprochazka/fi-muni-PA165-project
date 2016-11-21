package cz.muni.fi.pa165.team;

import cz.muni.fi.pa165.team.exceptions.MatchNotFoundException;
import org.springframework.stereotype.Repository;


import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.Collection;
import java.util.Date;
import java.util.UUID;

/**
 * This class implements the TeamMatchRepository interface.
 *
 * @author Tomas Smid <smid.thomas@gmail.com>
 */
@Repository
public class TeamMatchRepositoryImpl implements TeamMatchRepository
{

    private EntityManager em;

    public TeamMatchRepositoryImpl(EntityManager em)
    {
        this.em = em;
    }

    @Override
    public TeamMatch getMatchById(final UUID matchId)
    {
        if(matchId == null){
            throw new IllegalArgumentException("Cannot search for a null match id");
        }

        try {
            return em.createQuery("SELECT m FROM TeamMatch m WHERE m.id = :matchId", TeamMatch.class)
                .setParameter("matchId", matchId)
                .getSingleResult();
        }catch (NoResultException ex){
            throw new MatchNotFoundException(matchId, ex);
        }
    }

    @Override
    public Collection<TeamMatch> findMatchByHomeTeam(final UUID teamId)
    {
        if (teamId == null) {
            throw new IllegalArgumentException("Cannot search for null match home team");
        }

        TypedQuery<TeamMatch> query = em
            .createQuery("SELECT m FROM TeamMatch m WHERE m.homeTeam.id = :teamId", TeamMatch.class)
            .setParameter("teamId", teamId);
        return query.getResultList();
    }

    @Override
    public Collection<TeamMatch> findMatchByAwayTeam(final UUID teamId)
    {
        if (teamId == null) {
            throw new IllegalArgumentException("Cannot search for null match away team");
        }

        TypedQuery<TeamMatch> query = em
            .createQuery("SELECT m FROM TeamMatch m WHERE m.awayTeam.id = :teamId", TeamMatch.class)
            .setParameter("teamId", teamId);
        return query.getResultList();
    }


    @Override
    public Collection<TeamMatch> findMatchByStartTime(final Date startTime)
    {
        if (startTime == null) {
            throw new IllegalArgumentException("Cannot search for null match start time");
        }

        TypedQuery<TeamMatch> query = em
            .createQuery("SELECT m FROM TeamMatch m WHERE m.startTime = :startTime", TeamMatch.class)
            .setParameter("startTime", startTime);
        return query.getResultList();
    }

    @Override
    public Collection<TeamMatch> findAllMatches()
    {
        TypedQuery<TeamMatch> query = em.createQuery("SELECT m FROM TeamMatch m", TeamMatch.class);
        return query.getResultList();
    }

    @Override
    public Collection<TeamMatch> findAllPlayedMatches()
    {
        TypedQuery<TeamMatch> query = em.createQuery("SELECT m FROM TeamMatch m WHERE m.endTime IS NOT NULL",
            TeamMatch.class);
        return query.getResultList();
    }

    @Override
    public Collection<TeamMatch> findAllPlannedMatches()
    {
        TypedQuery<TeamMatch> query = em.createQuery("SELECT m FROM TeamMatch m WHERE m.endTime IS NULL",
            TeamMatch.class);
        return query.getResultList();
    }

    @Override
    public Collection<TeamMatch> findAllPlayedMatchesOfTeam(final UUID teamId)
    {
        TypedQuery<TeamMatch> query = em.createQuery("SELECT m FROM TeamMatch m WHERE m.endTime IS NOT NULL " +
                                            "AND (m.homeTeam.id = :teamId OR m.awayTeam.id = :teamId)", TeamMatch.class)
                                        .setParameter("teamId", teamId);
        return query.getResultList();
    }

    @Override
    public Collection<TeamMatch> findAllPlannedMatchesOfTeam(final UUID teamId)
    {
        TypedQuery<TeamMatch> query = em.createQuery("SELECT m FROM TeamMatch m WHERE m.endTime IS NULL " +
                                            "AND (m.homeTeam.id = :teamId OR m.awayTeam.id = :teamId)", TeamMatch.class)
                                        .setParameter("teamId", teamId);
        return query.getResultList();
    }
}
