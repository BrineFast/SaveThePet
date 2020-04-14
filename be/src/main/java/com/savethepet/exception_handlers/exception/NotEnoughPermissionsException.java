package com.savethepet.exception_handlers.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception when user tries to get or change a resource that is not available to them
 *
 * @author Alexey Klimov
 */
@ResponseStatus(code = HttpStatus.FORBIDDEN)
public class NotEnoughPermissionsException extends RuntimeException {

    public NotEnoughPermissionsException(String message) {
        super(message);
    }
}
