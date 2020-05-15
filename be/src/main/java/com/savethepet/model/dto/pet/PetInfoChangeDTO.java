package com.savethepet.model.dto.pet;

import com.savethepet.model.entity.Gender;
import com.savethepet.model.entity.Location;
import com.savethepet.model.entity.Status;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 * DTO with pet change DTO
 *
 * @author Alexey Klimov
 */
@Data
public class PetInfoChangeDTO {

    @NotBlank
    @Length(max = 30)
    private String breed;

    private Gender gender;

    @NotBlank
    @Length(max = 255)
    private String img;

    private Location location;

    private Status status;

}
