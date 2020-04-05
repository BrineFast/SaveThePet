package com.savethepet.model.entity;

import org.springframework.security.core.GrantedAuthority;

/**
 * All roles that exists in application
 *
 * @author Alexey Klimov
 */
public enum Role implements GrantedAuthority {
    USER;

    @Override
    public String getAuthority() {
        return name();
    }
}
