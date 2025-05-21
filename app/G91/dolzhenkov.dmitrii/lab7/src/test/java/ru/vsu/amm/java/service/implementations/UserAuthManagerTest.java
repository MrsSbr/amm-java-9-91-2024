package ru.vsu.amm.java.service.implementations;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.vsu.amm.java.entities.UserEntity;
import ru.vsu.amm.java.exceptions.WrongUserCredentialsException;
import ru.vsu.amm.java.model.requests.UserRequest;
import ru.vsu.amm.java.repository.UserRepository;
import ru.vsu.amm.java.utils.BcryptPasswordEncoder;
import ru.vsu.amm.java.utils.ErrorMessages;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.argThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static ru.vsu.amm.java.service.implementations.UserTestData.PASSWORD_CORRECT;
import static ru.vsu.amm.java.service.implementations.UserTestData.PASSWORD_HASHED;
import static ru.vsu.amm.java.service.implementations.UserTestData.PASSWORD_NEW;
import static ru.vsu.amm.java.service.implementations.UserTestData.PASSWORD_WRONG;
import static ru.vsu.amm.java.service.implementations.UserTestData.USERNAME_ADMIN;
import static ru.vsu.amm.java.service.implementations.UserTestData.USERNAME_EXISTING;
import static ru.vsu.amm.java.service.implementations.UserTestData.USERNAME_MISSING;
import static ru.vsu.amm.java.service.implementations.UserTestData.USERNAME_NEW;
import static ru.vsu.amm.java.service.implementations.UserTestData.adminEntity;
import static ru.vsu.amm.java.service.implementations.UserTestData.adminRequest;
import static ru.vsu.amm.java.service.implementations.UserTestData.existingUserEntity;
import static ru.vsu.amm.java.service.implementations.UserTestData.existingUserRequest;
import static ru.vsu.amm.java.service.implementations.UserTestData.newUserRequest;
import static ru.vsu.amm.java.service.implementations.UserTestData.userEntity;
import static ru.vsu.amm.java.service.implementations.UserTestData.userRequest;

class UserAuthManagerTest {

    private UserRepository userRepository;
    private BcryptPasswordEncoder passwordEncoder;
    private UserAuthManager authManager;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        passwordEncoder = mock(BcryptPasswordEncoder.class);
        authManager = new UserAuthManager(userRepository, passwordEncoder);
    }

    @Test
    void loginShouldReturnUserRoleWhenCredentialsAreCorrect() {
        UserRequest request = adminRequest();
        UserEntity entity = adminEntity();

        when(userRepository.findByName(USERNAME_ADMIN)).thenReturn(Optional.of(entity));
        when(passwordEncoder.checkPassword(PASSWORD_CORRECT, PASSWORD_HASHED)).thenReturn(true);

        var result = authManager.login(request);

        assertEquals(entity.getUserRole(), result);
        verify(userRepository).findByName(USERNAME_ADMIN);
        verify(passwordEncoder).checkPassword(PASSWORD_CORRECT, PASSWORD_HASHED);
    }

    @Test
    void loginShouldThrowWhenUserNotFound() {
        when(userRepository.findByName(USERNAME_MISSING)).thenReturn(Optional.empty());
        UserRequest request = userRequest(USERNAME_MISSING, "any");

        var ex = assertThrows(WrongUserCredentialsException.class, () -> authManager.login(request));

        assertEquals(ErrorMessages.USER_NOT_FOUND, ex.getMessage());
        verify(userRepository).findByName(USERNAME_MISSING);
    }

    @Test
    void loginShouldThrowWhenPasswordIsIncorrect() {
        UserRequest request = userRequest(USERNAME_ADMIN, PASSWORD_WRONG);
        UserEntity entity = userEntity();

        when(userRepository.findByName(USERNAME_ADMIN)).thenReturn(Optional.of(entity));
        when(passwordEncoder.checkPassword(PASSWORD_WRONG, PASSWORD_HASHED)).thenReturn(false);

        var ex = assertThrows(WrongUserCredentialsException.class, () -> authManager.login(request));

        assertEquals(ErrorMessages.INCORRECT_PASSWORD, ex.getMessage());
        verify(userRepository).findByName(USERNAME_ADMIN);
        verify(passwordEncoder).checkPassword(PASSWORD_WRONG, PASSWORD_HASHED);
    }

    @Test
    void registerShouldSaveUserWhenNotExists() {
        UserRequest request = newUserRequest();

        when(userRepository.findByName(USERNAME_NEW)).thenReturn(Optional.empty());
        when(passwordEncoder.hashPassword(PASSWORD_NEW)).thenReturn("hashedpass");

        authManager.register(request);

        verify(userRepository).findByName(USERNAME_NEW);
        verify(passwordEncoder).hashPassword(PASSWORD_NEW);
        verify(userRepository).save(argThat(user ->
                user.getUserName().equals(USERNAME_NEW) &&
                        user.getPassword().equals("hashedpass") &&
                        user.getUserRole().equals(userEntity().getUserRole())
        ));
    }

    @Test
    void registerShouldThrowWhenUserAlreadyExists() {
        when(userRepository.findByName(USERNAME_EXISTING)).thenReturn(Optional.of(existingUserEntity()));
        UserRequest request = existingUserRequest();

        var ex = assertThrows(WrongUserCredentialsException.class, () -> authManager.register(request));

        assertEquals(ErrorMessages.USER_ALREADY_EXISTS, ex.getMessage());
        verify(userRepository).findByName(USERNAME_EXISTING);
    }
}