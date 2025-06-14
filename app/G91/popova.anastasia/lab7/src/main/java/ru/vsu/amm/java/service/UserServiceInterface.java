package ru.vsu.amm.java.service;

import ru.vsu.amm.java.entities.User;

import java.util.UUID;

public interface UserServiceInterface {
    User register(String name, String email, String password);
    User login(String email, String password);
    void updateUserName(UUID userID, String newName);
    void deleteUser(UUID userID);
    User getByID(UUID userID);
}
