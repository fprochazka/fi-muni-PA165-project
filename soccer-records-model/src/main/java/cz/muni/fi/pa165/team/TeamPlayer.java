package cz.muni.fi.pa165.team;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.UUID;

/**
 * @author Libor Mühlpachr <libor.muhl@seznam.cz>
 */

@Entity
@Table(name = "players")
public class TeamPlayer {

    @Id
    @Column(nullable = false)
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
    private int age;

    @Column(nullable = false)
    @NotNull
    private int height;

    @Column(nullable = false)
    @NotNull
    private int weight;

    @Column(nullable = false)
    @NotNull
    private Team team;

    public TeamPlayer(String firstname, String surname, int age, int height, int weight, Team team)
    {
        this.id = UUID.randomUUID();
        this.firstname = firstname;
        this.surname = surname;
        this.age = age;
        this.height = height;
        this.weight = weight;
        this.team = team;
    }

    protected TeamPlayer() {}

    public UUID getId() {
        return id;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getSurname() {
        return surname;
    }

    public int getAge() { return age; }

    public int getHeight() { return height; }

    public int getWeight() { return weight; }

    public Team getTeam() { return team; }
}
