package com.savethepet.controller;

import com.savethepet.model.dao.UserRepo;
import com.savethepet.model.dto.user.UserInfoDTO;
import com.savethepet.model.entity.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.security.Principal;

/**
 * Everything related to user
 *
 * @author Alexey Klimov
 */
@Api
@RestController
public class UserPageController {

    @Autowired
    private UserRepo userRepo;

    /**
     * Returns Info about User
     *
     * @param id
     * @return
     */
    @ApiOperation("Return info about user")
    @ApiResponse(code = 200, message = "User info returned")
    @GetMapping("/user/{user_id}")
    public UserInfoDTO getUserInfo(@PathVariable("user_id") Long id) {
        User user = userRepo.findById(id).orElseThrow(() ->
                new UsernameNotFoundException("User with id=" + id.toString() + "not found"));
        return UserInfoDTO.builder()
                .name(user.getName())
                .location(user.getLocation())
                .pets(user.getPets())
                .phoneNumber(user.getPhoneNumber())
                .email(user.getEmail())
                .img(user.getImg())
                .build();
    }

    @PostMapping("/user/{user_id}")
    public void changeUserInfo(@ApiIgnore Principal principal, @PathVariable("user_id") Long id){

    }

}
