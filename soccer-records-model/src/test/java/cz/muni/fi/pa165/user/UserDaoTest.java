package cz.muni.fi.pa165.user;

import cz.muni.fi.pa165.config.ApplicationConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.annotations.Test;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.List;

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
    public void testFindAll() throws Exception
    {
        User user1 = userService.createUser("filip+1@prochazka.su", "secret");
        User user2 = userService.createUser("filip+2@prochazka.su", "secret");

        userDao.create(user1);
        userDao.create(user2);

        List<User> categories  = userDao.findAll();
        assertEquals(categories.size(), 2);

        assertTrue(categories.stream().filter(u -> u.getEmail().equals("filip+1@prochazka.su")).count() == 1);
        assertTrue(categories.stream().filter(u -> u.getEmail().equals("filip+2@prochazka.su")).count() == 1);
    }

    @Test
    public void testFindById() throws Exception
    {
        User user1 = userService.createUser("filip+1@prochazka.su", "secret");

        userDao.create(user1);

        User foundUser = userDao.findById(user1.getId());
        assertEquals("filip+1@prochazka.su", foundUser.getEmail());
    }

    @Test
    public void testFindUserByEmail() throws Exception
    {
        User user1 = userService.createUser("filip+1@prochazka.su", "secret");
        User user2 = userService.createUser("filip+2@prochazka.su", "secret");

        userDao.create(user1);
        userDao.create(user2);

        User foundUser = userDao.findUserByEmail("filip+1@prochazka.su");
        assertEquals("filip+1@prochazka.su", foundUser.getEmail());
    }

}
