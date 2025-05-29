package ru.vsu.amm.java.Service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.vsu.amm.java.DBConnection.DBConfiguration;
import ru.vsu.amm.java.Repository.Entities.Shareholder;
import ru.vsu.amm.java.Repository.ShareholderRepository;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class RepositoryIntegrationTest {
    private ShareholderRepository repository;

    @BeforeEach
    void setUp() {
        repository = new ShareholderRepository(DBConfiguration.getTestDataSource());
    }

    @AfterEach
    public void clear() throws SQLException {
        DataSource dataSource = DBConfiguration.getTestDataSource();
        var connection = dataSource.getConnection();
        try(Statement statement = connection.createStatement();){
            statement.execute("DELETE FROM shareholder WHERE id>1");
        }
    }

    @Test
    public void getDataFromBDByIdSuccess() throws  SQLException{
        Optional<Shareholder> user = repository.get(1);
        assertFalse(user.isEmpty());
        assertEquals(user.get().getEmail(),"scam@mail.ru");
    }

    @Test
    public void getDataFromBDByIdNotFound() throws  SQLException{
        Optional<Shareholder> user = repository.get(100);
        assertTrue(user.isEmpty());
    }

    @Test
    public void getDataFromBDByEmailSuccess() throws  SQLException{
        Optional<Shareholder> user = repository.getByEmail("scam@mail.ru");
        assertFalse(user.isEmpty());
        assertEquals(user.get().getId(),1);
    }

    @Test
    public void getDataFromBDByEmailNotFound() throws  SQLException{
        Optional<Shareholder> user = repository.getByEmail("scaadasdad");
        assertTrue(user.isEmpty());
    }

    @Test
    public void CreateUserSuccess() throws  SQLException{
        Shareholder user = new Shareholder(2,"12345678","qerty","123","qwe@","231");
        repository.create(user);
        Optional<Shareholder> user2 = repository.getByEmail("qwe@");
        assertFalse(user2.isEmpty());
    }

    @Test
    public void CreateUserException() throws  SQLException{
        Shareholder user = new Shareholder(2,"12345678","qerty","123","scam@mail.ru","231");
        SQLException exception = assertThrows(SQLException.class,() -> repository.create(user));
        assertEquals(exception.getSQLState(),"23505");
    }

}
