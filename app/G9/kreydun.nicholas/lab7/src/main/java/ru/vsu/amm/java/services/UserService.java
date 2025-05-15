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

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UUID create(String username, String email, String password) {
        if (email == null || userRepository.getByEmail(email) != null) {
            return null;
        }

        User user = new User();
        user.setId(UUID.randomUUID());
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(password);

        return userRepository.create(user);
    }

    public boolean update(UUID userId, String username, String email, String newPassword) {
        try {
            User user = userRepository.getById(userId);

            if (user == null)
                return false;

            user.setUsername(username);
            user.setEmail(email);
            if (newPassword != null && !newPassword.isEmpty()) {
                user.setPassword(newPassword);
            }

            return userRepository.update(user);
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    public boolean delete(UUID userId) {
        try {
            return userRepository.delete(userId);
        } catch (IllegalArgumentException e) {
            return false;
        }
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

}
