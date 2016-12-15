package cz.muni.fi.pa165.team.match;

import cz.muni.fi.pa165.team.TeamPlayer;
import org.hibernate.annotations.Type;
import org.springframework.util.Assert;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.UUID;

/**
 * This entity class represents the goal entity.
 *
 * @author Tomas Smid <smid.thomas@gmail.com>
 */
@Entity
public class TeamMatchGoal
{

    @Id
    @Column(nullable = false)
    @Type(type = "uuid-char")
    @NotNull
    private UUID id;

    @ManyToOne
    @NotNull
    @JoinColumn(nullable = false)
    private TeamPlayer scorer;

    @ManyToOne
    @NotNull
    @JoinColumn(nullable = false)
    private TeamPlayer assistant;

    @ManyToOne
    @NotNull
    @JoinColumn(nullable = false)
    private TeamMatch match;

    @Column(nullable = false)
    @NotNull
    private Date matchTime;

    /**
     * First constructor which takes 4 parameters for attributes set up.
     *
     * @param scorer    scorer of the goal
     * @param assistant assistant of the goal
     * @param match     match in which the goal was scored
     * @param matchTime time in which the goal was scored
     */
    public TeamMatchGoal(TeamPlayer scorer, TeamPlayer assistant, TeamMatch match, Date matchTime)
    {
        Assert.notNull(match, "Cannot create goal for a null match");
        Assert.notNull(scorer, "Cannot create goal for a null scorer");
        Assert.notNull(assistant, "Cannot create goal for a null assistant");
        validateGoalMatchTime(matchTime, match);

        this.id = UUID.randomUUID();
        this.scorer = scorer;
        this.assistant = assistant;
        this.match = match;
        this.matchTime = matchTime == null ? null : new Date(matchTime.getTime());
    }

    /**
     * Second constructor which does not take any parameter.
     *
     * @deprecated Hibernate internal
     */
    protected TeamMatchGoal()
    {

    }

    public UUID getId()
    {
        return id;
    }

    public TeamPlayer getScorer()
    {
        return scorer;
    }

    public TeamPlayer getAssistant()
    {
        return assistant;
    }

    public TeamMatch getMatch()
    {
        return match;
    }

    public Date getMatchTime()
    {
        return matchTime == null ? null : new Date(matchTime.getTime());
    }

    private void validateGoalMatchTime(Date matchTime, TeamMatch match)
    {
        Assert.notNull(matchTime, "Given a null goal match time");
        Assert.isTrue(matchTime.after(match.getStartTime()), "Goal match time is not after match start time");
        Assert.isTrue(match.getEndTime() == null || matchTime.before(match.getEndTime()),
            "Goal match time is not before match end time");
    }

}
