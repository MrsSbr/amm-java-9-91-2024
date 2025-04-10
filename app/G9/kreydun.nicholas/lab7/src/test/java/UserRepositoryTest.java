import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import ru.vsu.amm.java.entities.User;
import ru.vsu.amm.java.repository.UserRepository;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserRepositoryTest {

    private UserRepository userRepository;
    private UUID testUserId;
    private final String email = "test21@example.com";

    @BeforeEach
    void setUp() {
        userRepository = new UserRepository();
    }

    @Test
    @Order(1)
    void testCreateUser() {
        User user = new User();
        user.setId(UUID.randomUUID());
        user.setUsername("testuser21");
        user.setEmail(email);
        user.setPassword("password123");

        UUID createdUserId = userRepository.create(user);
        assertNotNull(createdUserId);

        testUserId = createdUserId;
    }

    @Test
    @Order(2)
    void testFindUserById() {
        User foundUser = userRepository.getById(testUserId);
        assertNotNull(foundUser);
        assertEquals("testuser21", foundUser.getUsername());
        assertEquals("test21@example.com", foundUser.getEmail());
    }
    @Test
    @Order(3)
    void testGetByEmail() {
        User user = userRepository.getByEmail(email);

        assertNotNull(user, "Пользователь с таким email должен существовать");
        assertEquals(email, user.getEmail(), "Email пользователя не совпадает");
    }
    @Test
    @Order(4)
    void testUpdateUser() {
        User user = userRepository.getById(testUserId);
        assertNotNull(user, "Пользователь должен существовать перед обновлением");

        user.setUsername("UpdatedName1");
        user.setEmail("updated1@example.com");

        boolean isUpdated = userRepository.update(user);
        assertTrue(isUpdated, "Обновление должно быть успешным");

        User updatedUser = userRepository.getById(testUserId);
        assertEquals("UpdatedName1", updatedUser.getUsername(), "Имя пользователя не обновилось");
        assertEquals("updated1@example.com", updatedUser.getEmail(), "Email пользователя не обновился");
    }
    @Test
    @Order(5)
    void testGetAll() {
        List<User> users = userRepository.getAll();

        assertFalse(users.isEmpty(), "Список пользователей не должен быть пустым");
    }

    @Test
    @Order(6)
    void testDeleteUser() {
        boolean isDeleted = userRepository.delete(testUserId);
        assertTrue(isDeleted);

        User deletedUser = userRepository.getById(testUserId);
        assertNull(deletedUser);
    }
}
