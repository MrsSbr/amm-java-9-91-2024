package ru.vsu.amm.java.services;

import ru.vsu.amm.java.DatabaseAccess;
import ru.vsu.amm.java.entity.UserEntity;
import ru.vsu.amm.java.exceptions.AuthenticationException;
import ru.vsu.amm.java.repo.UserRepository;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AuthenticationService {
    private static final Logger LOGGER = Logger.getLogger(AuthenticationService.class.getName());
    private final UserRepository userRepository;

    public AuthenticationService() throws IOException {
        LOGGER.log(Level.INFO, "Initializing Authentication Service");
        userRepository = new UserRepository(DatabaseAccess.getDataSource());
        LOGGER.log(Level.INFO, "Authentication Service successfully initialized");
    }

    public boolean login(String login, String password) {
        LOGGER.log(Level.INFO, "Attempting to login");
        try {
            UserEntity authUser = userRepository.findByLogin(login);
            if (authUser == null) {
                throw new AuthenticationException("Login does not exist");
            }
            LOGGER.log(Level.INFO, "Logged successfully");
            byte[] salt = authUser.getSalt();
            PasswordHash hash = new PasswordHash();
            return Arrays.equals(authUser.getPasswordHash(), hash.encrypt(password, salt));
        } catch (SQLException | AuthenticationException | NoSuchAlgorithmException | InvalidKeySpecException e) {
            LOGGER.log(Level.SEVERE, e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public boolean register(String login, String password, String email, String nickname, String phonenumber) {
        LOGGER.log(Level.INFO, "Attempting to register");
        try {
            UserEntity authUser = userRepository.findByLogin(login);
            if (authUser == null) {
                PasswordHash hash = new PasswordHash();
                byte[][] passHashAndSalt = hash.encrypt(password);
                byte[] passwordHash = passHashAndSalt[0];
                byte[] salt = passHashAndSalt[1];
                authUser = new UserEntity(login,
                        nickname,
                        phonenumber,
                        passwordHash,
                        email,
                        salt);
                userRepository.save(authUser);
                return true;
            }
        } catch (SQLException | NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }
        return false;
    }
}
