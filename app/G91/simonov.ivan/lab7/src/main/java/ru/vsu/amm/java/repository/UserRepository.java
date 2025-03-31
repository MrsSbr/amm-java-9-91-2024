package ru.vsu.amm.java.repository;

import ru.vsu.amm.java.configuration.DatabaseConfiguration;
import ru.vsu.amm.java.entities.User;
import ru.vsu.amm.java.mapper.UserMapper;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserRepository implements ParkingRepository<User> {

    private static final Logger logger = Logger.getLogger(UserRepository.class.getName());
    private final DataSource dataSource;

    public UserRepository() {
        dataSource = DatabaseConfiguration.getDataSource();
    }

    public Optional<User> getByLoginAndPassword(String login, String password) {

        String sql = """
                SELECT Id_user, LastName, FirstName, Patronymic, Login, Password, Role
                FROM "User"
                WHERE Login = ? AND Password = ?
                """;

        try (Connection connection = dataSource.getConnection()) {

            UserMapper userMapper = new UserMapper();
            PreparedStatement stmt = userMapper.mapAuthorisation(connection, sql, login, password);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return Optional.of(userMapper.mapRowToObject(rs));
            }

        } catch (SQLException e) {

            logger.log(Level.SEVERE, e.getMessage(), e);

        }

        return Optional.empty();

    }

    @Override
    public Optional<User> getById(int id) {

        String sql = """
                SELECT Id_user, LastName, FirstName, Patronymic, Login, Password, Role
                FROM "User"
                WHERE Id_user = ?
                """;

        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, id);

            ResultSet rs = stmt.executeQuery();

            UserMapper userMapper = new UserMapper();

            if (rs.next()) {
                return Optional.of(userMapper.mapRowToObject(rs));
            }

        } catch (SQLException e) {

            logger.log(Level.SEVERE, e.getMessage(), e);

        }

        return Optional.empty();

    }

    @Override
    public List<User> getAll() {

        String sql = """
                SELECT Id_user, LastName, FirstName, Patronymic, Login, Password, Role
                FROM "User"
                """;

        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            ResultSet rs = stmt.executeQuery();

            List<User> users = new ArrayList<>();

            UserMapper userMapper = new UserMapper();

            while (rs.next()) {
                users.add(userMapper.mapRowToObject(rs));
            }

            return users;

        }  catch (SQLException e) {

            logger.log(Level.SEVERE, e.getMessage(), e);

        }

        return null;
    }

    @Override
    public void save(User entity) {

        String sql = """
                INSERT INTO "User" (LastName, FirstName, Patronymic, Login, Password, Role)
                VALUES (?, ?, ?, ?, ?, ?)
                """;

        try (Connection connection = dataSource.getConnection()) {

            UserMapper userMapper = new UserMapper();
            PreparedStatement stmt = userMapper.mapObjectToRow(entity, connection, sql);
            stmt.execute();

        } catch (SQLException e) {

            logger.log(Level.SEVERE, e.getMessage(), e);

        }

    }

    @Override
    public void update(User entity) {

        String sql = """
                UPDATE "User"
                SET LastName = ?, FirstName = ?, Patronymic = ?, Login = ?, Password = ?, Role = ?
                WHERE Id_user = ?
                """;

        try (Connection connection = dataSource.getConnection()) {

            UserMapper userMapper = new UserMapper();
            PreparedStatement stmt = userMapper.mapObjectToRow(entity, connection, sql);
            stmt.setInt(7, entity.getUserId());
            stmt.execute();

        } catch (SQLException e) {

            logger.log(Level.SEVERE, e.getMessage(), e);

        }
    }

    @Override
    public void delete(User entity) {

        String sql = """
                DELETE FROM "User"
                WHERE Id_user = ?
                """;

        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, entity.getUserId());
            stmt.execute();

        } catch (SQLException e) {

            logger.log(Level.SEVERE, e.getMessage(), e);

        }
    }
}
