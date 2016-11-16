package cz.muni.fi.pa165.user;

import cz.muni.fi.pa165.user.exceptions.UserCannotBePromotedToRoleExceptions;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.fail;

/**
 * @author Filip Prochazka <filip@prochazka.su>
 */
public class UserTest
{

    @Test
    public void testPromoteToAdmin()
    {
        User user = getUserService().createUser("filip@prochazka.su", null, "heslo");

        assertEquals(UserRole.USER, user.getRole());
        user.promoteToAdmin();
        assertEquals(UserRole.ADMIN, user.getRole());
    }

    @Test()
    public void testCannotPromoteAdminToAdminException()
    {
        User user = getUserService().createUser("filip@prochazka.su", null, "heslo");

        user.promoteToAdmin();

        try {
            user.promoteToAdmin();
            fail("Expected exception");

        } catch (UserCannotBePromotedToRoleExceptions e) {
            assertEquals(UserRole.ADMIN, e.getNewRole());
            assertEquals(user, e.getUser());
        }
    }

    @Test
    public void testPromoteToModerator()
    {
        User user = getUserService().createUser("filip@prochazka.su", null, "heslo");

        assertEquals(UserRole.USER, user.getRole());
        user.promoteToModerator();
        assertEquals(UserRole.MODERATOR, user.getRole());
    }

    @Test()
    public void testCannotPromoteModeratorToModeratorException()
    {
        User user = getUserService().createUser("filip@prochazka.su", null, "heslo");

        user.promoteToModerator();

        try {
            user.promoteToModerator();
            fail("Expected exception");

        } catch (UserCannotBePromotedToRoleExceptions e) {
            assertEquals(UserRole.MODERATOR, e.getNewRole());
            assertEquals(user, e.getUser());
        }
    }

    private UserService getUserService()
    {
        return new UserService(new BCryptPasswordEncoder(13));
    }

}
