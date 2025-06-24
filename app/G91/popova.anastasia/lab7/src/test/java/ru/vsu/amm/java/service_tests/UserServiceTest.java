package ru.vsu.amm.java.service_tests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.vsu.amm.java.entities.User;
import ru.vsu.amm.java.repository.UserRepository;
import ru.vsu.amm.java.service.UserService;
import ru.vsu.amm.java.utils.PasswordHasher;
import ru.vsu.amm.java.utils.Validator;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private Validator validator;

    @Mock
    private PasswordHasher passwordHasher;

    @InjectMocks
    private UserService userService;

    private MockedStatic<Validator> mockedValidator;
    private MockedStatic<PasswordHasher> mockedPasswordHasher;

    private UUID userID;
    private String email;
    private String name;
    private String password;
    private User user;

    @BeforeEach
    void setUp() {
        userID = UUID.randomUUID();
        email = "test@example.com";
        name = "Test User";
        password = "password123";

        user = new User();
        user.setUserID(userID);
        user.setName(name);
        user.setEmail(email);
        user.setPassword("hashedPassword");

        mockedValidator = mockStatic(Validator.class);
        mockedPasswordHasher = mockStatic(PasswordHasher.class);

        mockedValidator.when(() -> Validator.isValidEmail(email)).thenReturn(true);
        mockedValidator.when(() -> Validator.isValidName(name)).thenReturn(true);
        mockedPasswordHasher.when(() -> PasswordHasher.hashPassword(password)).thenReturn("hashedPassword");
        mockedPasswordHasher.when(() -> PasswordHasher.checkPasswordHash(password, "hashedPassword")).thenReturn(true);
    }

    @AfterEach
    void tearDown() {
        mockedValidator.close();
        mockedPasswordHasher.close();
    }

    @Test
    void testRegister() {
        when(userRepository.getByEmail(email)).thenReturn(null);
        doNothing().when(userRepository).save(any(User.class));

        User result = userService.register(name, email, password);

        assertNotNull(result);
        assertNotNull(result.getUserID());
        assertEquals(name, result.getName());
        assertEquals(email, result.getEmail());
        assertEquals("hashedPassword", result.getPassword());
        verify(userRepository).getByEmail(email);
        verify(userRepository).save(any(User.class));
        mockedValidator.verify(() -> Validator.isValidEmail(email));
        mockedValidator.verify(() -> Validator.isValidName(name));
        mockedPasswordHasher.verify(() -> PasswordHasher.hashPassword(password));
    }

    @Test
    void testLogin() {
        when(userRepository.getByEmail(email)).thenReturn(user);

        User result = userService.login(email, password);

        assertNotNull(result);
        assertEquals(userID, result.getUserID());
        assertEquals(name, result.getName());
        assertEquals(email, result.getEmail());
        assertEquals("hashedPassword", result.getPassword());
        verify(userRepository).getByEmail(email);
        mockedPasswordHasher.verify(() -> PasswordHasher.checkPasswordHash(password, "hashedPassword"));
    }



    @Test
    void testLoginWithNonExistentUser() {
        when(userRepository.getByEmail(email)).thenReturn(null);

        SecurityException e = assertThrows(SecurityException.class, () -> {
            userService.login(email, password);
        });
        assertEquals("Пользователь не найден", e.getMessage());
        verify(userRepository).getByEmail(email);
        mockedPasswordHasher.verifyNoInteractions();
    }

    @Test
    void testLoginWithInvalidPassword() {
        when(userRepository.getByEmail(email)).thenReturn(user);
        mockedPasswordHasher.when(() -> PasswordHasher.checkPasswordHash(password, "hashedPassword")).thenReturn(false);

        SecurityException e = assertThrows(SecurityException.class, () -> {
            userService.login(email, password);
        });
        assertEquals("Неверный пароль!", e.getMessage());
        verify(userRepository).getByEmail(email);
        mockedPasswordHasher.verify(() -> PasswordHasher.checkPasswordHash(password, "hashedPassword"));
    }

    @Test
    void testUpdateUserName() {
        String newName = "Updated User";
        when(userRepository.getByID(userID)).thenReturn(user);
        doNothing().when(userRepository).update(any(User.class));

        userService.updateUserName(userID, newName);

        verify(userRepository).getByID(userID);
        verify(userRepository).update(user);
        assertEquals(newName, user.getName());
    }

    @Test
    void testDeleteUser() {
        doNothing().when(userRepository).delete(userID);

        userService.deleteUser(userID);

        verify(userRepository).delete(userID);
    }

    @Test
    void testGetUserByID() {
        when(userRepository.getByID(userID)).thenReturn(user);

        User result = userService.getByID(userID);

        assertNotNull(result);
        assertEquals(userID, result.getUserID());
        assertEquals(name, result.getName());
        assertEquals(email, result.getEmail());
        assertEquals("hashedPassword", result.getPassword());
        verify(userRepository).getByID(userID);
    }

    @Test
    void testRegisterWithInvalidEmail() {
        mockedValidator.when(() -> Validator.isValidEmail(email)).thenReturn(false);

        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> {
            userService.register(name, email, password);
        });
        assertEquals("Некорректный email!", e.getMessage());
        verify(userRepository, never()).getByEmail(email);
        verify(userRepository, never()).save(any());
        mockedValidator.verify(() -> Validator.isValidEmail(email));
    }

    @Test
    void testRegisterWithInvalidName() {
        mockedValidator.when(() -> Validator.isValidName(name)).thenReturn(false);

        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> {
            userService.register(name, email, password);
        });
        assertEquals("Некорректное имя пользователя!", e.getMessage());
        verify(userRepository, never()).getByEmail(email);
        verify(userRepository, never()).save(any());
        mockedValidator.verify(() -> Validator.isValidEmail(email));
        mockedValidator.verify(() -> Validator.isValidName(name));
    }

    @Test
    void testRegisterWithShortPassword() {
        String shortPassword = "short";

        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> {
            userService.register(name, email, shortPassword);
        });
        assertEquals("Пароль должен содержать минимум 8 символов", e.getMessage());
        verify(userRepository, never()).getByEmail(email);
        verify(userRepository, never()).save(any());
        mockedValidator.verify(() -> Validator.isValidEmail(email));
        mockedValidator.verify(() -> Validator.isValidName(name));
    }

    @Test
    void testRegisterWithExistingEmail() {
        when(userRepository.getByEmail(email)).thenReturn(user);

        IllegalStateException e = assertThrows(IllegalStateException.class, () -> {
            userService.register(name, email, password);
        });
        assertEquals("Пользователь с таким email уже существует!", e.getMessage());
        verify(userRepository).getByEmail(email);
        verify(userRepository, never()).save(any());
        mockedValidator.verify(() -> Validator.isValidEmail(email));
        mockedValidator.verify(() -> Validator.isValidName(name));
    }

}
