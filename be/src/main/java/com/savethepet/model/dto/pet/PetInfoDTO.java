package com.savethepet.model.dto.pet;

import com.savethepet.model.dto.user.UserInfoDTO;
import com.savethepet.model.entity.*;
import lombok.Builder;
import lombok.Data;

/**
 * DTO with pet info
 *
 * @author Pavel Yudin
 */
@Builder
@Data
public class PetInfoDTO {

    private UserInfoDTO user;

    private String breed;

    private Gender gender;

    private String img;

    private PetLocation location;

    private Status status;

    public static PetInfoDTO getDtoFromPet(Pet pet) {
        User user = pet.getUser();
        return PetInfoDTO.builder()
                .status(pet.getStatus())
                .location(pet.getLocation())
                .img(pet.getImg())
                .breed(pet.getBreed())
                .user(UserInfoDTO.builder()
                        .phoneNumber(user.getPhoneNumber())
                        .location(user.getLocation())
                        .name(user.getName())
                        .img(user.getImg())
                        .email(user.getEmail())
                        .build()).build();
    }
}