package ru.vsu.amm.java.services;

import ru.vsu.amm.java.entities.User;
import ru.vsu.amm.java.repository.UserRepository;

public class UserService {
    private UserRepository userRepository = new UserRepository();

    public User findUserByLoginAndPassword(String login, String password) {
        return userRepository.findByLoginAndPassword(login, password);
    }

    public void registerUser(User user) {
        userRepository.save(user);
    }
}
