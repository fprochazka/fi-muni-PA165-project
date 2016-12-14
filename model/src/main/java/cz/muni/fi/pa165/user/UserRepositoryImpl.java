package cz.muni.fi.pa165.user;

import cz.muni.fi.pa165.user.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.Collection;
import java.util.UUID;

/**
 * @author Filip Prochazka <filip@prochazka.su>
 */
@Repository
public class UserRepositoryImpl implements UserRepository
{

    private final EntityManager entityManager;

    @Autowired
    public UserRepositoryImpl(EntityManager entityManager)
    {
        this.entityManager = entityManager;
    }

    @Override
    public User findUserByEmail(final String email)
    {
        Assert.notNull(email, "Cannot search for null e-mail");
        Assert.hasLength(email, "Cannot search for empty e-mail");

        try {
            return entityManager
                .createQuery("SELECT u FROM User u WHERE email = :email", User.class)
                .setParameter("email", email)
                .getSingleResult();

        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public User getUserById(final UUID userId)
    {
        Assert.notNull(userId, "Cannot search for a null userId");

        try {
            return entityManager
                .createQuery("SELECT u FROM User u WHERE u.id = :userId", User.class)
                .setParameter("userId", userId)
                .getSingleResult();

        } catch (NoResultException e) {
            throw new UserNotFoundException(userId, e);
        }
    }

    @Override
    public Collection<User> findUsersByRole(final UserRole role)
    {
        Assert.notNull(role, "Cannot search for a null role");

        TypedQuery<User> query = entityManager
            .createQuery("SELECT u FROM User u WHERE u.role = :role", User.class)
            .setParameter("role", role);
        return query.getResultList();
    }

    @Override
    public Collection<User> findAllUsers()
    {
        TypedQuery<User> query = entityManager.createQuery("SELECT u FROM User u", User.class);
        return query.getResultList();
    }

}
