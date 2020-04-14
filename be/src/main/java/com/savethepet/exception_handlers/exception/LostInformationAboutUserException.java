package com.savethepet.exception_handlers.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception if user can delete all auth info
 *
 * @author Alexey Klimov
 */
@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class LostInformationAboutUserException extends RuntimeException {

    public LostInformationAboutUserException(String message) {
        super(message);
    }
}
