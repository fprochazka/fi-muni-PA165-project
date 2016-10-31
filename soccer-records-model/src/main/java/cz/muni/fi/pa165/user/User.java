package cz.muni.fi.pa165.user;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.UUID;

/**
 * @author Filip Prochazka <filip@prochazka.su>
 */
@Entity
public class User
{

    @Id
    @Column(nullable = false, length = 36)
    @Type(type = "uuid-char")
    @NotNull
    private UUID id;

    @Column(nullable = false)
    @NotNull
    private String passwordHash;

    @Column(nullable = false, unique = true)
    @NotNull
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @NotNull
    private UserRole role;

    public User(String email, String passwordHash)
    {
        this.id = UUID.randomUUID();
        this.role = UserRole.USER;
        this.email = email;
        this.passwordHash = passwordHash;
    }

    /**
     * @deprecated Hibernate internal
     */
    protected User()
    {
    }

    public UUID getId()
    {
        return id;
    }

    public String getEmail()
    {
        return email;
    }

    public UserRole getRole()
    {
        return role;
    }

    public String getPasswordHash()
    {
        return passwordHash;
    }

    /**
     * Change the user role to Admin
     */
    public void promoteToAdmin()
    {
        this.role = UserRole.ADMIN;
    }

    /**
     * Change the user role to Moderator
     */
    public void promoteToModerator()
    {
        this.role = UserRole.MODERATOR;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) {
            return true;
        }
        if (!(o instanceof User)) {
            return false;
        }

        User user = (User) o;

        return getId().equals(user.getId());

    }

    @Override
    public int hashCode()
    {
        return id.hashCode();
    }
}
