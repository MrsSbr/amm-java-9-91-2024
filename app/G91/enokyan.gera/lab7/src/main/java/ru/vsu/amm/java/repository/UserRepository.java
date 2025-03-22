package ru.vsu.amm.java.repository;

import ru.vsu.amm.java.configuration.DatabaseConfiguration;
import ru.vsu.amm.java.entity.UserEntity;
import ru.vsu.amm.java.util.RolesMapper;

import javax.sql.DataSource;
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
        final String query = "SELECT id, nickname, password, rating, roles FROM user WHERE id = ?";

        try (Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new UserEntity(
                        resultSet.getLong("id"),
                        resultSet.getString("nickname"),
                        resultSet.getString("password"),
                        resultSet.getDouble("rating"),
                        RolesMapper.mapStringToRoles(resultSet.getString("roles"))
                );
            }
        } catch (SQLException e) {
            // log
        }

        return null;
    }

    @Override
    public List<UserEntity> findAll() {
        List<UserEntity> users = new ArrayList<>();

        final String query = "SELECT id, nickname, password, rating, roles FROM user";

        try (Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                users.add(
                        new UserEntity(
                                resultSet.getLong("id"),
                                resultSet.getString("nickname"),
                                resultSet.getString("password"),
                                resultSet.getDouble("rating"),
                                RolesMapper.mapStringToRoles(resultSet.getString("roles"))
                        )
                );
            }
        } catch (SQLException e) {
            // log
        }

        return users;
    }

    @Override
    public void create(UserEntity entity) {
        final String query = "INSERT INTO user (nickname, password, rating, roles) VALUES (?, ?, ?, ?)";

        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, entity.getNickname());
            statement.setString(2, entity.getPassword());
            statement.setDouble(3, entity.getRating());
            statement.setString(4, RolesMapper.mapRolesToString(entity.getRoles()));
            statement.executeUpdate();
        } catch (SQLException e) {
            // log
        }
    }

    @Override
    public void update(UserEntity entity) {
        final String query = "UPDATE user SET nickname = ?, password = ?, rating = ?, roles = ? WHERE id = ?";

        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, entity.getNickname());
            statement.setString(2, entity.getPassword());
            statement.setDouble(3, entity.getRating());
            statement.setString(4, RolesMapper.mapRolesToString(entity.getRoles()));
            statement.setLong(5, entity.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            // log
        }
    }

    @Override
    public void delete(UserEntity entity) {
        final String query = "DELETE FROM user WHERE id = ?";

        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, entity.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            // log
        }
    }
}