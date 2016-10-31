package cz.muni.fi.pa165.team;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

/**
 * @author Tomas Smid <smid.thomas@gmail.com>
 */
@Entity
public class TeamMatch implements Comparable<TeamMatch>
{

    @Id
    @Column(nullable = false)
    @Type(type = "uuid-char")
    @NotNull
    private UUID id;

    @NotNull
    @ManyToOne
    private Team homeTeam;

    @NotNull
    @ManyToOne
    @JoinColumn(nullable = false)
    private Team awayTeam;

    @Column(nullable = false)
    @NotNull
    private Date startTime;

    @Column(nullable = true)//it is the default setting, could be missed, but mentioned for better overview
    private Date endTime;

    public TeamMatch(Team homeTeam, Team awayTeam, Date startTime)
    {
        this(homeTeam, awayTeam, startTime, null);
    }

    public TeamMatch(Team homeTeam, Team awayTeam, Date startTime, Date endTime)
    {
        this.id = UUID.randomUUID();
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.startTime = startTime == null ? null : new Date(startTime.getTime());
        this.endTime = endTime == null ? null : new Date(endTime.getTime());
    }

    /**
     * @deprecated Hibernate internal
     */
    protected TeamMatch()
    {

    }

    public UUID getId()
    {
        return id;
    }

    public Team getHomeTeam()
    {
        return homeTeam;
    }

    public Team getAwayTeam()
    {
        return awayTeam;
    }

    public Date getStartTime()
    {
        return startTime == null ? null : new Date(startTime.getTime());
    }

    public void setStartTime(Date startTime)
    {
        this.startTime = (startTime == null ? null : new Date(startTime.getTime()));
    }

    public Date getEndTime()
    {
        return endTime == null ? null : new Date(endTime.getTime());
    }

    public void setEndTime(Date endTime)
    {
        this.endTime = (endTime == null ? null : new Date(endTime.getTime()));
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof TeamMatch)) {
            return false;
        }
        TeamMatch match = (TeamMatch) obj;
        return ((this.id == match.id) ||
            (this.id != null && this.id.equals(match.id)));
    }

    @Override
    public int hashCode()
    {
        return id.hashCode();
    }

    @Override
    public int compareTo(TeamMatch teamMatch)
    {
        return this.id.compareTo(teamMatch.id);
    }

}
