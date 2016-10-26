package cz.muni.fi.pa165.user;

import cz.muni.fi.pa165.config.ApplicationConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * @author Libor Muhlpachr <libor.muhl@seznam.cz>
 */
@ContextConfiguration(classes=ApplicationConfig.class)
public class UserServiceTest extends AbstractTestNGSpringContextTests
{

    @Autowired
    public UserService userService;

    @Test
    public void testCreateUser() throws Exception
    {
        User user = userService.createUser("filip@prochazka.su", "heslo");
        assertEquals("filip@prochazka.su", user.getEmail());
        assertNotEquals("heslo", user.getPasswordHash());
    }

}
