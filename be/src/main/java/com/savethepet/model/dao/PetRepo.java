package com.savethepet.model.dao;

import com.savethepet.model.entity.Pet;
import com.savethepet.model.entity.Status;
import com.savethepet.model.entity.User;
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

    @Query("SELECT p from Pet p where (:breed is null or p.breed = :breed) and (:status is null or p.status= :status) " +
            "and (:user_id is null or p.user.id = :user_id)")
    List<Pet> findAll(@Param("breed") String breed, @Param("status") Status status, @Param("user_id") Long userId);

}
