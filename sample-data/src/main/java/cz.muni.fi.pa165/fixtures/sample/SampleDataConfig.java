package cz.muni.fi.pa165.fixtures.sample;

import cz.muni.fi.pa165.config.ModelConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import javax.annotation.PostConstruct;

/**
 * @author Tomas Smid <smid.thomas@gmail.com>
 */
@Configuration
@Import(ModelConfig.class)
@ComponentScan(basePackages = "cz.muni.fi.pa165.*")
public class SampleDataConfig
{

    private static final Logger log = LoggerFactory.getLogger(SampleDataConfig.class);

    @Autowired
    private SampleDataFixture sampleDataFixture;

    /**
     * Loads the sample data fixtures when application boots.
     */
    @PostConstruct
    public void dataLoading()
    {
        String fixturesDisabled = System.getenv("PA165_DATA_FIXTURES_DISABLED");

        if (fixturesDisabled == null || !fixturesDisabled.equals("1")) {
            log.info("Loading data fixtures");
            sampleDataFixture.loadData();
        } else {
            log.info("No data fixtures will be loaded");
        }
    }

}
