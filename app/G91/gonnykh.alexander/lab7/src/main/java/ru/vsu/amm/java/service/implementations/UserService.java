package ru.vsu.amm.java.service.implementations;

import ru.vsu.amm.java.entities.UserEntity;
import ru.vsu.amm.java.mappers.UserMapper;
import ru.vsu.amm.java.model.dto.UserDto;
import ru.vsu.amm.java.repository.implementations.UserRepository;

import java.util.NoSuchElementException;

public class UserService {
    private final UserRepository userRepository;

    public UserService() {
        this.userRepository = new UserRepository();
    }

    public UserDto getUserByEmail(String email) {
        UserEntity userEntity = userRepository.findByEmail(email)
                .orElseThrow(() -> new NoSuchElementException("User not found"));
        return UserMapper.UserEntityToUserDto(userEntity);
    }
}
