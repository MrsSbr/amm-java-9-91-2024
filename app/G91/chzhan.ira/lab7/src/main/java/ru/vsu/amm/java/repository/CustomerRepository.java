package ru.vsu.amm.java.repository;

import ru.vsu.amm.java.config.DbConfig;
import ru.vsu.amm.java.entities.Customer;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class CustomerRepository {

    private final DataSource dataSource;

    public CustomerRepository() {
        dataSource = DbConfig.getDataSource();
    }

    public Optional<Customer> findByName(String login) throws SQLException {
        final String query = "SELECT id, name, password FROM customer WHERE name = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, login);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(new Customer(
                            resultSet.getLong("id"),
                            resultSet.getString("name"),
                            resultSet.getString("password")
                    ));
                }
            }
        }
        return Optional.empty();
    }


    public void save(Customer entity) throws SQLException {
        final String query = "INSERT INTO customer (name, password) VALUES (?, ?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, entity.getName());
            preparedStatement.setString(2, entity.getPassword());
            preparedStatement.executeUpdate();
        }
    }
}
