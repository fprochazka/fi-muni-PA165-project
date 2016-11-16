package cz.muni.fi.pa165.hibernate;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.lang.reflect.Proxy;

/**
 * This EntityManager producer returns always a Proxy. All the EntityManager methods are wrapped by this proxy.
 * This ensures, that the real EntityManager is obtained/created at call time.
 *
 * @link {https://goo.gl/vJyoPs}
 */
@RequestScoped
public class ProxyEntityManager
{

    /**
     * Inject the default EntityManager, operated by a application container.
     */
    @PersistenceContext
    private EntityManager entityManager;

    /**
     * CDI Producer, joins JTA transaction and returns this EntityManager.
     */
    @Produces
    private EntityManager getEntityManager()
    {
        return (EntityManager) Proxy.newProxyInstance(
            this.getClass().getClassLoader(),
            new Class<?>[]{EntityManager.class},
            (proxy, method, args) -> {
                entityManager.joinTransaction();
                return method.invoke(entityManager, args);
            }
        );
    }

}
