package cz.muni.fi.pa165.team.exceptions;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * @author Tomas Smid <smid.thomas@gmail.com>
 */
public class MatchWithSameParametersAlreadyExistsException extends RuntimeException
{

    private final UUID homeTeamId;

    private final UUID awayTeamId;

    private final Date startTime;

    public MatchWithSameParametersAlreadyExistsException(UUID homeTeamId, UUID awayTeamId, Date startTime)
    {
        super(String.format("Match with home team %s, away team %s and start time %s already exists",
            homeTeamId.toString(),
            awayTeamId.toString(),
            new SimpleDateFormat("dd/MM/YYYY hh:mm:ss").format(startTime)));

        this.homeTeamId = homeTeamId;
        this.awayTeamId = awayTeamId;
        this.startTime = new Date(startTime.getTime());
    }

    public UUID getHomeTeamId()
    {
        return homeTeamId;
    }

    public UUID getAwayTeamId()
    {
        return awayTeamId;
    }

    public Date getStartTime()
    {
        return new Date(startTime.getTime());
    }
}
