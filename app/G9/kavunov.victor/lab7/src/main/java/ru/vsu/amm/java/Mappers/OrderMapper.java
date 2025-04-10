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

        order.setOrderNum(rs.getLong("OrderNum"));
        order.setUserId(rs.getLong("UserId"));
        order.setTotalCost(rs.getFloat("TotalCost"));
        order.setRegistrationDate(rs.getTimestamp("RegistrationDate").toLocalDateTime());

        return order;
    }

    @Override
    public PreparedStatement mapObjectToRow(Order order,
                                            Connection connection,
                                            String query) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setLong(1, order.getOrderNum());
        preparedStatement.setLong(2, order.getUserId());
        preparedStatement.setFloat(3, order.getTotalCost());
        preparedStatement.setTimestamp(4, Timestamp.valueOf(order.getRegistrationDate()));

        return preparedStatement;
    }
}
