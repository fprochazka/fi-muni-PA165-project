package cz.muni.fi.pa165.user;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * @author Libor Muhlpachr <libor.muhl@seznam.cz>
 */
public class UserServiceTest
{

    @Test
    public void testCreateUser() throws Exception
    {
        UserService userService = new UserService(new BCryptPasswordEncoder(13));

        User user = userService.createUser("filip@prochazka.su", "heslo");
        assertEquals("filip@prochazka.su", user.getEmail());
        assertNotEquals("heslo", user.getPasswordHash());
    }

}
