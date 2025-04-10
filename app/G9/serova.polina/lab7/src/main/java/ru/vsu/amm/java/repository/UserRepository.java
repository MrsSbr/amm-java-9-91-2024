package ru.vsu.amm.java.repository;

import ru.vsu.amm.java.configuration.DatabaseConfiguration;
import ru.vsu.amm.java.entity.UserEntity;
import ru.vsu.amm.java.util.RoleMapper;

import javax.sql.DataSource;
import java.sql.Date;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserRepository implements CrudRepository<UserEntity> {

    private final DataSource dataSource;

    public UserRepository() {
        dataSource = DatabaseConfiguration.getDataSource();
    }

    @Override
    public UserEntity findById(long id) {
        final String query = "SELECT id, name, surname, patronymic, birthday, email, password_hash, role FROM user_entity WHERE id = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return makeUserFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            // TODO: add logging
            return null;
        }
        return null;
    }

    public UserEntity findByEmail(String email) {
        final String query = "SELECT id, name, surname, patronymic, birthday, email, password_hash, role FROM user_entity WHERE email = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return makeUserFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            // TODO: add logging
            return null;
        }
        return null;
    }

    @Override
    public List<UserEntity> findAll() {
        List<UserEntity> users = new ArrayList<>();
        final String query = "SELECT id, name, surname, patronymic, birthday, email, password_hash, role FROM user_entity";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                users.add(
                        makeUserFromResultSet(resultSet)
                );
            }
        } catch (SQLException e) {
            // TODO add logging
            return null;
        }
        return users;
    }

    @Override
    public void upsert(UserEntity entity) {
        final String query = "INSERT INTO user_entity (name, surname, patronymic, birthday, email, password_hash, role) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, entity.getName());
            statement.setString(2, entity.getSurname());
            statement.setString(3, entity.getPatronymic());
            statement.setDate(4, Date.valueOf(entity.getBirthday()));
            statement.setString(5, entity.getEmail());
            statement.setString(6, entity.getPasswordHash());
            statement.setString(7, RoleMapper.mapRoleToString(entity.getRole()));
            statement.executeUpdate();
        } catch (SQLException e) {
            // TODO: add logging
        }
    }

    @Override
    public void update(UserEntity entity) {
        final String query = "UPDATE user_entity SET name = ?, surname = ?, patronymic = ?, birthday = ?, email = ?, password_hash = ?, role = ? WHERE id = ?";

        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, entity.getName());
            statement.setString(2, entity.getSurname());
            statement.setString(3, entity.getPatronymic());
            statement.setDate(4, Date.valueOf(entity.getBirthday()));
            statement.setString(5, entity.getEmail());
            statement.setString(6, entity.getPasswordHash());
            statement.setString(7, RoleMapper.mapRoleToString(entity.getRole()));
            statement.setLong(8, entity.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            // TODO: add logging
        }
    }

    @Override
    public void delete(UserEntity entity) {
        final String query = "DELETE FROM user_entity WHERE id = ?";

        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, entity.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            // TODO: add logging
        }
    }

    private UserEntity makeUserFromResultSet(ResultSet resultSet) throws SQLException {
        return new UserEntity(
                resultSet.getLong("id"),
                resultSet.getString("name"),
                resultSet.getString("surname"),
                resultSet.getString("patronymic"),
                resultSet.getDate("birthday").toLocalDate(),
                resultSet.getString("email"),
                resultSet.getString("password_hash"),
                RoleMapper.mapStringToRole(resultSet.getString("role"))
        );
    }
}
