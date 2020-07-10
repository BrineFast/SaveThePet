package com.savethepet.service;

import com.savethepet.exception_handlers.exception.*;
import com.savethepet.model.dao.UserRepo;
import com.savethepet.model.dto.user.UserInfoChangeDTO;
import com.savethepet.model.dto.user.UserInfoDTO;
import com.savethepet.model.dto.user.UserRegistrationDTO;
import com.savethepet.model.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.security.Principal;

/**
 * Service for UserPage
 *
 * @author Alexey Klimov
 */
@Service
public class UserService {

    private static final String userWithOauthId = "User with Oauth2 id =";
    private static final String notFound = "not found";
    private static final String yandex = "yandex";
    private static final String google = "google";
    private static final String facebook = "facebook";

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserInfoDTO saveUser(UserRegistrationDTO dto) {
        if (userRepo.findByEmail(dto.getEmail()).isPresent()) {
            throw new UserAlreadyExistException("User with with email: " + dto.getEmail() + " exists");
        } else {
            User userFromDto = new User();
            userFromDto.setEmail(dto.getEmail());
            userFromDto.setPassword(passwordEncoder.encode(dto.getPassword()));
            userFromDto.setName(dto.getName());
            return UserInfoDTO.getValue(userRepo.save(userFromDto));
        }
    }

    /**
     * Gets User by Id and throws exception if User didnt found
     *
     * @param id
     * @return
     */
    public User getUserById(Long id) {
        return userRepo.findById(id).orElseThrow(() ->
                new UserNotFoundException("User with id=" + id.toString() + notFound));
    }

    /**
     * Gets User by Email and throws exception if User didnt found
     *
     * @param email
     * @return
     */
    private User getUserByEmail(String email) {
        return userRepo.findByEmail(email).orElseThrow(() ->
                new UserNotFoundException("User with email=" + email + notFound));
    }

    /**
     * Changes user in db with information in dto
     *
     * @param userInfoChangeDTO
     * @param id
     */
    public UserInfoDTO updateUserFromDto(UserInfoChangeDTO userInfoChangeDTO, Long id) {
        User changedUser = getUserById(id);
        changedUser.setName(userInfoChangeDTO.getName());
        changedUser.setEmail(userInfoChangeDTO.getEmail());
        changedUser.setLocation(userInfoChangeDTO.getLocation());
        changedUser.setPhoneNumber(userInfoChangeDTO.getPhoneNumber());
        changedUser.setPassword(passwordEncoder.encode(userInfoChangeDTO.getPassword()));
        return UserInfoDTO.getValue(userRepo.save(changedUser));
    }

    /**
     * Gets user from DB by OAuth2
     *
     * @param registrationId
     * @param OAuth2Id
     * @return
     */
    public User getUserByOauth2ClientRegistrationId(String registrationId, String OAuth2Id) {
        switch (registrationId) {
            case yandex:
                return userRepo.findByYandexId(OAuth2Id).orElseThrow(() ->
                        new UserNotFoundException(userWithOauthId + OAuth2Id + notFound));
            case google:
                return userRepo.findByGoogleId(OAuth2Id).orElseThrow(() ->
                        new UserNotFoundException(userWithOauthId + OAuth2Id + notFound));
            case facebook:
                return userRepo.findByFacebookId(OAuth2Id).orElseThrow(() ->
                        new UserNotFoundException(userWithOauthId + OAuth2Id + notFound));
            default:
                throw new ClientRegistrationIdNotFound("Unknown client registration id= " + registrationId);
        }
    }

    /**
     * Checks that`s user by principal is user by id
     *
     * @param principal
     * @param id
     */
    public void checkUserHaveAccess(Principal principal, Long id) {
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

    /**
     * Checks if user trying delete all his Auth information
     *
     * @param clientName
     * @param id
     */
    public void checkLostAuth(String clientName, Long id) {
        User user = userRepo.findById(id).orElseThrow(() -> new UserNotFoundException("User with id = " + id.toString() + notFound));
        boolean isGoogleEmpty = user.getGoogleId().isEmpty();
        boolean isYandexEmpty = user.getYandexId().isEmpty();
        boolean isFacebookEmpty = user.getFacebookId().isEmpty();
        boolean isBasicEmpty = user.getPassword().isEmpty();
        switch (clientName) {
            case google:
                if (isYandexEmpty && isFacebookEmpty && isBasicEmpty)
                    throw new LostInformationAboutUserException("can`t delete google oauth2");
                break;
            case yandex:
                if (isGoogleEmpty && isFacebookEmpty && isBasicEmpty)
                    throw new LostInformationAboutUserException("can`t delete yandex oauth2");
                break;
            case facebook:
                if (isYandexEmpty && isGoogleEmpty && isBasicEmpty)
                    throw new LostInformationAboutUserException("can`t delete facebook oauth2");
                break;
            default:
                throw new ClientRegistrationIdNotFound("unknown client registration id = " + clientName);
        }
    }

    /**
     * Deletes user`s oauth2 account
     *
     * @param clientName
     * @param id
     */
    public void deleteOauthFromUser(String clientName, Long id) {
        User user = getUserById(id);
        switch (clientName) {
            case yandex:
                user.setYandexId(null);
                break;
            case google:
                user.setGoogleId(null);
                break;
            case facebook:
                user.setFacebookId(null);
                break;
            default:
                throw new ClientRegistrationIdNotFound("unknown client registration id = " + clientName);
        }
        userRepo.save(user);
    }
}
