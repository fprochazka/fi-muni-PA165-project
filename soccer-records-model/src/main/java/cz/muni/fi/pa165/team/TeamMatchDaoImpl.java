package cz.muni.fi.pa165.team;

import org.springframework.stereotype.Repository;


import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.Collection;
import java.util.Date;
import java.util.UUID;

/**
 * This class implements the TeamMatchDAO interface.
 *
 * @author Tomas Smid <smid.thomas@gmail.com>
 */
@Repository
public class TeamMatchDaoImpl implements TeamMatchDAO
{

    private EntityManager em;

    public TeamMatchDaoImpl(EntityManager em)
    {
        this.em = em;
    }

    @Override
    public void createMatch(TeamMatch teamMatch)
    {
        em.persist(teamMatch);
    }

    @Override
    public void updateMatch(TeamMatch teamMatch)
    {
        em.merge(teamMatch);
    }

    @Override
    public void deleteMatch(TeamMatch teamMatch)
    {
        Query query = em.createQuery("DELETE TeamMatchGoal tmg WHERE tmg.match = :teamMatch")
            .setParameter("teamMatch", teamMatch);
        query.executeUpdate();

        em.remove(teamMatch);
    }

    @Override
    public TeamMatch findMatchById(UUID id)
    {
        return em.find(TeamMatch.class, id);
    }

    @Override
    public Collection<TeamMatch> findMatchByHomeTeam(Team team)
    {
        if (team == null) {
            throw new IllegalArgumentException("Cannot search for null match home team");
        }

        TypedQuery<TeamMatch> query = em
            .createQuery("SELECT m FROM TeamMatch m WHERE m.homeTeam = :teamid", TeamMatch.class)
            .setParameter("teamid", team);
        return query.getResultList();
    }

    @Override
    public Collection<TeamMatch> findMatchByAwayTeam(Team team)
    {
        if (team == null) {
            throw new IllegalArgumentException("Cannot search for null match away team");
        }

        TypedQuery<TeamMatch> query = em
            .createQuery("SELECT m FROM TeamMatch m WHERE m.awayTeam = :teamid", TeamMatch.class)
            .setParameter("teamid", team);
        return query.getResultList();
    }


    @Override
    public Collection<TeamMatch> findMatchByStartTime(Date startTime)
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
}
