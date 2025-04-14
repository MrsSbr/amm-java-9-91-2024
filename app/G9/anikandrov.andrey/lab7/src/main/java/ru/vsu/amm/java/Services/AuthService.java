package ru.vsu.amm.java.Services;

import ru.vsu.amm.java.Entities.UserEntity;
import ru.vsu.amm.java.Exception.AlreadyExistException;
import ru.vsu.amm.java.Exception.DatabaseException;
import ru.vsu.amm.java.Exception.NotFoundException;
import ru.vsu.amm.java.Repository.UserRepository;

import java.sql.SQLException;

import java.util.logging.Level;
import java.util.logging.Logger;

public class AuthService {

    private final UserRepository userRepository;
    private static final Logger logger = Logger.getLogger(AuthService.class.getName());

    public AuthService() {
        userRepository = new UserRepository();
    }

    public AuthService(UserRepository userRep) {
        userRepository = userRep;
    }

    public void login(String name, String password) {
        try {

            UserEntity user = userRepository.findByUserName(name).orElseThrow(
                    () -> {
                        logger.log(Level.SEVERE, "Login failed - user not found: {0}", name);
                        return new NotFoundException("Login Error");
                    }
            );

            if (!user.getUserPassword().equals(password)) {
                logger.log(Level.WARNING, "Login failed - invalid password for user: {0}", name);
                throw new NotFoundException("Invalid Password");
            }

            logger.log(Level.INFO, "Login success for user: {0}", name);
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    public void register(String name, String password) {
        try {
            if (userRepository.findByUserName(name).isEmpty()) {
                UserEntity customer = new UserEntity(name, password);
                userRepository.save(customer);
                logger.log(Level.INFO, "User registered successfully: {0}", name);
            } else {
                logger.log(Level.WARNING, "Registration failed - user already exists: {0}", name);
                throw new AlreadyExistException("User With Such UserName Already Exist");
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Database error during registration: {0}", e.getMessage());
            throw new DatabaseException(e.getMessage());
        }
    }
}