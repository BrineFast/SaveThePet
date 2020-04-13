package com.savethepet.controller;

import com.savethepet.exception_handlers.Exception.NotEnoughPermissionsException;
import com.savethepet.model.dao.UserRepo;
import com.savethepet.model.dto.user.UserInfoChangeDTO;
import com.savethepet.model.dto.user.UserInfoDTO;
import com.savethepet.model.entity.User;
import com.savethepet.service.UserPageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.net.URI;
import java.security.Principal;

/**
 * Controller to requests to info about User
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
    private UserPageService userPageService;

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
        User user = userPageService.getUserById(id);
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

    /**
     * Changes info about user
     *
     * @param principal
     * @param id
     * @param userInfoChangeDTO
     * @return
     */
    @ApiOperation("Changes user information")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Info changed successfully"),
            @ApiResponse(code = 403, message = "Current user don`t have access to change this info"),
            @ApiResponse(code = 400, message = "The transmitted information is not valid")
    }
    )
    @PostMapping("/user/{user_id}")
    public ResponseEntity changeUserInfo(@ApiIgnore Principal principal, @PathVariable("user_id") Long id, @RequestBody @Valid UserInfoChangeDTO userInfoChangeDTO) {
        String principalName = principal.getName();
        if (principal instanceof OAuth2AuthenticationToken) {
            String oauth2ClientId = ((OAuth2AuthenticationToken) principal).getAuthorizedClientRegistrationId();
            User userFromDBByOauth2ClientId = userPageService.getUserByOauth2ClientRegistrationId(oauth2ClientId, principalName);
            if (!userFromDBByOauth2ClientId.getId().equals(id))
                throw new NotEnoughPermissionsException
                        ("user with id =" + userFromDBByOauth2ClientId.getId().toString() + "can`t change info about user with id=" + id.toString());
        } else {
            User userFromDBByEmail = userPageService.getUserByEmail(principalName);
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
