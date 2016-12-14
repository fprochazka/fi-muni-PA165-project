package cz.muni.fi.pa165.team.match.exceptions;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * @author Tomas Smid <smid.thomas@gmail.com>
 */
public class MatchWithSameParametersAlreadyExistsException extends RuntimeException
{

    private final UUID teamId;

    private final Date startTime;

    public MatchWithSameParametersAlreadyExistsException(UUID teamId, Date startTime)
    {
        super(String.format("There already exists match with team %s with start time %s",
            teamId.toString(),
            new SimpleDateFormat("dd/MM/YYYY hh:mm:ss").format(startTime)));

        this.teamId = teamId;
        this.startTime = new Date(startTime.getTime());
    }

    public UUID getTeamId()
    {
        return teamId;
    }

    public Date getStartTime()
    {
        return new Date(startTime.getTime());
    }
}
