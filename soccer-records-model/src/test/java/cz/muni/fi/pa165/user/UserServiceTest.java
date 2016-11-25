package cz.muni.fi.pa165.user;

import cz.muni.fi.pa165.user.exceptions.UserWithSameEmailIsAlreadyRegisteredException;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.testng.annotations.Test;

import static org.mockito.Mockito.doReturn;
import static org.testng.Assert.*;

/**
 * @author Libor Muhlpachr <libor.muhl@seznam.cz>
 */
public class UserServiceTest
{

    @Test
    public void testCreateUser() throws Exception
    {
        PasswordEncoder passwordEncoder = mockPasswordEncoder();
        UserService userService = new UserService(passwordEncoder);

        doReturn("secret-hash").when(passwordEncoder)
            .encode("heslo");

        User user = userService.createUser("filip@prochazka.su", null, "heslo");
        assertEquals("filip@prochazka.su", user.getEmail());
        assertNotEquals("heslo", user.getPasswordHash());
        assertEquals(UserRole.USER, user.getRole());
    }

    @Test
    public void testCreateUserWithAlreadyExistingUserWithSameEmailException() throws Exception
    {
        PasswordEncoder passwordEncoder = mockPasswordEncoder();
        UserService userService = new UserService(passwordEncoder);

        doReturn("secret-hash").when(passwordEncoder)
            .encode("heslo");

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
        PasswordEncoder passwordEncoder = mockPasswordEncoder();
        UserService userService = new UserService(passwordEncoder);

        doReturn("secret-hash").when(passwordEncoder)
            .encode("heslo");

        User sameEmailUser = userService.createUser("filip@prochazka.nejsu", null, "heslo");
        userService.createUser("filip@prochazka.su", sameEmailUser, "heslo");
    }

    private PasswordEncoder mockPasswordEncoder()
    {
        return Mockito.mock(PasswordEncoder.class, new DefaultMockitoThrowingAnswer());
    }

    private class DefaultMockitoThrowingAnswer implements Answer<Object>
    {
        @Override
        public Object answer(InvocationOnMock invocation) throws Throwable
        {
            throw new UnsupportedOperationException(String.format(
                "Method \"%s\" not stubbed on %s",
                invocation.getMethod().getName(),
                invocation.getMock().getClass().getName()
            ));
        }
    }

}
