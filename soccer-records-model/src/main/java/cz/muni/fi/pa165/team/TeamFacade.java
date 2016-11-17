package cz.muni.fi.pa165.team;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.UUID;

/**
 * @author Denis Galajda <galajda.denis@gmail.com>
 */
public class TeamFacade
{

    private TeamService teamService;

    private TeamRepository teamRepository;

    private EntityManager entityManager;

    public TeamFacade(
        TeamService teamService,
        TeamRepository teamRepository,
        EntityManager entityManager
    )
    {
        this.teamService = teamService;
        this.teamRepository = teamRepository;
        this.entityManager = entityManager;
    }

    /**
     * Create and save the team.
     */
    public Team createTeam(String teamName)
    {
        Team sameNameTeam = teamRepository.findTeamByName(teamName);

        Team team = teamService.createTeam(
            teamName,
            sameNameTeam
        );

        entityManager.persist(team);
        entityManager.flush();

        return team;
    }

    /**
     * Change name of team.
     */
    public void changeTeamName(UUID teamId, String newTeamName)
    {
        Team team = teamRepository.getTeamById(teamId);
        Team sameTeamName = teamRepository.findTeamByName(newTeamName);

        teamService.changeTeamName(team, newTeamName, sameTeamName);

        entityManager.flush();
    }

    /**
     * Delete team.
     */
    public void deleteTeam(UUID teamId)
    {
        Team team = teamRepository.getTeamById(teamId);

        Query query = entityManager.createQuery("DELETE FROM TeamMatchGoal tmg WHERE tmg.match IN " +
            "(SELECT tm FROM TeamMatch tm WHERE tm.awayTeam = :tid OR tm.homeTeam = :tid)")
            .setParameter("tid", team);
        query.executeUpdate();

        query = entityManager.createQuery("DELETE FROM TeamMatch tm WHERE tm.awayTeam = :tid OR " +
            "tm.homeTeam = :tid")
            .setParameter("tid", team);
        query.executeUpdate();

        query = entityManager.createQuery("DELETE FROM TeamPlayer tp WHERE tp.team = :tid")
            .setParameter("tid", team);
        query.executeUpdate();

        entityManager.remove(team);

        entityManager.flush();
    }
}
