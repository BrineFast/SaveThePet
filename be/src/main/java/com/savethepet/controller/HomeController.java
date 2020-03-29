package com.savethepet.controller;

import com.savethepet.model.dao.UserRepo;
import com.savethepet.model.entity.Role;
import com.savethepet.model.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.security.Principal;
import java.util.Collections;

/**
 * @author Alexey Klimov
 */
@RestController
public class HomeController {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${reroute.url}")
    private String rerouteURL;

    /**
     * Method for processing GET requests to the HOME page
`     * Save in Data Storage users from oauth2
`     * Reroute to frontend
     */
    @GetMapping("/home")
    public ResponseEntity<String> auth(Principal principal) {
        if (principal instanceof OAuth2AuthenticationToken) {
            OAuth2User user = ((OAuth2AuthenticationToken) principal).getPrincipal();
            String username = user.getAttribute("email");
            String name = user.getAttribute("name");
            User newUser = new User(username,
                    passwordEncoder.encode("oauth2"),
                    name);
            newUser.setRoles(Collections.singleton(Role.USER));
            userRepo.save(newUser);
        }
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setLocation(URI.create(rerouteURL + "/home"));
        return
                ResponseEntity.status(HttpStatus.OK)
                        .headers(responseHeaders)
                        .body("");
    }
}
