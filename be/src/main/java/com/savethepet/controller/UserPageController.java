package com.savethepet.controller;

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
import org.springframework.transaction.annotation.Transactional;
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
    @PatchMapping("/user/{user_id}")
    public ResponseEntity changeUserInfo(@ApiIgnore Principal principal, @PathVariable("user_id") Long id, @RequestBody @Valid UserInfoChangeDTO userInfoChangeDTO) {
        userPageService.checkUserInfoChangeAccess(principal, id);
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

    @PatchMapping("/user/{user_id}/oauth/{ClientName}")
    @Transactional
    public ResponseEntity addOauth2(@ApiIgnore Principal principal, @ApiIgnore @PathVariable("user_id") Long id, @PathVariable("ClientName") String clientName) {
        userPageService.checkUserInfoChangeAccess(principal, id);
        OAuth2Controller.New_OAUTH_USER_ID = id;
        OAuth2Controller.IS_NEW_OAUTH = true;
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setLocation(URI.create("http://localhost:8080/oauth2/authorization/" + clientName));
        return new ResponseEntity(responseHeaders, HttpStatus.MOVED_PERMANENTLY);
    }

}
