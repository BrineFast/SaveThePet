package com.savethepet.controller;

import com.savethepet.model.dto.user.PetInfoChangeDTO;
import com.savethepet.model.dto.user.PetInfoDTO;
import com.savethepet.model.entity.Pet;
import com.savethepet.service.PetPageService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

/**
 * Controller to requests to info about Pet
 *
 * @author Pavel Yudin
 */
public class PetPageController {

    @Autowired
    private PetPageService petPageService;

    /**
     * Returns info about Pets
     *
     * @param id
     * @return
     */
    @ApiOperation("Return info about pet")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Pet info returned"),
            @ApiResponse(code = 404, message = "Pet with this that id not exists")
    })
    public PetInfoDTO getPetInfo(@PathVariable("pet_id") Long id) {
        Pet pet = petPageService.getPetById(id);
        return PetInfoDTO.builder()
                .breed(pet.getBreed())
                .gender(pet.getGender())
                .img(pet.getImg())
                .position(pet.getPosition())
                .status(pet.getStatus())
                .build();
    }

    /**
     * Changes info about pet
     *
     * @param id
     * @param petInfoChangeDTO
     * @return
     */
    @ApiOperation("Changes pet information")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Info changed successfully"),
            @ApiResponse(code = 403, message = "Current user don`t have access to change this info"),
            @ApiResponse(code = 406, message = "The transmitted information is not valid"),
            @ApiResponse(code = 404, message = "Pet with this that id not exists")
    }
    )
    @PatchMapping("/pet/{pet_id}")
    public void changePetInfo(@PathVariable("pet_id") Long id, @RequestBody @Valid PetInfoChangeDTO petInfoChangeDTO) {
        petPageService.updatePetFromDto(petInfoChangeDTO, id);
    }


}
