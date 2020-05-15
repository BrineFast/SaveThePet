package com.savethepet.exception_handlers.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception for cases when there is no pet
 *
 * @author Pavel Yudin
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class PetNotFoundException extends RuntimeException{

    public PetNotFoundException(String message) {
        super(message);
    }

}
