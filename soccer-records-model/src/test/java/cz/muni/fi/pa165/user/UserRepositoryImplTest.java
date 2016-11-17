package cz.muni.fi.pa165.user;

import cz.muni.fi.pa165.config.ApplicationConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.annotations.Test;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.Collection;

import static org.testng.Assert.*;

/**
 * @author Libor Muhlpachr <libor.muhl@seznam.cz>
 */
@ContextConfiguration(classes=ApplicationConfig.class)
public class UserRepositoryImplTest extends AbstractTransactionalTestNGSpringContextTests
{

    @Autowired
    public UserRepositoryImpl userRepository;

    @Autowired
    public UserService userService;

    @PersistenceContext
    public EntityManager em;

    @Test
    public void testProxies() throws Exception
    {
        User user1 = userService.createUser("libor.muhl+1@gmail.com", null, "secret");
        em.persist(user1);
        em.flush();
        em.clear();

        User userRef = em.getReference(User.class, user1.getId());
        User userInitialized = em.find(User.class, user1.getId());
        assertTrue(userRef == userInitialized);
    }

    @Test
    public void testFindAll() throws Exception
    {
        Collection<User> noUsers = userRepository.findAllUsers();
        assertEquals(noUsers.size(), 0);

        User user1 = userService.createUser("libor.muhl+1@gmail.com", null, "secret");
        User user2 = userService.createUser("libor.muhl+2@gmail.com", null, "secret");

        em.persist(user1);
        em.persist(user2);
        em.flush();

        Collection<User> allUsers = userRepository.findAllUsers();
        assertEquals(allUsers.size(), 2);

        assertTrue(allUsers.stream().filter(u -> u.getEmail().equals("libor.muhl+1@gmail.com")).count() == 1);
        assertTrue(allUsers.stream().filter(u -> u.getEmail().equals("libor.muhl+2@gmail.com")).count() == 1);
    }

    @Test
    public void testFindUsersByRole() throws Exception
    {
        User user1 = userService.createUser("libor.muhl+1@gmail.com", null, "secret");
        User user2 = userService.createUser("libor.muhl+2@gmail.com", null, "secret");
        user2.promoteToAdmin();

        em.persist(user1);
        em.persist(user2);
        em.flush();

        Collection<User> regularUsers = userRepository.findUsersByRole(UserRole.USER);
        assertEquals(regularUsers.size(), 1);
        assertTrue(regularUsers.stream().filter(u -> u.getEmail().equals("libor.muhl+1@gmail.com")).count() == 1);

        Collection<User> adminUsers = userRepository.findUsersByRole(UserRole.ADMIN);
        assertEquals(adminUsers.size(), 1);
        assertTrue(adminUsers.stream().filter(u -> u.getEmail().equals("libor.muhl+2@gmail.com")).count() == 1);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testFindUserByRoleRequiresRole() throws Exception
    {
        userRepository.findUsersByRole(null);
    }

    @Test
    public void testFindById() throws Exception
    {
        User user1 = userService.createUser("libor.muhl+1@gmail.com", null, "secret");

        em.persist(user1);
        em.flush();

        User foundUser = userRepository.getUserById(user1.getId());
        assertEquals("libor.muhl+1@gmail.com", foundUser.getEmail());
    }

    @Test
    public void testFindUserByEmail() throws Exception
    {
        User user1 = userService.createUser("libor.muhl+1@gmail.com", null, "secret");
        User user2 = userService.createUser("libor.muhl+2@gmail.com", null, "secret");

        em.persist(user1);
        em.persist(user2);
        em.flush();

        User foundUser = userRepository.findUserByEmail("libor.muhl+1@gmail.com");
        assertEquals("libor.muhl+1@gmail.com", foundUser.getEmail());
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testFindUserByEmailRequiresString() throws Exception
    {
        userRepository.findUserByEmail(null);
    }

}
