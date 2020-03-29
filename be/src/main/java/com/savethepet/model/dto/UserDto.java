package com.savethepet.model.dto;

import com.savethepet.model.entity.User;
import lombok.Data;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * DTO for registration new users
 */
@Data
public class UserDto {

    @NotNull
    @NotEmpty
    private String name;

    @NotNull
    @NotEmpty
    private String password;

    @NotNull
    @NotEmpty
    @Email
    private String email;

    /**
     *Method for creating USER from dto
     */
    public static User userFromDto(UserDto dto, PasswordEncoder passwordEncoder) {
        return new User(
                dto.getEmail(),
                passwordEncoder.encode(dto.getPassword()),
                dto.getName()
        );
    }
}
