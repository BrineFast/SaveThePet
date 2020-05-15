package com.savethepet.service;

import com.savethepet.exception_handlers.exception.UserNotFoundException;
import com.savethepet.model.dao.PetRepo;
import com.savethepet.model.dao.ShelterRepo;
import com.savethepet.model.dto.pet.PetInfoChangeDTO;
import com.savethepet.model.dto.shelter.ShelterInfoDTO;
import com.savethepet.model.entity.Pet;
import com.savethepet.model.entity.Shelter;
import com.savethepet.model.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service for Shelter Page
 *
 * @author Pavel Yudin
 */
@Service
public class ShelterPageService {

    private static final String notFound = " not found";

    @Autowired
    private ShelterRepo shelterRepo;

    /**
     * Adding the shelter
     *
     * @param shelterInfoDTO
     *
     */
    public Shelter addingShelter(ShelterInfoDTO shelterInfoDTO) {
/*        User user = userRepo.findById(user_id).orElseThrow(() ->
                new UserNotFoundException("User with id = " + user_id.toString() + notFound));*/
        Shelter shelterFromDTO = new Shelter();
        shelterFromDTO.setDescription(shelterInfoDTO.getDescription());
        shelterFromDTO.setName(shelterInfoDTO.getName());
        shelterFromDTO.setLocation(shelterInfoDTO.getLocation());;
        shelterFromDTO.setTimeOfWork(shelterInfoDTO.getTimeOfWork());
        shelterRepo.save(shelterFromDTO);
        return shelterFromDTO;
    }
}
