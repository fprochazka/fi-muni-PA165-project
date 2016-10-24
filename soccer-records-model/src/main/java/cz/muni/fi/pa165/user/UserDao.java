package cz.muni.fi.pa165.user;

import java.util.List;
import java.util.UUID;

/**
 * @author Filip Prochazka <filip@prochazka.su>
 */
public interface UserDao
{
    public void create(User u);
    public User findById(UUID id);
    public User findUserByEmail(String email);
    public List<User> findAll();

}
