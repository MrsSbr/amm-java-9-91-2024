import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.vsu.amm.java.entities.User;
import ru.vsu.amm.java.repository.UserRepository;
import ru.vsu.amm.java.services.UserService;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.never;

class UserServiceMockTest {

    private UserRepository userRepository;
    private UserService userService;
    private User testUser;
    private UUID userId;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        userService = new UserService(userRepository); // Конструктор с репозиторием

        userId = UUID.randomUUID();
        testUser = new User();
        testUser.setId(userId);
        testUser.setUsername("testuser");
        testUser.setEmail("test@example.com");
        testUser.setPassword("password123");
    }

    @Test
    void testCreateUserSuccess() {
        when(userRepository.getByEmail(testUser.getEmail())).thenReturn(null);
        when(userRepository.create(any(User.class))).thenReturn(userId);

        UUID result = userService.create(testUser.getUsername(), testUser.getEmail(), testUser.getPassword());

        assertNotNull(result);
        verify(userRepository, times(1)).create(any(User.class));
    }

    @Test
    void testCreateUse_EmailExists() {
        when(userRepository.getByEmail(testUser.getEmail())).thenReturn(testUser);

        UUID result = userService.create(testUser.getUsername(), testUser.getEmail(), testUser.getPassword());

        assertNull(result);
        verify(userRepository, never()).create(any(User.class));
    }

    @Test
    void testUpdateUserSuccess() {
        when(userRepository.getById(userId)).thenReturn(testUser);
        when(userRepository.update(any(User.class))).thenReturn(true);

        boolean result = userService.update(UUID.fromString(userId.toString()), "newUsername", "new@example.com", "newPassword");

        assertTrue(result);
        verify(userRepository, times(1)).update(any(User.class));
    }

    @Test
    void testUpdateUserNotFound() {
        when(userRepository.getById(userId)).thenReturn(null);

        boolean result = userService.update(userId, "user", "email", "pass");

        assertFalse(result);
    }

    @Test
    void testDeleteUserSuccess() {
        when(userRepository.delete(userId)).thenReturn(true);

        boolean result = userService.delete(userId);

        assertTrue(result);
        verify(userRepository, times(1)).delete(userId);
    }

    @Test
    void testGetById() {
        when(userRepository.getById(userId)).thenReturn(testUser);

        User result = userService.getById(userId);

        assertNotNull(result);
        assertEquals("testuser", result.getUsername());
    }

    @Test
    void testGetByEmail() {
        when(userRepository.getByEmail("test@example.com")).thenReturn(testUser);

        User result = userService.getByEmail("test@example.com");

        assertNotNull(result);
        assertEquals("testuser", result.getUsername());
    }

    @Test
    void testGetAll() {
        when(userRepository.getAll()).thenReturn(Collections.singletonList(testUser));

        List<User> users = userService.getAll();

        assertEquals(1, users.size());
        assertEquals("testuser", users.get(0).getUsername());
    }
}
