package ru.vsu.amm.java.service.implementations;

import ru.vsu.amm.java.entities.UserEntity;
import ru.vsu.amm.java.exceptions.WrongUserCredentialsException;
import ru.vsu.amm.java.mappers.UserMapper;
import ru.vsu.amm.java.model.dto.UserDto;
import ru.vsu.amm.java.model.requests.LoginRequest;
import ru.vsu.amm.java.model.requests.RegisterRequest;
import ru.vsu.amm.java.repository.implementations.UserRepository;
import ru.vsu.amm.java.service.interfaces.AuthService;
import ru.vsu.amm.java.utils.BcryptPasswordEncoder;
import ru.vsu.amm.java.utils.ErrorMessages;

import java.util.Optional;

public class DefaultAuthService implements AuthService {
    private final UserRepository userRepository;
    private final BcryptPasswordEncoder bcryptPasswordEncoder;

    public DefaultAuthService(UserRepository userRepository, BcryptPasswordEncoder bcryptPasswordEncoder) {
        this.bcryptPasswordEncoder = bcryptPasswordEncoder;
        this.userRepository = userRepository;
    }

    public DefaultAuthService() {
        this.bcryptPasswordEncoder = new BcryptPasswordEncoder();
        this.userRepository = new UserRepository();
    }

    @Override
    public UserDto login(LoginRequest request) {
        Optional<UserEntity> userOptional = userRepository.findByEmail(request.email());

        if (userOptional.isEmpty()) {
            throw new WrongUserCredentialsException(ErrorMessages.USER_NOT_FOUND);
        }

        if (!bcryptPasswordEncoder.checkPassword(request.password(), userOptional.get().getPassword())) {
            throw new WrongUserCredentialsException(ErrorMessages.INCORRECT_PASSWORD);
        }

        return UserMapper.UserEntityToUserDto(userOptional.get());

    }

    @Override
    public void register(RegisterRequest request) {
        Optional<UserEntity> userOptional = userRepository.findByEmail(request.email());

        if (userOptional.isPresent()) {
            throw new WrongUserCredentialsException(ErrorMessages.USER_ALREADY_EXISTS);
        } else {
            UserEntity userEntity = new UserEntity(null, request.name(),
                    bcryptPasswordEncoder.hashPassword(request.password()), request.email(), request.role());
            userRepository.save(userEntity);

        }
    }
}
