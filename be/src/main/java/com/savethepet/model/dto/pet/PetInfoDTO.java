package com.savethepet.model.dto.pet;

import com.savethepet.model.dto.user.UserInfoDTO;
import com.savethepet.model.entity.*;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * DTO with pet info
 *
 * @author Pavel Yudin
 */
@Builder
@Data
public class PetInfoDTO {

    private UserInfoDTO user;

    private Long shelter_id;

    @NotBlank
    private String breed;

    private Gender gender;

    @NotBlank
    private String img;

    private Location location;

    private Status status;

    public static PetInfoDTO getDtoFromPet(Pet pet) {
        User user = pet.getUser();
        return PetInfoDTO.builder()
                .shelter_id(pet.getShelterId())
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