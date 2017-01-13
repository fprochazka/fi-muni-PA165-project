package cz.muni.fi.pa165.team.player;

import cz.muni.fi.pa165.team.Team;
import cz.muni.fi.pa165.team.TeamPlayer;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.UUID;

/**
 * @author Libor Muhlpachr <libor.muhl@seznam.cz>
 */

public class TeamPlayerRequest
{

    @NotEmpty
    public String firstname;

    @NotEmpty
    public String surname;

    @NotEmpty
    public int height;

    @NotEmpty
    public int weight;

    public static TeamPlayerRequest fromTeamPlayer(TeamPlayer teamPlayer)
    {
        TeamPlayerRequest request = new TeamPlayerRequest();

        request.setFirstname(teamPlayer.getFirstname());
        request.setSurname(teamPlayer.getSurname());
        request.setHeight(teamPlayer.getHeight());
        request.setWeight(teamPlayer.getWeight());

        return request;
    }

    public String getFirstname()
    {
        return firstname;
    }

    public void setFirstname(String firstname)
    {
        this.firstname = firstname;
    }

    public String getSurname()
    {
        return surname;
    }

    public void setSurname(String surname)
    {
        this.surname = surname;
    }

    public int getHeight()
    {
        return height;
    }

    public void setHeight(int height)
    {
        this.height = height;
    }

    public int getWeight()
    {
        return weight;
    }

    public void setWeight(int weight)
    {
        this.weight = weight;
    }
}
