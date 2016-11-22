package cz.muni.fi.pa165.team;

import cz.muni.fi.pa165.team.exceptions.TeamWithSameNameAlreadyExistsException;

/**
 * @author Denis Galajda <galajda.denis@gmail.com>
 */
public class TeamService
{

    /**
     * Create a team.
     */
    public Team createTeam(String name, Team sameNameTeam)
    {
        if (sameNameTeam != null) {
            throw new TeamWithSameNameAlreadyExistsException(sameNameTeam.getName());
        }
        return new Team(name);
    }

    /**
     * Change a name of team.
     */
    public void changeTeamName(Team team, String name, Team sameNameTeam)
    {
        if (sameNameTeam != null && team != sameNameTeam) {
            throw new TeamWithSameNameAlreadyExistsException(sameNameTeam.getName());
        }
        team.changeName(name);
    }

}
