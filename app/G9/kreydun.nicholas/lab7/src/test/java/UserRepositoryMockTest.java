import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.vsu.amm.java.entities.User;
import ru.vsu.amm.java.repository.UserRepository;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.any;

class UserRepositoryMockTest {
    private UserRepository userRepository;
    private User testUser;
    private UUID userId;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);

        userId = UUID.randomUUID();
        testUser = new User();
        testUser.setId(userId);
        testUser.setUsername("testuser");
        testUser.setEmail("test@example.com");
        testUser.setPassword("password123");
    }

    @Test
    void testGetUserById() {
        when(userRepository.getById(userId)).thenReturn(testUser);

        User foundUser = userRepository.getById(userId);

        assertNotNull(foundUser);
        assertEquals("testuser", foundUser.getUsername());
        verify(userRepository, times(1)).getById(userId);
    }

    @Test
    void testGetAllUsers() {
        List<User> users = Arrays.asList(testUser, new User());
        when(userRepository.getAll()).thenReturn(users);

        List<User> result = userRepository.getAll();

        assertEquals(2, result.size());
        verify(userRepository, times(1)).getAll();
    }

    @Test
    void testCreateUser() {
        when(userRepository.create(any(User.class))).thenReturn(userId);

        UUID newUserId = userRepository.create(testUser);

        assertNotNull(newUserId);
        verify(userRepository, times(1)).create(testUser);
    }

    @Test
    void testUpdateUser() {
        when(userRepository.update(testUser)).thenReturn(true);

        boolean result = userRepository.update(testUser);

        assertTrue(result);
        verify(userRepository, times(1)).update(testUser);
    }

    @Test
    void testDeleteUser() {
        when(userRepository.delete(userId)).thenReturn(true);

        boolean result = userRepository.delete(userId);

        assertTrue(result);
        verify(userRepository, times(1)).delete(userId);
    }
}
