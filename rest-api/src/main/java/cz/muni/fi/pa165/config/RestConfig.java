package cz.muni.fi.pa165.config;

import cz.muni.fi.pa165.fixtures.sample.SampleDataConfig;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;


/**
 * @author Filip Procházka <filip@prochazka.su>
 */
@EnableWebMvc
@Configuration
@ComponentScan(basePackages = "cz.muni.fi.pa165")
@Import({ModelConfig.class, SampleDataConfig.class})
public class RestConfig extends WebMvcConfigurerAdapter
{

    /**
     * Maps the main page to a specific view.
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry)
    {
    }

    /**
     * Provides localized messages.
     */
    @Bean
    public MessageSource messageSource()
    {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasenames(
            "messages/messages",
            "messages/validation"
        );
        return messageSource;
    }

}
