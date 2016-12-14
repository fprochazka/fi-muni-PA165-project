package cz.muni.fi.pa165.team;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.UUID;

/**
 * @author Libor Mühlpachr <libor.muhl@seznam.cz>
 */
@Service
public class TeamPlayerFacade
{

    private TeamPlayerService teamPlayerService;

    private TeamPlayerRepository teamPlayerRepository;

    private EntityManager entityManager;

    @Autowired
    public TeamPlayerFacade(
        TeamPlayerService teamPlayerService,
        TeamPlayerRepository teamPlayerRepository,
        EntityManager entityManager
    )
    {
        this.teamPlayerService = teamPlayerService;
        this.teamPlayerRepository = teamPlayerRepository;
        this.entityManager = entityManager;
    }

    /**
     * This method takes 5 parameters and creates a player.
     *
     * @param teamPlayerFirstname firstname of the player
     * @param teamPlayerSurname surname of the player
     * @param teamPlayerHeight height of the player
     * @param teamPlayerWeight weight of the player
     * @param teamPlayerTeam team of the player
     * @return created player
     */
    public TeamPlayer createTeamPlayer(String teamPlayerFirstname, String teamPlayerSurname, int teamPlayerHeight, int teamPlayerWeight, Team teamPlayerTeam)
    {
        TeamPlayer teamPlayer = teamPlayerService.createTeamPlayer(
            teamPlayerFirstname,
            teamPlayerSurname,
            teamPlayerHeight,
            teamPlayerWeight,
            teamPlayerTeam
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
    public void deleteTeamPlayer(UUID teamPlayerId)
    {
        TeamPlayer teamPlayer = teamPlayerRepository.getTeamPlayerById(teamPlayerId);

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
     * @param teamPlayerId id of the player whose firstname is to be changed
     * @param newTeamPlayerFirstname new firstname of the player
     */
    public void changeTeamPlayerFirstname(UUID teamPlayerId, String newTeamPlayerFirstname)
    {
        TeamPlayer teamPlayer = teamPlayerRepository.getTeamPlayerById(teamPlayerId);

        teamPlayerService.changeTeamPlayerFirstname(teamPlayer, newTeamPlayerFirstname);

        entityManager.flush();
    }

    /**
     * This method changes player's surname.
     *
     * @param teamPlayerId id of the player whose surname is to be changed
     * @param newTeamPlayerSurname new surname of the player
     */
    public void changeTeamPlayerSurname(UUID teamPlayerId, String newTeamPlayerSurname)
    {
        TeamPlayer teamPlayer = teamPlayerRepository.getTeamPlayerById(teamPlayerId);

        teamPlayerService.changeTeamPlayerSurname(teamPlayer, newTeamPlayerSurname);

        entityManager.flush();
    }

    /**
     * This method changes player's height.
     *
     * @param teamPlayerId id of the player whose height is to be changed
     * @param newTeamPlayerHeight new height of the player
     */
    public void changeTeamPlayerHeight(UUID teamPlayerId, int newTeamPlayerHeight)
    {
        TeamPlayer teamPlayer = teamPlayerRepository.getTeamPlayerById(teamPlayerId);

        teamPlayerService.changeTeamPlayerHeight(teamPlayer, newTeamPlayerHeight);

        entityManager.flush();
    }

    /**
     * This method changes player's weight.
     *
     * @param teamPlayerId id of the player whose weight is to be changed
     * @param newTeamPlayerWeight new weight of the player
     */
    public void changeTeamPlayerWeight(UUID teamPlayerId, int newTeamPlayerWeight)
    {
        TeamPlayer teamPlayer = teamPlayerRepository.getTeamPlayerById(teamPlayerId);

        teamPlayerService.changeTeamPlayerWeight(teamPlayer, newTeamPlayerWeight);

        entityManager.flush();
    }
}
