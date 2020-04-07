package com.savethepet.config;

import com.savethepet.service.CustomUserService;
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
 * Configuration of Spring Security: *
 * 1) Basic auth
 * 2) Google Oauth2
 * 3) Yandex Oauth2
 * 4) Vk Oauth2
 * @author Alexey Klimov
 */
@Configuration
@EnableWebSecurity
@EnableOAuth2Client
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    @Autowired
    private CustomUserService customUserService;


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
                            "/configuration/ui",
                            "/login/oauth2/yandex").permitAll()
                    .anyRequest().authenticated()
                .and()
                    .formLogin()
                        .defaultSuccessUrl("/home")
                .and()
                    .oauth2Login()
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
