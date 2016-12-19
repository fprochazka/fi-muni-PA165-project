package cz.muni.fi.pa165.config;

import cz.muni.fi.pa165.fixtures.sample.SampleDataConfig;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;


/**
 * @author Filip Procházka <filip@prochazka.su>
 */
@EnableWebMvc
@Configuration
@ComponentScan(basePackages = "cz.muni.fi.pa165")
@Import({ModelConfig.class, WebSecurityConfig.class, SampleDataConfig.class})
public class WebConfig extends WebMvcConfigurerAdapter
{

    /**
     * Maps the main page to a specific view.
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry)
    {
    }


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry)
    {
        registry
            .addResourceHandler("/resources/**").addResourceLocations("/resources/");
    }

    /**
     * Provides mapping from view names to JSP pages in WEB-INF/jsp directory.
     */
    @Bean
    public ViewResolver viewResolver()
    {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setViewClass(JstlView.class);
        viewResolver.setPrefix("/WEB-INF/jsp/");
        viewResolver.setSuffix(".jsp");
        viewResolver.setContentType("text/html; charset=UTF-8");
        return viewResolver;
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
