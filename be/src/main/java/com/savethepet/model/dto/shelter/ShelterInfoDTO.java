package com.savethepet.model.dto.shelter;

import com.savethepet.model.entity.Location;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * DTO with shelter info
 *
 * @author Pavel Yudin
 */
@Builder
@Data
public class ShelterInfoDTO {

    @NotBlank
    private String name;

    @NotBlank
    private String description;

/*    @OneToMany
    private Set<Pet> pets;*/

    private transient Location location;

    @NotBlank
    private String timeOfWork;

}
