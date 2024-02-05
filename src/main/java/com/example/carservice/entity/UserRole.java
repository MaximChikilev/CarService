package com.example.carservice.entity;

public enum UserRole {
    ROLE_CLIENT, ROLE_MANAGER, ROLE_ADMIN;

    @Override
    public String toString() {
        return name();
    }

    public static UserRole fromString(String role) {
        return UserRole.valueOf(role.toUpperCase());
    }

    public String getName() {
        return this.name();
    }
}
