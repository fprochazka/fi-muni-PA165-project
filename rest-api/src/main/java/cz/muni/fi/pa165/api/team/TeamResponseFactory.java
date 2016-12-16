package cz.muni.fi.pa165.api.team;

import cz.muni.fi.pa165.team.Team;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author Filip Prochazka <filip@prochazka.su>
 */
@Service
public class TeamResponseFactory
{

    public Collection<TeamResponse> getTeams(Collection<Team> teams)
    {
        return teams.stream()
            .map(this::getTeam)
            .collect(Collectors.toCollection(ArrayList::new));
    }

    public TeamResponse getTeam(Team team)
    {
        return new TeamResponse(team.getId(), team.getName());
    }

    class TeamResponse
    {

        public UUID id;

        public String name;

        TeamResponse(UUID id, String name)
        {
            this.id = id;
            this.name = name;
        }
    }

}
