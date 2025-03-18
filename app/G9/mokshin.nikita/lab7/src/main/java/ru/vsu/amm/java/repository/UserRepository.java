package ru.vsu.amm.java.repository;

import ru.vsu.amm.java.exception.SqlException;
import ru.vsu.amm.java.model.User;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserRepository implements CrudRepository<User> {
    private final DataSource dataSource;

    public UserRepository(final DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Optional<User> findByLogin(String login) {
        String sql = "SELECT id, name, login, hash_password FROM user_entity WHERE login = ?";
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, login);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    User user = new User(
                            resultSet.getLong("id"),
                            resultSet.getString("name"),
                            resultSet.getString("login"),
                            resultSet.getString("hash_password"));
                    return Optional.of(user);
                }
            }

        } catch (SQLException e) {
            throw new SqlException(e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public Optional<User> findById(long id) {
        String sql = "SELECT id, name, login, hash_password FROM user_entity WHERE id = ?";
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Long userId = resultSet.getLong("id");
                    String name = resultSet.getString("name");
                    String login = resultSet.getString("login");
                    String hashPassword = resultSet.getString("hash_password");
                    User user = new User(userId, name, login, hashPassword);
                    return Optional.of(user);
                }
            }

        } catch (SQLException e) {
            throw new SqlException(e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT id, name, login, hash_password FROM user_entity";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String name = resultSet.getString("name");
                String login = resultSet.getString("login");
                String hashPassword = resultSet.getString("hash_password");
                users.add(new User(id, name, login, hashPassword));
            }

        } catch (SQLException e) {
            throw new SqlException(e.getMessage());
        }
        return users;
    }

    @Override
    public void save(User user) {
        String sql = """
                        INSERT INTO user_entity (id, name, login, hash_password)
                        VALUES (?, ?, ?, ?)
                        ON CONFLICT (id) DO UPDATE
                        SET id = EXCLUDED.id, name = EXCLUDED.name, login = EXCLUDED.login, hash_password = EXCLUDED.hash_password
                    """;
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sql);
            if (user.getId() == null) {
                statement.setLong(1, Types.BIGINT);
            } else {
                statement.setLong(1, user.getId());
            }
            statement.setString(2, user.getName());
            statement.setString(3, user.getLogin());
            statement.setString(4, user.getHashPassword());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new SqlException(e.getMessage());
        }
    }

    @Override
    public void delete(User user) {
        String sql = "DELETE FROM user_entity WHERE id = ?";
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, user.getId());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new SqlException(e.getMessage());
        }
    }
}
