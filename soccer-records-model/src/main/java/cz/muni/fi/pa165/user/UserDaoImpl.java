package cz.muni.fi.pa165.user;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.UUID;

/**
 * @author Filip Prochazka <filip@prochazka.su>
 */
@Repository
public class UserDaoImpl implements UserDao
{

    private EntityManager em;

    @PersistenceContext
    public void setEntityManager(final EntityManager entityManager){
        this.em = entityManager;
    }

    @Override
    public void create(User u)
    {
        em.persist(u);
    }

    @Override
    public User findUserByEmail(String email)
    {
        if (email == null || email.isEmpty()) {
            throw new IllegalArgumentException("Cannot search for null e-mail");
        }

        try {
            return em
                .createQuery("SELECT u FROM User u WHERE email = :email", User.class)
                .setParameter("email", email)
                .getSingleResult();

        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public User findById(UUID id)
    {
        return em.find(User.class, id);
    }

    @Override
    public List<User> findAll()
    {
        TypedQuery<User> query = em.createQuery("SELECT u FROM User u", User.class);
        return query.getResultList();
    }

}
