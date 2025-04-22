import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.vsu.amm.java.entity.UserEntity;
import ru.vsu.amm.java.repository.UserRepository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserRepositoryTest {

    private UserRepository userRepository;

    private PreparedStatement preparedStatement;

    private ResultSet resultSet;

    @BeforeEach
    void setUp() throws Exception {
        DataSource dataSource = mock(DataSource.class);
        Connection connection = mock(Connection.class);
        preparedStatement = mock(PreparedStatement.class);
        resultSet = mock(ResultSet.class);

        when(dataSource.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString(), anyInt())).thenReturn(preparedStatement);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);

        userRepository = new UserRepository(dataSource);
    }


    @Test
    void testSave() throws Exception {
        UserEntity user = new UserEntity(null, "testUser", "testPass");

        when(preparedStatement.executeUpdate()).thenReturn(1);
        when(preparedStatement.getGeneratedKeys()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getLong(1)).thenReturn(1L);

        Long id = userRepository.save(user);
        assertNotNull(id);
        assertEquals(1L, id);
    }

    @Test
    void testFindById() throws Exception {
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getLong("id")).thenReturn(1L);
        when(resultSet.getString("login")).thenReturn("testUser");
        when(resultSet.getString("password")).thenReturn("testPass");

        Optional<UserEntity> userOpt = userRepository.findById(1L);

        assertTrue(userOpt.isPresent());
        UserEntity user = userOpt.get();
        assertEquals("testUser", user.getLogin());
        assertEquals("testPass", user.getPassword());
    }

    @Test
    void testFindByLogin() throws Exception {
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getLong("id")).thenReturn(1L);
        when(resultSet.getString("login")).thenReturn("user");
        when(resultSet.getString("password")).thenReturn("pass");

        Optional<UserEntity> userOpt = userRepository.findByLogin("user");

        assertTrue(userOpt.isPresent());
        UserEntity user = userOpt.get();
        assertEquals("user", user.getLogin());
        assertEquals("pass", user.getPassword());
    }

}
