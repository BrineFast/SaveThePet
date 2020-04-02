package com.savethepet.model.entity;

import org.springframework.security.core.GrantedAuthority;

/**
 * all roles that exists in application
 *
 * @author Alexey Klimov
 */
public enum Role implements GrantedAuthority {
    USER,
    ADMIN;

    @Override
    public String getAuthority() {
        return name();
    }
}
