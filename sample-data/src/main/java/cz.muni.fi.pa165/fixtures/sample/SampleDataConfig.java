package cz.muni.fi.pa165.fixtures.sample;

import cz.muni.fi.pa165.config.ModelConfig;
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

    @Autowired
    private SampleDataFixture sampleDataFixture;

    /**
     * Loads the sample data fixtures when application boots.
     */
    @PostConstruct
    public void dataLoading()
    {
        sampleDataFixture.loadData();
    }

}
