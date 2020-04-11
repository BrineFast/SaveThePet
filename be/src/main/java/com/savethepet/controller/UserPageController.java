package com.savethepet.controller;

import com.savethepet.exception_handlers.Exception.NotEnoughPermissionsException;
import com.savethepet.model.dao.UserRepo;
import com.savethepet.model.dto.user.UserInfoChangeDTO;
import com.savethepet.model.dto.user.UserInfoDTO;
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
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.net.URI;
import java.security.Principal;

/**
 * Everything related to user
 *
 * @author Alexey Klimov
 */
@Api
@RestController
public class UserPageController {

    @Value("${reroute.url}")
    private String rerouteURL;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Returns Info about User
     *
     * @param id
     * @return
     */
    @ApiOperation("Return info about user")
    @ApiResponse(code = 200, message = "User info returned")
    @GetMapping("/user/{user_id}")
    public ResponseEntity<UserInfoDTO> getUserInfo(@PathVariable("user_id") Long id) {
        User user = userRepo.findById(id).orElseThrow(() ->
                new UsernameNotFoundException("User with id=" + id.toString() + "not found"));
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setLocation(URI.create(rerouteURL + "/user/" + id.toString()));
        return ResponseEntity.status(HttpStatus.OK)
                .headers(responseHeaders)
                .body(
                        UserInfoDTO.builder()
                                .name(user.getName())
                                .location(user.getLocation())
                                .pets(user.getPets())
                                .phoneNumber(user.getPhoneNumber())
                                .email(user.getEmail())
                                .img(user.getImg())
                                .build());
    }

    @ApiOperation("Changes user information")
    @ApiResponses(
            @ApiResponse()
    )
    @PostMapping("/user/{user_id}")
    public ResponseEntity changeUserInfo(@ApiIgnore Principal principal, @PathVariable("user_id") Long id, @RequestBody UserInfoChangeDTO userInfoChangeDTO) {
        String principalName = principal.getName();
        if (principal instanceof OAuth2AuthenticationToken) {
            String oauth2ClientId = ((OAuth2AuthenticationToken) principal).getAuthorizedClientRegistrationId();
            User userFromDBByOauth2Id;
            switch (oauth2ClientId) {
                case "yandex":
                    userFromDBByOauth2Id = userRepo.findByYandexId(principalName).get();
                    if (!userFromDBByOauth2Id.getId().equals(id))
                        throw new NotEnoughPermissionsException(
                                "User with id " + userFromDBByOauth2Id.getId() + " can`t change info user with id " + id.toString());
                    break;
                case "google":
                    userFromDBByOauth2Id = userRepo.findByGoogleId(principalName).get();
                    if (!userFromDBByOauth2Id.getId().equals(id))
                        throw new NotEnoughPermissionsException(
                                "User with id " + userFromDBByOauth2Id.getId() + " can`t change info user with id " + id.toString());
                    break;
                case "facebook":
                    userFromDBByOauth2Id = userRepo.findByFacebookId(principalName).get();
                    if (!userFromDBByOauth2Id.getId().equals(id))
                        throw new NotEnoughPermissionsException(
                                "User with id " + userFromDBByOauth2Id.getId() + " can`t change info user with id " + id.toString());
            }
        } else {
            User userFromDBByEmail = userRepo.findByEmail(principalName).get();
            if (!userFromDBByEmail.getId().equals(id))
                throw new NotEnoughPermissionsException(
                        "User with id " + userFromDBByEmail.getId() + " can`t change info user with id " + id.toString());
        }
        User changedUser = new User();
        changedUser.setName(userInfoChangeDTO.getName());
        changedUser.setEmail(userInfoChangeDTO.getEmail());
        changedUser.setLocation(userInfoChangeDTO.getLocation());
        changedUser.setPhoneNumber(userInfoChangeDTO.getPhoneNumber());
        changedUser.setPassword(passwordEncoder.encode(userInfoChangeDTO.getPassword()));
        userRepo.save(changedUser);
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setLocation(URI.create(rerouteURL + "/user/" + id.toString()));
        return new ResponseEntity(responseHeaders, HttpStatus.OK);
    }

}
