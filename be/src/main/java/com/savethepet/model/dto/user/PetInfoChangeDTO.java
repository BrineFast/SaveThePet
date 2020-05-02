package com.savethepet.model.dto.user;

import com.savethepet.model.entity.Gender;
import com.savethepet.model.entity.PetLocation;
import com.savethepet.model.entity.Status;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Data
public class PetInfoChangeDTO {

    @NotBlank
    @Length(max = 30)
    private String breed;

    private Gender gender;

    @NotBlank
    @Length(max = 255)
    private String img;
    
    private PetLocation location;

    private Status status;

}
