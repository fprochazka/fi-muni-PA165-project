package cz.muni.fi.pa165.team;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * @author Denis Galajda <galajda.denis@gmail.com>
 */
public class TeamDetailsRequest
{

    @NotEmpty
    public String name;

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    /**
     * Fill DTO from Team entity.
     */
    public static TeamDetailsRequest fromTeam(Team team)
    {
        TeamDetailsRequest request = new TeamDetailsRequest();
        request.setName(team.getName());
        return request;
    }

}
