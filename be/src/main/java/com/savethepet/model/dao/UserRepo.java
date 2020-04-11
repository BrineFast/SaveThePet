package com.savethepet.model.dao;

import com.savethepet.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

/**
 * Repository for USERS
 *
 * @author Alexey Klimov
 */
public interface UserRepo extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    Optional<User> findByFacebookId(String id);

    Optional<User> findByYandexId(String id);

    Optional<User> findByGoogleId(String id);

}