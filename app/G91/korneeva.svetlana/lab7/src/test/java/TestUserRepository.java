import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.vsu.amm.java.config.DbConfig;
import ru.vsu.amm.java.entity.UserEntity;
import ru.vsu.amm.java.repository.UserRepository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestUserRepository {

    private UserRepository userRepository;

    @BeforeEach
    void setup() {
        userRepository = new UserRepository(DbConfig.getDataSourceTest());
    }

    @AfterEach
    public void cleanup() throws SQLException {
        DataSource dataSource = DbConfig.getDataSourceTest();
        Connection connection = dataSource.getConnection();
        try (Statement statement = connection.createStatement()) {
            statement.execute("DELETE FROM users");
        }
    }

    @Test
    void saveAndFindByEmail() throws SQLException {

        UserEntity user = new UserEntity(null, "test@example.com", "hashed_password");

        userRepository.save(user);
        Optional<UserEntity> result = userRepository.findByEmail("test@example.com");

        assertTrue(result.isPresent());
        assertEquals("test@example.com", result.get().getEmail());
        assertEquals("hashed_password", result.get().getHashPassword());
    }

    @Test
    void findByEmailWithNotExist() throws SQLException {
        Optional<UserEntity> result = userRepository.findByEmail("nonexistent@example.com");

        assertFalse(result.isPresent());
    }
}
