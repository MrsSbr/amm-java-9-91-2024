package ru.vsu.amm.java.Mappers;

import ru.vsu.amm.java.Entities.Smartphone;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SmartphoneMapper implements Mapper<Smartphone> {
    public SmartphoneMapper() {
    }

    @Override
    public Smartphone mapRowToObject(ResultSet rs) throws SQLException {
        Smartphone smartphone = new Smartphone();

        smartphone.setSmartphoneId(rs.getLong("SmartphoneID"));
        smartphone.setBrand(rs.getString("Brand"));
        smartphone.setModel(rs.getString("Model"));
        smartphone.setRam(rs.getInt("RAM"));
        smartphone.setStorageMemory(rs.getInt("StorageMemory"));
        smartphone.setMainCameraResolution(rs.getFloat("MainCameraResolution"));
        smartphone.setScreenSize(rs.getFloat("ScreenSize"));
        smartphone.setColor(rs.getString("Color"));
        smartphone.setPrice(rs.getFloat("Price"));
        smartphone.setAmount(rs.getInt("Amount"));

        return smartphone;
    }

    @Override
    public PreparedStatement mapObjectToRow(Smartphone smartphone,
                                            Connection connection,
                                            String query) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, smartphone.getBrand());
        preparedStatement.setString(2, smartphone.getModel());
        preparedStatement.setInt(3, smartphone.getRam());
        preparedStatement.setInt(4, smartphone.getStorageMemory());
        preparedStatement.setFloat(5, smartphone.getMainCameraResolution());
        preparedStatement.setFloat(6, smartphone.getScreenSize());
        preparedStatement.setString(7, smartphone.getColor());
        preparedStatement.setFloat(8, smartphone.getPrice());
        preparedStatement.setInt(9, smartphone.getAmount());

        return preparedStatement;
    }
}
