package service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mindrot.jbcrypt.BCrypt;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.vsu.amm.java.entities.UserEntity;
import ru.vsu.amm.java.exception.DatabaseException;
import ru.vsu.amm.java.exception.RegisterException;
import ru.vsu.amm.java.repository.UserRepository;
import ru.vsu.amm.java.service.AuthService;

import java.sql.SQLException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @InjectMocks
    private AuthService authService;

    @Mock
    private UserRepository userRepositoryMock;

    @Test
    void testSuccessfulLogin() throws SQLException {
        String username = "user";
        String password = "user";
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

        UserEntity user = new UserEntity(1L, username, "user@user", hashedPassword);
        when(userRepositoryMock.findByUsername(username)).thenReturn(Optional.of(user));

        assertTrue(authService.login(username, password));
    }

    @Test
    void testLoginWithIncorrectPassword() throws SQLException {
        String username = "user";
        String password = "юзер";
        String hashedPassword = BCrypt.hashpw("user", BCrypt.gensalt());

        UserEntity user = new UserEntity(1L, username, "user@user", hashedPassword);
        when(userRepositoryMock.findByUsername(username)).thenReturn(Optional.of(user));

        assertFalse(authService.login(username, password));
    }

    @Test
    void testLoginUserNotFound() throws SQLException {
        when(userRepositoryMock.findByUsername("notfound")).thenReturn(Optional.empty());

        DatabaseException exception = assertThrows(DatabaseException.class,
                () -> authService.login("notfound", "pass"));
        assertEquals("Such a user does not exist", exception.getMessage());
    }

    @Test
    void testRegisterSuccess() throws SQLException {
        String username = "newuser";
        String password = "password";
        String email = "new@mail.com";

        when(userRepositoryMock.findByUsername(username)).thenReturn(Optional.empty());

        boolean result = authService.register(username, password, email);

        assertTrue(result);
        verify(userRepositoryMock, times(1)).save(any(UserEntity.class));
    }

    @Test
    void testRegisterUserAlreadyExists() throws SQLException {
        String username = "existinguser";
        String password = "password";
        String email = "exists@mail.com";

        when(userRepositoryMock.findByUsername(username)).thenReturn(Optional.of(
                new UserEntity(1L, username, email, "hash")));

        assertThrows(RegisterException.class, () -> authService.register(username, password, email));
        verify(userRepositoryMock, never()).save(any(UserEntity.class));
    }
}