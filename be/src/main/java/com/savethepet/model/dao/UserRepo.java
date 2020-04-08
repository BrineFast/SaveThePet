package com.savethepet.model.dao;

import com.savethepet.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

/**
 * Repository for USERS
 *
 * @author Alexey Klimov
 */
public interface UserRepo extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    @Query("SELECT u FROM User  u" +
            " where(:id is null or u.googleId = :id)" +
            " and(:id is null or u.yandexId = :id)" +
            " and(:id is null or u.facebookId = :id)")
    Optional<User> findByAuthId(String id);
}
