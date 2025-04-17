package com.greenwashing.digibooky.domain;

public enum UserRole {
    MEMBER("Member"),
    LIBRARIANr("Librarian"),
    ADMIN("Admin");

    private final String roleValue;

    UserRole(String roleValue) {
        this.roleValue = roleValue;
    }

    public String getRoleValue() {
        return roleValue;
    }

    @Override
    public String toString() {
        return roleValue;
    }
}
