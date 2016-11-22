package cz.muni.fi.pa165.user;

import cz.muni.fi.pa165.user.exceptions.UserWithSameEmailIsAlreadyRegisteredException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.Assert;

/**
 * @author Filip Prochazka <filip@prochazka.su>
 */
public class UserService
{

    private PasswordEncoder passwordEncoder;

    public UserService(PasswordEncoder passwordEncoder)
    {
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Creates a user with hashed password.
     */
    public User createUser(
        String email,
        User sameEmailUser,
        String password
    )
    {
        Assert.notNull(password, "Cannot create user with null password");

        if (sameEmailUser != null) {
            throw new UserWithSameEmailIsAlreadyRegisteredException(sameEmailUser.getEmail());
        }

        String passwordHash = passwordEncoder.encode(password);
        return new User(email, passwordHash);
    }

    /**
     * Promotes user to admin.
     */
    public void promoteUserToAdmin(User user)
    {
        user.promoteToAdmin();
    }

    /**
     * Promotes user to moderator.
     */
    public void promoteUserToModerator(User user)
    {
        user.promoteToModerator();
    }
}
