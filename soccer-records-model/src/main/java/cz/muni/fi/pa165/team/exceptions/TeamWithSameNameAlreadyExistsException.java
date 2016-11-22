package cz.muni.fi.pa165.team.exceptions;

/**
 * @author Denis Galajda <galajda.denis@gmail.com>
 */
public class TeamWithSameNameAlreadyExistsException extends RuntimeException
{

    private final String teamName;

    public TeamWithSameNameAlreadyExistsException(String teamName)
    {
        super(String.format("Team with name %s already exists", teamName));
        this.teamName = teamName;
    }

    public String getTeamName()
    {
        return teamName;
    }
}
