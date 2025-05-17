import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.vsu.amm.java.entity.Role;
import ru.vsu.amm.java.entity.UserEntity;
import ru.vsu.amm.java.repository.UserRepository;

import javax.sql.DataSource;

import java.sql.Array;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserRepositoryTest {
    private UserRepository userRepository;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;
    private Array array;

    @BeforeEach
    void setUp() throws SQLException {
        DataSource dataSource = mock(DataSource.class);
        Connection connection = mock(Connection.class);
        preparedStatement = mock(PreparedStatement.class);
        resultSet = mock(ResultSet.class);
        array = mock(Array.class);

        when(dataSource.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString(), anyInt())).thenReturn(preparedStatement);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);

        userRepository = new UserRepository(dataSource);
    }

    @Test
    void testCreate() throws SQLException {
        UserEntity user = new UserEntity();
        user.setNickname("test");
        user.setPassword("test");
        user.setRating(1000d);
        user.setRoles(List.of(Role.Player));

        when(preparedStatement.executeUpdate()).thenReturn(1);
        when(preparedStatement.getGeneratedKeys()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getLong(1)).thenReturn(1L);

        Long id = userRepository.create(user);
        assertEquals(1L, id);
    }

    @Test
    void testFindById() throws SQLException {
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getArray("roles")).thenReturn(array);
        when(array.getArray()).thenReturn(new String[]{"Player"});
        when(resultSet.getLong("id")).thenReturn(1L);
        when(resultSet.getString("nickname")).thenReturn("test");
        when(resultSet.getString("password")).thenReturn("test");
        when(resultSet.getDouble("rating")).thenReturn(1000d);

        UserEntity user = userRepository.findById(1L);

        assertNotNull(user);
        assertEquals("test", user.getNickname());
        assertEquals("test", user.getPassword());
    }

    @Test
    void testFindByNickname() throws SQLException {
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getArray("roles")).thenReturn(array);
        when(array.getArray()).thenReturn(new String[]{"Player"});
        when(resultSet.getLong("id")).thenReturn(1L);
        when(resultSet.getString("nickname")).thenReturn("test");
        when(resultSet.getString("password")).thenReturn("test");
        when(resultSet.getDouble("rating")).thenReturn(1000d);

        UserEntity user = userRepository.findByNickname("test");

        assertNotNull(user);
        assertEquals("test", user.getNickname());
        assertEquals("test", user.getPassword());
    }
}