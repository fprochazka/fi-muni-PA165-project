package cz.muni.fi.pa165.team;

import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.UUID;

/**
 * @author Libor Mühlpachr <libor.muhl@seznam.cz>
 */

@Entity
@Table(name = "players")
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

        TeamPlayer teamPlayer = (TeamPlayer) o;

        return getId().equals(teamPlayer.getId());
    }

    @Override
    public int hashCode()
    {
        return id.hashCode();
    }

}
