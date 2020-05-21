package com.savethepet.controller;

import com.savethepet.model.dto.pet.PetInfoChangeDTO;
import com.savethepet.model.dto.pet.PetInfoDTO;
import com.savethepet.model.dto.shelter.ShelterInfoDTO;
import com.savethepet.model.entity.Pet;
import com.savethepet.model.entity.Shelter;
import com.savethepet.model.entity.Status;
import com.savethepet.service.PetPageService;
import com.savethepet.service.ShelterPageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

/**
 * Controller to requests to info about Shelter
 *
 * @author Pavel Yudin
 */
@Api
@RestController
public class ShelterPageController {

    @Autowired
    private ShelterPageService shelterPageService;

    /**
     * Returns info about Shelter
     *
     * @param shelter_id
     * @return
     */
    @ApiOperation("Return info about shelter")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Shelter info returned"),
            @ApiResponse(code = 404, message = "Shelter with this that id not exists")
    })
    @GetMapping("shelter/{shelter_id}")
    public ShelterInfoDTO getShelterInfo(@PathVariable("shelter_id") Long shelter_id) {
       return ShelterInfoDTO.getDTOFromShelter(shelterPageService.getShelterById(shelter_id));
    }

    /**
     * Changes info about shelter
     *
     * @param shelter_id
     * @param shelterInfoDTO
     * @return
     */
    @ApiOperation("Changes shelter information")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Info changed successfully"),
            @ApiResponse(code = 406, message = "The transmitted information is not valid"),
            @ApiResponse(code = 404, message = "Shelter with this that id not exists")
    }
    )
    @PatchMapping("/shelter/{shelter_id}")
    public Shelter changeShelterInfo(@PathVariable("shelter_id") Long shelter_id, @RequestBody @Valid ShelterInfoDTO shelterInfoDTO) {
        return shelterPageService.updateShelterFromDto(shelterInfoDTO, shelter_id);
    }

    /**
     * Get all shelters
     *
     */
    @ApiOperation("Get all shelters")
    @ApiResponses(value = {
            @ApiResponse(code = 302, message = "Redirected"),
            @ApiResponse(code = 400, message = "Unknown client id")
    })
    @GetMapping("/shelter")
    public List<ShelterInfoDTO> getShelters() {
        return shelterPageService.getShelters();
    }

    /**
     * Adding new shelter
     *
     * @param shelterInfoDTO
     * @return
     */
    @ApiOperation("Adding the shelter")
    @ApiResponses(value = {
            @ApiResponse(code = 302, message = "redirected"),
            @ApiResponse(code = 400, message = "Unknown client id")
    })
    @PostMapping("/createShelter")
    public Shelter addShelter(@RequestBody @Valid ShelterInfoDTO shelterInfoDTO) {
        return shelterPageService.addingShelter(shelterInfoDTO);
    }

    /**
     * Delete the shelter
     *
     * @param principal
     * @param shelter_id
     */
    @ApiOperation("Delete the shelter")
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "Shelter with this that id not exists"),
            @ApiResponse(code = 400, message = "Unknown client id"),
    })
    @DeleteMapping("/shelter/{shelter_id}")
    public void deleteShelter(@ApiIgnore Principal principal, @PathVariable("shelter_id") Long shelter_id) {
        shelterPageService.deleteShelter(shelter_id);
    }
}
