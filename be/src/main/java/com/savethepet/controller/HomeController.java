package com.savethepet.controller;

import com.savethepet.model.dao.UserRepo;
import com.savethepet.model.entity.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.net.URI;
import java.security.Principal;

/**
 * Oauth 2 controller
 *
 * @author Alexey Klimov
 */
@Api
@RestController
public class HomeController {

    @Autowired
    private UserRepo userRepo;

    @Value("${reroute.url}")
    private String rerouteURL;

    /**
     * Saves in data base if user came from OAuth2 and redirects to /home
     *
     * @param principal
     * @return
     */
    @ApiOperation("Returns home page and save new user from oauth2")
    @ApiResponse(code = 200, message = "Successfully")
    @GetMapping("/home")
    public ResponseEntity<String> auth(@ApiIgnore Principal principal) {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setLocation(URI.create(rerouteURL + "/home"));
        if (userRepo.findByGoogleId(principal.getName()).isEmpty()) {
            OAuth2User userFromOauth = ((OAuth2AuthenticationToken) principal).getPrincipal();
            User newUser = new User();
            newUser.setEmail(userFromOauth.getAttribute("email"));
            newUser.setName(userFromOauth.getAttribute("name"));
            newUser.setImg(userFromOauth.getAttribute("picture"));
            userRepo.save(newUser);
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .headers(responseHeaders)
                .body(" ...|\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"| |\\\n" +
                        "... |Холодное пиво! ||\"\"\\__,_\n" +
                        "... |_____________ |||_|__|_ )\n" +
                        "... *(@)|(@)\"\"\"*******(@)\"");
    }

}

