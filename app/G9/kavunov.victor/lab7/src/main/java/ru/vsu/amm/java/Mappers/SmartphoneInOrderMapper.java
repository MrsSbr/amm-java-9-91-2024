package ru.vsu.amm.java.Mappers;

import ru.vsu.amm.java.Entities.SmartphoneInOrder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SmartphoneInOrderMapper implements Mapper<SmartphoneInOrder> {
    public SmartphoneInOrderMapper() {
    }

    @Override
    public SmartphoneInOrder mapRowToObject(ResultSet rs) throws SQLException {
        SmartphoneInOrder smartphoneInOrder = new SmartphoneInOrder();

        smartphoneInOrder.setSmartphoneInOrderId(rs.getLong("SmartphoneInOrderID"));
        smartphoneInOrder.setOrderNum(rs.getLong("OrderNum"));
        smartphoneInOrder.setSmartphoneId(rs.getLong("SmartphoneID"));
        smartphoneInOrder.setAmount(rs.getInt("Amount"));

        return smartphoneInOrder;
    }

    @Override
    public PreparedStatement mapObjectToRow(SmartphoneInOrder smartphoneInOrder,
                                            Connection connection,
                                            String query) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setLong(1, smartphoneInOrder.getOrderNum());
        preparedStatement.setLong(2, smartphoneInOrder.getSmartphoneId());
        preparedStatement.setInt(3, smartphoneInOrder.getAmount());

        return preparedStatement;
    }
}
