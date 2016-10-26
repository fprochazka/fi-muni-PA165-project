package cz.muni.fi.pa165.config;

import org.springframework.context.annotation.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@ComponentScan(basePackages = "cz.muni.fi.pa165")
@Import(PersistenceApplicationContext.class)
@ImportResource(locations = "application-context.xml")
public class ApplicationConfig
{

}
