import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.vsu.amm.java.configuration.DatabaseConfiguration;
import ru.vsu.amm.java.entity.UserEntity;
import ru.vsu.amm.java.repository.UserRepository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UserRepositoryTest {

    private UserRepository userRepository;

    @BeforeEach
    void setup() {
        userRepository = new UserRepository();
    }

    @AfterEach
    public void cleanup() throws SQLException {
        DataSource dataSource = DatabaseConfiguration.getDataSource();
        Connection connection = dataSource.getConnection();
        try (Statement statement = connection.createStatement()) {
            statement.execute("DELETE FROM user_entity");
        }
    }

    @Test
    public void testSaveAndFindById() throws SQLException {
        UserEntity user = new UserEntity(null, "testUser", "testPass");
        userRepository.save(user);

        Optional<UserEntity> fetchedByLogin = userRepository.findByLogin("testUser");
        assertTrue(fetchedByLogin.isPresent(), "Пользователь должен быть найден по логину после сохранения");

        Long userId = fetchedByLogin.get().getId();
        assertNotNull(userId, "Id пользователя не должен быть null");

        Optional<UserEntity> fetchedById = userRepository.findById(userId);
        assertTrue(fetchedById.isPresent(), "Пользователь должен быть найден по id");
        assertEquals("testUser", fetchedById.get().getLogin());
        assertEquals("testPass", fetchedById.get().getPassword());
    }

    @Test
    public void testFindByLogin() throws SQLException {
        UserEntity user = new UserEntity(null, "user", "pass");
        userRepository.save(user);

        Optional<UserEntity> fetchedUser = userRepository.findByLogin("user");
        assertTrue(fetchedUser.isPresent(), "Пользователь должен быть найден по логину");
        assertEquals("user", fetchedUser.get().getLogin());
        assertEquals("pass", fetchedUser.get().getPassword());
    }

    @Test
    public void testFindAll() throws SQLException {
        List<UserEntity> users = userRepository.findAll();
        assertEquals(0, users.size(), "В начале теста пользователей быть не должно");

        userRepository.save(new UserEntity(null, "user1", "pass1"));
        userRepository.save(new UserEntity(null, "user2", "pass2"));

        users = userRepository.findAll();
        assertEquals(2, users.size(), "Должно быть найдено 2 пользователя");

        boolean foundUser1 = users.stream()
                .anyMatch(u -> "user1".equals(u.getLogin()) && "pass1".equals(u.getPassword()));
        boolean foundUser2 = users.stream()
                .anyMatch(u -> "user2".equals(u.getLogin()) && "pass2".equals(u.getPassword()));

        assertTrue(foundUser1 && foundUser2, "Оба пользователя должны быть найдены с корректными данными");
    }
}
