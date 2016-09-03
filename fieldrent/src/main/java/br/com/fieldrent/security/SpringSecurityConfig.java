package br.com.fieldrent.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Created by inafalcao on 12/14/15.
 */

@Configuration
@EnableWebSecurity
@Order(1)
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserService userDetailsService;

    @Autowired
    private JwtTokenAuthenticationService tokenAuthenticationService;

    /*@Autowired
    ClientRepository userRepository;*/

    public SpringSecurityConfig() {
        super(true);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .exceptionHandling().and()
                .anonymous().and()
                .servletApi().and()
                .headers().cacheControl();

        http
                .authorizeRequests()
                .antMatchers("/").permitAll()

                .antMatchers(HttpMethod.POST, "/client").permitAll()
                .antMatchers(HttpMethod.POST, "/client-company").permitAll()
                .antMatchers(HttpMethod.POST, "/api/login").permitAll()

                //.antMatchers("/**/add").hasRole("ADMIN")
                .antMatchers("/**").hasRole("USER")
                .anyRequest().hasRole("USER");

        // custom JSON based authentication by POST of {"email":"<name>","password":"<password>"} which sets the token header upon authentication
        http.addFilterBefore(new StatelessLoginFilter("/api/login", tokenAuthenticationService, userDetailsService, authenticationManager()), UsernamePasswordAuthenticationFilter.class);

        // custom Token based authentication based on the header previously given to the client
        http.addFilterBefore(new StatelessAuthenticationFilter(tokenAuthenticationService), UsernamePasswordAuthenticationFilter.class);

        String encript = new BCryptPasswordEncoder().encode("field@123!");

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService()).passwordEncoder(new BCryptPasswordEncoder());
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManager();
    }

    @Bean
    @Override
    public UserService userDetailsService() {
        return userDetailsService;
    }

    @Bean
    public JwtTokenAuthenticationService getTokenAuthenticationService() {
        return tokenAuthenticationService;
    }

}
