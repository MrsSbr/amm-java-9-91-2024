package ru.vsu.amm.java.services;

import ru.vsu.amm.java.entities.User;
import ru.vsu.amm.java.repository.UserRepository;

import java.util.List;
import java.util.UUID;

public class UserService {
    private final UserRepository userRepository;

    public UserService() {
        this.userRepository = new UserRepository();
    }

    public UUID create(User user) {
        if (user != null) {
            return userRepository.create(user);
        }
        return null;
    }

    public User getById(UUID id) {
        return userRepository.getById(id);
    }

    public User getByEmail(String email) {
        return userRepository.getByEmail(email);
    }

    public List<User> getAll() {
        return userRepository.getAll();
    }

    public boolean update(User user) {
        if (user != null) {
            return userRepository.update(user);
        }
        return false;
    }

    public boolean delete(UUID id) {
        return userRepository.delete(id);
    }
}
