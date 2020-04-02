package com.savethepet.model.dto;

import com.savethepet.model.entity.User;
import lombok.Builder;
import lombok.Data;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

/**
 * DTO for registration new users
 *
 * @author Alexey Klimov
 */
@Data
@Builder
public class UserDto {

    @Email
    private String email;

    @NotBlank
    private String password;

    @NotBlank
    private String name;

    /**
     * Creates new User from dto
     *
     * @param dto
     * @param passwordEncoder
     * @return
     */
    public static User userFromDto(UserDto dto, PasswordEncoder passwordEncoder) {
        return new User(
                dto.getEmail(),
                passwordEncoder.encode(dto.getPassword()),
                dto.getName()
        );
    }
}
