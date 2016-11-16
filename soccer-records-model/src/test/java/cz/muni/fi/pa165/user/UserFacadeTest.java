package cz.muni.fi.pa165.user;

import cz.muni.fi.pa165.config.ApplicationConfig;
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
@ContextConfiguration(classes=ApplicationConfig.class)
public class UserFacadeTest extends AbstractTransactionalTestNGSpringContextTests
{

    @Autowired
    public UserRepository userRepository;

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

        User foundUser = userRepository.getUserById(createdUser.getId());
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

        userFacade.promoteUserToModerator(user.getId());
        em.clear();

        User promotedUser = userRepository.getUserById(user.getId());
        assertEquals(UserRole.MODERATOR, promotedUser.getRole());
    }

    @Test
    public void testPromoteUserToAdmin() throws Exception
    {
        User user = userService.createUser("filip@prochazka.su", null, "heslo");
        em.persist(user);
        em.flush();
        em.clear();

        userFacade.promoteUserToAdmin(user.getId());
        em.clear();

        User promotedUser = userRepository.getUserById(user.getId());
        assertEquals(UserRole.ADMIN, promotedUser.getRole());
    }

}
