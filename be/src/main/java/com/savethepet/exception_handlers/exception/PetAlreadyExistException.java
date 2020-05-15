package com.savethepet.exception_handlers.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception for attempts to add an existing pet
 *
 * @author Pavel Yudin
 */
@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class PetAlreadyExistException extends RuntimeException{

    public PetAlreadyExistException(String message) {
        super(message);
    }

}
