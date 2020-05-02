package com.savethepet.service;

import com.savethepet.exception_handlers.exception.PetNotFoundException;
import com.savethepet.exception_handlers.exception.UserNotFoundException;
import com.savethepet.model.dao.PetRepo;
import com.savethepet.model.dao.UserRepo;
import com.savethepet.model.dto.user.PetInfoChangeDTO;
import com.savethepet.model.entity.Pet;
import com.savethepet.model.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * Service for Pet Page
 *
 * @author Pavel Yudin
 */
@Service
public class PetPageService {

    private static final String userWithOauthId = "Pet with id = ";
    private static final String notFound = " not found";
    private static final String alreadyExist = " already exist";

    @Autowired
    private PetRepo petRepo;

    @Autowired
    private UserRepo userRepo;

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
     * Gets Pet by User Id and throws exception if Pet didn`t found
     *
     * @param id
     * @return
     */
/*    public Pet getPetByUserId(Long id) {
        petRepo.findById(id).orElseThrow(() ->
                new PetNotFoundException("Pet with id = " + id.toString() + notFound))
        if (userRepo.findById(id).orElseThrow(() ->
                new PetNotFoundException("Pet with id = " + id.toString() + notFound)).get
        return petRepo.findById(id).orElseThrow(() ->
                new PetNotFoundException("Pet with id = " + id.toString() + notFound));
    }*/

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

    /**
     * Adding the pet
     *
     * @param petInfoDTO
     * @param user_id
     */
    public void addingPet(PetInfoChangeDTO petInfoDTO, Long user_id) {
        User user = userRepo.findById(user_id).orElseThrow(() ->
                new UserNotFoundException("User with id = " + user_id.toString() + notFound));
        Pet petFromDTO = new Pet();
        petFromDTO.setUser(user);
        petFromDTO.setBreed(petInfoDTO.getBreed());
        petFromDTO.setGender(petInfoDTO.getGender());
        petFromDTO.setImg(petInfoDTO.getImg());
        petFromDTO.setLocation(petInfoDTO.getLocation());
        petFromDTO.setStatus(petInfoDTO.getStatus());
        petRepo.save(petFromDTO);
    }

    /**
     * Delete pet
     *
     * @param id
     */
    public void deletePet(Long id) {
        petRepo.findById(id).orElseThrow(() ->
                new PetNotFoundException("Pet with id = " + id.toString() + notFound));
        petRepo.deleteById(id);
    }
}
