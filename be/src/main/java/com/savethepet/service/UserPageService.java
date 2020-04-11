package com.savethepet.service;

import com.savethepet.exception_handlers.Exception.UserNotFoundException;
import com.savethepet.model.dao.UserRepo;
import com.savethepet.model.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service for UserPage
 *
 * @author Alexey Klimov
 */
@Service
public class UserPageService {

    @Autowired
    UserRepo userRepo;

    public User getUserById(Long id) {
        return userRepo.findById(id).orElseThrow(() ->
                new UserNotFoundException("User with id=" + id.toString() + "not found"));
    }

    public User getUserByEmail(String email) {
        return userRepo.findByEmail(email).orElseThrow(() ->
                new UserNotFoundException("User with email=" + email + "not found"));
    }

    public User getUserByOauth2ClientRegistrationId(String registrationId, String username) {
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
}
