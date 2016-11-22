package cz.muni.fi.pa165.team.exceptions;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * @author Tomas Smid <smid.thomas@gmail.com>
 */
public class MatchWithSameParametersAlreadyExistsException extends RuntimeException
{

    private final String homeTeam;

    private final String awayTeam;

    private final Date startTime;

    public MatchWithSameParametersAlreadyExistsException(
        String homeTeam,
        String awayTeam,
        Date startTime
    )
    {
        super(String.format("Match with home team %s, away team %s and " +
            "start time %s already exists",
            homeTeam,
            awayTeam,
            new SimpleDateFormat("dd/MM/YYYY hh:mm:ss").format(startTime)));

        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.startTime = new Date(startTime.getTime());
    }

    public String getHomeTeam()
    {
        return homeTeam;
    }

    public String getAwayTeam()
    {
        return awayTeam;
    }

    public Date getStartTime()
    {
        return new Date(startTime.getTime());
    }
}
