package cz.muni.fi.pa165.team.player;

import cz.muni.fi.pa165.team.TeamPlayer;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author Libor Muhlpachr <libor.muhl@seznam.cz>
 */
public class TeamPlayerRequest
{

    @NotBlank
    @Size(max = 255)
    public String firstname;

    @NotBlank
    @Size(max = 255)
    public String surname;

    @NotNull
    @Min(1)
    public Integer height;

    @NotNull
    @Min(1)
    public Integer weight;

    /**
     * Creates the request object from TeamPlayer entity
     */
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

    public Integer getHeight()
    {
        return height;
    }

    public void setHeight(Integer height)
    {
        this.height = height;
    }

    public Integer getWeight()
    {
        return weight;
    }

    public void setWeight(Integer weight)
    {
        this.weight = weight;
    }
}
