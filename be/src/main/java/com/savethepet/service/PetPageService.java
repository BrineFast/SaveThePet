package com.savethepet.service;

import com.savethepet.exception_handlers.exception.PetNotFoundException;
import com.savethepet.exception_handlers.exception.UserNotFoundException;
import com.savethepet.model.dao.PetRepo;
import com.savethepet.model.dto.user.PetInfoChangeDTO;
import com.savethepet.model.dto.user.UserInfoChangeDTO;
import com.savethepet.model.entity.Pet;
import com.savethepet.model.entity.User;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Service for Pet Page
 *
 * @author Pavel Yudin
 */
public class PetPageService {

    private static final String userWithOauthId = "Pet with id = ";
    private static final String notFound = " not found";

    @Autowired
    private PetRepo petRepo;

    /**
     * Gets Pet by Id and throws exception if Pet didn`t found
     *
     * @param id
     * @return
     */
    public Pet getPetById(Long id) {
        return petRepo.findById(id).orElseThrow(() ->
                new PetNotFoundException("Pet with id = " + id.toString() + notFound));
    }

    /**
     * Changes pet in db with information from dto
     *
     * @param petInfoChangeDTO
     * @param id
     */
    public void updatePetFromDto(PetInfoChangeDTO petInfoChangeDTO, Long id) {
        Pet changedPet = getPetById(id);
        changedPet.setBreed(petInfoChangeDTO.getBreed());
        changedPet.setGender(petInfoChangeDTO.getGender());
        changedPet.setStatus(petInfoChangeDTO.getStatus());
        petRepo.save(changedPet);
    }


}
