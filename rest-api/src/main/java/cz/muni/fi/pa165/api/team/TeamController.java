package cz.muni.fi.pa165.api.team;

import cz.muni.fi.pa165.api.exceptions.ResourceNotFoundException;
import cz.muni.fi.pa165.team.Team;
import cz.muni.fi.pa165.team.TeamRepository;
import cz.muni.fi.pa165.team.exceptions.TeamNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Collection;
import java.util.HashMap;
import java.util.UUID;

/**
 * @author Filip Prochazka <filip@prochazka.su>
 */
@RestController
public class TeamController
{

    public static final String TEAM_NOT_FOUND = "team-not-found";

    private final TeamRepository teamRepository;
    private final TeamResponseFactory teamResponseFactory;

    @Autowired
    public TeamController(
        TeamRepository teamRepository,
        TeamResponseFactory teamResponseFactory
    )
    {
        this.teamRepository = teamRepository;
        this.teamResponseFactory = teamResponseFactory;
    }

    @RequestMapping(value = "/teams", method = RequestMethod.GET)
    public ResponseEntity<Object> viewTeams()
    {
        Collection<Team> teams = teamRepository.findAll();

        return new ResponseEntity<>(new HashMap<String, Object>()
        {{
            put("teams", teamResponseFactory.getTeams(teams));
        }}, null, HttpStatus.OK);
    }

    @RequestMapping(value = "/teams/{teamId}", method = RequestMethod.GET)
    public ResponseEntity<Object> viewTeam(
        @PathVariable @Valid UUID teamId
    )
    {
        try {
            Team team = teamRepository.getTeamById(teamId);

            return new ResponseEntity<>(new HashMap<String, Object>()
            {{
                put("team", teamResponseFactory.getTeam(team));
            }}, null, HttpStatus.OK);

        } catch (TeamNotFoundException e) {
            throw new ResourceNotFoundException(TEAM_NOT_FOUND, "Team not found", e);
        }
    }

}
