package com.savethepet.controller;

import com.savethepet.exception_handlers.Exception.UserAlreadyExistException;
import com.savethepet.model.dao.UserRepo;
import com.savethepet.model.dto.UserDto;
import com.savethepet.model.entity.Role;
import com.savethepet.model.entity.User;
import com.savethepet.service.CustomUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.util.Collections;

/**
 * @author Alexey Klimov
 * Form Auth Controller
 */
@RestController
public class AuthController {

    @Value("${reroute.url}")
    private String rerouteURL;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private CustomUserService userService;

    /**
     * Method for processing GET requests to the LOGIN page
     * Rerouting to frontend
     */
    @GetMapping("/login")
    public ResponseEntity<String> getLogin() throws IOException {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setLocation(URI.create(rerouteURL + "/login"));
        return
                ResponseEntity.status(HttpStatus.OK)
                        .headers(responseHeaders)
                        .body("");
    }

    /**
     * Method for processing GET requests to the REGISTRATION page
     * Rerouting to frontend
     */
    @GetMapping("/registration")
    public ResponseEntity<String> getRegistration(HttpServletResponse response) throws IOException {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setLocation(URI.create(rerouteURL + "/registration"));
        return
                ResponseEntity.status(HttpStatus.OK)
                        .headers(responseHeaders)
                        .body("");
    }

    /**
     * Method for processing POST requests to the REGISTRATION page
     * Registration of users
     */
    @PostMapping("/registration")
    public ResponseEntity<String> addUser(@Validated @RequestBody UserDto dto) throws UserAlreadyExistException {
        User userFromDto = UserDto.userFromDto(dto, passwordEncoder);
        HttpHeaders responseHeaders = new HttpHeaders();
        if (userService.loadUserByUsername(userFromDto.getUsername()) != null) {
            throw new UserAlreadyExistException();
        }
        userFromDto.setRoles(Collections.singleton(Role.USER));
        userRepo.save(userFromDto);
        responseHeaders.setLocation(URI.create(rerouteURL + "/login"));
        return ResponseEntity.status(HttpStatus.OK)
                .headers(responseHeaders)
                .body("");
    }
}
