package com.savethepet.controller;

import com.savethepet.model.dto.pet.PetInfoChangeDTO;
import com.savethepet.model.dto.shelter.ShelterInfoDTO;
import com.savethepet.model.entity.Pet;
import com.savethepet.model.entity.Shelter;
import com.savethepet.service.PetPageService;
import com.savethepet.service.ShelterPageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

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
     * Adding new shelter
     *
     * @param shelterInfoDTO
     *
     */
    @ApiOperation("Adding the shelter")
    @ApiResponses(value = {
            @ApiResponse(code = 302, message = "redirected"),
            @ApiResponse(code = 404, message = "Pet with this that id not exists"),
            @ApiResponse(code = 403, message = "Current user don`t have access to change this info"),
            @ApiResponse(code = 400, message = "Unknown client id")
    })
    @PostMapping("/createShelter")
    public Shelter addShelter(@RequestBody @Valid ShelterInfoDTO shelterInfoDTO) {
        return shelterPageService.addingShelter(shelterInfoDTO);
    }
}
