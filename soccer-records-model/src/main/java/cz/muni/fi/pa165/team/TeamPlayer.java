package cz.muni.fi.pa165.team;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.UUID;

/**
 * This entity class represents the Player entity.
 *
 * @author Libor Mühlpachr <libor.muhl@seznam.cz>
 */
@Entity
public class TeamPlayer
{

    @Id
    @Column(nullable = false, length = 36)
    @Type(type = "uuid-char")
    @NotNull
    private UUID id;

    @Column(nullable = false)
    @NotNull
    private String firstname;

    @Column(nullable = false)
    @NotNull
    private String surname;

    @Column(nullable = false)
    @NotNull
    private int height;

    @Column(nullable = false)
    @NotNull
    private int weight;

    @NotNull
    @ManyToOne
    @JoinColumn(nullable = false)
    private Team team;

    /**
     * This constructor takes 5 parameters.
     *
     * @param firstname player's firstname
     * @param surname   player's surname
     * @param height    player's height
     * @param weight    player's weight
     * @param team      player's team
     */
    public TeamPlayer(String firstname, String surname, int height, int weight, Team team)
    {
        this.id = UUID.randomUUID();
        this.firstname = firstname;
        this.surname = surname;
        this.height = height;
        this.weight = weight;
        this.team = team;
    }

    /**
     * This constructor doesn't take any parameters.
     *
     * @deprecated Hibernate internal
     */
    protected TeamPlayer()
    {

    }

    public UUID getId()
    {
        return id;
    }

    public String getFirstname()
    {
        return firstname;
    }

    public String getSurname()
    {
        return surname;
    }

    public int getHeight()
    {
        return height;
    }

    public int getWeight()
    {
        return weight;
    }

    public Team getTeam()
    {
        return team;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TeamPlayer)) {
            return false;
        }

        TeamPlayer that = (TeamPlayer) o;

        return id.equals(that.getId());
    }

    @Override
    public int hashCode()
    {
        return id.hashCode();
    }
}
