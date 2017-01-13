package cz.muni.fi.pa165.team;

import org.hibernate.validator.constraints.NotEmpty;

import java.util.UUID;

/**
 * @author Denis Galajda <galajda.denis@gmail.com>
 */
public class TeamDetailsRequest
{

    @NotEmpty
    private String name;

    private UUID id;

    public String getName()
    {
        return name;
    }

    public UUID getId()
    {
        return id;
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
        request.id = team.getId();
        return request;
    }

}
