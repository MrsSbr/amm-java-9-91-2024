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
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AuthenticationService {
    private static final Logger logger = Logger.getLogger(AuthenticationService.class.getName());
    private final UserRepository userRepository = new UserRepository(DatabaseAccess.getDataSource());
    ;

    public AuthenticationService() throws IOException {
        logger.log(Level.INFO, "Initializing Authentication Service");
        logger.log(Level.INFO, "Authentication Service successfully initialized");
    }

    public boolean login(String login, String password) {
        logger.log(Level.INFO, "Attempting to login");
        try {
            UserEntity authUser = userRepository.findByLogin(login);

            if (authUser == null) {
                logger.log(Level.SEVERE, "User not found");
                throw new AuthenticationException("Login does not exist");
            }
            logger.log(Level.INFO, "User found successfully");


            byte[] salt = Base64.getDecoder().decode(authUser.getSalt());
            PasswordHash hash = new PasswordHash();
            boolean pass = Arrays.equals(Base64.getDecoder().decode(authUser.getPasswordHash()), hash.encrypt(password, salt));
            if (pass) {
                logger.log(Level.INFO, "Successfully logged in");
            }
            return pass;

        } catch (SQLException | AuthenticationException | NoSuchAlgorithmException | InvalidKeySpecException e) {
            logger.log(Level.SEVERE, e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public boolean register(String login, String password, String email, String nickname, String phonenumber) {
        logger.log(Level.INFO, "Attempting to register");
        try {
            UserEntity authUser = userRepository.findByLogin(login);
            //UserEntity authUser = null;
            if (authUser == null) {
                logger.log(Level.INFO, "Existing user not found");
                PasswordHash hash = new PasswordHash();
                byte[][] passHashAndSalt = hash.encrypt(password);
                byte[] passwordHash = passHashAndSalt[0];
                byte[] salt = passHashAndSalt[1];
                authUser = new UserEntity(login,
                        nickname,
                        phonenumber,
                        Base64.getEncoder().encodeToString(passwordHash),
                        email,
                        Base64.getEncoder().encodeToString(salt));
                return userRepository.save(authUser);
            }
        } catch (SQLException | NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }
        return false;
    }
}
