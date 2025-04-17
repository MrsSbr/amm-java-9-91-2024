package ru.vsu.amm.java.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.vsu.amm.java.exception.SqlException;
import ru.vsu.amm.java.model.User;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserRepository implements CrudRepository<User> {
    private static final Logger logger = LoggerFactory.getLogger(UserRepository.class);
    private final DataSource dataSource;

    public UserRepository(final DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Optional<User> findByLogin(String login) {
        logger.info("Find user with login={}", login);
        String sql = "SELECT id, name, login, hash_password FROM user_entity WHERE login = ?";
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, login);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    User user = new User(
                            resultSet.getLong("id"),
                            resultSet.getString("name"),
                            resultSet.getString("login"),
                            resultSet.getString("hash_password"));
                    logger.info("User found: {}", user);
                    return Optional.of(user);
                }
            }

        } catch (SQLException e) {
            logger.error("Error finding user with login={}: {}", login, e.getMessage());
            throw new SqlException(e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public Optional<User> findById(long id) {
        logger.info("Find user with id={}", id);
        String sql = "SELECT id, name, login, hash_password FROM user_entity WHERE id = ?";
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, id);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Long userId = resultSet.getLong("id");
                String name = resultSet.getString("name");
                String login = resultSet.getString("login");
                String hashPassword = resultSet.getString("hash_password");
                User user = new User(userId, name, login, hashPassword);
                logger.info("User found: {}", user);
                return Optional.of(user);
            }

        } catch (SQLException e) {
            logger.error("Error finding user with id={}: {}", id, e.getMessage());
            throw new SqlException(e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public List<User> findAll() {
        logger.info("Find all users");
        List<User> users = new ArrayList<>();
        String sql = "SELECT id, name, login, hash_password FROM user_entity";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String name = resultSet.getString("name");
                String login = resultSet.getString("login");
                String hashPassword = resultSet.getString("hash_password");
                users.add(new User(id, name, login, hashPassword));
            }
            logger.info("Found {} users", users.size());
        } catch (SQLException e) {
            logger.error("Error found all users: {}", e.getMessage());
            throw new SqlException(e.getMessage());
        }
        return users;
    }

    @Override
    public void save(User user) {
        logger.info("Saving user: {}", user);
        String sql = user.getId() == null ? "INSERT INTO user_entity (name, login, hash_password) VALUES (?, ?, ?)" : "UPDATE user SET name = ?, login = ?, hash_password = ? WHERE id = ?";

        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, user.getName());
            statement.setString(2, user.getLogin());
            statement.setString(3, user.getHashPassword());

            if (user.getId() != null) {
                statement.setLong(4, user.getId());
            }
            statement.executeUpdate();

            logger.info("User saved successfully: {}", user);
        } catch (SQLException e) {
            logger.error("Error saving user {}: {}", user, e.getMessage());
            throw new SqlException(e.getMessage());
        }
    }

    @Override
    public void delete(User user) {
        logger.info("Deleting user with id={}", user.getId());
        String sql = "DELETE FROM user_entity WHERE id = ?";
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, user.getId());

            statement.executeUpdate();

            logger.info("User with id={} deleted successfully", user.getId());
        } catch (SQLException e) {
            logger.error("Error deleting user with id={}: {}", user.getId(), e.getMessage());
            throw new SqlException(e.getMessage());
        }
    }
}
