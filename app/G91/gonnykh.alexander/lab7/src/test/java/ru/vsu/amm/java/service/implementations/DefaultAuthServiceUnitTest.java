package ru.vsu.amm.java.service.implementations;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.vsu.amm.java.entities.UserEntity;
import ru.vsu.amm.java.enums.Role;
import ru.vsu.amm.java.exceptions.WrongUserCredentialsException;
import ru.vsu.amm.java.mappers.UserMapper;
import ru.vsu.amm.java.model.dto.UserDto;
import ru.vsu.amm.java.model.requests.LoginRequest;
import ru.vsu.amm.java.model.requests.RegisterRequest;
import ru.vsu.amm.java.repository.implementations.UserRepository;
import ru.vsu.amm.java.utils.BcryptPasswordEncoder;
import ru.vsu.amm.java.utils.ErrorMessages;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class DefaultAuthServiceUnitTest {
    private DefaultAuthService authService;
    private UserRepository userRepository;
    private BcryptPasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        passwordEncoder = mock(BcryptPasswordEncoder.class);
        authService = new DefaultAuthService(userRepository, passwordEncoder);
    }

    @Test
    void loginShouldReturnUserDtoWhenCredentialsAreCorrect() {
        String email = "test@example.com";
        String password = "password123";
        UserEntity user = new UserEntity(1L, "Test User", "hashed", email, Role.USER);
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
        when(passwordEncoder.checkPassword(password, user.getPassword())).thenReturn(true);

        LoginRequest request = new LoginRequest(email, password);

        UserDto result = authService.login(request);

        assertEquals(UserMapper.UserEntityToUserDto(user), result);

    }

    @Test
    void loginShouldThrowWhenUserNotFound() {
        when(userRepository.findByEmail("missing@example.com")).thenReturn(Optional.empty());
        LoginRequest request = new LoginRequest("missing@example.com", "any");

        WrongUserCredentialsException ex = assertThrows(WrongUserCredentialsException.class, () ->
                authService.login(request));

        assertEquals(ErrorMessages.USER_NOT_FOUND, ex.getMessage());
    }

    @Test
    void loginShouldThrowWhenPasswordIsWrong() {
        UserEntity user = new UserEntity(1L, "Test", "hashed",
                "email@test.com", Role.USER);

        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
        when(passwordEncoder.checkPassword("wrongpass", "hashed")).thenReturn(false);

        LoginRequest request = new LoginRequest(user.getEmail(), "wrongpass");

        WrongUserCredentialsException ex = assertThrows(WrongUserCredentialsException.class, () ->
                authService.login(request));

        assertEquals(ErrorMessages.INCORRECT_PASSWORD, ex.getMessage());
    }

    @Test
    void registerShouldThrowWhenUserAlreadyExists() {
        RegisterRequest request = new RegisterRequest("Name", "password", "email@test.com", Role.USER);
        when(userRepository.findByEmail(request.email())).thenReturn(Optional.of(new UserEntity()));

        WrongUserCredentialsException ex = assertThrows(WrongUserCredentialsException.class, () ->
                authService.register(request));

        assertEquals(ErrorMessages.USER_ALREADY_EXISTS, ex.getMessage());
    }

}