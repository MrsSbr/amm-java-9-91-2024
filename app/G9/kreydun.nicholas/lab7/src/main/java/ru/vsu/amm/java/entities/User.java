package ru.vsu.amm.java.entities;

import java.time.LocalTime;
import java.util.UUID;

public class User {

    private UUID id;
    private String username;
    private String email;
    private String password;
    private LocalTime createdAt;

    public User() {}

    public UUID getId() {
        return id;
    }
    public void setId(UUID id) {
        this.id = id;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public LocalTime getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(LocalTime createdAt) {
        this.createdAt = createdAt;
    }
}