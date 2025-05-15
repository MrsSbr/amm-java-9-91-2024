package ru.vsu.amm.java.repository;
import ru.vsu.amm.java.config.DbConfig;
import ru.vsu.amm.java.entities.Order;
import ru.vsu.amm.java.entities.Toy;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OrderRepository {
    private final DataSource dataSource;

    public OrderRepository() {
        dataSource = DbConfig.getDataSource();
    }

    public List<Order> findByCustomerId(Long customerId) throws SQLException {
        final String query = "SELECT id, customer_id, toy_id, quantity, total_price FROM orders WHERE customer_id = ?";
        List<Order> orders = new ArrayList<>();

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setLong(1, customerId);
            ResultSet resultSet = preparedStatement.executeQuery();

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
        return orders;

    }

    public void save (Order order) throws SQLException {
        final String query = "INSERT INTO orders (customer_id, toy_id, quantity, total_price) VALUES(?,?,?,?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setLong(1, order.getCustomerId());
            preparedStatement.setLong(2, order.getToyId());
            preparedStatement.setInt(3, order.getQuantity());
            preparedStatement.setBigDecimal(4, order.getTotalPrice());
            preparedStatement.executeUpdate();
        }
    }
}
