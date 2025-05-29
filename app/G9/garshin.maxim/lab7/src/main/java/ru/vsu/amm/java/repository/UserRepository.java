package ru.vsu.amm.java.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.vsu.amm.java.config.DatabaseConfiguration;
import ru.vsu.amm.java.entities.UserEntity;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserRepository implements Repository<UserEntity> {
    private static final Logger logger = LoggerFactory.getLogger(UserRepository.class);
    private final DataSource dataSource;

    public UserRepository() {
        this.dataSource = DatabaseConfiguration.getDataSource();
    }

    public UserRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Optional<UserEntity> findById(Long id) throws SQLException {
        final String query = "SELECT userId, username, email, userPassword FROM UserEntity WHERE userId = ?";

        logger.info("Executing query: {} with id: {}", query, id);

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                UserEntity user = new UserEntity(
                        resultSet.getString("username"),
                        resultSet.getString("email"),
                        resultSet.getString("userPassword")
                );
                logger.info("User found: {}", user);
                return Optional.of(user);
            }
            logger.info("No user found with id: {}", id);
            return Optional.empty();
        }
    }

    @Override
    public List<UserEntity> findAll() throws SQLException {
        final String query = "SELECT userId, username, email, userPassword FROM UserEntity";
        logger.info("Executing query: {}", query);

        List<UserEntity> users = new ArrayList<>();

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {


            while (resultSet.next()) {
                UserEntity user = new UserEntity(
                        resultSet.getString("username"),
                        resultSet.getString("email"),
                        resultSet.getString("userPassword")
                );
                users.add(user);
                logger.info("User retrieved: {}", user);
            }
        }
        logger.info("Total users found: {}", users.size());
        return users;
    }

    @Override
    public void save(UserEntity entity) throws SQLException {
        final String query = "INSERT INTO UserEntity (username, email, userPassword) VALUES (?, ?, ?)";
        logger.info("Executing query: {} with username: {}, email: {}", query, entity.getUsername(), entity.getEmail());

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, entity.getUsername());
            preparedStatement.setString(2, entity.getEmail());
            preparedStatement.setString(3, entity.getUserPassword());
            preparedStatement.executeUpdate();
            logger.info("User saved: {}", entity);
        }
    }

    @Override
    public void delete(UserEntity entity) throws SQLException {
        final String query = "DELETE FROM UserEntity WHERE userId = ?";
        logger.info("Executing query: {} with userId: {}", query, entity.getUserId());

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setLong(1, entity.getUserId());
            int rowsAffected = preparedStatement.executeUpdate();
            logger.info("User deleted with userId: {}, rows affected: {}", entity.getUserId(), rowsAffected);
        }
    }

    public Optional<UserEntity> findByUsername(String username) throws SQLException {
        final String query = "SELECT userId, username, email, userPassword FROM UserEntity WHERE username = ?";
        logger.info("Executing query: {} with username: {}", query, username);

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                UserEntity user = new UserEntity(
                        resultSet.getLong("userId"),
                        resultSet.getString("username"),
                        resultSet.getString("email"),
                        resultSet.getString("userPassword")
                );
                logger.info("User found by username: {}", user);
                return Optional.of(user);
            }
            logger.info("No user found with username: {}", username);
            return Optional.empty();
        }
    }
}