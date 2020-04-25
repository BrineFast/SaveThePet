package com.savethepet.model.dto.user;

import com.savethepet.model.entity.Gender;
import com.savethepet.model.entity.Position;
import com.savethepet.model.entity.Status;
import com.sun.istack.Nullable;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Data
public class PetInfoChangeDTO {

    @NotBlank
    @Length(max = 30)
    private String breed;

    @NotBlank
    private Gender gender;

    @NotBlank
    @Length(max = 255)
    private String img;

    @NotBlank
    private Position position;

    @NotBlank
    private Status status;

}
