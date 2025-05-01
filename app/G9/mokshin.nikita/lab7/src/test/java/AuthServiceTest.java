import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.vsu.amm.java.exception.AuthException;
import ru.vsu.amm.java.model.User;
import ru.vsu.amm.java.model.request.CredentialsRequest;
import ru.vsu.amm.java.model.request.UserRequest;
import ru.vsu.amm.java.repository.UserRepository;
import ru.vsu.amm.java.service.AuthService;
import ru.vsu.amm.java.util.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


class AuthServiceTest {
    private AuthService authService;
    private UserRepository userRepository;
    private HttpServletRequest request;
    private HttpSession session;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        request = mock(HttpServletRequest.class);
        session = mock(HttpSession.class);
        authService = new AuthService(userRepository);
    }

    @Test
    void login_success() {
        String login = "testUser";
        String password = "password";

        CredentialsRequest credentials = new CredentialsRequest(login, password);
        User user = User.builder()
                .login(login)
                .hashPassword(PasswordEncoder.encode(password))
                .build();

        when(userRepository.findByLogin(login)).thenReturn(Optional.of(user));
        when(request.getSession(true)).thenReturn(session);

        assertDoesNotThrow(() -> authService.login(credentials, request));
        verify(session).setAttribute("user", user);
    }

    @Test
    void login_wrongPassword_shouldThrow() {
        String login = "testUser";
        String password = "password";

        CredentialsRequest credentials = new CredentialsRequest(login, password);
        User user = User.builder()
                .login(login)
                .hashPassword(PasswordEncoder.encode("wrongPassword"))
                .build();

        when(userRepository.findByLogin(login)).thenReturn(Optional.of(user));

        assertThrows(AuthException.class, () -> authService.login(credentials, request));
    }

    @Test
    void login_userNotFound_shouldThrow() {
        String login = "testUser";
        String password = "password";

        CredentialsRequest credentials = new CredentialsRequest(login, password);
        when(userRepository.findByLogin(login)).thenReturn(Optional.empty());

        assertThrows(AuthException.class, () -> authService.login(credentials, request));
    }

    @Test
    void register_success() {
        String login = "testUser";
        String password = "password";

        UserRequest userRequest = new UserRequest("Test Name", login, password);
        User savedUser = User.builder()
                .login(login)
                .name("Test Name")
                .hashPassword(PasswordEncoder.encode(password))
                .build();

        when(userRepository.findByLogin(login)).thenReturn(Optional.empty()).thenReturn(Optional.of(savedUser));
        when(request.getSession(true)).thenReturn(session);

        authService.register(userRequest, request);

        verify(userRepository).save(savedUser);
        verify(session).setAttribute("user", savedUser);
    }

    @Test
    void register_userAlreadyExists_shouldThrow() {
        String login = "testUser";
        String password = "password";

        UserRequest userRequest = new UserRequest("Test Name", login, password);
        User existingUser = User.builder().login(login).build();

        when(userRepository.findByLogin(login)).thenReturn(Optional.of(existingUser));

        assertThrows(AuthException.class, () -> authService.register(userRequest, request));
    }

    @Test
    void logout_success() {
        String login = "testUser";
        User user = User.builder().login(login).build();

        when(request.getSession(false)).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(user);

        authService.logout(request);

        verify(session).invalidate();
    }
}
