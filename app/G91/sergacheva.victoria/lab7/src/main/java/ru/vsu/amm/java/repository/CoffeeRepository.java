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
import java.util.Optional;

public class CoffeeRepository implements CrudRepository<Coffee> {
    private final DataSource dataSource;

    public CoffeeRepository(final DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Optional<Coffee> findById(long id) {
        String sql = """
                        SELECT coffee.id, title, description, user_entity.id, name, login, hash_password
                        FROM coffee
                            JOIN user_entity ON coffee.id_author = user_entity.id
                        WHERE coffee.id = ?
                        """;
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, id);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                User user = new User(resultSet.getLong("user_entity.id"),
                        resultSet.getString("name"),
                        resultSet.getString("login"),
                        resultSet.getString("hash_password"));

                return Optional.of(new Coffee(resultSet.getLong("coffee.id"),
                        resultSet.getString("title"),
                        resultSet.getString("description"),
                        user));
            }
        } catch (SQLException e) {
            throw new SqlException(e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public List<Coffee> findAll() {
        List<Coffee> coffees = new ArrayList<>();
        String sql = """
                        SELECT coffee.id, title, description, user_entity.id, name, login, hash_password
                        FROM coffee
                            JOIN user_entity ON coffee.id_author = user_entity.id
                        """;
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sql);

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

    @Override
    public void save(Coffee coffee) {
        String sql = coffee.getId() == null ? "INSERT INTO coffee (title, description, id_author) VALUES (?, ?, ?)" : "UPDATE coffee SET title = ?, description = ?, id_author = ? WHERE id = ?";

        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, coffee.getTitle());
            statement.setString(2, coffee.getDescription());
            statement.setLong(3, coffee.getAuthor().getId());
            if (coffee.getId() != null) {
                statement.setLong(4, coffee.getId());
            }
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new SqlException(e.getMessage());
        }
    }

    @Override
    public void delete(Coffee coffee) {
        String sql = "DELETE FROM coffee WHERE id = ?";
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, coffee.getId());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new SqlException(e.getMessage());
        }
    }
}
