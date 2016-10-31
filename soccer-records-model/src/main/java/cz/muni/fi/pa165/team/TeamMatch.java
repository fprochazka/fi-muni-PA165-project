package cz.muni.fi.pa165.team;

import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.persistence.JoinColumn;
import java.util.Date;
import java.util.UUID;

/**
 * This entity class represents the match entity.
 *
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
    @JoinColumn(nullable = false)
    private Team homeTeam;

    @NotNull
    @ManyToOne
    @JoinColumn(nullable = false)
    private Team awayTeam;

    @Column(nullable = false)
    @NotNull
    private Date startTime;

    /** nullable=true is the default setting, could be missed, but mentioned for better overview */
    @Column(nullable = true)
    private Date endTime;

    /**
     * First constructor which takes 3 parameters for attributes set up and
     * also sets the match end time to null.
     *
     * @param homeTeam  home team in the match
     * @param awayTeam  away team in the match
     * @param startTime start time of the match
     */
    public TeamMatch(Team homeTeam, Team awayTeam, Date startTime)
    {
        this(homeTeam, awayTeam, startTime, null);
    }

    /**
     * Second constructor which takes 4 parameters for attributes set up.
     *
     * @param homeTeam  home team in the match
     * @param awayTeam  away team in the match
     * @param startTime start time of the match
     * @param endTime   end time of the match
     */
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
    public boolean equals(Object o)
    {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TeamMatch)) {
            return false;
        }

        TeamMatch that = (TeamMatch) o;

        return id.equals(that.id);
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
