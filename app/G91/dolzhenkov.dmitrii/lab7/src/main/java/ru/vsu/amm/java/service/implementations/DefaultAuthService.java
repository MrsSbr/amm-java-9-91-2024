package ru.vsu.amm.java.service.implementations;


import ru.vsu.amm.java.entities.UserEntity;
import ru.vsu.amm.java.exceptions.WrongUserCredentialsException;
import ru.vsu.amm.java.model.requests.LoginRequest;
import ru.vsu.amm.java.model.requests.RegisterRequest;
import ru.vsu.amm.java.repository.UserRepository;
import ru.vsu.amm.java.service.interfaces.AuthService;
import ru.vsu.amm.java.utils.BcryptPasswordEncoder;
import ru.vsu.amm.java.utils.ErrorMessages;

import java.util.Optional;

public class DefaultAuthService implements AuthService {
    private final UserRepository userRepository;
    private final BcryptPasswordEncoder passwordEncoder;

    public DefaultAuthService() {
        this.passwordEncoder = new BcryptPasswordEncoder(12);
        this.userRepository = new UserRepository();
    }

    @Override
    public void login(LoginRequest request) {
        UserEntity user = userRepository.findByName(request.name())
                .orElseThrow(() -> new WrongUserCredentialsException(ErrorMessages.USER_NOT_FOUND));

        performPasswordCheck(user.getPassword(), request.password());
    }

    @Override
    public void register(RegisterRequest request) {
        Optional<UserEntity> userOptional = userRepository.findByName(request.name());

        if (userOptional.isPresent()) {
            throw new WrongUserCredentialsException(ErrorMessages.USER_ALREADY_EXISTS);
        } else {
            UserEntity userEntity = new UserEntity(null, request.name(),
                    passwordEncoder.hashPassword(request.password()));
            userRepository.save(userEntity);
        }
    }

    private void performPasswordCheck(String storedHash, String rawPassword) {
        if (!passwordEncoder.checkPassword(storedHash, rawPassword)) {
            throw new WrongUserCredentialsException(ErrorMessages.INCORRECT_PASSWORD);
        }
    }
}