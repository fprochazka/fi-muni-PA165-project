package cz.muni.fi.pa165.team;

import java.util.UUID;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import org.hibernate.annotations.Type;

/**
 *
 * @author Denis Galajda
 */
@Entity
@Table(name = "team")
public class Team
{

    @Id
    @Column(nullable = false)
    @Type(type="uuid-char")
    @NotNull
    private UUID id;

    @Column(nullable = false, unique = true)
    @NotNull
    private String name;

    public Team(String name)
    {
        this.id = UUID.randomUUID();
        this.name = name;
    }


    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Team)) {
            return false;
        }

        Team team = (Team) o;

        return getId().equals(team.getId());

    }

    @Override
    public int hashCode()
    {
        return getId().hashCode();
    }
}
