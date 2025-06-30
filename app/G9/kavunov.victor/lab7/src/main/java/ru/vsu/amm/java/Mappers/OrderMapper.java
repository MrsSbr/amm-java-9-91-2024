package ru.vsu.amm.java.Mappers;

import ru.vsu.amm.java.Entities.Order;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class OrderMapper implements Mapper<Order> {
    public OrderMapper() {
    }

    @Override
    public Order mapRowToObject(ResultSet rs) throws SQLException {
        Order order = new Order();

        order.setId(rs.getLong("Order_id"));
        order.setUserId(rs.getLong("User_id"));
        order.setSmartphoneId(rs.getLong("Smartphone_id"));
        order.setIsPaid(rs.getBoolean("Is_paid"));
        order.setRegistrationDate(rs.getTimestamp("Registration_date").toLocalDateTime());

        return order;
    }

    @Override
    public PreparedStatement mapObjectToRow(Order order,
                                            Connection connection,
                                            String query) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setLong(1, order.getUserId());
        preparedStatement.setLong(2, order.getSmartphoneId());
        preparedStatement.setBoolean(3, order.getIsPaid());
        preparedStatement.setTimestamp(4, Timestamp.valueOf(order.getRegistrationDate()));

        return preparedStatement;
    }
}
