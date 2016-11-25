package cz.muni.fi.pa165.user;

import cz.muni.fi.pa165.user.exceptions.UserCannotBePromotedToRoleExceptions;
import org.hibernate.annotations.Type;
import org.springframework.util.Assert;

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
        Assert.notNull(email, "Cannot create user with null e-mail");
        Assert.notNull(passwordHash, "Cannot create user with null password");

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
        if (!this.role.allowTransition(UserRole.ADMIN)) {
            throw new UserCannotBePromotedToRoleExceptions(this, UserRole.ADMIN);
        }

        this.role = UserRole.ADMIN;
    }

    /**
     * Change the user role to Moderator
     */
    public void promoteToModerator()
    {
        if (!this.role.allowTransition(UserRole.MODERATOR)) {
            throw new UserCannotBePromotedToRoleExceptions(this, UserRole.MODERATOR);
        }

        this.role = UserRole.MODERATOR;
    }

}
