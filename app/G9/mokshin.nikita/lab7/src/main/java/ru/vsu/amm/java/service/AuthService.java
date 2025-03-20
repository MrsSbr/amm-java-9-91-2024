package ru.vsu.amm.java.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.vsu.amm.java.exception.AuthException;
import ru.vsu.amm.java.model.User;
import ru.vsu.amm.java.model.request.CredentialsRequest;
import ru.vsu.amm.java.model.request.UserRequest;
import ru.vsu.amm.java.repository.UserRepository;
import ru.vsu.amm.java.util.PasswordEncoder;

public class AuthService {
    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);
    private final UserRepository userRepository;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void login(CredentialsRequest credentialsRequest, HttpServletRequest request) {
        logger.info("Login for user: {}", credentialsRequest.getLogin());

        User user = userRepository.findByLogin(credentialsRequest.getLogin())
                .orElseThrow(() -> new AuthException("Invalid login or password"));

        if (!PasswordEncoder.matches(credentialsRequest.getPassword(), user.getHashPassword())) {
            logger.info("Login failed for user: {} - Incorrect password", credentialsRequest.getLogin());
            throw new AuthException("Invalid login or password");
        }

        HttpSession session = request.getSession(true);
        session.setAttribute("user", user);
        logger.info("User {} logged in successfully", user);
    }

    public void register(UserRequest userRequest, HttpServletRequest request) {
        logger.info("Attempting to register user: {}", userRequest.getLogin());

        if (userRepository.findByLogin(userRequest.getLogin()).isPresent()) {
            logger.info("Registration failed - User {} already exists", userRequest.getLogin());
            throw new AuthException("User with this login already exists");
        }

        String hashedPassword = PasswordEncoder.encode(userRequest.getPassword());

        User user = User.builder()
                .login(userRequest.getLogin())
                .name(userRequest.getName())
                .hashPassword(hashedPassword)
                .build();

        userRepository.save(user);
        user = userRepository.findByLogin(user.getLogin()).get();

        HttpSession session = request.getSession(true);
        session.setAttribute("user", user);
        logger.info("User {} registered successfully", user);
    }

    public void logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("user") != null) {
            User user = (User) session.getAttribute("user");
            session.invalidate();
            logger.info("User {} logged out successfully", user);
        }
    }
}
