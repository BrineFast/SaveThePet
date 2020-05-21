package com.savethepet.service;

import com.savethepet.exception_handlers.exception.ShelterNotFoundException;
import com.savethepet.model.dao.ShelterRepo;
import com.savethepet.model.dto.shelter.ShelterInfoDTO;
import com.savethepet.model.entity.Shelter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
     * Gets Shelter by Id and throws exception if Shelter didn`t found
     *
     * @param shelter_id
     * @return
     */
    public Shelter getShelterById(Long shelter_id) {
        return shelterRepo.findById(shelter_id).orElseThrow(() ->
                new ShelterNotFoundException("Shelter with id = " + shelter_id.toString() + notFound));
    }

    /**
     * Get shelters list with filters for the search
     *
     *
     * @return
     */
    public List<ShelterInfoDTO> getShelters() throws ShelterNotFoundException {
        return shelterRepo.findAll().stream()
                .map(ShelterInfoDTO::getDTOFromShelter).collect(Collectors.toList());
    }

    /**
     * Changes shelter in db with information from dto
     *
     * @param shelterInfoDTO
     * @param shelter_id
     * @return
     */
    public Shelter updateShelterFromDto(ShelterInfoDTO shelterInfoDTO, Long shelter_id) {
        Shelter changedShelter = getShelterById(shelter_id);
        changedShelter.setDescription(shelterInfoDTO.getDescription());
        changedShelter.setLocation(shelterInfoDTO.getLocation());
        changedShelter.setName(shelterInfoDTO.getName());
        changedShelter.setTimeOfWork(shelterInfoDTO.getTimeOfWork());
        shelterRepo.save(changedShelter);
        return changedShelter;
    }

    /**
     * Adding the shelter
     *
     * @param shelterInfoDTO
     *
     */
    public Shelter addingShelter(ShelterInfoDTO shelterInfoDTO) {
        Shelter shelterFromDTO = new Shelter();
        shelterFromDTO.setDescription(shelterInfoDTO.getDescription());
        shelterFromDTO.setName(shelterInfoDTO.getName());
        shelterFromDTO.setLocation(shelterInfoDTO.getLocation());;
        shelterFromDTO.setTimeOfWork(shelterInfoDTO.getTimeOfWork());
        shelterRepo.save(shelterFromDTO);
        return shelterFromDTO;
    }

    /**
     * Delete the shelter
     *
     * @param shelter_id
     */
    public void deleteShelter(Long shelter_id) throws ShelterNotFoundException {
        if (shelterRepo.deleteAllById(shelter_id) == null)
            throw new ShelterNotFoundException("Shelter with id = " + shelter_id.toString() + notFound);
    }

    /*        User user = userRepo.findById(user_id).orElseThrow(() ->
                new UserNotFoundException("User with id = " + user_id.toString() + notFound));*/
}
