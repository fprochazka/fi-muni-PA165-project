package cz.muni.fi.pa165.config;

import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.instrument.classloading.InstrumentationLoadTimeWeaver;
import org.springframework.instrument.classloading.LoadTimeWeaver;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

/**
 * The main application context config.
 *
 * @author Filip Prochazka <filip@prochazka.su>
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories
@ComponentScan(basePackages = "cz.muni.fi.pa165.*")
public class ModelConfig
{

    /**
     * This is the central class for javax.validation (JSR-303) setup in a Spring application context:
     * It bootstraps a javax.validation.ValidationFactory and exposes it through the Spring Validator interface
     * as well as through the JSR-303 Validator interface and the ValidatorFactory interface itself.
     */
    @Bean
    public LocalValidatorFactoryBean validatorFactory()
    {
        return new LocalValidatorFactoryBean();
    }

    /**
     * Implementation of PasswordEncoder that uses the BCrypt strong hashing function.
     */
    @Bean
    public PasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder(13);
    }

    /**
     * Binds a JPA EntityManager from the specified factory to the thread, potentially allowing for one thread-bound EntityManager per factory.
     */
    @Autowired
    @Bean
    public JpaTransactionManager transactionManager(final EntityManagerFactory entityManagerFactory)
    {
        return new JpaTransactionManager(entityManagerFactory);
    }

    /**
     * FactoryBean that creates a JPA EntityManagerFactory according to JPA's standard container bootstrap contract.
     */
    @Autowired
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(
        final DataSource dataSource,
        final LoadTimeWeaver loadTimeWeaver
    )
    {
        LocalContainerEntityManagerFactoryBean jpaFactoryBean = new LocalContainerEntityManagerFactoryBean();
        jpaFactoryBean.setDataSource(dataSource);
        jpaFactoryBean.setLoadTimeWeaver(loadTimeWeaver);
        jpaFactoryBean.setPersistenceProviderClass(HibernatePersistenceProvider.class);
        return jpaFactoryBean;
    }

    /**
     * Defines the contract for adding one or more ClassFileTransformers to a ClassLoader.
     */
    @Bean
    public LoadTimeWeaver instrumentationLoadTimeWeaver()
    {
        return new InstrumentationLoadTimeWeaver();
    }

    /**
     * A factory for connections to the physical data source that this DataSource object represents.
     */
    @Bean
    public DataSource dataSource()
    {
        EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
        return builder.setType(EmbeddedDatabaseType.HSQL).build();
    }


}
