package com.savethepet.exception_handlers.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception for cases when there is no shelter
 *
 * @author Pavel Yudin
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class ShelterNotFoundException extends RuntimeException {

    public ShelterNotFoundException(String message) {
        super(message);
    }

}
