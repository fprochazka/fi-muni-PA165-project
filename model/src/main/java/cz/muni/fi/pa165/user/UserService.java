package cz.muni.fi.pa165.user;

import cz.muni.fi.pa165.user.exceptions.UserWithSameEmailIsAlreadyRegisteredException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * @author Filip Prochazka <filip@prochazka.su>
 */
@Service
public class UserService
{

    private final PasswordEncoder passwordEncoder;

    @Autowired
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
        Assert.notNull(email, "Cannot create user with null email");
        Assert.notNull(password, "Cannot create user with null password");
        Assert.isTrue(
            sameEmailUser == null || email.equals(sameEmailUser.getEmail()),
            "The given email and email of sameEmailUser do not match"
        );

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
