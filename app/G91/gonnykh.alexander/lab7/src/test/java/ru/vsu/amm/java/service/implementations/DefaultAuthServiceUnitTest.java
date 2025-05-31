package ru.vsu.amm.java.service.implementations;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.vsu.amm.java.entities.UserEntity;
import ru.vsu.amm.java.exceptions.WrongUserCredentialsException;
import ru.vsu.amm.java.mappers.UserMapper;
import ru.vsu.amm.java.model.dto.UserDto;
import ru.vsu.amm.java.model.requests.LoginRequest;
import ru.vsu.amm.java.model.requests.RegisterRequest;
import ru.vsu.amm.java.repository.implementations.UserRepository;
import ru.vsu.amm.java.utils.BcryptPasswordEncoder;
import ru.vsu.amm.java.utils.ErrorMessages;
import utils.TestDataFactory;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
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
        UserEntity admin = TestDataFactory.adminEntity();
        when(userRepository.findByEmail(admin.getEmail()))
                .thenReturn(Optional.of(admin));
        when(passwordEncoder.checkPassword(
                TestDataFactory.correctLoginRequest().password(),
                admin.getPassword()))
                .thenReturn(true);

        LoginRequest request = TestDataFactory.correctLoginRequest();
        UserDto result = authService.login(request);

        assertEquals(
                UserMapper.UserEntityToUserDto(admin),
                result
        );

        verify(userRepository).findByEmail(admin.getEmail());
        verify(passwordEncoder).checkPassword(
                request.password(), admin.getPassword()
        );
    }

    @Test
    void loginShouldThrowWhenUserNotFound() {
        String missingEmail = "missing@example.com";
        when(userRepository.findByEmail(missingEmail))
                .thenReturn(Optional.empty());

        LoginRequest request = new LoginRequest(missingEmail, "any");

        WrongUserCredentialsException ex = assertThrows(
                WrongUserCredentialsException.class,
                () -> authService.login(request)
        );

        assertEquals(
                ErrorMessages.USER_NOT_FOUND,
                ex.getMessage()
        );

        verify(userRepository).findByEmail(missingEmail);
    }

    @Test
    void loginShouldThrowWhenPasswordIsWrong() {
        UserEntity admin = TestDataFactory.adminEntity();
        when(userRepository.findByEmail(admin.getEmail()))
                .thenReturn(Optional.of(admin));
        when(passwordEncoder.checkPassword(
                TestDataFactory.wrongLoginRequest().password(),
                admin.getPassword()))
                .thenReturn(false);

        LoginRequest request = TestDataFactory.wrongLoginRequest();

        WrongUserCredentialsException ex = assertThrows(
                WrongUserCredentialsException.class,
                () -> authService.login(request)
        );

        assertEquals(
                ErrorMessages.INCORRECT_PASSWORD,
                ex.getMessage()
        );

        verify(userRepository).findByEmail(admin.getEmail());
        verify(passwordEncoder).checkPassword(
                request.password(), admin.getPassword()
        );
    }

    @Test
    void registerShouldThrowWhenUserAlreadyExists() {
        when(userRepository.findByEmail(
                TestDataFactory.userRegister().email()))
                .thenReturn(Optional.of(TestDataFactory.userEntity()));

        RegisterRequest request = TestDataFactory.userRegister();

        WrongUserCredentialsException ex = assertThrows(
                WrongUserCredentialsException.class,
                () -> authService.register(request)
        );

        assertEquals(
                ErrorMessages.USER_ALREADY_EXISTS,
                ex.getMessage()
        );

        verify(userRepository).findByEmail(request.email());
    }
}