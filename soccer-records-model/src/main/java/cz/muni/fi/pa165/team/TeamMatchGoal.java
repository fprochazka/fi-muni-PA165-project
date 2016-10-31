package cz.muni.fi.pa165.team;

import org.hibernate.annotations.Type;

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
public class TeamMatchGoal implements Comparable<TeamMatchGoal>
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
    TeamMatchGoal(TeamPlayer scorer, TeamPlayer assistant, TeamMatch match, Date matchTime)
    {
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

    TeamPlayer getScorer()
    {
        return scorer;
    }

    TeamPlayer getAssistant()
    {
        return assistant;
    }

    TeamMatch getMatch()
    {
        return match;
    }

    Date getMatchTime()
    {
        return matchTime == null ? null : new Date(matchTime.getTime());
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TeamMatchGoal)) {
            return false;
        }

        TeamMatchGoal that = (TeamMatchGoal) o;

        return id.equals(that.id);
    }

    @Override
    public int hashCode()
    {
        return id.hashCode();
    }

    @Override
    public int compareTo(TeamMatchGoal teamMatchGoal)
    {
        return this.id.compareTo(teamMatchGoal.id);
    }

}
