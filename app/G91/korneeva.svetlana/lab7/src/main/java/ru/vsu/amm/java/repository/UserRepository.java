package ru.vsu.amm.java.repository;

import ru.vsu.amm.java.config.DbConfig;
import ru.vsu.amm.java.entity.UserEntity;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class UserRepository {

    private final DataSource dataSource;

    public UserRepository() {
        dataSource = DbConfig.getDataSource();
    }

    public Optional<UserEntity> findByEmail(String email) throws SQLException {
        final String query = "SELECT id, email, hash_password FROM users WHERE email = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return Optional.of(new UserEntity(
                        resultSet.getLong("id"),
                        resultSet.getString("email"),
                        resultSet.getString("hash_password")
                ));
            }
        }
        return Optional.empty();
    }

    public void save(UserEntity user) throws SQLException {
        final String query = "INSERT INTO users (email, hash_password) VALUES (?, ?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, user.getEmail());
            preparedStatement.setString(2, user.getHashPassword());
            preparedStatement.executeUpdate();
        }
    }
}
