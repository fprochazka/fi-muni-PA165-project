package cz.muni.fi.pa165.team;

import org.hibernate.annotations.Type;
import org.springframework.util.Assert;

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
        Assert.notNull(firstname, "Cannot create teamPlayer without firstname.");
        Assert.notNull(surname, "Cannot create teamPlayer without surname.");
        Assert.notNull(height, "Cannot create teamPlayer without height.");
        Assert.isTrue(height > 0, "Height must be a positive integer");
        Assert.notNull(weight, "Cannot create teamPlayer without weight.");
        Assert.isTrue(weight > 0, "Weight must be a positive integer");
        Assert.notNull(team, "Cannot create teamPlayer without team.");

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

    /**
     * This method changes player's firstname.
     *
     * @param firstname player's firstname
     */
    public void changeFirstname(String firstname)
    {
        Assert.notNull(firstname, "Cannot change firstname to null.");

        this.firstname = firstname;
    }

    public String getSurname()
    {
        return surname;
    }

    /**
     * This method changes player's surname.
     *
     * @param surname player's surname
     */
    public void changeSurname(String surname)
    {
        Assert.notNull(surname, "Cannot change surname to null.");

        this.surname = surname;
    }

    public int getHeight()
    {
        return height;
    }

    /**
     * This method changes player's height.
     *
     * @param height player's height
     */
    public void changeHeight(int height)
    {
        Assert.notNull(height, "Cannot change height to null.");
        Assert.isTrue(height > 0, "Height must be a positive integer");

        this.height = height;
    }

    public int getWeight()
    {
        return weight;
    }

    /**
     * This method changes player's weight.
     *
     * @param weight player's weight
     */
    public void changeWeight(int weight)
    {
        Assert.notNull(weight, "Cannot change weight to null.");
        Assert.isTrue(weight > 0, "Weight must be a positive integer");

        this.weight = weight;
    }

    public Team getTeam()
    {
        return team;
    }
}
