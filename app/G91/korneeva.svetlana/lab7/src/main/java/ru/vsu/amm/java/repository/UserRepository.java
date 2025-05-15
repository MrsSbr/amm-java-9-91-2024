package ru.vsu.amm.java.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.vsu.amm.java.config.DbConfig;
import ru.vsu.amm.java.entity.UserEntity;

import javax.sql.DataSource;
import javax.xml.crypto.Data;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class UserRepository {
    private static final Logger logger = LoggerFactory.getLogger(UserRepository.class);

    private final DataSource dataSource;

    public UserRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Optional<UserEntity> findByEmail(String email) throws SQLException {
        final String query = "SELECT id, email, hash_password FROM users WHERE email = ?";
        Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, email);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            Optional<UserEntity> userEntityOptional = Optional.of(new UserEntity(
                    resultSet.getLong("id"),
                    resultSet.getString("email"),
                    resultSet.getString("hash_password")
            ));
            logger.info("User found: {}", userEntityOptional.get().getEmail());
            return userEntityOptional;
        }

        logger.info("No user found with email: {}", email);
        return Optional.empty();
    }

    public void save(UserEntity user) throws SQLException {
        final String query = "INSERT INTO users (email, hash_password) VALUES (?, ?)";
        Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, user.getEmail());
        preparedStatement.setString(2, user.getHashPassword());
        preparedStatement.executeUpdate();
        logger.info("Saved user: {}", user.getEmail());
    }
}
