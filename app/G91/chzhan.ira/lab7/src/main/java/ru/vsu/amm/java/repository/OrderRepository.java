package ru.vsu.amm.java.repository;

import ru.vsu.amm.java.config.DbConfig;
import ru.vsu.amm.java.entities.Order;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OrderRepository {
    private DataSource dataSource;

    public OrderRepository() {
        dataSource = DbConfig.getDataSourceForService();
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public List<Order> findByCustomerId(Long customerId) throws SQLException {
        final String query = "SELECT id, customer_id, toy_id, quantity, total_price FROM orders WHERE customer_id = ?";
        List<Order> orders = new ArrayList<>();

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setLong(1, customerId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    orders.add(new Order(
                            resultSet.getLong("id"),
                            resultSet.getLong("customer_id"),
                            resultSet.getLong("toy_id"),
                            resultSet.getInt("quantity"),
                            resultSet.getBigDecimal("total_price")
                    ));
                }
            }
        }
        return orders;

    }

    public Order save(Order order) throws SQLException {
        final String query = "INSERT INTO orders (customer_id, toy_id, quantity, total_price) VALUES(?,?,?,?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setLong(1, order.getCustomerId());
            preparedStatement.setLong(2, order.getToyId());
            preparedStatement.setInt(3, order.getQuantity());
            preparedStatement.setBigDecimal(4, order.getTotalPrice());
            preparedStatement.executeUpdate();

            // Получаем сгенерированный ID
            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    order.setId(generatedKeys.getLong(1));
                }
            }
        }
        return order;
    }

    public Optional<Order> findById(Long id) throws SQLException {
        final String query = "SELECT id, customer_id, toy_id, quantity, total_price FROM orders WHERE id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setLong(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(new Order(
                            resultSet.getLong("id"),
                            resultSet.getLong("customer_id"),
                            resultSet.getLong("toy_id"),
                            resultSet.getInt("quantity"),
                            resultSet.getBigDecimal("total_price")
                    ));
                }
            }
        }
        return Optional.empty();
    }

}
