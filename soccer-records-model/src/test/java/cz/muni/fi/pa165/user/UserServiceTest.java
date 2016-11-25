package cz.muni.fi.pa165.user;

import cz.muni.fi.pa165.user.exceptions.UserWithSameEmailIsAlreadyRegisteredException;
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
        UserService userService = getUserService();

        User user = userService.createUser("filip@prochazka.su", null, "heslo");
        assertEquals("filip@prochazka.su", user.getEmail());
        assertNotEquals("heslo", user.getPasswordHash());
        assertEquals(UserRole.USER, user.getRole());
    }

    @Test
    public void testCreateUserWithAlreadyExistingUserWithSameEmailException() throws Exception
    {
        UserService userService = getUserService();

        User sameEmailUser = userService.createUser("filip@prochazka.su", null, "heslo");
        try {
            userService.createUser("filip@prochazka.su", sameEmailUser, "heslo");
            fail("Expected exception");

        } catch (UserWithSameEmailIsAlreadyRegisteredException e) {
            assertEquals("filip@prochazka.su", e.getEmail());
        }
    }

    @Test(
        expectedExceptions = IllegalArgumentException.class,
        expectedExceptionsMessageRegExp = "The given email and email of sameEmailUser do not match"
    )
    public void testCreateUserWithAlreadyExistingUserWithDifferentEmailException() throws Exception
    {
        UserService userService = getUserService();

        User sameEmailUser = userService.createUser("filip@prochazka.nejsu", null, "heslo");
        userService.createUser("filip@prochazka.su", sameEmailUser, "heslo");
    }

    private UserService getUserService()
    {
        return new UserService(new BCryptPasswordEncoder(13));
    }

}
