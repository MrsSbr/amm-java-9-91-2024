package ru.vsu.amm.java.service;

import ru.vsu.amm.java.entities.User;
import ru.vsu.amm.java.exceptions.AuthException;
import ru.vsu.amm.java.mapper.UserMapper;
import ru.vsu.amm.java.repository.UserRepository;
import ru.vsu.amm.java.requests.RegisterRequest;
import ru.vsu.amm.java.requests.SignInRequest;

import java.util.Optional;

public class AuthService {

    private final UserRepository userRepository;

    public AuthService() {

        userRepository = new UserRepository();

    }

    public User signIn(SignInRequest request) {


        Optional<User> user = userRepository.getByLoginAndPassword(
                request.login(),
                request.password()
        );

        if (user.isPresent()) {

            return user.get();

        } else {

            throw new AuthException("Invalid login or password!");

        }

    }

    public User register(RegisterRequest request) {

        UserMapper userMapper = new UserMapper();
        User user = userMapper.mapRequestToObject(request);

        if (userRepository.getByLoginAndPassword(user.getLogin(), user.getPassword()).isEmpty()) {

            userRepository.save(user);
            return user;

        } else {

            throw new AuthException("User already exists!");

        }

    }

}
