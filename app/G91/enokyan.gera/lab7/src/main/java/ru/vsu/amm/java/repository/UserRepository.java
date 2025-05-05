package ru.vsu.amm.java.repository;

import ru.vsu.amm.java.configuration.DatabaseConfiguration;
import ru.vsu.amm.java.entity.Role;
import ru.vsu.amm.java.entity.UserEntity;
import ru.vsu.amm.java.util.RolesMapper;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

public class UserRepository implements CrudRepository<UserEntity> {

    private final DataSource dataSource;

    public UserRepository() {
        dataSource = DatabaseConfiguration.getDataSource();
    }

    @Override
    public UserEntity findById(long id) {
        final String query = "SELECT id, nickname, password, rating, roles FROM elo_user WHERE id = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return makeUserFromResultSet(resultSet);
                }
            }
        } catch (SQLException e) {
            // log
        }

        return null;
    }

    public UserEntity findByNickname(String nickname) {
        final String query = "SELECT id, nickname, password, rating, roles FROM elo_user WHERE nickname = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, nickname);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return makeUserFromResultSet(resultSet);
                }
            }
        } catch (SQLException e) {
            // log
        }

        return null;
    }

    @Override
    public List<UserEntity> findAll() {
        final String query = "SELECT id, nickname, password, rating, roles FROM elo_user";

        List<UserEntity> users = new ArrayList<>();

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                users.add(makeUserFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            // log
        }

        return users;
    }

    public List<UserEntity> findTopRated(long count) {
        final String query = "SELECT id, nickname, password, rating, roles FROM elo_user ORDER BY rating DESC, nickname LIMIT ?";

        List<UserEntity> users = new ArrayList<>();

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, count);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                users.add(makeUserFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            // log
        }

        return users;
    }

    @Override
    public void create(UserEntity entity) {
        final String query = "INSERT INTO elo_user (nickname, password, rating, roles) VALUES (?, ?, ?, ?)";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            prepareInsertOrUpdateStatement(connection, statement, entity);
            statement.executeUpdate();
        } catch (SQLException e) {
            //log
        }
    }

    @Override
    public void update(UserEntity entity) {
        final String query = "UPDATE elo_user SET nickname = ?, password = ?, rating = ?, roles = ? WHERE id = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            prepareInsertOrUpdateStatement(connection, statement, entity);
            statement.setLong(5, entity.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            // log
        }
    }

    @Override
    public void delete(UserEntity entity) {
        final String query = "DELETE FROM elo_user WHERE id = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, entity.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            // log
        }
    }

    private UserEntity makeUserFromResultSet(ResultSet resultSet) throws SQLException {
        Array array = resultSet.getArray("roles");
        String[] textArray = (String[]) array.getArray();
        List<Role> roles = RolesMapper.mapArrayOfStringsToRoles(textArray);
        return new UserEntity(
                resultSet.getLong("id"),
                resultSet.getString("nickname"),
                resultSet.getString("password"),
                resultSet.getDouble("rating"), roles
        );
    }

    private void prepareInsertOrUpdateStatement(Connection connection, PreparedStatement statement, UserEntity entity)
            throws SQLException {
        Array roles = connection.createArrayOf("TEXT", RolesMapper.mapRolesToArrayOfStrings(entity.getRoles()));
        statement.setString(1, entity.getNickname());
        statement.setString(2, entity.getPassword());
        statement.setDouble(3, entity.getRating());
        statement.setArray(4, roles);
    }
}