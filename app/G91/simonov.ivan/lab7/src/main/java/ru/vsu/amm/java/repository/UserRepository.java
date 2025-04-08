package ru.vsu.amm.java.repository;

import ru.vsu.amm.java.configuration.DatabaseConfiguration;
import ru.vsu.amm.java.entities.User;
import ru.vsu.amm.java.exceptions.DeleteException;
import ru.vsu.amm.java.exceptions.UpdateException;
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

import static ru.vsu.amm.java.utils.LoggerInitializer.initializeLogger;

public class UserRepository implements ParkingRepository<User> {

    private static final Logger logger = initializeLogger(
            "app/G91/simonov.ivan/lab7/src/main/java/ru/vsu/amm/java/logs/user-repository-logs.log",
            SessionRepository.class.getName());

    private final DataSource dataSource;

    public UserRepository() {
        dataSource = DatabaseConfiguration.getDataSource();
    }

    public Optional<User> getByLoginAndPassword(String login, String password) {

        String sql = """
                SELECT Id_user, LastName, FirstName, Patronymic, Login, "Password", "Role"
                FROM "User"
                WHERE Login = ? AND "Password" = ?
                """;

        try (Connection connection = dataSource.getConnection()) {

            UserMapper userMapper = new UserMapper();
            PreparedStatement stmt = userMapper.mapAuthorisation(connection, login, password, sql);

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
                SELECT Id_user, LastName, FirstName, Patronymic, Login, "Password", "Role"
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
                SELECT Id_user, LastName, FirstName, Patronymic, Login, "Password", "Role"
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

        } catch (SQLException e) {

            logger.log(Level.SEVERE, e.getMessage(), e);

        }

        return null;
    }

    @Override
    public int save(User entity) {

        String sql = """
                INSERT INTO "User" (LastName, FirstName, Patronymic, Login, "Password", "Role")
                VALUES (?, ?, ?, ?, ?, ?)
                """;

        try (Connection connection = dataSource.getConnection()) {

            UserMapper userMapper = new UserMapper();
            PreparedStatement stmt = userMapper.mapObjectToRow(entity, connection, sql);
            stmt.execute();

            ResultSet generatedKeys = stmt.getGeneratedKeys();

            if (generatedKeys.next()) {
                return generatedKeys.getInt(1);
            }

        } catch (SQLException e) {

            logger.log(Level.SEVERE, e.getMessage(), e);

        }

        return 0;
    }

    @Override
    public void update(User entity) {

        String sql = """
                UPDATE "User"
                SET LastName = ?, FirstName = ?, Patronymic = ?, Login = ?, "Password" = ?, "Role" = ?
                WHERE Id_user = ?
                """;

        try (Connection connection = dataSource.getConnection()) {

            UserMapper userMapper = new UserMapper();
            PreparedStatement stmt = userMapper.mapObjectToRow(entity, connection, sql);
            stmt.setInt(7, entity.getUserId());
            stmt.execute();

        } catch (SQLException e) {

            logger.log(Level.SEVERE, e.getMessage(), e);
            throw new UpdateException(e.getMessage());

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
            throw new DeleteException(e.getMessage());

        }
    }
}
