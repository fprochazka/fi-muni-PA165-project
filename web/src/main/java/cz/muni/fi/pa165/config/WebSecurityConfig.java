package cz.muni.fi.pa165.config;

import cz.muni.fi.pa165.user.authentication.PasswordAuthenticationFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.web.filter.CharacterEncodingFilter;

/**
 * @author Filip Prochazka <filip@prochazka.su>
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter
{

    @Autowired
    private PasswordAuthenticationFacade passwordAuthenticationFacade;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    protected void configure(HttpSecurity http) throws Exception
    {
        http
            .authorizeRequests()
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/register").permitAll()
                .antMatchers("/resources/**").permitAll()
                .antMatchers("/match/create", "/match/{id}/delete", "/match/{id}/edit").hasAnyRole("ADMIN", "MODERATOR")
                .antMatchers("/match/{mid}/team/{tid}/goal/create", "/match/{mid}/goal/{gid}/delete").hasAnyRole("ADMIN", "MODERATOR")
                .antMatchers("/team/create").access("hasRole('ADMIN') or hasRole('MODERATOR')")
                .antMatchers("/team/*/edit").access("hasRole('ADMIN') or hasRole('MODERATOR')")
                .antMatchers("/team/*/delete").access("hasRole('ADMIN') or hasRole('MODERATOR')")
                .anyRequest().authenticated()
                .and()
            .formLogin()
                .loginPage("/sign-in")
                .defaultSuccessUrl("/")
                .usernameParameter("email")
                .permitAll()
                .and()
            .logout()
                .logoutUrl("/sign-out")
                .permitAll()
                .and()
            .csrf()
                .and()
            .exceptionHandling()
                .accessDeniedPage("/403");

        CharacterEncodingFilter encodingFilter = new CharacterEncodingFilter();
        encodingFilter.setEncoding("UTF-8");
        encodingFilter.setForceEncoding(true);
        http.addFilterBefore(encodingFilter, CsrfFilter.class);
    }

    /**
     * Binds PasswordAuthenticationFacade to AuthenticationManagerBuilder.
     * @throws Exception if userDetailsService is not accepted
     */
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception
    {
        auth
            .userDetailsService(passwordAuthenticationFacade)
            .passwordEncoder(passwordEncoder);
    }

}
