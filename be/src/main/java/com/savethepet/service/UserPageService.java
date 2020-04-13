package com.savethepet.service;

import com.savethepet.exception_handlers.Exception.NotEnoughPermissionsException;
import com.savethepet.exception_handlers.Exception.UserNotFoundException;
import com.savethepet.model.dao.UserRepo;
import com.savethepet.model.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Service;

import java.security.Principal;

/**
 * Service for UserPage
 *
 * @author Alexey Klimov
 */
@Service
public class UserPageService {

    @Autowired
    private UserRepo userRepo;

    public User getUserById(Long id) {
        return userRepo.findById(id).orElseThrow(() ->
                new UserNotFoundException("User with id=" + id.toString() + "not found"));
    }

    private User getUserByEmail(String email) {
        return userRepo.findByEmail(email).orElseThrow(() ->
                new UserNotFoundException("User with email=" + email + "not found"));
    }

    private User getUserByOauth2ClientRegistrationId(String registrationId, String username) {
        switch (registrationId) {
            case "yandex":
                return userRepo.findByYandexId(username).orElseThrow(() ->
                        new UserNotFoundException("User with Oauth2 id=" + username + "not found"));
            case "google":
                return userRepo.findByGoogleId(username).orElseThrow(() ->
                        new UserNotFoundException("User with Oauth2 id=" + username + "not found"));
            case "facebook":
                return userRepo.findByFacebookId(username).orElseThrow(() ->
                        new UserNotFoundException("User with Oauth2 id=" + username + "not found"));
            default:
                throw new RuntimeException("Unknown client registration id= " + registrationId);
        }
    }

    public void checkUserInfoChangeAccess(Principal principal, Long id) {
        String principalName = principal.getName();
        if (principal instanceof OAuth2AuthenticationToken) {
            String oauth2ClientId = ((OAuth2AuthenticationToken) principal).getAuthorizedClientRegistrationId();
            User userFromDBByOauth2ClientId = getUserByOauth2ClientRegistrationId(oauth2ClientId, principalName);
            if (!userFromDBByOauth2ClientId.getId().equals(id))
                throw new NotEnoughPermissionsException
                        ("user with id =" + userFromDBByOauth2ClientId.getId().toString() + "can`t change info about user with id=" + id.toString());
        } else {
            User userFromDBByEmail = getUserByEmail(principalName);
            if (!userFromDBByEmail.getId().equals(id))
                throw new NotEnoughPermissionsException(
                        "User with id " + userFromDBByEmail.getId() + " can`t change info user with id " + id.toString());
        }
    }
}