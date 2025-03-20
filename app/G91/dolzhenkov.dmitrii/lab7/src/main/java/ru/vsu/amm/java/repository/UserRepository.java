package ru.vsu.amm.java.repository;

import lombok.RequiredArgsConstructor;
import ru.vsu.amm.java.entities.UserEntity;
import ru.vsu.amm.java.mappers.UserMapper;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class UserRepository implements CrudRepository<UserEntity>{
    private DataSource dataSource;

    @Override
    public Optional<UserEntity> findById(Long id) throws SQLException {
        final String query = "SELECT * FROM User WHERE id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(UserMapper.ResultSetToUserEntity(resultSet));
                }
            }

        }
        return Optional.empty();
    }

    @Override
    public List<UserEntity> findAll() throws SQLException {
        final String query = "SELECT * FROM User";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                List<UserEntity> users = new ArrayList<>();
                while (resultSet.next()) {
                    users.add(UserMapper.ResultSetToUserEntity(resultSet));
                }
                return users;
            }
        }
    }

    @Override
    public void save(UserEntity entity) throws SQLException {
        final String query = "INSERT INTO User(name, password) VALUES(?,?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, entity.getUserName());
            preparedStatement.setString(2, entity.getPassword());
            preparedStatement.executeUpdate();
        }
    }

    @Override
    public void update(UserEntity entity) throws SQLException {
        final String query = "UPDATE User SET name = ?, password = ? WHERE id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, entity.getUserName());
            preparedStatement.setString(2, entity.getPassword());
            preparedStatement.setLong(3, entity.getId());
            preparedStatement.executeUpdate();
        }
    }

    @Override
    public void delete(Long id) throws SQLException {
        final String query = "DELETE FROM User WHERE id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        }
    }
}
