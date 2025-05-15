package ru.vsu.amm.java.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger logger = LoggerFactory.getLogger(CoffeeRepository.class);
    private final DataSource dataSource;

    public CoffeeRepository(final DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Optional<Coffee> findById(long id) {
        logger.info("Find with id = {}", id);
        String sql = """
                        SELECT coffee.id AS coffee_id, title, description, user_entity.id AS user_entity_id, name, login, hash_password
                        FROM coffee
                            JOIN user_entity ON coffee.id_author = user_entity.id
                        WHERE coffee.id = ?
                        """;
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, id);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                User user = new User(resultSet.getLong("user_entity_id"),
                        resultSet.getString("name"),
                        resultSet.getString("login"),
                        resultSet.getString("hash_password"));

                Coffee coffee = new Coffee(resultSet.getLong("coffee_id"),
                        resultSet.getString("title"),
                        resultSet.getString("description"),
                        user);

                logger.info("Found coffee: {}", coffee);
                return Optional.of(coffee);
            }
        } catch (SQLException e) {
            logger.error("Coffee with id = {} not found {}", id ,e.getMessage());
            throw new SqlException(e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public List<Coffee> findAll() {
        logger.info("Find all");
        List<Coffee> coffees = new ArrayList<>();
        String sql = """
                        SELECT coffee.id AS coffee_id, title, description, user_entity.id AS user_entity_id, name, login, hash_password
                        FROM coffee
                            JOIN user_entity ON coffee.id_author = user_entity.id
                        """;
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sql);

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                User user = new User(resultSet.getLong("user_entity_id"),
                        resultSet.getString("name"),
                        resultSet.getString("login"),
                        resultSet.getString("hash_password"));

                coffees.add(new Coffee(resultSet.getLong("coffee_id"),
                        resultSet.getString("title"),
                        resultSet.getString("description"),
                        user));
            }
            logger.info("Found coffees size = {}", coffees.size());
        } catch (SQLException e) {
            logger.error("Coffees not found {}", e.getMessage());
            throw new SqlException(e.getMessage());
        }
        return coffees;
    }

    @Override
    public long save(Coffee coffee) {
        logger.info("Saving coffee: {}", coffee);
        String sql = coffee.getId() == null ? "INSERT INTO coffee (title, description, id_author) VALUES (?, ?, ?)" : "UPDATE coffee SET title = ?, description = ?, id_author = ? WHERE id = ?";
        long coffeeId = 0;

        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, coffee.getTitle());
            statement.setString(2, coffee.getDescription());
            statement.setLong(3, coffee.getAuthor().getId());
            if (coffee.getId() != null) {
                coffeeId = coffee.getId();
                statement.setLong(4, coffee.getId());
                statement.executeUpdate();
            } else {
                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    coffeeId = resultSet.getLong("id");
                }
            }

        } catch (SQLException e) {
            logger.error("Coffees not saved {}", e.getMessage());
            throw new SqlException(e.getMessage());
        }
        return coffeeId;
    }

    @Override
    public void delete(Coffee coffee) {
        logger.info("Deleting coffee with id = {}", coffee.getId());
        String sql = "DELETE FROM coffee WHERE id = ?";
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, coffee.getId());

            statement.executeUpdate();
        } catch (SQLException e) {
            logger.error("Coffees not deleted {}", e.getMessage());
            throw new SqlException(e.getMessage());
        }
        logger.info("Deleted coffee with id = {}", coffee.getId());
    }
}
