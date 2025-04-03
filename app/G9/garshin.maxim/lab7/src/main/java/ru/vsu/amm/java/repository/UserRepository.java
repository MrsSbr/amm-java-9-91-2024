package ru.vsu.amm.java.repository;

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
    private final DataSource dataSource;

    public UserRepository() {
        this.dataSource = DatabaseConfiguration.getDataSource();
    }

    @Override
    public Optional<UserEntity> findById(Long id) throws SQLException {
        final String query = "SELECT userId, userNickname, email, userPassword FROM UserEntity WHERE userId = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return Optional.of(new UserEntity(
                        resultSet.getLong("userId"),
                        resultSet.getString("userNickname"),
                        resultSet.getString("email"),
                        resultSet.getString("userPassword")
                ));
            }
            return Optional.empty();
        }
    }

    @Override
    public List<UserEntity> findAll() throws SQLException {
        final String query = "SELECT userId, userNickname, email, userPassword FROM UserEntity";
        List<UserEntity> users = new ArrayList<>();

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {


            while (resultSet.next()) {
                users.add(new UserEntity(
                        resultSet.getLong("userId"),
                        resultSet.getString("userNickname"),
                        resultSet.getString("email"),
                        resultSet.getString("userPassword")
                ));
            }
        }
        return users;
    }

    @Override
    public void save(UserEntity entity) throws SQLException {
        final String query = "INSERT INTO UserEntity (userNickname, email, userPassword) VALUES (?, ?, ?)";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, entity.getUserNickname());
            preparedStatement.setString(2, entity.getEmail());
            preparedStatement.setString(3, entity.getUserPassword());
            preparedStatement.executeUpdate();
        }
    }

    @Override
    public void delete(UserEntity entity) throws SQLException {
        final String query = "DELETE FROM UserEntity WHERE userId = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setLong(1, entity.getUserId());
            preparedStatement.executeUpdate();
        }
    }

    public Optional<UserEntity> findByNickname(String nickname) throws SQLException {
        final String query = "SELECT userId, userNickname, email, userPassword FROM UserEntity WHERE userNickname = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, nickname);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return Optional.of(new UserEntity(
                        resultSet.getLong("userId"),
                        resultSet.getString("userNickname"),
                        resultSet.getString("email"),
                        resultSet.getString("userPassword")
                ));
            }
            return Optional.empty();
        }
    }
}