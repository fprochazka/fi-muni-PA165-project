package cz.muni.fi.pa165.user.authentication;

import cz.muni.fi.pa165.user.User;
import cz.muni.fi.pa165.user.UserRepository;
import cz.muni.fi.pa165.user.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Filip Prochazka <filip@prochazka.su>
 */
@Service
public class PasswordAuthenticationFacade implements UserDetailsService
{

    private final UserRepository userRepository;

    @Autowired
    public PasswordAuthenticationFacade(UserRepository userRepository)
    {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException
    {
        User user = userRepository.findUserByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException(String.format(
                "User with email '%s' not found",
                username
            ));
        }

        return new org.springframework.security.core.userdetails.User(
            user.getEmail(),
            user.getPasswordHash(),
            true,
            true,
            true,
            true,
            buildUserAuthority(user.getRole())
        );
    }

    private List<GrantedAuthority> buildUserAuthority(UserRole userRole)
    {
        Set<GrantedAuthority> authorities = new HashSet<>();

        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));

        if (userRole == UserRole.MODERATOR) {
            authorities.add(new SimpleGrantedAuthority("ROLE_MODERATOR"));
        }

        if (userRole == UserRole.ADMIN) {
            authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        }

        return new ArrayList<>(authorities);
    }

}
