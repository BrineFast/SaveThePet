package com.savethepet.exception_handlers.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception when user tries make OAuth2 with unknown client
 *
 * @author Alexey Klimov
 */
@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class ClientRegistrationIdNotFound extends RuntimeException {

    public ClientRegistrationIdNotFound(String message) {
        super(message);
    }
}
