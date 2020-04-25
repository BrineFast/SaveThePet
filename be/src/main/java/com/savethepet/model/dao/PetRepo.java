package com.savethepet.model.dao;

import com.savethepet.model.entity.Pet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repository for PETS
 *
 * @author Pavel Yudin
 */
public interface PetRepo extends JpaRepository<Pet, Long> {

    Optional<Pet> findById(String id);

}
