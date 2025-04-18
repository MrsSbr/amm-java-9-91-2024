package repositories;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.vsu.amm.java.entities.UserEntity;
import ru.vsu.amm.java.enums.Role;
import ru.vsu.amm.java.repositories.UserRepository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserRepositoryTest {

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

    private void mockResultSet() throws SQLException {
        when(resultSet.getLong("User_id")).thenReturn(1L);
        when(resultSet.getString("First_Name")).thenReturn("Ivan");
        when(resultSet.getString("Last_Name")).thenReturn("Ivanov");
        when(resultSet.getString("Patronymic")).thenReturn("Ivanovich");
        when(resultSet.getString("City")).thenReturn("Voronezh");
        when(resultSet.getString("Email")).thenReturn("user@test.com");
        when(resultSet.getString("Phone_Number")).thenReturn("89994441234");
        when(resultSet.getString("Password")).thenReturn("passw0rd");
        when(resultSet.getString("Role")).thenReturn("USER");
    }

    @Test
    void findById_ShouldReturnUser() throws SQLException {

        when(dataSource.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.execute()).thenReturn(true);
        when(preparedStatement.getResultSet()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        mockResultSet();

        Optional<UserEntity> userOptional = userRepository.findById(1L);

        assertTrue(userOptional.isPresent());

        UserEntity user = userOptional.get();

        assertEquals("user@test.com", user.getEmail());
        assertEquals("passw0rd", user.getPassword());
        verify(preparedStatement).setLong(1, 1L);
        verify(preparedStatement).execute();
    }

    @Test
    void findByEmail_ShouldReturnUser() throws SQLException {

        when(dataSource.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.execute()).thenReturn(true);
        when(preparedStatement.getResultSet()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        mockResultSet();

        String email = "user@test.com";
        Optional<UserEntity> userOptional = userRepository.findByEmail(email);

        assertTrue(userOptional.isPresent());

        UserEntity user = userOptional.get();

        assertEquals(email, user.getEmail());
        assertEquals("passw0rd", user.getPassword());
        verify(preparedStatement).setString(1, email);
        verify(preparedStatement).execute();
    }

    @Test
    void save_ShouldExecuteInsert() throws SQLException {

        String email = "user@test.com";
        String password = "passw0rd";
        String firstName = "Ivan";
        String lastName = "Ivanov";
        String patronymic = "Ivanovich";
        String city = "Voronezh";
        String phoneNumber = "89994441234";
        Role role = Role.USER;

        UserEntity user = new UserEntity(null,
                firstName, lastName, patronymic,
                city, email, phoneNumber, password, role);

        when(dataSource.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.execute()).thenReturn(false);

        userRepository.save(user);

        verify(preparedStatement).setString(1, firstName);
        verify(preparedStatement).setString(2, lastName);
        verify(preparedStatement).setString(3, patronymic);
        verify(preparedStatement).setString(4, city);
        verify(preparedStatement).setString(5, email);
        verify(preparedStatement).setString(6, phoneNumber);
        verify(preparedStatement).setString(7, password);
        verify(preparedStatement).setString(8, role.toString());
    }
}
