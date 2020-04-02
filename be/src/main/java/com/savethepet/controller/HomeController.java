package com.savethepet.controller;

import com.savethepet.model.dao.UserRepo;
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

/**
 * Oauth 2 controller
 *
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
     * Saves in data base if user came from OAuth2 and redirects to /home
     *
     * @param principal
     * @return
     */
    @GetMapping("/home")
    public ResponseEntity<String> auth(Principal principal) {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setLocation(URI.create(rerouteURL + "/home"));


        if (principal instanceof OAuth2AuthenticationToken) {
            OAuth2User userFromOauth = ((OAuth2AuthenticationToken) principal).getPrincipal();
            if (!userRepo.findByGoogleId(userFromOauth.getAttribute("sub")).isPresent()) {
                User newUser = new User(userFromOauth.getAttribute("email"),
                        userFromOauth.getAttribute("password"),
                        passwordEncoder.encode(userFromOauth.getAttribute("name")));
                newUser.setImg(userFromOauth.getAttribute("picture"));
                userRepo.save(newUser);
            }
        }
        return ResponseEntity
                .status(HttpStatus.OK)
                .headers(responseHeaders)
                .body(" ...|\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"| |\\\n" +
                        "... |Холодное пиво! ||\"\"\\__,_\n" +
                        "... |_____________ |||_|__|_ )\n" +
                        "... *(@)|(@)\"\"\"*******(@)\"");
    }

}

