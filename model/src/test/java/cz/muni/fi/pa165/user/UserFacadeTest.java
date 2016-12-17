package cz.muni.fi.pa165.user;

import cz.muni.fi.pa165.config.ModelConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.annotations.Test;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

/**
 * @author Filip Prochazka <filip@prochazka.su>
 */
@ContextConfiguration(classes= ModelConfig.class)
public class UserFacadeTest extends AbstractTransactionalTestNGSpringContextTests
{

    @Autowired
    public UserFacade userFacade;

    @Autowired
    public UserService userService;

    @PersistenceContext
    public EntityManager em;

    @Test
    public void testCreateUser() throws Exception
    {
        User createdUser = userFacade.createUser("filip@prochazka.su", "heslo");
        em.clear();

        User foundUser = em.find(User.class, createdUser.getId());
        assertNotNull(foundUser);
        assertEquals("filip@prochazka.su", foundUser.getEmail());
    }

    @Test
    public void testPromoteUserToModerator() throws Exception
    {
        User user = userService.createUser("filip@prochazka.su", null, "heslo");
        em.persist(user);
        em.flush();
        em.clear();

        User returnedUser = userFacade.promoteUserToModerator(user.getId());
        assertEquals(user.getId(), returnedUser.getId());
        assertEquals(UserRole.MODERATOR, returnedUser.getRole());
        em.clear();

        User promotedUser = em.find(User.class, user.getId());
        assertEquals(UserRole.MODERATOR, promotedUser.getRole());
    }

    @Test
    public void testPromoteUserToAdmin() throws Exception
    {
        User user = userService.createUser("filip@prochazka.su", null, "heslo");
        em.persist(user);
        em.flush();
        em.clear();

        User returnedUser = userFacade.promoteUserToAdmin(user.getId());
        assertEquals(user.getId(), returnedUser.getId());
        assertEquals(UserRole.ADMIN, returnedUser.getRole());
        em.clear();

        User promotedUser = em.find(User.class, user.getId());
        assertEquals(UserRole.ADMIN, promotedUser.getRole());
    }

}
