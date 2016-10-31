package cz.muni.fi.pa165.team;

import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.util.UUID;

/**
 * This entity class represents the team entity.
 *
 * @author Denis Galajda <galajda.denis@gmail.com>
 */
@Entity
public class Team
{

    @Id
    @Column(nullable = false, length = 36)
    @Type(type = "uuid-char")
    @NotNull
    private UUID id;

    @Column(nullable = false, unique = true)
    @NotNull
    private String name;

    /**
     * Constructor which takes 1 parameter for attributes set up.
     *
     * @param name name of the team
     */
    public Team(String name)
    {
        this.id = UUID.randomUUID();
        this.name = name;
    }

    /**
     * No-args constructor
     *
     * @deprecated Hibernate internal
     */
    protected Team()
    {
    }

    public UUID getId()
    {
        return id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
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

        Team that = (Team) o;

        return id.equals(that.id);
    }

    @Override
    public int hashCode()
    {
        return id.hashCode();
    }
}
