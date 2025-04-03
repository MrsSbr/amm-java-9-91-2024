package ru.vsu.amm.java.repository.implementations;

import lombok.extern.slf4j.Slf4j;
import ru.vsu.amm.java.configuration.DatabaseConfiguration;
import ru.vsu.amm.java.entities.UserEntity;
import ru.vsu.amm.java.exceptions.DataAccessException;
import ru.vsu.amm.java.mappers.UserMapper;
import ru.vsu.amm.java.repository.interfaces.CrudRepository;
import ru.vsu.amm.java.utils.ErrorMessages;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
public class UserRepository implements CrudRepository<UserEntity> {
    private final DataSource dataSource;

    public UserRepository() {
        this.dataSource = DatabaseConfiguration.getDataSource();
    }

    @Override
    public Optional<UserEntity> findById(Long id) {
        final String query = "SELECT id, username, password, email FROM user WHERE id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setLong(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(UserMapper.ResultSetToUserEntity(resultSet));
                }
            }
        } catch (SQLException e) {
            log.error(ErrorMessages.FIND_USER_BY_ID + id, e);
            throw new DataAccessException(ErrorMessages.FIND_USER_BY_ID + id, e);
        }
        return Optional.empty();
    }

    @Override
    public List<UserEntity> findAll() {
        final String query = "SELECT id, username, password, email FROM user";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                List<UserEntity> users = new ArrayList<>();
                while (resultSet.next()) {
                    users.add(UserMapper.ResultSetToUserEntity(resultSet));
                }
                return users;
            }
        } catch (SQLException e) {
            log.error(ErrorMessages.FIND_ALL_USERS, e);
            throw new DataAccessException(ErrorMessages.FIND_ALL_USERS, e);
        }
    }

    @Override
    public void save(UserEntity entity) {
        final String query = "INSERT INTO user(username, password, email) VALUES(?,?,?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, entity.getUsername());
            preparedStatement.setString(2, entity.getPassword());
            preparedStatement.setString(3, entity.getEmail());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            log.error(ErrorMessages.SAVE_USER, e);
            throw new DataAccessException(ErrorMessages.SAVE_USER, e);
        }
    }

    @Override
    public void update(UserEntity entity) {
        final String query = "UPDATE user SET username = ?, password = ?, email = ? WHERE id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, entity.getUsername());
            preparedStatement.setString(2, entity.getPassword());
            preparedStatement.setString(3, entity.getEmail());
            preparedStatement.setLong(4, entity.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            log.error(ErrorMessages.UPDATE_USER + entity, e);
            throw new DataAccessException(ErrorMessages.UPDATE_USER + entity, e);
        }
    }

    @Override
    public void delete(Long id) {
        final String query = "DELETE FROM user WHERE id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            log.error(ErrorMessages.DELETE_USER_BY_ID + id, e);
            throw new DataAccessException(ErrorMessages.DELETE_USER_BY_ID + id, e);
        }
    }

    public Optional<UserEntity> findByEmail(String email) {
        final String query = "SELECT id, username, password, email FROM user WHERE email = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, email);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(UserMapper.ResultSetToUserEntity(resultSet));
                }
            }
        } catch (SQLException e) {
            log.error(ErrorMessages.FIND_USER_BY_EMAIL + email, e);
            throw new DataAccessException(ErrorMessages.FIND_USER_BY_EMAIL + email, e);
        }
        return Optional.empty();
    }
}
