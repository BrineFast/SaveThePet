package com.savethepet.model.dto.user;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * DTO for registration new users
 *
 * @author Alexey Klimov
 */
@Data
@Builder
public class registrationUserDTO {

    @Email
    @Pattern(regexp = "^(?:[a-zA-Z0-9_'^&/+-])" +
            "+(?:\\.(?:[a-zA-Z0-9_'^&/+-])+)" +
            "*@(?:(?:\\[?(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?))\\.)" +
            "{3}(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\]?)|(?:[a-zA-Z0-9-]+\\.)" +
            "+(?:[a-zA-Z]){2,}\\.?)$")
    private String email;

    @NotBlank
    private String password;

    @NotBlank
    private String name;

}
