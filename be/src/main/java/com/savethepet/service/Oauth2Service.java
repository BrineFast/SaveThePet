package com.savethepet.service;

import com.savethepet.model.dao.UserRepo;
import com.savethepet.model.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class Oauth2Service {

    @Autowired
    private UserRepo userRepo;

    public Optional<User> getUserByOauth2ClientRegistrationId(String clientRegistrationId, String name) {
        switch (clientRegistrationId) {
            case "yandex":
                return userRepo.findByYandexId(name);
            case "google":
                return userRepo.findByGoogleId(name);
            case "facebook":
                return userRepo.findByFacebookId(name);
            default:
                throw new RuntimeException("Unknown client registration id= " + clientRegistrationId);
        }
    }
}
