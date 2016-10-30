package cz.muni.fi.pa165.team;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.UUID;

/**
 * @author Libor Mühlpachr
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

    public TeamPlayer(String firstname, String surname) {
        this.id = UUID.randomUUID();
        this.firstname = firstname;
        this.surname = surname;
    }

    public TeamPlayer() {
    }

    public UUID getId() {
        return id;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getSurname() {
        return surname;
    }

}
