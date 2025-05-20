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
    private final UserRepository userRepository = new UserRepository(DatabaseAccess.getDataSource());
    ;

    public AuthenticationService() throws IOException {
        LOGGER.log(Level.INFO, "Initializing Authentication Service");
        LOGGER.log(Level.INFO, "Authentication Service successfully initialized");
    }

    public boolean login(String login, String password) {
        LOGGER.log(Level.INFO, "Attempting to login");
        try {
            UserEntity authUser = userRepository.findByLogin(login);
            if (authUser == null) {
                LOGGER.log(Level.SEVERE, "User not found");
                throw new AuthenticationException("Login does not exist");
            }
            LOGGER.log(Level.INFO, "Logged successfully");
            byte[] salt = authUser.getSalt().getBytes();
            PasswordHash hash = new PasswordHash();
            return Arrays.equals(authUser.getPasswordHash().getBytes(), hash.encrypt(password, salt));
        } catch (SQLException | AuthenticationException | NoSuchAlgorithmException | InvalidKeySpecException e) {
            LOGGER.log(Level.SEVERE, e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public boolean register(String login, String password, String email, String nickname, String phonenumber) {
        LOGGER.log(Level.INFO, "Attempting to register");
        try {
            UserEntity authUser = userRepository.findByLogin(login);
            //UserEntity authUser = null;
            if (authUser == null) {
                PasswordHash hash = new PasswordHash();
                byte[][] passHashAndSalt = hash.encrypt(password);
                byte[] passwordHash = passHashAndSalt[0];
                byte[] salt = passHashAndSalt[1];
                authUser = new UserEntity(login,
                        nickname,
                        phonenumber,
                        Arrays.toString(passwordHash),
                        email,
                        Arrays.toString(salt));
                return userRepository.save(authUser);
            }
        } catch (SQLException | NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }
        return false;
    }
}
