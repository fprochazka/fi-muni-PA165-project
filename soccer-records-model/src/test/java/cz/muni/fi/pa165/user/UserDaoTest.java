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
public class UserDaoTest extends AbstractTransactionalTestNGSpringContextTests
{

    @Autowired
    public UserDao userDao;

    @Autowired
    public UserService userService;

    @PersistenceContext
    public EntityManager em;

    @Test
    public void testDelete() throws Exception
    {
        User user1 = userService.createUser("libor.muhl+1@gmail.com", "secret");
        userDao.createUser(user1);
        em.flush();

        userDao.deleteUser(user1);
        em.flush();

        assertNull(userDao.findUserById(user1.getId()));
    }

    @Test
    public void testFindAll() throws Exception
    {
        Collection<User> noUsers = userDao.findAllUsers();
        assertEquals(noUsers.size(), 0);

        User user1 = userService.createUser("libor.muhl+1@gmail.com", "secret");
        User user2 = userService.createUser("libor.muhl+2@gmail.com", "secret");

        userDao.createUser(user1);
        userDao.createUser(user2);

        em.flush();

        Collection<User> allUsers = userDao.findAllUsers();
        assertEquals(allUsers.size(), 2);

        assertTrue(allUsers.stream().filter(u -> u.getEmail().equals("libor.muhl+1@gmail.com")).count() == 1);
        assertTrue(allUsers.stream().filter(u -> u.getEmail().equals("libor.muhl+2@gmail.com")).count() == 1);
    }

    @Test
    public void testFindUsersByRole() throws Exception
    {
        User user1 = userService.createUser("libor.muhl+1@gmail.com", "secret");
        User user2 = userService.createUser("libor.muhl+2@gmail.com", "secret");
        user2.promoteToAdmin();

        userDao.createUser(user1);
        userDao.createUser(user2);

        em.flush();

        Collection<User> regularUsers = userDao.findUsersByRole(UserRole.USER);
        assertEquals(regularUsers.size(), 1);
        assertTrue(regularUsers.stream().filter(u -> u.getEmail().equals("libor.muhl+1@gmail.com")).count() == 1);

        Collection<User> adminUsers = userDao.findUsersByRole(UserRole.ADMIN);
        assertEquals(adminUsers.size(), 1);
        assertTrue(adminUsers.stream().filter(u -> u.getEmail().equals("libor.muhl+2@gmail.com")).count() == 1);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testFindUserByRoleRequiresRole() throws Exception
    {
        userDao.findUsersByRole(null);
    }

    @Test
    public void testFindById() throws Exception
    {
        User user1 = userService.createUser("libor.muhl+1@gmail.com", "secret");

        userDao.createUser(user1);

        User foundUser = userDao.findUserById(user1.getId());
        assertEquals("libor.muhl+1@gmail.com", foundUser.getEmail());
    }

    @Test
    public void testFindUserByEmail() throws Exception
    {
        User user1 = userService.createUser("libor.muhl+1@gmail.com", "secret");
        User user2 = userService.createUser("libor.muhl+2@gmail.com", "secret");

        userDao.createUser(user1);
        userDao.createUser(user2);

        User foundUser = userDao.findUserByEmail("libor.muhl+1@gmail.com");
        assertEquals("libor.muhl+1@gmail.com", foundUser.getEmail());
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testFindUserByEmailRequiresString() throws Exception
    {
        userDao.findUserByEmail(null);
    }

}
