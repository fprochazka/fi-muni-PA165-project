package cz.muni.fi.pa165.team;

import cz.muni.fi.pa165.team.exceptions.GoalWithSameParametersAlreadyExistsException;
import cz.muni.fi.pa165.team.exceptions.MatchTimeCollisionException;
import cz.muni.fi.pa165.team.exceptions.MatchWithSameParametersAlreadyExistsException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author Tomas Smid <smid.thomas@gmail.com>
 */
public class TeamMatchService
{

    public TeamMatchService(){

    }

    public TeamMatch createMatch(
        List<TeamMatch> sameHomeTeamMatches,
        Team homeTeam,
        Team awayTeam,
        Date startTime,
        Date endTime
    )
    {
        if (awayTeam == null){
            throw new IllegalArgumentException("Match cannot be created with a null away team");
        }

        if (homeTeam.equals(awayTeam)){
            throw new IllegalArgumentException("Match cannot be created for home and away teams which are same");
        }

        validateMatchTimes(startTime, endTime);

        if(!sameHomeTeamMatches.isEmpty())
        {
            for(TeamMatch tm : sameHomeTeamMatches)
            {
                if (awayTeam.equals(tm.getAwayTeam()) &&
                        startTime.equals(tm.getStartTime()))
                {
                    throw new MatchWithSameParametersAlreadyExistsException(
                        homeTeam.getName(),
                        awayTeam.getName(),
                        startTime
                    );
                }
            }
        }

        return new TeamMatch(homeTeam,awayTeam,startTime,endTime);
    }

    public void changeMatchTime(
        List<TeamMatch> allMatches,
        TeamMatch match,
        String strStartTime,
        String strEndTime
    )
    {

        Date startTime = getProperDate(strStartTime, "start");
        Date endTime = getProperDate(strEndTime, "end");

        validateMatchTimes(startTime, endTime);

        for (TeamMatch tm : allMatches){
            if (startTime.equals(tm.getStartTime()) &&
                (match.getHomeTeam().equals(tm.getHomeTeam()) ||
                    match.getAwayTeam().equals(tm.getAwayTeam())))
            {
                throw new MatchTimeCollisionException(
                    match.getId(),
                    match.getHomeTeam().getName(),
                    match.getAwayTeam().getName(),
                    startTime);
            }
        }

        match.changeMatchTime(startTime, endTime);
    }

    public void endMatch(TeamMatch match, Date endTime){

        if (endTime == null){
            throw new IllegalArgumentException("Cannot end the match with a null end time");
        }

        validateMatchTimes(match.getStartTime(),endTime);

        match.endMatch(endTime);
    }

    public TeamMatchGoal addNewScoredGoal(
        List<TeamMatchGoal> goalsInActualMatch,
        TeamPlayer scorer,
        TeamPlayer assistant,
        TeamMatch match,
        Date matchTime
    )
    {
        if (scorer == null){
            throw new IllegalArgumentException("Cannot create new goal with a null scorer");
        }

        if (assistant == null){
            throw new IllegalArgumentException("Cannot create new goal with a null assistant");
        }

        if (scorer.equals(assistant)){
            throw new IllegalArgumentException("Cannot create new goal with scorer and assistant who are the same player");
        }

        validateGoalMatchTime(matchTime,match);

        if (!goalsInActualMatch.isEmpty())
        {
            for(TeamMatchGoal tmg : goalsInActualMatch)
            {
                if(tmg.getScorer().equals(scorer) && tmg.getAssistant().equals(assistant)
                    && tmg.getMatchTime().equals(matchTime))
                {
                    throw new GoalWithSameParametersAlreadyExistsException(
                        String.format("%s %s",scorer.getFirstname(),scorer.getSurname()),
                        String.format("%s %s",assistant.getFirstname(),assistant.getSurname()),
                        match.getId(),
                        matchTime
                    );
                }
            }
        }

        return new TeamMatchGoal(scorer,assistant,match,matchTime);
    }

    private Date getProperDate(String date, String whatDate){

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/YYYY");
        formatter.setLenient(false);
        Date properDate = null;

        if (date != null){
            try {
                properDate = formatter.parse(date);
            } catch (ParseException ex) {
                throw new IllegalArgumentException(
                    String.format("Cannot change match %s time, because of its invalid format",whatDate)
                );
            }
        }

        return properDate;
    }

    private void validateMatchTimes(Date startTime, Date endTime){

        if(startTime == null){
            throw new IllegalArgumentException("Match start time is null");
        }

        if(endTime != null && !endTime.after(startTime)){
            throw new IllegalArgumentException("Match end time is not after start time");
        }
    }

    private void validateGoalMatchTime(Date matchTime, TeamMatch match){

        if (matchTime == null){
            throw new IllegalArgumentException("Cannot create new goal with a null match time");
        }

        if (matchTime.before(match.getStartTime())){
            throw new IllegalArgumentException("Cannot create new goal with invalid match time " +
                "- goal match time is before match start time");
        }

        if (match.getEndTime() != null && matchTime.after(match.getEndTime())){
            throw new IllegalArgumentException("Cannot create new goal with invalid match time " +
                "- goal match time is after match end time");
        }
    }
}
