package com.savethepet.model.dto.user;

import com.savethepet.model.entity.Pet;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.xml.stream.Location;

/**
 * DTO with user info
 *
 * @author Alexey Klimov
 */
@AllArgsConstructor
@Builder
@Data
public class UserInfoDTO {

    private String email;

    private String name;

    private String phoneNumber;

    private String img;

    private String location;

}
