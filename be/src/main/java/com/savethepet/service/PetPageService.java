package com.savethepet.service;

import com.savethepet.exception_handlers.exception.PetNotFoundException;
import com.savethepet.exception_handlers.exception.UserNotFoundException;
import com.savethepet.model.dao.PetRepo;
import com.savethepet.model.dao.UserRepo;
import com.savethepet.model.dto.pet.PetInfoChangeDTO;
import com.savethepet.model.dto.pet.PetInfoDTO;
import com.savethepet.model.entity.Pet;
import com.savethepet.model.entity.Status;
import com.savethepet.model.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
    public List<PetInfoDTO> getPetsByUser(Long id) {
        User user = userRepo.findById(id).orElseThrow(() ->
                new UserNotFoundException("User with id=" + id.toString() + notFound));
        return petRepo.findByUser(user).stream()
                .map(PetInfoDTO::getDtoFromPet).collect(Collectors.toList());
    }

    /**
     * Get pets list with filters for the search
     *
     * @param breed
     * @param status
     * @return
     */
    public List<PetInfoDTO> getPets(String breed, Status status) throws PetNotFoundException {
        return petRepo.findAll(breed, status, null).stream()
                .map(PetInfoDTO::getDtoFromPet).collect(Collectors.toList());
    }

    /**
     * Changes pet in db with information from dto
     *
     * @param petInfoChangeDTO
     * @param id
     * @return
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
     * @param shelter_id
     * @return
     */
    public Pet addingPet(PetInfoChangeDTO petInfoDTO, Long user_id, Long shelter_id) {
        User user = userRepo.findById(user_id).orElseThrow(() ->
                new UserNotFoundException("User with id = " + user_id.toString() + notFound));
        Pet petFromDTO = new Pet();
        if (shelter_id == null)
            petFromDTO.setUser(user);
        else
            petFromDTO.setShelterId(shelter_id);
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
    public void deletePet(Long pet_id) throws PetNotFoundException {
        if (petRepo.deleteAllById(pet_id) == null) ;
        throw new PetNotFoundException("Pet with id = " + pet_id.toString() + notFound);
    }
}
