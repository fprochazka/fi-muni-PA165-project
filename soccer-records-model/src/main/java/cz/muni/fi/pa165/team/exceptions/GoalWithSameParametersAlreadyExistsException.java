package cz.muni.fi.pa165.team.exceptions;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * @author Tomas Smid <smid.thomas@gmail.com>
 */
public class GoalWithSameParametersAlreadyExistsException extends  RuntimeException
{

    private final String scorer;

    private final String assistant;

    private final UUID matchId;

    private final Date matchTime;

    public GoalWithSameParametersAlreadyExistsException(
        String scorer,
        String assistant,
        UUID matchId,
        Date matchTime
    )
    {
        super(String.format("Goal from match %s scored by %s with assistant %s " +
                "and scored in match time %s already exists",
            matchId.toString(),
            scorer,
            assistant,
            new SimpleDateFormat("dd/MM/YYYY hh:mm:ss").format(matchTime)));

        this.scorer = scorer;
        this.assistant = assistant;
        this.matchId = matchId;
        this.matchTime = new Date(matchTime.getTime());
    }

    public String getScorer()
    {
        return scorer;
    }

    public String getAssistant()
    {
        return assistant;
    }

    public UUID getMatchId()
    {
        return matchId;
    }

    public Date getMatchTime()
    {
        return new Date(matchTime.getTime());
    }
}
