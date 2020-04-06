package com.savethepet.controller;

import com.savethepet.model.dao.UserRepo;
import com.savethepet.model.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
public class Oauth2Controller {

    @Autowired
    private UserRepo userRepo;

    @Value("${reroute.url}")
    private String rerouteURL;

    @GetMapping("/login/oauth2/code/vk")
    public ResponseEntity<String> saveUserFromVkOauth(OAuth2User oAuth2User) {
        if (userRepo.findByVkId(oAuth2User.getAttribute("id")).isEmpty()) {
            User newUser = new User();

        }
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setLocation(URI.create(rerouteURL + "/home"));
        return ResponseEntity
                .status(HttpStatus.OK)
                .headers(responseHeaders)
                .body(" ...|\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"| |\\\n" +
                        "... |Холодное пиво! ||\"\"\\__,_\n" +
                        "... |_____________ |||_|__|_ )\n" +
                        "... *(@)|(@)\"\"\"*******(@)\"");
    }

    @GetMapping("/login/oauth2/code/yandex")
    public ResponseEntity<String> saveUserFromYandexOauth(OAuth2User oAuth2User) {
        if (userRepo.findByVkId(oAuth2User.getAttribute("id")).isEmpty()) {
            User newUser = new User();
        }
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setLocation(URI.create(rerouteURL + "/home"));
        return ResponseEntity
                .status(HttpStatus.OK)
                .headers(responseHeaders)
                .body(" ...|\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"| |\\\n" +
                        "... |Холодное пиво! ||\"\"\\__,_\n" +
                        "... |_____________ |||_|__|_ )\n" +
                        "... *(@)|(@)\"\"\"*******(@)\"");
    }

}
