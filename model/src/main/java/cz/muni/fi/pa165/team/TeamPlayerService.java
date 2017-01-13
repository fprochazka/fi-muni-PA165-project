package cz.muni.fi.pa165.team;

import org.springframework.stereotype.Service;

/**
 * @author Libor Mühlpachr <libor.muhl@seznam.cz>
 */
@Service
public class TeamPlayerService
{

    /**
     * Creates a new player.
     *
     * @param firstname player's firstname
     * @param surname   player's surname
     * @param height    player's height
     * @param weight    player's weight
     * @param team      player's team
     * @return created player
     */
    public TeamPlayer createTeamPlayer(String firstname, String surname, int height, int weight, Team team)
    {
        return new TeamPlayer(firstname, surname, height, weight, team);
    }

    /**
     * Changes player details.
     *
     * @param tp        player whose firstname is to be changed
     * @param firstname new firstname of the player
     * @param surname   new surname of the player
     * @param height    new height of the player
     * @param weight    new weight of the player
     */
    public void changeTeamPlayerDetails(
        TeamPlayer tp,
        String firstname,
        String surname,
        int height,
        int weight
    )
    {
        tp.changeFirstname(firstname);
        tp.changeSurname(surname);
        tp.changeHeight(height);
        tp.changeWeight(weight);
    }

}
