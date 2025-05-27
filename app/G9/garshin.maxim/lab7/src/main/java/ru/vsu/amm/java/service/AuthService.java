package ru.vsu.amm.java.service;

import org.mindrot.jbcrypt.BCrypt;
import ru.vsu.amm.java.entities.UserEntity;
import ru.vsu.amm.java.exception.DatabaseException;
import ru.vsu.amm.java.exception.RegisterException;
import ru.vsu.amm.java.repository.UserRepository;

import java.sql.SQLException;
import java.util.logging.Logger;

public class AuthService {
    private static final Logger log = Logger.getLogger(AuthService.class.getName());
    private final UserRepository userRepository;

    public AuthService() {
        userRepository = new UserRepository();
    }

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean login(String username, String password) {
        try {
            UserEntity user = userRepository.findByUsername(username).orElseThrow(
                    () -> new DatabaseException("Such a user does not exist")
            );
            return BCrypt.checkpw(password, user.getUserPassword());
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    public boolean register(String username, String password, String email) {
        try {
            if (userRepository.findByUsername(username).isEmpty()) {
                UserEntity newUser = new UserEntity(username, email, BCrypt.hashpw(password, BCrypt.gensalt()));

                userRepository.save(newUser);
                return true;
            } else {
                throw new RegisterException("A user with this username already exists");
            }
        } catch (SQLException e) {
            log.severe("error DatabaseException");
            throw new DatabaseException(e.getMessage());
        }
    }
}