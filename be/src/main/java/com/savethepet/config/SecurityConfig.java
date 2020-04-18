package com.savethepet.config;

import com.savethepet.service.CustomUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.InMemoryOAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;


/**
 * Configuration of Spring Security: *
 * 1) Basic auth
 * 2) Google Oauth2
 * 3) Yandex Oauth2
 * 4) Facebook Oauth2
 *
 * @author Alexey Klimov
 */
@Configuration
@EnableWebSecurity
@EnableOAuth2Client
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final String DEFAULT_REDIRECT = "/home";

    @Value("${google.id}")
    private String googleClientId;

    @Value("${google.secret}")
    private String googleClientSecret;

    @Value("${yandex.id}")
    private String yandexClientId;

    @Value("${yandex.secret}")
    private String yandexClientSecret;

    @Value("${facebook.id}")
    private String facebookClientId;

    @Value("${facebook.secret}")
    private String facebookClientSecret;

    @Value("${admin.account.username}")
    private String adminUsername;

    @Value("${admin.account.password}")
    private String adminPassword;

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
                .antMatchers(
                        DEFAULT_REDIRECT,
                        "/login",
                        "/registration",
                        "/api-docs",
                        "/swagger-resources/**",
                        "/swagger-ui.html",
                        "/webjars/**",
                        "/configuration/ui").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .defaultSuccessUrl(DEFAULT_REDIRECT)
                .and()
                .oauth2Login()
                .clientRegistrationRepository(clientRegistrationRepository())
                .authorizedClientService(oAuth2AuthorizedClientService())
                .defaultSuccessUrl("/be/oauth/registration", true)
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
                .passwordEncoder(passwordEncoder())
                .and()
                .inMemoryAuthentication().withUser(adminUsername).password(passwordEncoder().encode(adminPassword)).roles("USER");
    }

    /**
     * Configure to Oath2 clients
     *
     * @return
     */
    @PostConstruct
    public ClientRegistrationRepository clientRegistrationRepository() {
        ClientRegistration yandex =
                CustomOauth2Provider.YANDEX.getBuilder("yandex")
                        .clientId(yandexClientId)
                        .clientSecret(yandexClientSecret)
                        .build();

        ClientRegistration facebook =
                CustomOauth2Provider.FACEBOOK.getBuilder("facebook")
                        .clientId(facebookClientId)
                        .clientSecret(facebookClientSecret)
                        .build();

        ClientRegistration google =
                CustomOauth2Provider.GOOGLE.getBuilder("google")
                        .clientId(googleClientId)
                        .clientSecret(googleClientSecret)
                        .build();

        List<ClientRegistration> clientRegistrationList = new ArrayList<>();
        clientRegistrationList.add(yandex);
        clientRegistrationList.add(facebook);
        clientRegistrationList.add(google);
        return new InMemoryClientRegistrationRepository(clientRegistrationList);
    }

    @Bean
    public OAuth2AuthorizedClientService oAuth2AuthorizedClientService() {
        return new
                InMemoryOAuth2AuthorizedClientService(clientRegistrationRepository());
    }
}
