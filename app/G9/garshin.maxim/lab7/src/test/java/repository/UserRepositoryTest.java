package repository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.vsu.amm.java.entities.UserEntity;
import ru.vsu.amm.java.repository.UserRepository;

import javax.sql.DataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserRepositoryTest {

    @Mock
    private DataSource dataSource;

    @Mock
    private Connection connection;

    @Mock
    private PreparedStatement preparedStatement;

    @Mock
    private ResultSet resultSet;

    @InjectMocks
    private UserRepository userRepository;

    private void mockResultSetForFindById() throws SQLException {
        when(resultSet.getString("username")).thenReturn("username");
        when(resultSet.getString("email")).thenReturn("email@email");
        when(resultSet.getString("userPassword")).thenReturn("passw0rd");
    }

    private void mockResultSet() throws SQLException {
        when(resultSet.getLong("userId")).thenReturn(1L);
        when(resultSet.getString("username")).thenReturn("username");
        when(resultSet.getString("email")).thenReturn("email@email");
        when(resultSet.getString("userPassword")).thenReturn("passw0rd");
    }

    @Test
    void findById_ShouldReturnUser() throws SQLException {
        when(dataSource.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);

        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        mockResultSetForFindById();

        Optional<UserEntity> userOptional = userRepository.findById(1L);
        assertTrue(userOptional.isPresent(), "Ожидаем, что Optional не пустой");

        UserEntity user = userOptional.get();
        assertEquals("username", user.getUsername());
        assertEquals("email@email", user.getEmail());
        assertEquals("passw0rd", user.getUserPassword());

        verify(preparedStatement).setLong(1, 1L);
        verify(preparedStatement).executeQuery();
    }

    @Test
    void findById_WhenNotFound_ShouldReturnEmpty() throws SQLException {
        when(dataSource.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);

        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(false);

        Optional<UserEntity> userOptional = userRepository.findById(42L);
        assertTrue(userOptional.isEmpty(), "Если resultSet.next() == false, то Optional.empty()");

        verify(preparedStatement).setLong(1, 42L);
        verify(preparedStatement).executeQuery();
    }

    @Test
    void findByUsername_ShouldReturnUser() throws SQLException {
        when(dataSource.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);

        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        mockResultSet();

        String username = "username";
        Optional<UserEntity> userOptional = userRepository.findByUsername(username);
        assertTrue(userOptional.isPresent());

        UserEntity user = userOptional.get();
        assertEquals(1L, user.getUserId());
        assertEquals("username", user.getUsername());
        assertEquals("email@email", user.getEmail());
        assertEquals("passw0rd", user.getUserPassword());

        verify(preparedStatement).setString(1, username);
        verify(preparedStatement).executeQuery();
    }

    @Test
    void findByUsername_WhenNotFound_ShouldReturnEmpty() throws SQLException {
        when(dataSource.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);

        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(false);

        Optional<UserEntity> userOptional = userRepository.findByUsername("no_such_user");
        assertTrue(userOptional.isEmpty());

        verify(preparedStatement).setString(1, "no_such_user");
        verify(preparedStatement).executeQuery();
    }
}