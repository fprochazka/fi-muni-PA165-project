package cz.muni.fi.pa165.team.exceptions;

import java.util.UUID;

/**
 * @author Tomas Smid <smid.thomas@gmail.com>
 */
public class GoalNotFoundException extends RuntimeException
{

    private final UUID goalId;

    public GoalNotFoundException(final UUID goalId, Throwable cause){
        super(String.format("Goal %s not found", goalId.toString()), cause);
        this.goalId = goalId;
    }

    public UUID getGoalId()
    {
        return goalId;
    }
}
