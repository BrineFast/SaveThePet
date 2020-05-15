package com.savethepet.controller;

import com.savethepet.model.dto.pet.PetInfoDTO;
import com.savethepet.model.dto.user.UserInfoChangeDTO;
import com.savethepet.model.dto.user.UserInfoDTO;
import com.savethepet.model.entity.Status;
import com.savethepet.model.entity.User;
import com.savethepet.service.PetPageService;
import com.savethepet.service.UserPageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.net.URI;
import java.security.Principal;
import java.util.List;

/**
 * Controller to requests to info about User
 *
 * @author Alexey Klimov
 */
@Api
@RestController
public class UserPageController {

    @Autowired
    private UserPageService userPageService;

    @Autowired
    private PetPageService petPageService;

    /**
     * Returns Info about User
     *
     * @param id
     * @return
     */
    @ApiOperation("Return info about user")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "User info returned"),
            @ApiResponse(code = 404, message = "User with this that id not exists")
    })
    @GetMapping("/user/{user_id}")
    public UserInfoDTO getUserInfo(@PathVariable("user_id") Long id) {
        User user = userPageService.getUserById(id);
        return UserInfoDTO.builder()
                .name(user.getName())
                .location(user.getLocation())
                .phoneNumber(user.getPhoneNumber())
                .email(user.getEmail())
                .img(user.getImg())
                .build();
    }


    /**
     * Returns info about user pets
     *
     * @param id
     * @return
     */
    @ApiOperation("Return info about user pets")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "User info returned"),
            @ApiResponse(code = 404, message = "User with this that id not exists")
    })
    @GetMapping("user/{user_id}/pets")
    public List<PetInfoDTO> getUserPet(@PathVariable("user_id") Long id) {
        return petPageService.getPetsByUser(id);
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
            @ApiResponse(code = 406, message = "The transmitted information is not valid"),
            @ApiResponse(code = 404, message = "User with this that id not exists")
    }
    )
    @PatchMapping("/user/{user_id}")
    public void changeUserInfo(@ApiIgnore Principal principal, @PathVariable("user_id") Long id, @RequestBody @Valid UserInfoChangeDTO userInfoChangeDTO) {
        userPageService.checkUserInfoChangeAccess(principal, id);
        userPageService.updateUserFromDto(userInfoChangeDTO, id);
    }

    /**
     * Links to user new OAuth2 account
     *
     * @param principal
     * @param id
     * @param clientName
     * @return
     */
    @ApiOperation("Links to user new OAuth2 account")
    @ApiResponses(value = {
            @ApiResponse(code = 302, message = "redirected to oauth2 controller"),
            @ApiResponse(code = 404, message = "User with this that id not exists"),
            @ApiResponse(code = 403, message = "Current user don`t have access to change this info"),
            @ApiResponse(code = 400, message = "Unknown client id")
    })
    @PatchMapping("/user/{user_id}/oauth/{ClientName}")
    @Transactional
    public ResponseEntity addOauth2(@ApiIgnore Principal principal, @PathVariable("user_id") Long id, @PathVariable("ClientName") String clientName) {
        userPageService.checkUserInfoChangeAccess(principal, id);
        OAuth2Controller.NEW_OAUTH_USER_ID = id;
        OAuth2Controller.IS_LINK_NEW_OAUTH = true;
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setLocation(URI.create("http://localhost:8080/oauth2/authorization/" + clientName));
        return new ResponseEntity(responseHeaders, HttpStatus.FOUND);
    }

    /**
     * Delete OAuth2 account from user
     *
     * @param principal
     * @param id
     * @param clientName
     */
    @ApiOperation("deletes oauth2 account from user")
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "User with this that id not exists"),
            @ApiResponse(code = 403, message = "Current user don`t have access to change this info"),
            @ApiResponse(code = 400, message = "Unknown client id"),
            @ApiResponse(code = 406, message = "Can`t delete this OAuth2 because user lost access to account")
    })
    @DeleteMapping("/user/{user_id}/oauth/{ClientName}")
    public void deleteOauth2(@ApiIgnore Principal principal, @PathVariable("user_id") Long id, @PathVariable("ClientName") String clientName) {
        userPageService.checkUserInfoChangeAccess(principal, id);
        userPageService.checkLostAuth(clientName, id);
        userPageService.deleteOauthFromUser(clientName, id);
    }
}
