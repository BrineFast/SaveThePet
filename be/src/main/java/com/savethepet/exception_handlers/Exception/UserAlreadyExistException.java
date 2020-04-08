package com.savethepet.exception_handlers.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception for attempts to register an existing user
 *
 * @author Alexey Klimov
 */
@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class UserAlreadyExistException extends RuntimeException {

    public UserAlreadyExistException(String message) {
        super(message);
    }

}
