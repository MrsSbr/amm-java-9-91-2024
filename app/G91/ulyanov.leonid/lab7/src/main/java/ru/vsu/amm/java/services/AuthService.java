package ru.vsu.amm.java.services;

import lombok.extern.slf4j.Slf4j;
import org.mindrot.jbcrypt.BCrypt;
import ru.vsu.amm.java.entities.User;
import ru.vsu.amm.java.mappers.UserMapper;
import ru.vsu.amm.java.repos.UserRepository;
import ru.vsu.amm.java.requests.Auth.LoginRequest;
import ru.vsu.amm.java.requests.Auth.RegisterRequest;

import javax.naming.AuthenticationException;
import java.util.Optional;

@Slf4j
public class AuthService {
    private final UserRepository userRepository;

    public AuthService() {
        this.userRepository = new UserRepository();
    }

    public User login(LoginRequest request) throws AuthenticationException {
        Optional<User> user = userRepository.getByEmail(request.email());

        if (user.isPresent()) {
            if (BCrypt.checkpw(request.password(), user.get().getPassword())) {
                return user.get();
            } else {
                log.error("Login failed: password doesn't match");
                throw new AuthenticationException("Passwords don't match.");
            }
        } else {
            log.error("Login failed: user not found");
            throw new AuthenticationException("Such user doesn't exist.");
        }
    }

    public User register(RegisterRequest request) throws AuthenticationException {
        UserMapper userMapper = new UserMapper();
        User user = userMapper.mapRequestToObject(request);

        if (userRepository.getByEmail(request.email()).isEmpty()) {
            userRepository.create(user);
            return user;
        } else {
            log.error("Register failed: user already exists");
            throw new AuthenticationException("Such user already exists.");
        }
    }
}
