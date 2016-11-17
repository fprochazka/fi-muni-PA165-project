package cz.muni.fi.pa165.user;

import javax.persistence.EntityManager;
import java.util.UUID;

/**
 * @author Filip Prochazka <filip@prochazka.su>
 */
public class UserFacade
{

    private UserService userService;

    private UserRepository userRepository;

    private EntityManager entityManager;

    public UserFacade(
        UserService userService,
        UserRepository userRepository,
        EntityManager entityManager
    )
    {
        this.userService = userService;
        this.userRepository = userRepository;
        this.entityManager = entityManager;
    }

    /**
     * Create and save the user.
     */
    public User createUser(String email, String password)
    {
        User sameEmailUser = userRepository.findUserByEmail(email);

        User user = userService.createUser(
            email,
            sameEmailUser,
            password
        );

        entityManager.persist(user);
        entityManager.flush();

        return user;
    }

    /**
     * Promote user to admin and persist.
     */
    public void promoteUserToAdmin(UUID userId)
    {
        User user = userRepository.getUserById(userId);

        userService.promoteUserToAdmin(user);

        entityManager.flush();
    }

    /**
     * Promote user to moderator and persist.
     */
    public void promoteUserToModerator(UUID userId)
    {
        User user = userRepository.getUserById(userId);

        userService.promoteUserToModerator(user);

        entityManager.flush();
    }

}
