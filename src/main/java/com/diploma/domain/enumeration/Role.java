package com.diploma.domain.enumeration;

import lombok.Getter;

@Getter
public enum Role {
    USER("ROLE_USER"),
    ANALYST("ROLE_ANALYST"),
    ADMIN("ROLE_ADMIN");

    private String value;

    Role(String value) {
        this.value = value;
    }
}
