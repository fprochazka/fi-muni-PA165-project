package cz.muni.fi.pa165.team.match;

import cz.muni.fi.pa165.team.Team;
import cz.muni.fi.pa165.team.TeamPlayer;
import cz.muni.fi.pa165.team.match.exceptions.GoalWithSameParametersAlreadyExistsException;
import cz.muni.fi.pa165.team.match.exceptions.MatchTimeCollisionException;
import cz.muni.fi.pa165.team.match.exceptions.MatchWithSameParametersAlreadyExistsException;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Date;

/**
 * @author Tomas Smid <smid.thomas@gmail.com>
 */
@Service
public class TeamMatchService
{

    /**
     * This method creates new match if all needed parameters are valid.
     *
     * @param conflictingMatchForHomeTeam conflicting match with same start time and
     *                                    with teams from which one of them is homeTeam
     * @param conflictingMatchForAwayTeam conflicting match with same start time and
     *                                    with teams from which one of them is awayTeam
     * @param homeTeam                    home team of match which should be created
     * @param awayTeam                    away team of match which should be created
     * @param startTime                   start time of match which should be created
     * @param endTime                     end time of match which should be created
     * @return newly created match
     */
    public TeamMatch createMatch(
        TeamMatch conflictingMatchForHomeTeam,
        TeamMatch conflictingMatchForAwayTeam,
        Team homeTeam,
        Team awayTeam,
        Date startTime,
        Date endTime
    )
    {
        Assert.notNull(homeTeam, "Match cannot be created with a null home team");
        Assert.notNull(awayTeam, "Match cannot be created with a null away team");
        Assert.isTrue(!homeTeam.equals(awayTeam), "Match cannot be created for home and away teams which are same");

        if (isMatchReallyConflicting(conflictingMatchForHomeTeam, homeTeam, startTime)) {
            throw new MatchWithSameParametersAlreadyExistsException(homeTeam.getId(), startTime);
        }
        if (isMatchReallyConflicting(conflictingMatchForAwayTeam, awayTeam, startTime)) {
            throw new MatchWithSameParametersAlreadyExistsException(awayTeam.getId(), startTime);
        }

        return new TeamMatch(homeTeam, awayTeam, startTime, endTime);
    }

    /**
     * This method changes start time and end time of match if new times are valid.
     *
     * @param conflictingMatchForHomeTeam conflicting match with same start time and
     *                                    with teams from which one of them is home team
     *                                    of the given match
     * @param conflictingMatchForAwayTeam conflicting match with same start time and
     *                                    with teams from which one of them is away team
     *                                    of the given match
     * @param match                       match which start time and end time should be updated
     * @param startTime                   new start time of the match
     * @param endTime                     new end time of the match
     */
    public void changeMatchTime(
        TeamMatch conflictingMatchForHomeTeam,
        TeamMatch conflictingMatchForAwayTeam,
        TeamMatch match,
        Date startTime,
        Date endTime
    )
    {
        Assert.notNull(match, "Cannot change match times of null match");

        if (isMatchReallyConflicting(conflictingMatchForHomeTeam, match.getHomeTeam(), startTime)) {
            throw new MatchTimeCollisionException(
                match.getId(),
                conflictingMatchForHomeTeam.getId(),
                match.getHomeTeam().getId(),
                startTime
            );
        }

        if (isMatchReallyConflicting(conflictingMatchForAwayTeam, match.getAwayTeam(), startTime)) {
            throw new MatchTimeCollisionException(
                match.getId(),
                conflictingMatchForAwayTeam.getId(),
                match.getAwayTeam().getId(),
                startTime
            );
        }

        match.changeMatchTime(startTime, endTime);
    }

    /**
     * This method sets new end time to the match.
     *
     * @param match   match which end time should be updated
     * @param endTime new end time of the match
     */
    public void endMatch(TeamMatch match, Date endTime)
    {
        Assert.notNull(match, "Cannot end the null match");
        Assert.isNull(match.getEndTime(), "Cannot end already ended match");

        match.endMatch(endTime);
    }

    /**
     * This method creates new goal if all needed parameters are valid.
     *
     * @param scorer    scorer of the goal
     * @param assistant assistant of the goal
     * @param match     match in which the goal is scored
     * @param matchTime time of the match in which the goal is scored
     * @param sameGoal  goal which has same parameters as the goal which should
     *                  be newly created, so it is conflicting goal
     * @return newly created goal
     */
    public TeamMatchGoal addNewScoredGoal(
        TeamPlayer scorer,
        TeamPlayer assistant,
        TeamMatch match,
        Date matchTime,
        TeamMatchGoal sameGoal
    )
    {
        Assert.notNull(scorer, "Cannot create new goal with a null scorer");
        Assert.notNull(assistant, "Cannot create new goal with a null assistant");
        Assert.isTrue(!scorer.equals(assistant), "Cannot create new goal with scorer and assistant who are the same player");

        if (
            sameGoal != null
            && sameGoal.getMatch().equals(match)
            && sameGoal.getScorer().equals(scorer)
            && sameGoal.getAssistant().equals(assistant)
            && sameGoal.getMatchTime().equals(matchTime)
        ) {
            throw new GoalWithSameParametersAlreadyExistsException(
                scorer.getId(),
                assistant.getId(),
                match.getId(),
                matchTime
            );
        }

        return new TeamMatchGoal(scorer, assistant, match, matchTime);
    }

    private boolean isMatchReallyConflicting(TeamMatch conflictingMatch, Team team, Date startTime)
    {
        return (conflictingMatch != null
            && conflictingMatch.getStartTime().equals(startTime)
            && (conflictingMatch.getHomeTeam().equals(team) || conflictingMatch.getAwayTeam().equals(team)));
    }
}
