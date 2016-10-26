package cz.muni.fi.pa165.config;

import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.instrument.classloading.InstrumentationLoadTimeWeaver;
import org.springframework.instrument.classloading.LoadTimeWeaver;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;


/**
 * @author Filip Prochazka <filip@prochazka.su>
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories
public class PersistenceApplicationContext
{

    @Bean
    public PersistenceExceptionTranslationPostProcessor postProcessor()
    {
        return new PersistenceExceptionTranslationPostProcessor();
    }

    @Autowired
    @Bean
    public JpaTransactionManager transactionManager(final EntityManagerFactory entityManagerFactory)
    {
        return new JpaTransactionManager(entityManagerFactory);
    }

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

    @Bean
    public LoadTimeWeaver instrumentationLoadTimeWeaver()
    {
        return new InstrumentationLoadTimeWeaver();
    }

    @Bean
    public DataSource dataSource()
    {
        EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
        return builder.setType(EmbeddedDatabaseType.DERBY).build();
    }
}
