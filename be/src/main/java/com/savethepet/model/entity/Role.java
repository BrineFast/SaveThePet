package com.savethepet.model.entity;

import org.springframework.security.core.GrantedAuthority;

/**
 * @author Alexey Klimov
 * all roles that exists in application
 */
public enum Role implements GrantedAuthority {
    USER,
    ADMIN;

    @Override
    public String getAuthority() {
        return name();
    }
}
