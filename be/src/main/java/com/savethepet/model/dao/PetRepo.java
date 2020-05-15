package com.savethepet.model.dao;

import com.savethepet.model.dto.user.UserInfoDTO;
import com.savethepet.model.entity.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Repository for PETS
 *
 * @author Pavel Yudin
 */
public interface PetRepo extends JpaRepository<Pet, Long> {

    Long deleteAllById(Long pet_id);

    List<Pet> findByUser(UserInfoDTO user);

    @Query("SELECT p from Pet p where :breed is null or p.breed = :breed")
    List<Pet> findAllByBreed(@Param("breed") String breed);

}
