package cz.muni.fi.pa165.config;

import org.springframework.context.annotation.*;

/**
 * The main application context config.
 *
 * @author Filip Prochazka <filip@prochazka.su>
 */
@Configuration
@ComponentScan(basePackages = "cz.muni.fi.pa165")
@Import(PersistenceApplicationContext.class)
@ImportResource(locations = "application-context.xml")
public class ApplicationConfig
{

}
