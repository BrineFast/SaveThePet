package com.savethepet.exception_handlers.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception when user tries link already used OAuth2 account
 *
 * @author Alexey Klimov
 */
@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class AccountAlreadyUsingException extends RuntimeException {

    public AccountAlreadyUsingException(String message) {
        super(message);
    }
}
