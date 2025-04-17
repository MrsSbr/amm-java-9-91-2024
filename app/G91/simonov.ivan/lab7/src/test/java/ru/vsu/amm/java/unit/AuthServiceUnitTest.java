package ru.vsu.amm.java.unit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.vsu.amm.java.entities.User;
import ru.vsu.amm.java.exceptions.AuthException;
import ru.vsu.amm.java.repository.UserRepository;
import ru.vsu.amm.java.service.AuthService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static ru.vsu.amm.java.utils.UserFactory.makeRegisterRequest;
import static ru.vsu.amm.java.utils.UserFactory.makeSignInRequest;
import static ru.vsu.amm.java.utils.UserFactory.makeUser;

public class AuthServiceUnitTest {

    private AuthService authService;
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {

        userRepository = mock(UserRepository.class);
        authService = new AuthService(userRepository);

    }

    @Test
    public void testRegistrationOfExistingUser() {

        when(userRepository.getByLoginAndPassword(anyString(), anyString()))
                .thenReturn(Optional.of(new User()));

        assertThrows(AuthException.class, () -> authService.register(makeRegisterRequest()));

    }

    @Test
    public void testRegistrationOfNewUser() {

        when(userRepository.getByLoginAndPassword(anyString(), anyString()))
                .thenReturn(Optional.empty());

        when(userRepository.save(any(User.class))).thenReturn(1);

        assertEquals(makeUser(), authService.register(makeRegisterRequest()));

    }

    @Test
    public void testSignInOfExistingUser() {

        User user = makeUser();

        when(userRepository.getByLoginAndPassword(anyString(), anyString()))
                .thenReturn(Optional.of(user));

        assertEquals(user, authService.signIn(makeSignInRequest()));

    }

    @Test
    public void testSignInOfNewUser() {

        when(userRepository.getByLoginAndPassword(anyString(), anyString()))
                .thenReturn(Optional.empty());

        assertThrows(AuthException.class, () -> authService.signIn(makeSignInRequest()));

    }

}
