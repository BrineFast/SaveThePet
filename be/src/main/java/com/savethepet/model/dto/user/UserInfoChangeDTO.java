package com.savethepet.model.dto.user;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * DTO with user change DTO
 *
 * @author Pavel Yudin
 */

@Data
public class UserInfoChangeDTO {

    @Email(regexp = "^(?:[a-zA-Z0-9_'^&/+-])" +
            "+(?:\\.(?:[a-zA-Z0-9_'^&/+-])+)" +
            "*@(?:(?:\\[?(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?))\\.)" +
            "{3}(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\]?)|(?:[a-zA-Z0-9-]+\\.)" +
            "+(?:[a-zA-Z]){2,}\\.?)$")
    @Length(max=40)
    private String email;

    @NotBlank
    @Length(max = 255)
    private String password;

    @NotBlank
    @Length(max = 30)
    private String name;

    @Pattern(regexp = "((8|\\+7)[\\- ]?)?(\\(?\\d{3}\\)?[\\- ]?)?[\\d\\- ]{7,10}$")
    private String phoneNumber;

    @NotBlank
    @Length(max = 255)
    private String img;

    @NotBlank
    @Length(max = 255)
    private String location;
}
