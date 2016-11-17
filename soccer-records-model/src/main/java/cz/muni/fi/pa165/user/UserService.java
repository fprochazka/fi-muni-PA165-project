package cz.muni.fi.pa165.user;

import cz.muni.fi.pa165.user.exceptions.UserWithSameEmailIsAlreadyRegisteredException;
import org.springframework.security.crypto.password.PasswordEncoder;

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
