package ru.vsu.amm.java.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.vsu.amm.java.entities.User;
import ru.vsu.amm.java.repository.UserRepository;
import ru.vsu.amm.java.utils.PasswordHasher;
import ru.vsu.amm.java.utils.Validator;
import java.util.UUID;


public class UserService implements UserServiceInterface {
    private static final Logger log = LoggerFactory.getLogger((UserService.class));

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User register(String name, String email, String password) {
        log.debug("Registering user with email: {}", email);
        try {
            if (!Validator.isValidEmail(email)) {
                log.error("Invalid email format: {}", email);
                throw new IllegalArgumentException("Некорректный email!");
            }
            if (!Validator.isValidName(name)) {
                log.error("Invalid name: {}", name);
                throw new IllegalArgumentException("Некорректное имя пользователя!");
            }
            if (password.length() < 8) {
                log.error("Password is too short: {}", email);
                throw new IllegalArgumentException("Пароль должен содержать минимум 8 символов");
            }
            if (userRepository.getByEmail(email) != null) {
                log.error("User with email already exists: {}", email);
                throw new IllegalStateException("Пользователь с таким email уже существует!");
            }
            User user = new User();
            user.setUserID(UUID.randomUUID());
            user.setName(name);
            user.setEmail(email);
            user.setPassword(PasswordHasher.hashPassword(password));
            userRepository.save(user);
            log.info("Registered user successfully: {}", user.getUserID());
            return user;
        } catch (RuntimeException e) {
            log.error("Failed to register user with email: {}", email, e);
            throw e;
        }
    }

    @Override
    public User login(String email, String password) {
        log.debug("Attempting login for email: {}", email);
        try {
            User user = userRepository.getByEmail(email);
            if (email == null || email.isEmpty() || password == null || password.isEmpty()) {
                log.error("Empty email or password provided");
                throw new IllegalArgumentException("Email и пароль обязательны для заполнения");
            }
            if (user == null) {
                log.warn("No user found with email: {}", email);
                throw new SecurityException("Пользователь не найден");
            }
            if(!PasswordHasher.checkPasswordHash(password, user.getPassword())) {
                log.warn("Invalid password for email: {}", email);
                throw new SecurityException("Неверный пароль!");
            }
            log.info("Successfully logged in user: {}", user.getUserID());
            return user;
        } catch (RuntimeException e) {
            log.error("Failed to login user with email: {}", email, e);
            throw e;
        }
    }

    @Override
    public void updateUserName(UUID userID, String newName) {
        log.debug("Updating name for user: {}, newName: {}", userID, newName);
        try {
            User user = userRepository.getByID(userID);
            if (user == null) {
                throw new IllegalArgumentException("Пользователь не найден");
            }
            user.setName(newName);
            userRepository.update(user);
            log.info("Successfully updated name for user: {}", userID);
        } catch (RuntimeException e) {
            log.error("Failed to update name for user: {}", userID, e);
            throw e;
        }
    }

    @Override
    public void deleteUser(UUID userID) {
        log.debug("Deleting user: {}", userID);
        try {
            userRepository.delete(userID);
            log.info("Deleted user successfully: {}", userID);
        } catch (RuntimeException e) {
            log.error("Failed to delete user: {}", userID, e);
            throw e;
        }
    }

    @Override
    public User getByID(UUID userID) {
        log.debug("Fetching user with ID: {}", userID);
        try {
            User user = userRepository.getByID(userID);
            if (user == null) {
                log.warn("No user found with ID: {}", userID);
                throw new IllegalArgumentException("Пользователь не найден");
            }
            log.info("Fetched user successfully: {}", userID);
            return user;
        } catch (RuntimeException e) {
            log.error("Failed to fetch user with ID: {}", userID, e);
            throw e;
        }
    }

}
