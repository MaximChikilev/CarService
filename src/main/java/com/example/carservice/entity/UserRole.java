package com.example.carservice.entity;

public enum UserRole {
    ADMIN, MANAGER;

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
