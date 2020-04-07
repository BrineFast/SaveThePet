package com.savethepet.controller;

import com.savethepet.model.dao.UserRepo;
import com.savethepet.model.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class Oauth2Controller {

    @Autowired
    private UserRepo userRepo;

    @Value("${reroute.url}")
    private String rerouteURL;

    @GetMapping("/login/oauth2/yandex")
    public void addUserFromYandex(){
        ResponseEntity<String> tokenResponse
    }
}
