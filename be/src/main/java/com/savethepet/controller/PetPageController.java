package com.savethepet.controller;

import com.savethepet.model.dto.pet.PetInfoChangeDTO;
import com.savethepet.model.dto.pet.PetInfoDTO;
import com.savethepet.model.entity.Pet;
import com.savethepet.model.entity.Status;
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
        return PetInfoDTO.getDtoFromPet(petPageService.getPetById(id));
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
    public Pet changePetInfo(@PathVariable("pet_id") Long id, @RequestBody @Valid PetInfoChangeDTO petInfoChangeDTO) {
        return petPageService.updatePetFromDto(petInfoChangeDTO, id);
    }

    /**
     * Adding new pet
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
    @GetMapping("/pet")
    public List<PetInfoDTO> getPets(@RequestParam(name = "breed", required = false) String breed,
                                    @RequestParam(name = "status", required = false) Status status) {
        return petPageService.getPets(breed, status);
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
    public Pet addPet(@RequestBody @Valid PetInfoChangeDTO petInfoChangeDTO,
                                   Long user_id) {
        return petPageService.addingPet(petInfoChangeDTO, user_id);
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
