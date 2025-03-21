package ru.vsu.amm.java.repository;

import ru.vsu.amm.java.configuration.DatabaseConfiguration;
import ru.vsu.amm.java.entity.UserEntity;
import ru.vsu.amm.java.exception.DatabaseException;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserRepository implements CrudRepository<UserEntity> {

    private final DataSource dataSource;

    public UserRepository() {
        dataSource = DatabaseConfiguration.getDataSource();
    }

    @Override
    public Optional<UserEntity> findById(Long id) throws SQLException {
        final String query = "SELECT id, login, password FROM user_entity WHERE id = ?";
        Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setLong(1, id);
        preparedStatement.execute();

        ResultSet resultSet = preparedStatement.getResultSet();

        if (resultSet.next()) {
            return Optional.of(new UserEntity(
                    resultSet.getLong("id"),
                    resultSet.getString("login"),
                    resultSet.getString("password")
            ));
        }
        return Optional.empty();
    }

    @Override
    public List<UserEntity> findAll() throws SQLException {
        final String query = "SELECT id, login, password FROM user_entity";
        Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.execute();

        ResultSet resultSet = preparedStatement.getResultSet();

        List<UserEntity> users = new ArrayList<>();
        while (resultSet.next()) {
            users.add(new UserEntity(
                    resultSet.getLong("id"),
                    resultSet.getString("login"),
                    resultSet.getString("password")
            ));
        }

        return users;
    }

    public Optional<UserEntity> findByLogin(String login) throws SQLException {
        final String query = "SELECT id, login, password FROM user_entity WHERE login = ?";
        Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, login);
        preparedStatement.execute();

        ResultSet resultSet = preparedStatement.getResultSet();

        if (resultSet.next()) {
            return Optional.of(new UserEntity(
                    resultSet.getLong("id"),
                    resultSet.getString("login"),
                    resultSet.getString("password")
                    ));
        }
        return Optional.empty();
    }

    @Override
    public Long save(UserEntity entity) throws SQLException {
        final String query = "INSERT INTO user_entity (login, password) VALUES (?, ?)";
        Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setString(1, entity.getLogin());
        preparedStatement.setString(2, entity.getPassword());
        preparedStatement.execute();

        try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                return generatedKeys.getLong(1);
            } else {
                throw new DatabaseException("Не удалось получить id после вставки.");
            }
        }
    }

    @Override
    public void delete(UserEntity entity) {

    }

    @Override
    public void update(UserEntity entity) {

    }
}
