package com.savethepet.model.dao;

import com.savethepet.model.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Alexey Klimov
 * Reprository for USERS
 */
@Repository
public interface UserRepo extends CrudRepository<User, Long> {
    User findByUsername(String username);
}
