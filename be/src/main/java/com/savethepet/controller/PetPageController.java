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
import java.util.List;

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
                .user(pet.getUser())
                .breed(pet.getBreed())
                .gender(pet.getGender())
                .img(pet.getImg())
                .location(pet.getLocation())
                .status(pet.getStatus())
                .build();
    }

    /**
     * Returns info about user pets
     *
     * @param id
     * @return
     */
    @ApiOperation("Return info about user pets")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "User info returned"),
            @ApiResponse(code = 404, message = "User with this that id not exists")
    })
    @GetMapping("user/{user_id}/pets")
    public List<Pet> getUserPets(@PathVariable("user_id") Long id) {
        return petPageService.getPetsByUserId(id);
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
    public PetInfoChangeDTO changePetInfo(@PathVariable("pet_id") Long id, @RequestBody @Valid PetInfoChangeDTO petInfoChangeDTO) {
        petPageService.updatePetFromDto(petInfoChangeDTO, id);
        return petInfoChangeDTO;
    }

    /**
     * Adding new pet
     *
     *
     * @param breed
     */
    @ApiOperation("Search by breed of pet")
    @ApiResponses(value = {
            @ApiResponse(code = 302, message = "Redirected"),
            @ApiResponse(code = 404, message = "Pet with this that id not exists"),
            @ApiResponse(code = 403, message = "Current user don`t have access to change this info"),
            @ApiResponse(code = 400, message = "Unknown client id")
    })
    @GetMapping("/pet/{pet_id}/breed")
    public List<Pet> getPetsBreed(String breed) {
        return petPageService.getPetsFromBreed(breed);
    }

    /**
     * Adding new pet
     *
     * @param petInfoChangeDTO
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
    public PetInfoChangeDTO addPet(@RequestBody @Valid PetInfoChangeDTO petInfoChangeDTO,
                       Long user_id) {
        petPageService.addingPet(petInfoChangeDTO, user_id);
        return petInfoChangeDTO;
    }

    /**
     * Delete pet
     *
     * @param principal
     * @param pet_id
     */
    @ApiOperation("Delete the pet")
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "Pet with this that id not exists"),
            @ApiResponse(code = 403, message = "Current user don`t have access to change this info"),
            @ApiResponse(code = 400, message = "Unknown client id"),
    })
    @DeleteMapping("/pet/{pet_id}")
    public void deletePet(@ApiIgnore Principal principal, @PathVariable("pet_id") Long pet_id) {
        petPageService.deletePet(pet_id);
    }
}
