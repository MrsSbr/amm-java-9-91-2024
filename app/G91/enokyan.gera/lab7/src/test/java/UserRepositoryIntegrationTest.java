import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.vsu.amm.java.configuration.DatabaseConfiguration;
import ru.vsu.amm.java.entity.Role;
import ru.vsu.amm.java.entity.UserEntity;
import ru.vsu.amm.java.repository.UserRepository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UserRepositoryIntegrationTest {

    private UserRepository userRepository;

    @BeforeEach
    void setup() {
        userRepository = new UserRepository();
    }

    @AfterEach
    public void cleanup() throws SQLException {
        DataSource dataSource = DatabaseConfiguration.getDataSource();
        try (Connection connection = dataSource.getConnection(); Statement statement = connection.createStatement()) {
            statement.execute("DELETE FROM elo_user");
        }
    }

    @Test
    public void testCreateAndFindById() {
        UserEntity user = new UserEntity();
        user.setNickname("test");
        user.setPassword("test");
        user.setRating(1000d);
        user.setRoles(List.of(Role.Player));

        long id = userRepository.create(user);

        UserEntity fetchedUser = userRepository.findById(id);
        assertNotNull(fetchedUser);
        assertEquals("test", fetchedUser.getNickname());
        assertEquals("test", fetchedUser.getPassword());
    }

    @Test
    public void testFindByNickname() {
        UserEntity user = new UserEntity();
        user.setNickname("test");
        user.setPassword("test");
        user.setRating(1000d);
        user.setRoles(List.of(Role.Player));

        userRepository.create(user);

        UserEntity fetchedUser = userRepository.findByNickname("test");
        assertNotNull(fetchedUser);
        assertEquals("test", fetchedUser.getNickname());
        assertEquals("test", fetchedUser.getPassword());
    }

    @Test
    public void testFindAll() {
        List<UserEntity> users = userRepository.findAll();
        assertEquals(0, users.size());

        UserEntity user1 = new UserEntity();
        user1.setNickname("test1");
        user1.setPassword("test");
        user1.setRating(1000d);
        user1.setRoles(List.of(Role.Player));

        UserEntity user2 = new UserEntity();
        user2.setNickname("test2");
        user2.setPassword("test");
        user2.setRating(1000d);
        user2.setRoles(List.of(Role.Player));

        userRepository.create(user1);
        userRepository.create(user2);

        users = userRepository.findAll();
        assertEquals(2, users.size());

        boolean foundUser1 = users.stream().anyMatch(u -> "test1".equals(u.getNickname()) && "test".equals(u.getPassword()));
        boolean foundUser2 = users.stream().anyMatch(u -> "test2".equals(u.getNickname()) && "test".equals(u.getPassword()));

        assertTrue(foundUser1 && foundUser2);
    }
}