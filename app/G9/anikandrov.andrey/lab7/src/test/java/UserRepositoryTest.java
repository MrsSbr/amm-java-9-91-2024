import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ru.vsu.amm.java.Entities.UserEntity;
import ru.vsu.amm.java.Enums.Roles;
import ru.vsu.amm.java.Repository.UserRepository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

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
        UserEntity user = new UserEntity("user1", "password1");

        when(preparedStatement.executeUpdate()).thenReturn(1);
        when(preparedStatement.getGeneratedKeys()).thenReturn(resultSet);

        when(resultSet.next()).thenReturn(true);
        when(resultSet.getLong(1)).thenReturn(1L);

        userRepository.save(user);

        assertEquals("user1", user.getUserName());
        assertEquals("password1", user.getUserPassword());
    }

    @Test
    void testFindById() throws Exception {
        when(preparedStatement.execute()).thenReturn(true);
        when(preparedStatement.getResultSet()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);

        when(resultSet.getLong("User_ID")).thenReturn(1L);
        when(resultSet.getString("User_Name")).thenReturn("user1");
        when(resultSet.getString("User_Password")).thenReturn("password1");
        when(resultSet.getString("User_Role")).thenReturn("GUEST");
        when(resultSet.getString("Phone")).thenReturn(null);
        when(resultSet.getDate("Birth_Date")).thenReturn(null);

        Optional<UserEntity> userOpt = userRepository.findById(1L);

        assertTrue(userOpt.isPresent());
        UserEntity user = userOpt.get();
        assertEquals("user1", user.getUserName());
        assertEquals("password1", user.getUserPassword());
    }

    @Test
    void testFindByUserName() throws Exception {
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getLong("User_ID")).thenReturn(1L);
        when(resultSet.getString("User_Name")).thenReturn("user1");
        when(resultSet.getString("User_Password")).thenReturn("password1");
        when(resultSet.getString("User_Role")).thenReturn("GUEST");
        when(resultSet.getString("Phone")).thenReturn(null);
        when(resultSet.getDate("Birth_Date")).thenReturn(null);

        Optional<UserEntity> userOpt = userRepository.findByUserName("user1");

        assertTrue(userOpt.isPresent());
        UserEntity user = userOpt.get();
        assertEquals("user1", user.getUserName());
        assertEquals("password1", user.getUserPassword());
        assertEquals(Roles.GUEST, user.getUserRole());
    }

    @Test
    void testFindAll() throws Exception {
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true, true, false);

        when(resultSet.getLong("User_ID")).thenReturn(1L, 2L);
        when(resultSet.getString("User_Name")).thenReturn("user1", "user2");
        when(resultSet.getString("User_Password")).thenReturn("password1", "password2");
        when(resultSet.getString("User_Role")).thenReturn("GUEST", "ADMIN");
        when(resultSet.getString("Phone")).thenReturn("123", "456");
        when(resultSet.getDate("Birth_Date")).thenReturn(Date.valueOf(LocalDate.of(1990, 1, 1)), Date.valueOf(LocalDate.of(1995, 1, 1)));

        List<UserEntity> users = userRepository.findAll();

        assertEquals(2, users.size());

        UserEntity user1 = users.get(0);
        assertEquals(1L, user1.getUserID());
        assertEquals("user1", user1.getUserName());
        assertEquals(Roles.GUEST, user1.getUserRole());

        UserEntity user2 = users.get(1);
        assertEquals(2L, user2.getUserID());
        assertEquals("user2", user2.getUserName());
        assertEquals(Roles.ADMIN, user2.getUserRole());
    }

    @Test
    void testDelete() throws Exception {
        UserEntity user = new UserEntity(1L, "user1", "password1");

        when(preparedStatement.executeUpdate()).thenReturn(1);

        userRepository.delete(user);

        verify(preparedStatement).setLong(1, 1L);
        verify(preparedStatement).execute();
    }

    @Test
    void testUpdate() throws Exception {
        UserEntity user = new UserEntity(1L, "user3", "password3");

        when(preparedStatement.executeUpdate()).thenReturn(1);

        userRepository.update(user);

        verify(preparedStatement).setString(1, "user3");
        verify(preparedStatement).setString(2, "password3");
        verify(preparedStatement).setLong(3, 1L);
        verify(preparedStatement).execute();
    }

    @Test
    void testFindByIdNotFound() throws Exception {
        when(preparedStatement.execute()).thenReturn(true);
        when(preparedStatement.getResultSet()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(false);

        Optional<UserEntity> userOpt = userRepository.findById(999L);

        assertFalse(userOpt.isPresent());
    }

    @Test
    void testFindByUserNameNotFound() throws Exception {
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(false);

        Optional<UserEntity> userOpt = userRepository.findByUserName("user999");

        assertFalse(userOpt.isPresent());
    }
}