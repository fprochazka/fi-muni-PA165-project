package cz.muni.fi.pa165.user;

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

    public User createUser(String email, String password)
    {
        String passwordHash = passwordEncoder.encode(password);
        return new User(email, passwordHash);
    }
}
