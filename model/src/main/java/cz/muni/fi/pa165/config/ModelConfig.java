package cz.muni.fi.pa165.config;

import org.hsqldb.jdbc.JDBCDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

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

    private static final Logger log = LoggerFactory.getLogger(ModelConfig.class);

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
        jpaFactoryBean.setPackagesToScan("cz.muni.fi.pa165");

        HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
        jpaVendorAdapter.setDatabase(Database.HSQL);
        jpaVendorAdapter.setShowSql(true);
        jpaFactoryBean.setJpaVendorAdapter(jpaVendorAdapter);

        Properties hibernateProperties = new Properties();
        String schemaCreateDisabled = System.getenv("PA165_SCHEMA_CREATE_DISABLED");
        if (schemaCreateDisabled == null || !schemaCreateDisabled.equals("1")) {
            log.info("Creating database schema from entities");
            hibernateProperties.put("hibernate.hbm2ddl.auto", "create");
        } else {
            log.info("Skipping database schema creation");
            hibernateProperties.put("hibernate.hbm2ddl.auto", "validate");
        }
        jpaFactoryBean.setJpaProperties(hibernateProperties);
        jpaFactoryBean.afterPropertiesSet();

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
        String externalDatabase = System.getenv("PA165_EXTERNAL_HSQL");
        if (externalDatabase != null && externalDatabase.equals("1")) {
            log.info("Using external HSQL database");

            JDBCDataSource remoteDatasource = new JDBCDataSource();
            remoteDatasource.setUrl("jdbc:hsqldb:hsql://database:9001/pa165");
            remoteDatasource.setUser("sa");
            remoteDatasource.setPassword("");
            return remoteDatasource;

        } else {
            log.info("Using embeded HSQL database");

            EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
            return builder.setType(EmbeddedDatabaseType.HSQL).build();
        }
    }

}
