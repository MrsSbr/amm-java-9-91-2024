package ru.vsu.amm.java.repository;

import ru.vsu.amm.java.entity.Coffee;
import ru.vsu.amm.java.entity.User;
import ru.vsu.amm.java.exception.SqlException;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LikedCoffeeRepository {
    private final DataSource dataSource;

    public LikedCoffeeRepository(final DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void save(User user, Coffee coffee) {
        String sql = "INSERT INTO user_coffee (id_user, id_coffee) VALUES (?, ?)";

        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, user.getId());
            statement.setLong(2, coffee.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new SqlException(e.getMessage());
        }
    }

    public List<Coffee> findByUserId(long userId) {
        List<Coffee> coffees = new ArrayList<>();
        String sql = """
                        SELECT coffee.id, title, description, user_entity.id, name, login, hash_password
                        FROM coffee
                        JOIN user_coffee ON coffee.id = user_coffee.id_coffee
                        JOIN user_entity ON user_entity.id = user_coffee.id_user
                        WHERE user_coffee.id_user = ?
                        """;
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, userId);

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                User user = new User(resultSet.getLong("user_entity.id"),
                        resultSet.getString("name"),
                        resultSet.getString("login"),
                        resultSet.getString("hash_password"));

                coffees.add(new Coffee(resultSet.getLong("coffee.id"),
                        resultSet.getString("title"),
                        resultSet.getString("description"),
                        user));
            }
        } catch (SQLException e) {
            throw new SqlException(e.getMessage());
        }
        return coffees;
    }

    public void delete(long userId, long coffeeId) {
        String sql = "DELETE FROM user_coffee WHERE id_user = ? AND id_coffee = ?";
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, userId);
            statement.setLong(2, coffeeId);

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new SqlException(e.getMessage());
        }
    }
}
