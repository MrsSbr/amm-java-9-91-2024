package ru.vsu.amm.java.service;

import ru.vsu.amm.java.entities.UserEntity;
import ru.vsu.amm.java.enums.Role;
import ru.vsu.amm.java.repositories.UserRepository;
import ru.vsu.amm.java.requests.LoginRequest;
import ru.vsu.amm.java.requests.RegisterRequest;
import ru.vsu.amm.java.service.interfaces.AuthService;
import ru.vsu.amm.java.utils.PasswordEncoder;

import javax.naming.AuthenticationException;
import java.sql.SQLException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserAuthService implements AuthService {
    private static final Logger logger = Logger.getLogger(UserAuthService.class.getName());
    private final UserRepository userRepository;

    public UserAuthService() {
        userRepository = new UserRepository();
    }

    @Override
    public UserEntity login(LoginRequest request) throws SQLException, AuthenticationException {
        Optional<UserEntity> userOptional = userRepository.findByEmail(request.email());
        if (userOptional.isEmpty()) {
            logger.log(Level.SEVERE, "User " + request.email() + " not found");
            throw new AuthenticationException("User not found");
        }

        UserEntity user = userOptional.get();
        if (!PasswordEncoder.checkPassword(request.password(), user.getPassword())) {
            logger.log(Level.SEVERE, "Password " + request.password()
                    + "is invalid for user " + request.email());
            throw new AuthenticationException("Invalid password");
        }

        return user;
    }

    @Override
    public UserEntity register(RegisterRequest request) throws SQLException, AuthenticationException {
        Optional<UserEntity> userOptional = userRepository.findByEmail(request.email());
        if (userOptional.isPresent()) {
            logger.log(Level.SEVERE, "User " + request.email() + " already exist");
            throw new AuthenticationException("User Already exists");
        }

        UserEntity user = new UserEntity(null,
                request.firstName(),
                request.lastName(),
                request.patronymic(),
                request.city(),
                request.email(),
                request.phoneNumber(),
                PasswordEncoder.hashPassword(request.password()),
                Role.USER
        );

        userRepository.save(user);
        logger.log(Level.INFO, "User registered");
        return user;
    }
}
