package cz.muni.fi.pa165.team.match;

import cz.muni.fi.pa165.team.Team;
import org.hibernate.annotations.Type;
import org.springframework.util.Assert;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
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
    private LocalDateTime startTime;

    /**
     * nullable=true is the default setting, could be missed, but mentioned for better overview
     */
    @Column(nullable = true)
    private LocalDateTime endTime;

    /**
     * First constructor which takes 3 parameters for attributes set up and
     * also sets the match end time to null.
     *
     * @param homeTeam  home team in the match
     * @param awayTeam  away team in the match
     * @param startTime start time of the match
     */
    public TeamMatch(Team homeTeam, Team awayTeam, LocalDateTime startTime)
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
    public TeamMatch(Team homeTeam, Team awayTeam, LocalDateTime startTime, LocalDateTime endTime)
    {
        Assert.notNull(homeTeam, "Cannot create match with a null home team");
        Assert.notNull(awayTeam, "Cannot create match with a null away team");
        validateMatchTimes(startTime, endTime);

        this.id = UUID.randomUUID();
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.startTime = startTime;
        this.endTime = endTime;
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

    public LocalDateTime getStartTime()
    {
        return startTime;
    }

    public LocalDateTime getEndTime()
    {
        return endTime;
    }

    /**
     * This method enables change the match start and end time.
     *
     * @param startTime new start time of the match, cannot be null
     * @param endTime   new end time of the match, can be null
     */
    public void changeMatchTime(LocalDateTime startTime, LocalDateTime endTime)
    {
        validateMatchTimes(startTime, endTime);

        this.startTime = startTime;
        this.endTime = endTime;
    }

    /**
     * Ends the match, respectively sets the new end time of the match.
     *
     * @param endTime new end time of the match, cannot be null
     * @param lastGoalTime time in the match of the last scored goal
     */
    public void endMatch(LocalDateTime endTime, LocalDateTime lastGoalTime)
    {
        Assert.notNull(endTime, "Cannot end the match with a null end time");

        validateMatchTimes(this.startTime, endTime);

        if(lastGoalTime != null){
            Assert.isTrue(endTime.isAfter(lastGoalTime), "Match end time is not after last match scored goal time");
        }

        this.endTime = endTime;
    }

    private void validateMatchTimes(LocalDateTime startTime, LocalDateTime endTime)
    {
        Assert.notNull(startTime, "Match start time is null");
        Assert.isTrue((endTime == null || endTime.isAfter(startTime)), "Match end time is not after start time");
    }
}
