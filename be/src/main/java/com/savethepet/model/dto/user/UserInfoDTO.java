package com.savethepet.model.dto.user;

import com.savethepet.model.entity.Pet;
import com.savethepet.model.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

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

    public static UserInfoDTO getValue(User user) {
        return
                UserInfoDTO.builder()
                        .email(user.getEmail())
                        .location(user.getLocation())
                        .img(user.getImg())
                        .name(user.getName())
                        .phoneNumber(user.getPhoneNumber())
                        .build();
    }

}
