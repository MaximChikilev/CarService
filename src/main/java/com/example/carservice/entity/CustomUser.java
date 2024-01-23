package com.example.carservice.entity;

import javax.persistence.*;

@Entity
public class CustomUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private long id;
    private String login;
    private String password;
    @Enumerated(EnumType.STRING)
    private UserRole role;
    private String email;

    public CustomUser(String login, String password, UserRole role, String email) {
        this.login = login;
        this.password = password;
        this.role = role;
        this.email = email;
    }

    public CustomUser() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole userRole) {
        this.role = userRole;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
