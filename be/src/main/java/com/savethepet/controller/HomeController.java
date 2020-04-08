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

    @ApiOperation("saves user from oauth2")
    @ApiResponse(code = 200, message = "all ok")
    @GetMapping("/be/oauth/registration")
    public ResponseEntity<String> addUserFromOauth2(@ApiIgnore Principal principal) {
        OAuth2User userFromOauth = ((OAuth2AuthenticationToken) principal).getPrincipal();
        if (userRepo.findByAuthId(principal.getName()).isEmpty()) {
            User newUser = new User();
            String clientRegistrationId = ((OAuth2AuthenticationToken) principal).getAuthorizedClientRegistrationId();
            if (clientRegistrationId.equals("google")) {
                newUser.setName(userFromOauth.getAttribute("name"));
                newUser.setGoogleId(principal.getName());
            } else if (clientRegistrationId.equals("facebook")) {
                newUser.setName(userFromOauth.getAttribute("name"));
                newUser.setFacebookId(principal.getName());
            } else if (clientRegistrationId.equals("yandex")) {
                newUser.setName(userFromOauth.getAttribute("real_name"));
                newUser.setYandexId(principal.getName());
            }
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
