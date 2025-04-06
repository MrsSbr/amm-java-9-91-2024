import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.vsu.amm.java.connection.DataSourceProvider;
import ru.vsu.amm.java.entities.User;
import ru.vsu.amm.java.repository.UserRepository;
import util.TestDatabaseInitializer;
import util.TestDataSourceProvider;

import java.io.IOException;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNull;

public class UserRepositoryTest {

    private UserRepository userRepository;
    private TestDatabaseInitializer dbInitializer;

    @BeforeEach
    public void setUp() throws SQLException, IOException {
        DataSourceProvider.setTestDataSource(TestDataSourceProvider.getTestDataSource());

        dbInitializer = new TestDatabaseInitializer(TestDataSourceProvider.getTestDataSource());
        dbInitializer.initializeDatabase("src/test/resources/CREATE.sql", "src/test/resources/INSERT.sql");

        userRepository = new UserRepository();
    }

    @AfterEach
    public void tearDown() throws SQLException {
        try (var conn = TestDataSourceProvider.getTestDataSource().getConnection();
             var stmt = conn.createStatement()) {
            stmt.execute("DROP ALL OBJECTS");
        }

        DataSourceProvider.resetTestDataSource();
    }

    @Test
    public void testGetById() {
        User user = userRepository.getById(1L);
        assertNotNull(user);
        assertEquals("User1", user.getLogin());
        assertEquals("+72345678966", user.getPhoneNumber());
        assertEquals("111", user.getPassword());
        assertEquals("user1@gmail.com", user.getEmail());
    }

    @Test
    public void testGetAll() {
        assertEquals(3, userRepository.getAll().size());
    }

    @Test
    public void testSave() {
        User newUser = new User();
        newUser.setPassword("newpassword");
        newUser.setPhoneNumber("+71122233377");
        newUser.setEmail("newuser@mail.com");
        newUser.setLogin("NewUser");

        userRepository.save(newUser);

        User savedUser = userRepository.findByLoginAndPassword("NewUser", "newpassword");
        assertNotNull(savedUser);
        assertEquals("NewUser", savedUser.getLogin());
        assertEquals("+71122233377", savedUser.getPhoneNumber());
    }

    @Test
    public void testSaveFailure() {
        User newUser = new User();
        newUser.setPassword("111");
        newUser.setPhoneNumber("+72345678966");
        newUser.setEmail("duplicate@gmail.com");
        newUser.setLogin("User1");

        Exception exception = assertThrows(RuntimeException.class, () -> userRepository.save(newUser));

        assertNotNull(exception.getCause());
        SQLException sqlException = (SQLException) exception.getCause();
        assertEquals(UserRepository.CODE, sqlException.getSQLState());
        assertTrue(sqlException.getMessage().contains("Unique index or primary key violation"));
    }

    @Test
    public void testUpdate() {
        User user = userRepository.getById(1L);
        assertNotNull(user);

        user.setPassword("updatedpassword");
        user.setPhoneNumber("+79298765432");
        userRepository.update(user);

        User updatedUser = userRepository.getById(1L);
        assertEquals("updatedpassword", updatedUser.getPassword());
        assertEquals("+79298765432", updatedUser.getPhoneNumber());
    }

    @Test
    public void testDelete() {
        userRepository.delete(3L);

        User deletedUser = userRepository.getById(3L);
        assertNull(deletedUser);
        assertEquals(2, userRepository.getAll().size());
    }

    @Test
    public void testFindByLoginAndPassword() {
        User user = userRepository.findByLoginAndPassword("User2", "222");
        assertNotNull(user);
        assertEquals("User2", user.getLogin());
    }
}
