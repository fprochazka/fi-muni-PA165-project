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
public class TeamMatch
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

    /**
     * nullable=true is the default setting, could be missed, but mentioned for better overview
     */
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

    public Date getEndTime()
    {
        return endTime == null ? null : new Date(endTime.getTime());
    }

    /**
     * This method enables change the match start and end time.
     *
     * @param startTime new start time of the match, cannot be null
     * @param endTime new end time of the match, can be null
     */
    public void changeMatchTime(Date startTime, Date endTime){
        if(startTime == null){
            throw new IllegalArgumentException("Cannot change the match start time to null");
        }

        this.startTime = new Date(startTime.getTime());
        this.endTime = (endTime == null ? null : new Date(endTime.getTime()));
    }

    /**
     * Ends the match, respectively sets the new end time of the match.
     *
     * @param endTime new end time of the match, cannot be null
     */
    public void endMatch(Date endTime){
        if(endTime == null){
            throw new IllegalArgumentException("Cannot end the match with a null end time");
        }

        this.endTime = new Date(endTime.getTime());
    }
}
