package com.savethepet.model.dao;

import com.savethepet.model.entity.Shelter;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository for SHELTERS
 *
 * @author Pavel Yudin
 */
public interface ShelterRepo extends JpaRepository<Shelter, Long> {

}
