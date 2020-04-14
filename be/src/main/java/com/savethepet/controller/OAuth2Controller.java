package com.savethepet.controller;

import com.savethepet.exception_handlers.Exception.AccountAlreadyUsingException;
import com.savethepet.exception_handlers.Exception.ClientRegistrationIdNotFound;
import com.savethepet.model.dao.UserRepo;
import com.savethepet.model.entity.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.transaction.annotation.Transactional;
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
public class OAuth2Controller {

    /**
     * Flag that shows, what controller need to do - Handle new user or Update existing user
     * Sets to true by UserPageController
     *
     * @see com.savethepet.controller.UserPageController#addOauth2(Principal, Long, String)
     */
    public static boolean IS_LINK_NEW_OAUTH = false;

    /**
     * Id of user to link the new OAuth to
     * Sets by UserPageController
     *
     * @see com.savethepet.controller.UserPageController#addOauth2(Principal, Long, String)
     */
    public static Long NEW_OAUTH_USER_ID;


    @Value("${reroute.url}")
    private String rerouteURL;

    @Autowired
    private UserRepo userRepo;

    /**
     * Adds in DB User from OAuth2 or link new OAuth2 account to existing user
     *
     * @param principal
     * @return
     */
    @ApiOperation("Register user from OAuth or Adds new OAuth to existing user")
    @ApiResponses(value = {
            @ApiResponse(code = 302, message = "redirected to frontend"),
            @ApiResponse(code = 406, message = "Attempt to link OAuth account that already in use"),
            @ApiResponse(code = 404, message = "Attempt to use unknown OAuth2 client")
    }
    )
    @Transactional
    @GetMapping("/be/oauth/registration")
    public ResponseEntity<Void> addUserFromOauth2(@ApiIgnore Principal principal) {
        OAuth2User userFromOauth = ((OAuth2AuthenticationToken) principal).getPrincipal();
        String clientRegistrationId = ((OAuth2AuthenticationToken) principal).getAuthorizedClientRegistrationId();
        String name = principal.getName();
        switch (clientRegistrationId) {
            case "google":
                if (userRepo.findByGoogleId(name).isEmpty()) {
                    if (IS_LINK_NEW_OAUTH) {
                        User userForNewOauth = userRepo.findById(NEW_OAUTH_USER_ID).get();
                        userForNewOauth.setGoogleId(name);
                        userRepo.save(userForNewOauth);
                        IS_LINK_NEW_OAUTH = false;
                    } else {
                        User newUser = new User();
                        newUser.setName(userFromOauth.getAttribute("name"));
                        newUser.setGoogleId(name);
                        userRepo.save(newUser);
                    }
                } else if (IS_LINK_NEW_OAUTH) {
                    IS_LINK_NEW_OAUTH = false;
                    throw new AccountAlreadyUsingException("google account with id = " + name + "already exists");
                }
                break;
            case "facebook":
                if (userRepo.findByFacebookId(name).isEmpty()) {
                    if (IS_LINK_NEW_OAUTH) {
                        User userForNewOauth = userRepo.findById(NEW_OAUTH_USER_ID).get();
                        userForNewOauth.setFacebookId(name);
                        userRepo.save(userForNewOauth);
                        IS_LINK_NEW_OAUTH = false;
                    } else {
                        User newUser = new User();
                        newUser.setName(userFromOauth.getAttribute("name"));
                        newUser.setFacebookId(name);
                        userRepo.save(newUser);
                    }
                } else if (IS_LINK_NEW_OAUTH) {
                    IS_LINK_NEW_OAUTH = false;
                    throw new AccountAlreadyUsingException("facebook account with id = " + name + "already exists");
                }
                break;
            case "yandex":
                if (userRepo.findByYandexId(name).isEmpty()) {
                    if (IS_LINK_NEW_OAUTH) {
                        User userForNewOauth = userRepo.findById(NEW_OAUTH_USER_ID).get();
                        userForNewOauth.setYandexId(name);
                        userRepo.save(userForNewOauth);
                    } else {
                        User newUser = new User();
                        newUser.setName(userFromOauth.getAttribute("name"));
                        newUser.setYandexId(name);
                        userRepo.save(newUser);
                        IS_LINK_NEW_OAUTH = false;
                    }
                } else if (IS_LINK_NEW_OAUTH) {
                    IS_LINK_NEW_OAUTH = false;
                    throw new AccountAlreadyUsingException("google account with id = " + name + "already exists");
                }
                break;
            default:
                throw new ClientRegistrationIdNotFound("Unknown client registration id" + clientRegistrationId);
        }
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setLocation(URI.create(rerouteURL + "/home"));
        return new ResponseEntity<>(responseHeaders, HttpStatus.FOUND);
    }
}