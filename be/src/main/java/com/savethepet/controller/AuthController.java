package com.savethepet.controller;

import com.savethepet.exception_handlers.Exception.UserAlreadyExistException;
import com.savethepet.model.dao.UserRepo;
import com.savethepet.model.dto.registrationUserDTO;
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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

/**
 * Form Auth Controller
 *
 * @author Alexey Klimov
 */
@Api
@RestController
public class AuthController {

    @Value("${reroute.url}")
    private String rerouteURL;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;


    /**
     * Redirects request to frontend
     *
     * @return
     */
    @ApiOperation("Returns login page")
    @ApiResponse(code = 200, message = "get successful")
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
     * Adds new User to DataBase or throws exception if user already exists and redirect to login     *
     *
     * @param registrationDto
     * @return
     * @throws UserAlreadyExistException
     */
    @ApiOperation("Return home page and add new user in database")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "User saved successfully"),
            @ApiResponse(code = 406, message = "User already exists")
    })
    @PostMapping("/registration")
    public ResponseEntity<String> addUser(@Validated @RequestBody registrationUserDTO registrationDto) {
        HttpHeaders responseHeaders = new HttpHeaders();
        if (userRepo.findByEmail(registrationDto.getEmail()).isPresent()) {
            throw new UserAlreadyExistException(registrationDto.toString());
        } else {
            User userFromDto = new User();
            userFromDto.setEmail(registrationDto.getEmail());
            userFromDto.setPassword(passwordEncoder.encode(registrationDto.getPassword()));
            userFromDto.setName(registrationDto.getPassword());
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
