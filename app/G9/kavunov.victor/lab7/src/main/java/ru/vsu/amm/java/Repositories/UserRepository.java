package ru.vsu.amm.java.Repositories;

import lombok.extern.slf4j.Slf4j;
import ru.vsu.amm.java.Configuration.DbConfiguration;
import ru.vsu.amm.java.Entities.User;
import ru.vsu.amm.java.Mappers.UserMapper;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
public class UserRepository implements CrudRepository<User> {
    private final DataSource dataSource;

    public UserRepository() {
        dataSource = DbConfiguration.getDataSource();
    }

    @Override
    public Optional<User> findById(Long id) {
        String query = "SELECT Surname, Name, Patronymicname, PhoneNumber, Email, Password, Birthday, Role " +
                "FROM Users WHERE UserId = ?;";
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            UserMapper userMapper = new UserMapper();

            if (resultSet.next()) {
                return Optional.of(userMapper.mapRowToObject(resultSet));
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
        }

        return Optional.empty();
    }

    @Override
    public List<User> findAll() throws SQLException {
        String query = "SELECT * " +
                "FROM Users;";
        List<User> result = new ArrayList<>();
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            UserMapper userMapper = new UserMapper();
            while (resultSet.next()) {
                User user = userMapper.mapRowToObject(resultSet);
                result.add(user);
            }
            return result;
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
        return new ArrayList<>();
    }

    @Override
    public void save(User entity) throws SQLException {
        String query = "INSERT INTO Users(Surname, Name, Patronymicname, PhoneNumber, Email, Password, Birthday, Role) " +
                "VALUES(?, ?, ?, ?, ?, ?, ?, ?);";
        try (Connection connection = dataSource.getConnection()) {
            UserMapper userMapper = new UserMapper();
            PreparedStatement statement = userMapper.mapObjectToRow(entity, connection, query);
            statement.execute();
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
    }

    @Override
    public void update(User entity) throws SQLException {
        String query = "UPDATE Users " +
                "SET Surname = ?, Name = ?, Patronymicname = ?, PhoneNumber = ?, Email = ?, Password = ?, " +
                "Birthday = ?, Role = ? " +
                "WHERE UserID = ?;";

        try (Connection connection = dataSource.getConnection()) {
            UserMapper userMapper = new UserMapper();
            PreparedStatement statement = userMapper.mapObjectToRow(entity, connection, query);
            statement.setLong(9, entity.getUserId());
            statement.execute();
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
    }

    @Override
    public void delete(Long id) throws SQLException {
        String query = "DELETE FROM Users WHERE UserId = ?;";
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, id);
            statement.execute();
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
    }
}