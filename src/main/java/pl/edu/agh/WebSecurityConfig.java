package pl.edu.agh;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.boot.context.embedded.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import pl.edu.agh.services.UserDetailsService;

@Configuration
@EnableWebSecurity
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    @Qualifier("currentUserDetailsService")
    private UserDetailsService userDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS, "/*/**").permitAll()
                .antMatchers(
//                        "/index.html", "/home.html", "/login.html",
        "/", "/*.html","/*.js", "user", "user_login").permitAll()
        .and()
                .formLogin()
                .defaultSuccessUrl("/#/home")
                .loginPage("/#/login")
                .permitAll()
        .and()
                .logout().logoutUrl("/logout")
//                .deleteCookies("JSESSIONID")
//                .clearAuthentication(true)
                .permitAll()

//                .headers()
//                .cacheControl()
        .and()
                .csrf().disable();
//                .csrfTokenRepository(csrfTokenRepository());
//        .and()
//                .sessionManagement()
//                .maximumSessions(1)
//                .expiredUrl("/expired")
//                .maxSessionsPreventsLogin(true)
//                .sessionRegistry(sessionRegistry());
//        .and()
//                .addFilterAfter(new CsrfHeaderFilter(), CsrfFilter.class);

    }

//    @Bean
//    public SessionRegistry sessionRegistry() {
//        SessionRegistry sessionRegistry = new SessionRegistryImpl();
//        return sessionRegistry;
//    }

//    private CsrfTokenRepository csrfTokenRepository() {
//        HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();
//        repository.setHeaderName("X-XSRF-TOKEN");
//        repository.setSessionAttributeName("_csrf");
//        return repository;
//    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder authentication) throws Exception {
        authentication.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
//        authentication.inMemoryAuthentication().withUser("user").password("password").roles("USER");
    }

//    @Bean
//    public static ServletListenerRegistrationBean httpSessionEventPublisher() {
//        return new ServletListenerRegistrationBean(new HttpSessionEventPublisher());
//    }
}