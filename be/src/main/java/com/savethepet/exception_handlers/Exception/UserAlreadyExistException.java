package com.savethepet.exception_handlers.Exception;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Alexey Klimov
 * Exception for attempts to register an existing user
 */

@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
@NoArgsConstructor
@Data
public class UserAlreadyExistException extends Exception {

    private final String message = "User already exists";
}
