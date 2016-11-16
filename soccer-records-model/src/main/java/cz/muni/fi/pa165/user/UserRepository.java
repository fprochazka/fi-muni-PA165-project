package cz.muni.fi.pa165.user;

import java.util.Collection;
import java.util.UUID;

/**
 * @author Filip Prochazka <filip@prochazka.su>
 */
public interface UserRepository
{

    /**
     * Finds user by his id
     */
    User getUserById(final UUID id);

    /**
     * Finds user by his email
     */
    User findUserByEmail(final String email);

    /**
     * Finds users by given role
     */
    Collection<User> findUsersByRole(final UserRole role);

    /**
     * Finds all users
     */
    Collection<User> findAllUsers();

}
