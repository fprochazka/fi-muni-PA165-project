package cz.muni.fi.pa165.team;

import cz.muni.fi.pa165.team.match.TeamMatchGoal;
import cz.muni.fi.pa165.team.match.TeamMatchGoalRepositoryImpl;
import cz.muni.fi.pa165.team.result.TeamPlayerResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collection;
import java.util.UUID;

/**
 * @author Libor Mühlpachr <libor.muhl@seznam.cz>
 */
@Service
@Transactional
public class TeamPlayerFacade
{

    private TeamPlayerService teamPlayerService;

    private TeamPlayerRepository teamPlayerRepository;

    private TeamMatchGoalRepositoryImpl teamMatchGoalRepository;

    private TeamRepository teamRepository;

    private EntityManager entityManager;

    @Autowired
    public TeamPlayerFacade(
        TeamPlayerService teamPlayerService,
        TeamPlayerRepository teamPlayerRepository,
        TeamMatchGoalRepositoryImpl teamMatchGoalRepository,
        TeamRepository teamRepository
    )
    {
        this.teamPlayerService = teamPlayerService;
        this.teamPlayerRepository = teamPlayerRepository;
        this.teamMatchGoalRepository = teamMatchGoalRepository;
        this.teamRepository = teamRepository;
        this.entityManager = entityManager;
    }

    @PersistenceContext
    public void setEntityManager(EntityManager entityManager)
    {
        this.entityManager = entityManager;
    }

    /**
     * This method takes 5 parameters and creates a player.
     *
     * @param teamPlayerFirstname firstname of the player
     * @param teamPlayerSurname   surname of the player
     * @param teamPlayerHeight    height of the player
     * @param teamPlayerWeight    weight of the player
     * @return created player
     */
    public TeamPlayer createTeamPlayer(
        UUID teamId,
        String teamPlayerFirstname,
        String teamPlayerSurname,
        int teamPlayerHeight,
        int teamPlayerWeight
    )
    {
        Team team = teamRepository.getTeamById(teamId);

        TeamPlayer teamPlayer = teamPlayerService.createTeamPlayer(
            teamPlayerFirstname,
            teamPlayerSurname,
            teamPlayerHeight,
            teamPlayerWeight,
            team
        );

        entityManager.persist(teamPlayer);
        entityManager.flush();

        return teamPlayer;
    }

    /**
     * This method deletes a player.
     *
     * @param teamPlayerId id of the player to be deleted
     */
    public void deleteTeamPlayer(UUID teamId, UUID teamPlayerId)
    {
        TeamPlayer teamPlayer = teamPlayerRepository.getTeamPlayerByTeamAndId(teamId, teamPlayerId);

        entityManager
            .createQuery("DELETE FROM TeamMatchGoal tmg WHERE tmg.scorer.id = :tpid OR tmg.assistant.id = :tpid")
            .setParameter("tpid", teamPlayer.getId())
            .executeUpdate();

        entityManager.remove(teamPlayer);
        entityManager.flush();
    }

    /**
     * This method changes player's firstname.
     *
     * @param teamPlayerId           id of the player whose firstname is to be changed
     * @param newTeamPlayerFirstname new firstname of the player
     */
    public void changeTeamPlayerDetails(
        UUID teamId,
        UUID teamPlayerId,
        String newTeamPlayerFirstname,
        String newTeamPlayerSurname,
        int newTeamPlayerHeight,
        int newTeamPlayerWeight
    )
    {
        TeamPlayer teamPlayer = teamPlayerRepository.getTeamPlayerByTeamAndId(teamId, teamPlayerId);

        teamPlayerService.changeTeamPlayerDetails(
            teamPlayer,
            newTeamPlayerFirstname,
            newTeamPlayerSurname,
            newTeamPlayerHeight,
            newTeamPlayerWeight
        );

        entityManager.flush();
    }

    /**
     * This method changes player's weight.
     *
     * @param teamPlayerID id of the player
     * @return {@link TeamPlayerResult} with player stats
     */
    public TeamPlayerResult getPlayerStatistics(UUID teamPlayerID)
    {
        TeamPlayer player = teamPlayerRepository.getTeamPlayerById(teamPlayerID);

        Collection<TeamMatchGoal> scorredGoals = teamMatchGoalRepository.findGoalByScorer(teamPlayerID);
        int goals = (scorredGoals != null) ? scorredGoals.size() : 0;

        Collection<TeamMatchGoal> assistedGoals = teamMatchGoalRepository.findGoalByAssistant(teamPlayerID);
        int assists = (assistedGoals != null) ? assistedGoals.size() : 0;

        return new TeamPlayerResult(player, goals, assists);
    }
}
