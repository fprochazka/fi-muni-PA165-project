package cz.muni.fi.pa165.user;

import java.util.List;
import java.util.UUID;

/**
 * @author Filip Prochazka <filip@prochazka.su>
 */
public interface UserDao
{

    /**
     * Persists the given user
     */
    void create(User u);

    /**
     * Finds user by his id
     */
    User findById(UUID id);

    /**
     * Finds user by his email
     */
    User findUserByEmail(String email);

    /**
     * Finds all users
     */
    List<User> findAll();

}
