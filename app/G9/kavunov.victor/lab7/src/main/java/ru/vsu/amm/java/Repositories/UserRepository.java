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

    public UserRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public UserRepository() {
        dataSource = DbConfiguration.getDataSource();
    }

    @Override
    public Optional<User> findById(Long id) {
        String query = "SELECT User_id, Surname, Name, Patronymicname, Phone_number, Email, Password " +
                "FROM Users WHERE User_id = ?;";
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
        String query = "SELECT User_id, Surname, Name, Patronymicname, Phone_number, Email, Password " +
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
        String query = "INSERT INTO Users(Surname, Name, Patronymicname, Phone_number, Email, Password) VALUES(?, ?, ?, ?, ?, ?);";
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
        String query = "UPDATE Users SET Surname = ?, Name = ?, Patronymicname = ?, Phone_number = ?, Email = ?, Password = ? WHERE User_id = ?;";

        try (Connection connection = dataSource.getConnection()) {
            UserMapper userMapper = new UserMapper();
            PreparedStatement statement = userMapper.mapObjectToRow(entity, connection, query);
            statement.setLong(7, entity.getUserId());
            statement.execute();
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
    }

    @Override
    public void delete(Long id) throws SQLException {
        String query = "DELETE FROM Users WHERE user_id = ?;";
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, id);
            statement.execute();
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
    }

    public Optional<User> findByPhoneNumber(String phoneNumber) {
        String query = "SELECT User_id, Surname, Name, Patronymicname, Phone_number, Email, Password " +
                "FROM Users WHERE Phone_number = ?;";
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, phoneNumber);
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

    public Optional<User> findByEmail(String email) {
        String query = "SELECT User_id, Surname, Name, Patronymicname, Phone_number, Email, Password " +
                "FROM Users WHERE Email = ?;";
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, email);
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
}