package cz.muni.fi.pa165.user;

import java.util.Collection;
import java.util.UUID;

/**
 * @author Filip Prochazka <filip@prochazka.su>
 */
public interface UserDao
{

    /**
     * Persists the given user
     */
    void createUser(User u);

    /**
     * Persists the given user
     */
    void deleteUser(User u);

    /**
     * Finds user by his id
     */
    User findUserById(UUID id);

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
