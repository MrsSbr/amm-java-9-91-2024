package ru.vsu.amm.java.unit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mindrot.jbcrypt.BCrypt;
import ru.vsu.amm.java.entities.User;
import ru.vsu.amm.java.repos.UserRepository;
import ru.vsu.amm.java.requests.Auth.LoginRequest;
import ru.vsu.amm.java.requests.Auth.RegisterRequest;
import ru.vsu.amm.java.services.AuthService;
import ru.vsu.amm.java.utils.UserFactory;

import javax.naming.AuthenticationException;
import java.lang.reflect.Field;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class AuthServiceUnitTest {

    private AuthService authService;
    private UserRepository userRepository;

    @BeforeEach
    void setUp() throws Exception {
        authService = new AuthService();
        userRepository = mock(UserRepository.class);
        Field repoField = AuthService.class.getDeclaredField("userRepository");
        repoField.setAccessible(true);
        repoField.set(authService, userRepository);
    }

    @Test
    void loginShouldReturnUserWhenCredentialsAreCorrect() throws AuthenticationException {
        User expected = UserFactory.newUser();
        expected.setPassword(BCrypt.hashpw("password1234", BCrypt.gensalt()));

        when(userRepository.getByEmail(expected.getEmail()))
                .thenReturn(Optional.of(expected));

        LoginRequest request = UserFactory.correctLoginRequest();

        User result = authService.login(request);

        assertEquals(expected.getEmail(), result.getEmail());
        assertEquals(expected.getFirstName(), result.getFirstName());

        verify(userRepository).getByEmail(expected.getEmail());
    }

    @Test
    void loginShouldThrowWhenUserNotFound() {
        String missingEmail = "missing@example.com";
        when(userRepository.getByEmail(missingEmail)).thenReturn(Optional.empty());

        LoginRequest request = new LoginRequest(missingEmail, "anypass");

        assertThrows(AuthenticationException.class, () -> authService.login(request));

        verify(userRepository).getByEmail(missingEmail);
    }

    @Test
    void loginShouldThrowWhenPasswordIsWrong() {
        User stored = UserFactory.newUser();
        stored.setPassword(BCrypt.hashpw("password1234", BCrypt.gensalt()));
        when(userRepository.getByEmail(stored.getEmail()))
                .thenReturn(Optional.of(stored));

        LoginRequest wrongPasswordRequest = UserFactory.wrongLoginRequest();

        assertThrows(AuthenticationException.class, () -> authService.login(wrongPasswordRequest));

        verify(userRepository).getByEmail(stored.getEmail());
    }

    @Test
    void registerShouldThrowWhenUserAlreadyExists() {
        User existing = UserFactory.newUser();
        when(userRepository.getByEmail(existing.getEmail()))
                .thenReturn(Optional.of(existing));

        RegisterRequest request = UserFactory.correctRegisterRequest();

        assertThrows(AuthenticationException.class, () -> authService.register(request));

        verify(userRepository).getByEmail(existing.getEmail());
    }

    @Test
    void registerShouldReturnNewUserWhenSuccess() throws AuthenticationException {
        RegisterRequest request = UserFactory.correctRegisterRequest();
        when(userRepository.getByEmail(request.email())).thenReturn(Optional.empty());

        doAnswer(invocation -> {
            User arg = invocation.getArgument(0);
            arg.setUserId(1);
            return 1;
        }).when(userRepository).create(any(User.class));

        User result = authService.register(request);

        assertEquals(request.email(), result.getEmail());
        assertNotNull(result.getUserId());

        verify(userRepository).getByEmail(request.email());
        verify(userRepository).create(any(User.class));
    }
} 