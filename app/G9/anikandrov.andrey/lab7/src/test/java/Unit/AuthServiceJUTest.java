package Unit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.vsu.amm.java.Entities.UserEntity;
import ru.vsu.amm.java.Repository.UserRepository;
import ru.vsu.amm.java.Services.AuthService;

import ru.vsu.amm.java.Exception.AlreadyExistException;
import ru.vsu.amm.java.Exception.DatabaseException;
import ru.vsu.amm.java.Exception.NotFoundException;

import java.sql.SQLException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;


public class AuthServiceJUTest {

    private AuthService authService;
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        authService = new AuthService(userRepository);
    }

    @Test
    void testLoginSuccess() throws SQLException {
        UserEntity mockUser = new UserEntity("user1", "password1");
        when(userRepository.findByUserName("user1")).thenReturn(Optional.of(mockUser));

        assertDoesNotThrow(() -> authService.login("user1", "password1"));
    }

    @Test
    void testLoginUserNotFound() throws SQLException {
        when(userRepository.findByUserName("userWrong")).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class,
                () -> authService.login("userWrong", "password1"));
        assertEquals("Login Error", exception.getMessage());
    }

    @Test
    void testLoginInvalidPassword() throws SQLException {
        UserEntity mockUser = new UserEntity("user1", "password1");
        when(userRepository.findByUserName("user1")).thenReturn(Optional.of(mockUser));

        NotFoundException exception = assertThrows(NotFoundException.class,
                () -> authService.login("user1", "password999"));
        assertEquals("Invalid Password", exception.getMessage());
    }

    @Test
    void testRegisterSuccess() throws SQLException {
        when(userRepository.findByUserName("user10")).thenReturn(Optional.empty());
        doNothing().when(userRepository).save(any());

        assertDoesNotThrow(() -> authService.register("user10", "password10"));
    }

    @Test
    void testRegisterUserAlreadyExists() throws SQLException {
        UserEntity mockUser = new UserEntity("user1", "password1");
        when(userRepository.findByUserName("user1")).thenReturn(Optional.of(mockUser));

        AlreadyExistException exception = assertThrows(AlreadyExistException.class,
                () -> authService.register("user1", "password1"));
        assertEquals("User With Such UserName Already Exist", exception.getMessage());
    }

    @Test
    void testDatabaseErrorLogin() throws SQLException {
        when(userRepository.findByUserName(anyString())).thenThrow(new SQLException("DB error"));

        assertThrows(DatabaseException.class, () -> authService.login("user1", "password1"));
    }

    @Test
    void testDatabaseErrorRegister() throws SQLException {
        when(userRepository.findByUserName(anyString())).thenThrow(new SQLException("DB error"));

        assertThrows(DatabaseException.class, () -> authService.register("user1", "password1"));
    }
}