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

import java.net.URI;
import java.util.Collections;

/**
 * Form Auth Controller
 *
 * @author Alexey Klimov
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
     * Redirects request to frontend
     *
     * @return
     */
    @GetMapping("/login")
    public ResponseEntity<String> getLogin() {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setLocation(URI.create(rerouteURL + "/login"));
        return
                ResponseEntity.status(HttpStatus.OK)
                        .headers(responseHeaders)
                        .body("");
    }

    /**
     * Redirects request to frontend
     *
     * @return
     */
    @GetMapping("/registration")
    public ResponseEntity<String> getRegistration() {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setLocation(URI.create(rerouteURL + "/registration"));
        return
                ResponseEntity.status(HttpStatus.OK)
                        .headers(responseHeaders)
                        .body("");
    }

    /**
     * Adds new User to DataBase or throws exception if user already exists and redirect to login     *
     *
     * @param registrationDto
     * @return
     * @throws UserAlreadyExistException
     */
    @PostMapping("/registration")
    public ResponseEntity<String> addUser(@Validated @RequestBody UserDto registrationDto) throws UserAlreadyExistException {
        HttpHeaders responseHeaders = new HttpHeaders();
        if (userRepo.findByEmail(registrationDto.getEmail()).isPresent()) {
            throw new UserAlreadyExistException("pivo");
        } else {
            User userFromDto = UserDto.userFromDto(registrationDto, passwordEncoder);
            userFromDto.setRoles(Collections.singleton(Role.USER));
            userRepo.save(userFromDto);
        }
        responseHeaders.setLocation(URI.create(rerouteURL + "/login"));
        return ResponseEntity.status(HttpStatus.OK)
                .headers(responseHeaders)
                .body(" ...|\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"| |\\\n" +
                        "... |Холодное пиво! ||\"\"\\__,_\n" +
                        "... |_____________ |||_|__|_ )\n" +
                        "... *(@)|(@)\"\"\"*******(@)\"");
    }
}