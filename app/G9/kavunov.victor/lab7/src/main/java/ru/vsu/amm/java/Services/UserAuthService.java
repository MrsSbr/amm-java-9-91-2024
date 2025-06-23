package ru.vsu.amm.java.Services;

import ru.vsu.amm.java.Entities.User;
import ru.vsu.amm.java.Repositories.UserRepository;
import ru.vsu.amm.java.Requests.LoginRequest;
import ru.vsu.amm.java.Requests.RegisterRequest;
import ru.vsu.amm.java.Services.Interfaces.AuthService;
import ru.vsu.amm.java.Utils.PasswordEncoder;

import ru.vsu.amm.java.Exceptions.AuthenticationException;
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
    public User login(LoginRequest request) throws SQLException, AuthenticationException {
        Optional<User> userOptional;

        if (request.login().contains("@")) {
            userOptional = userRepository.findByEmail(request.login());
        } else {
            userOptional = userRepository.findByPhoneNumber(request.login());
        }

        if (userOptional.isEmpty()) {
            logger.log(Level.SEVERE, "User with login " + request.login() + " not found");
            throw new AuthenticationException("User not found");
        }

        User user = userOptional.get();
        if (!PasswordEncoder.checkPassword(request.password(), user.getPassword())) {
            logger.log(Level.SEVERE, "Password is invalid for user " + request.login());
            throw new AuthenticationException("Invalid password");
        }

        return user;
    }

    @Override
    public User register(RegisterRequest request) throws SQLException, AuthenticationException {
        Optional<User> userByEmail = userRepository.findByEmail(request.email());
        if (userByEmail.isPresent()) {
            logger.log(Level.SEVERE, "User with email " + request.email() + " already exists");
            throw new AuthenticationException("User with this email already exists");
        }

        Optional<User> userByPhone = userRepository.findByPhoneNumber(request.phoneNumber());
        if (userByPhone.isPresent()) {
            logger.log(Level.SEVERE, "User with phone number " + request.phoneNumber() + " already exists");
            throw new AuthenticationException("User with this phone number already exists");
        }

        User user = new User(null,
                request.surname(),
                request.name(),
                request.patronymicname(),
                request.phoneNumber(),
                PasswordEncoder.hashPassword(request.password()),
                request.email()
        );

        userRepository.save(user);
        logger.log(Level.INFO, "User registered successfully");
        return userRepository.findByEmail(user.getEmail()).get();
    }
}
