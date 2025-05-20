import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.vsu.amm.java.configuration.DatabaseConfig;
import ru.vsu.amm.java.entity.User;
import ru.vsu.amm.java.repository.UserRepository;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UserRepositoryTest {
    private static UserRepository userRepository;
    private static HikariDataSource dataSource;

    @BeforeAll
    static void setup() throws SQLException {
        System.setProperty("test", "true");
        dataSource = DatabaseConfig.getDataSource();
        userRepository = new UserRepository(dataSource);
    }

    @BeforeEach
    void cleanDatabase() throws SQLException {
        Connection connection = dataSource.getConnection();
        Statement statement = connection.createStatement();
        statement.execute("DELETE FROM user_entity");
    }

    @Test
    void testSaveAndFindById() {
        User user = new User(null, "Test User", "test_login", "password123");
        long userId = userRepository.save(user);

        Optional<User> found = userRepository.findById(userId);
        assertTrue(found.isPresent());
        assertEquals("Test User", found.get().getName());
        assertEquals("test_login", found.get().getLogin());
    }

    @Test
    void testFindAll() {
        userRepository.save(new User(null, "User One", "login1", "pass1"));
        userRepository.save(new User(null, "User Two", "login2", "pass2"));

        List<User> users = userRepository.findAll();
        assertEquals(2, users.size());
    }

    @Test
    void testDelete() {
        User user = new User(null, "To Delete", "delete_login", "delete_pass");
        long userId = userRepository.save(user);
        user.setId(userId);

        userRepository.delete(user);
        Optional<User> deletedUser = userRepository.findById(user.getId());

        assertFalse(deletedUser.isPresent());
    }
}