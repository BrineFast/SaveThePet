package com.savethepet.model.dto.user;

import com.savethepet.model.entity.Gender;
import com.savethepet.model.entity.Position;
import com.savethepet.model.entity.Status;
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

    private String breed;

    private Gender gender;

    private String img;

    private Position position;

    private Status status;

}
