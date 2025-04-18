package service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.vsu.amm.java.entities.UserEntity;
import ru.vsu.amm.java.enums.Role;
import ru.vsu.amm.java.repositories.UserRepository;
import ru.vsu.amm.java.requests.LoginRequest;
import ru.vsu.amm.java.requests.RegisterRequest;
import ru.vsu.amm.java.service.UserAuthService;
import ru.vsu.amm.java.utils.PasswordEncoder;

import javax.naming.AuthenticationException;
import java.sql.SQLException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

    @InjectMocks
    private UserAuthService authService;

    @Mock
    private UserRepository userRepository;

    @Test
    void login_Successful() throws SQLException, AuthenticationException {

        String email = "user@test.com";
        String password = "passw0rd";
        Long id = 1L;
        String firstName = "Ivan";
        String lastName = "Ivanov";
        String patronymic = "Ivanovich";
        String city = "Voronezh";
        String phoneNumber = "89994441234";
        Role role = Role.USER;

        UserEntity existingUser = new UserEntity(id,
                firstName, lastName, patronymic,
                city, email, phoneNumber, PasswordEncoder.hashPassword(password), role);

        LoginRequest request = new LoginRequest(email, password);

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(existingUser));

        UserEntity result = authService.login(request);

        assertNotNull(result);
        assertEquals(existingUser.getUserId(), result.getUserId());
        verify(userRepository, times(1)).findByEmail(email);
    }

    @Test
    void login_UserNotFound_ThrowsException() throws SQLException {

        String wrongEmail = "wrong@email.com";
        String password = "passw0rd";

        LoginRequest request = new LoginRequest(wrongEmail, password);

        when(userRepository.findByEmail(any())).thenReturn(Optional.empty());

        AuthenticationException exception = assertThrows(
                AuthenticationException.class,
                () -> authService.login(request));

        assertEquals("User not found", exception.getMessage());
        verify(userRepository, times(1)).findByEmail(wrongEmail);
    }

    @Test
    void login_WrongPassword_ThrowsException() throws SQLException {

        String email = "user@test.com";
        String password = "passw0rd";
        Long id = 1L;
        String firstName = "Ivan";
        String lastName = "Ivanov";
        String patronymic = "Ivanovich";
        String city = "Voronezh";
        String phoneNumber = "89994441234";
        Role role = Role.USER;

        UserEntity existingUser = new UserEntity(id,
                firstName, lastName, patronymic,
                city, email, phoneNumber, PasswordEncoder.hashPassword(password), role);

        String wrongPassword = "wrongPassword";
        LoginRequest request = new LoginRequest(email, wrongPassword);

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(existingUser));

        AuthenticationException exception = assertThrows(
                AuthenticationException.class,
                () -> authService.login(request));

        assertEquals("Invalid password", exception.getMessage());
    }

    @Test
    void register_NewUser_Success() throws Exception {

        String email = "user@test.com";
        String password = "passw0rd";
        String firstName = "Ivan";
        String lastName = "Ivanov";
        String patronymic = "Ivanovich";
        String city = "Voronezh";
        String phoneNumber = "89994441234";
        Role defaultRole = Role.USER;

        RegisterRequest request = new RegisterRequest(
                firstName, lastName, patronymic,
                city, email, phoneNumber, password);
        when(userRepository.findByEmail(request.email())).thenReturn(Optional.empty());

        UserEntity result = authService.register(request);

        assertNotNull(result);
        assertTrue(PasswordEncoder.checkPassword(password, result.getPassword()));
        assertEquals(defaultRole, result.getRole());
        verify(userRepository, times(1)).save(any(UserEntity.class));
    }

    @Test
    void register_ExistingEmail_ThrowsException() throws SQLException {

        String email = "user@test.com";
        String password = "passw0rd";
        String firstName = "Ivan";
        String lastName = "Ivanov";
        String patronymic = "Ivanovich";
        String city = "Voronezh";
        String phoneNumber = "89994441234";
        Role role = Role.USER;
        Long id = 1L;

        UserEntity existingUser = new UserEntity(id,
                firstName, lastName, patronymic,
                city, email, phoneNumber, PasswordEncoder.hashPassword(password), role);

        RegisterRequest request = new RegisterRequest(
                firstName, lastName, patronymic,
                city, email, phoneNumber, password);

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(existingUser));

        AuthenticationException exception = assertThrows(
                AuthenticationException.class,
                () -> authService.register(request));

        assertEquals("User Already exists", exception.getMessage());
        verify(userRepository, never()).save(any());
    }
}
