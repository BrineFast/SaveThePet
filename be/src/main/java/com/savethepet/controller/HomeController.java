package com.savethepet.controller;

import com.savethepet.model.dao.UserRepo;
import com.savethepet.model.entity.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.net.URI;
import java.security.Principal;

/**
 * Controller to Oauth2
 *
 * @author Alexey Klimov
 */
@Api
@RestController
public class HomeController {

    @Value("${reroute.url}")
    private String rerouteURL;

    @Autowired
    UserRepo userRepo;

    @ApiOperation("Register user from Ouath")
    @ApiResponse(code = 301, message = "redirected to frontend")
    @GetMapping("/be/oauth/registration")
    public ResponseEntity<Void> addUserFromOauth2(@ApiIgnore Principal principal) {
        OAuth2User userFromOauth = ((OAuth2AuthenticationToken) principal).getPrincipal();
        User newUser = new User();
        String clientRegistrationId = ((OAuth2AuthenticationToken) principal).getAuthorizedClientRegistrationId();
        if (clientRegistrationId.equals("google") && userRepo.findByGoogleId(principal.getName()).isEmpty()) {
            newUser.setName(userFromOauth.getAttribute("name"));
            newUser.setGoogleId(principal.getName());
            userRepo.save(newUser);
        } else if (clientRegistrationId.equals("facebook") && userRepo.findByFacebookId(principal.getName()).isEmpty()) {
            newUser.setName(userFromOauth.getAttribute("name"));
            newUser.setFacebookId(principal.getName());
            userRepo.save(newUser);
        } else if (clientRegistrationId.equals("yandex") && userRepo.findByYandexId(principal.getName()).isEmpty()) {
            newUser.setName(userFromOauth.getAttribute("real_name"));
            newUser.setYandexId(principal.getName());
            userRepo.save(newUser);
        }
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setLocation(URI.create(rerouteURL + "/home"));
        return new ResponseEntity<>(responseHeaders, HttpStatus.MOVED_PERMANENTLY);
    }
}