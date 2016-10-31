package cz.muni.fi.pa165.team;

import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

/**
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
    private TeamPlayer scorer;

    @ManyToOne
    @NotNull
    private TeamPlayer assistant;

    @ManyToOne
    @NotNull
    private TeamMatch match;

    @Column(nullable = false)
    @NotNull
    private Date matchTime;

    public TeamMatchGoal(TeamPlayer scorer, TeamPlayer assistant, TeamMatch match, Date matchTime)
    {
        this.id = UUID.randomUUID();
        this.scorer = scorer;
        this.assistant = assistant;
        this.match = match;
        this.matchTime = matchTime == null ? null : new Date(matchTime.getTime());
    }

    /**
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

    @Override
    public boolean equals(Object obj)
    {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof TeamMatchGoal)) {
            return false;
        }
        TeamMatchGoal goal = (TeamMatchGoal) obj;
        return ((this.id == goal.id) ||
            (this.id != null && this.id.equals(goal.id)));
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
