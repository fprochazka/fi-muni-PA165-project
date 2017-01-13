package cz.muni.fi.pa165.team.teamPlayer;

import cz.muni.fi.pa165.team.Team;
import cz.muni.fi.pa165.team.TeamPlayer;
import org.hibernate.validator.constraints.NotEmpty;

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

    @NotEmpty
    public Team team;

    public static TeamPlayerRequest fromTeamPlayer(TeamPlayer teamPlayer)
    {
        TeamPlayerRequest request = new TeamPlayerRequest();

        request.setFirstname(teamPlayer.getFirstname());
        request.setSurname(teamPlayer.getSurname());
        request.setHeight(teamPlayer.getHeight());
        request.setWeight(teamPlayer.getWeight());
        request.setTeam(teamPlayer.getTeam());

        return request;
    }

    public String getFirstname()
    {
        return firstname;
    }

    public void setFirstname()
    {
        this.firstname = firstname;
    }

    public String getSurname()
    {
        return surname;
    }

    public void setSurname()
    {
        this.surname = surname;
    }

    public int getHeight()
    {
        return height;
    }

    public void setHeight()
    {
        this.height = height;
    }

    public int getWeight()
    {
        return weight;
    }

    public void setWeight()
    {
        this.weight = weight;
    }

    public Team getTeam()
    {
        return team;
    }

    public void setTeam()
    {
        this.team = team;
    }
}
