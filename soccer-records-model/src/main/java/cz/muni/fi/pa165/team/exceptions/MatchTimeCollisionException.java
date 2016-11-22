package cz.muni.fi.pa165.team.exceptions;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * @author Tomas Smid <smid.thomas@gmail.com>
 */
public class MatchTimeCollisionException extends RuntimeException
{
    private final UUID matchId;

    private final String homeTeam;

    private final String awayTeam;

    private final Date startTime;

    public MatchTimeCollisionException(
        UUID matchId,
        String homeTeam,
        String awayTeam,
        Date startTime
    )
    {
        super(String.format("There cannot be changed the start time of the match " +
                "%s with home team %s and away team %s to new value %s, because " +
                "there already exists another match with same start time in which " +
                "one of these teams plays",
            matchId.toString(),
            homeTeam,
            awayTeam,
            new SimpleDateFormat("dd/MM/YYYY hh:mm:ss").format(startTime)));

        this.matchId = matchId;
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.startTime = new Date(startTime.getTime());
    }
}
