package com.savethepet.controller;

import com.savethepet.model.dto.user.PetInfoChangeDTO;
import com.savethepet.model.dto.user.PetInfoDTO;
import com.savethepet.model.entity.Pet;
import com.savethepet.service.PetPageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.security.Principal;

/**
 * Controller to requests to info about Pet
 *
 * @author Pavel Yudin
 */
@Api
@RestController
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
    @GetMapping("pet/{pet_id}")
    public PetInfoDTO getPetInfo(@PathVariable("pet_id") Long id) {
        Pet pet = petPageService.getPetById(id);
        return PetInfoDTO.builder()
                .breed(pet.getBreed())
                .gender(pet.getGender())
                .img(pet.getImg())
                .location(pet.getLocation())
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

    /**
     * Adding new pet
     *
     * @param petInfoDTO
//     * @param id
     * @param user_id
     */
    @ApiOperation("Adding the pet")
    @ApiResponses(value = {
            @ApiResponse(code = 302, message = "redirected"),
            @ApiResponse(code = 404, message = "Pet with this that id not exists"),
            @ApiResponse(code = 403, message = "Current user don`t have access to change this info"),
            @ApiResponse(code = 400, message = "Unknown client id")
    })
    @PostMapping("/createPet")
    public void addPet(@RequestBody @Valid PetInfoDTO petInfoDTO,
                       Long user_id) {
        petPageService.addingPet(petInfoDTO, user_id);
    }

    /**
     * Delete pet
     *
     * @param principal
     * @param id
     */
    @ApiOperation("Delete the pet")
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "Pet with this that id not exists"),
            @ApiResponse(code = 403, message = "Current user don`t have access to change this info"),
            @ApiResponse(code = 400, message = "Unknown client id"),
            @ApiResponse(code = 406, message = "Can`t delete this pet because user lost access to account")
    })
    @DeleteMapping("/pet/{pet_id}")
    public void deletePet(@ApiIgnore Principal principal, @PathVariable("pet_id") Long id) {
        petPageService.deletePet(id);
    }
}
