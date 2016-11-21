package cz.muni.fi.pa165.team;

import org.springframework.stereotype.Service;

/**
 * @author Libor Mühlpachr <libor.muhl@seznam.cz>
 */

@Service
public class TeamPlayerService
{

    /**
     * This method creates a player.
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
        return new TeamPlayer(firstname, surname, weight, height, team);
    }

    /**
     * This method changes player's firstname.
     *
     * @param tp        - player whose firstname is to be changed
     * @param firstname - new firstname of the player
     */
    public void changeTeamPlayerFirstname(TeamPlayer tp, String firstname)
    {
        tp.changeFirstname(firstname);
    }

    /**
     * This method changes player's surname.
     *
     * @param tp - player whose surname is to be changed
     * @param surname - new surname of the player
     */
    public void changeTeamPlayerSurname(TeamPlayer tp, String surname)
    {
        tp.changeSurname(surname);
    }

    /**
     * This method changes player's height.
     *
     * @param tp - player whose height is to be changed
     * @param height - new height of the player
     */
    public void changeTeamPlayerHeight(TeamPlayer tp, int height)
    {
        tp.changeHeight(height);
    }

    /**
     * This method changes player's weight.
     *
     * @param tp - player whose weight is to be changed
     * @param weight - new weight of the player
     */
    public void changeTeamPlayerWeight(TeamPlayer tp, int weight)
    {
        tp.changeWeight(weight);
    }
}
