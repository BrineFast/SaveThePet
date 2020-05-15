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

import java.util.List;

/**
 * Service for Pet Page
 *
 * @author Pavel Yudin
 */
@Service
public class PetPageService {

    private static final String notFound = " not found";

    @Autowired
    private PetRepo petRepo;

    @Autowired
    private UserRepo userRepo;

    /**
     * Gets Pet by Id and throws exception if Pet didn`t found
     *
     * @param pet_id
     * @return
     */
    public Pet getPetById(Long pet_id) {
        return petRepo.findById(pet_id).orElseThrow(() ->
                new PetNotFoundException("Pet with id = " + pet_id.toString() + notFound));
    }

    /**
     * Gets Pets by User Id and throws exception if Pet didn`t found
     *
     * @param id
     * @return
     */
    public List<Pet> getPetsByUserId(Long id) {
        User user = userRepo.findById(id).orElseThrow(() ->
                new UserNotFoundException("User with id=" + id.toString() + notFound));
        return petRepo.findByUser(user);
    }

    /**
     * Get pets list with filters for the search
     *
     * @param breed
     * @return
     */
    public List<Pet> getPetsFromBreed(String breed) throws PetNotFoundException{
        if (petRepo.findAllByBreed(breed).size() == 0)
            throw new PetNotFoundException("Pets with breed = " + breed + notFound);
        return petRepo.findAllByBreed(breed);
    }

    /**
     * Changes pet in db with information from dto
     *
     * @param petInfoChangeDTO
     * @param id
     */
    public Pet updatePetFromDto(PetInfoChangeDTO petInfoChangeDTO, Long id) {
        Pet changedPet = getPetById(id);
        changedPet.setBreed(petInfoChangeDTO.getBreed());
        changedPet.setGender(petInfoChangeDTO.getGender());
        changedPet.setImg(petInfoChangeDTO.getImg());
        changedPet.setStatus(petInfoChangeDTO.getStatus());
        petRepo.save(changedPet);
        return changedPet;
    }

    /**
     * Adding the pet
     *
     * @param petInfoDTO
     * @param user_id
     */
    public Pet addingPet(PetInfoChangeDTO petInfoDTO, Long user_id) {
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
        return petFromDTO;
    }

    /**
     * Delete pet
     *
     * @param pet_id
     */
    public void deletePet(Long pet_id) throws PetNotFoundException{
        if (petRepo.deleteAllById(pet_id) == null);
            throw new PetNotFoundException("Pet with id = " + pet_id.toString() + notFound);
    }
}
