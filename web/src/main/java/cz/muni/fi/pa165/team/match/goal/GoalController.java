package cz.muni.fi.pa165.team.match.goal;

import cz.muni.fi.pa165.response.RedirectResponse;
import cz.muni.fi.pa165.team.TeamPlayerRepository;
import cz.muni.fi.pa165.team.TeamRepository;
import cz.muni.fi.pa165.team.match.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.UUID;

/**
 * @author Tomas Smid <smid.thomas@gmail.com>
 */
@Controller
public class GoalController
{
    private final TeamMatchFacade teamMatchFacade;

    private final TeamRepository teamRepository;

    private final TeamPlayerRepository teamPlayerRepository;

    private final TeamMatchRepository teamMatchRepository;

    @Autowired
    public GoalController(
        TeamMatchFacade teamMatchFacade,
        TeamRepository teamRepository,
        TeamPlayerRepository teamPlayerRepository,
        TeamMatchRepository teamMatchRepository
    )
    {
        this.teamMatchFacade = teamMatchFacade;
        this.teamRepository = teamRepository;
        this.teamPlayerRepository = teamPlayerRepository;
        this.teamMatchRepository = teamMatchRepository;
    }

    @InitBinder("goalRequest")
    private void initBinder(WebDataBinder binder)
    {
        binder.setValidator(new GoalController.GoalCreationValidator());
    }

    @RequestMapping(value = "/match/{mid}/team/{tid}/goal/create", method = RequestMethod.GET)
    public ModelAndView viewNewGoal(
        @PathVariable("mid") UUID matchId,
        @PathVariable("tid") UUID teamId
    )
    {
        return new ModelAndView("team/match/goal/create")
            .addObject("players", teamPlayerRepository.findTeamPlayerByTeam(teamRepository.getTeamById(teamId)))
            .addObject("match", teamMatchRepository.getMatchById(matchId))
            .addObject("goalRequest", new GoalRequest());
    }

    @RequestMapping(value = "/match/{mid}/team/{tid}/goal/create", method = RequestMethod.POST)
    public ModelAndView submitNewGoal(
        @PathVariable("mid") UUID matchId,
        @PathVariable("tid") UUID teamId,
        @ModelAttribute("goalRequest") @Validated GoalRequest goalRequest,
        BindingResult result
    )
    {
        if (result.hasErrors()) {
            return new ModelAndView("team/match/goal/create")
                .addObject("players", teamPlayerRepository.findTeamPlayerByTeam(teamRepository.getTeamById(teamId)))
                .addObject("match", teamMatchRepository.getMatchById(matchId))
                .addObject("goalRequest", goalRequest);
        }

        teamMatchFacade.addNewScoredGoal(
            goalRequest.getScorerId(),
            goalRequest.getAssistantId(),
            matchId,
            goalRequest.getMatchTime()
        );

        return new RedirectResponse("/match/" + matchId);
    }

    @RequestMapping(value = "/match/{mid}/goal/{gid}/delete", method = RequestMethod.POST)
    public ModelAndView deleteGoal(@PathVariable("mid") UUID matchId, @PathVariable("gid") UUID goalId)
    {
        teamMatchFacade.deleteMatchGoal(goalId);

        return new RedirectResponse("/match/" + matchId);
    }

    /**
     * Validates goal form data.
     */
    class GoalCreationValidator implements Validator
    {

        @Override
        public boolean supports(Class type)
        {
            return GoalRequest.class.isAssignableFrom(type);
        }

        @Override
        public void validate(Object target, Errors errors)
        {
            GoalRequest request = (GoalRequest) target;

            if (request.getScorerId() == null){
                errors.rejectValue("scorerId", "goalform.scorernotspecified");
            }

            if (request.getAssistantId() == null){
                errors.rejectValue("assistantId", "goalform.assistantnotspecified");
            }

            if (request.getScorerId() != null && request.getAssistantId() != null
                && request.getScorerId().equals(request.getAssistantId()))
            {
                errors.rejectValue("assistantId", "goalform.sameplayers");
            }
        }

    }
}
