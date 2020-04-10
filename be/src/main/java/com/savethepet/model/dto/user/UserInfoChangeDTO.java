package com.savethepet.model.dto.user;

import lombok.Data;

@Data
public class UserInfoChangeDTO {

    private String email;

    private String googleId;

    private String yandexId;

    private String facebookId;

    private String password;

    private String name;

    private String phoneNumber;

    private String img;

    private String location;
}
