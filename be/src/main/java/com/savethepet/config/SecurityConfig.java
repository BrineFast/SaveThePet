package com.savethepet.config;

import com.savethepet.service.CustomUserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;


/**
 * Configuration of Spring Security
 *
 * Form auth
 * Google Oauth2
 *
 * @author Alexey Klimov
 */
@Configuration
@AllArgsConstructor
@EnableWebSecurity
@EnableOAuth2Client
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * Service for getting user information
     */
    @Autowired
    private CustomUserService customUserService;

    /**
     * Encoder for storage passwords in data storage
     */
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    /**
     * Configure for http requests
     *
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf()
                    .disable()
                .authorizeRequests()
                    .antMatchers("/login",
                            "/registration",
                            "/home",
                            "/api-docs",
                            "/swagger-resources/**",
                            "/swagger-ui.html",
                            "/webjars/**",
                            "/configuration/ui").permitAll()
                    .anyRequest().authenticated()
                .and()
                    .formLogin()
                        .defaultSuccessUrl("/home")
                .and()
                    .oauth2Login()
                        .defaultSuccessUrl("/home")
                .and()
                    .logout()
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID");

    }


    /**
     * Configure for authentication
     *
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(customUserService)
                .passwordEncoder(passwordEncoder());

    }
}
