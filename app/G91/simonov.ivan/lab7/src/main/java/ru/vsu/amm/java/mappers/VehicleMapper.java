package ru.vsu.amm.java.mappers;

import ru.vsu.amm.java.entities.Vehicle;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class VehicleMapper {

    public static Vehicle mapRowToObject(ResultSet rs) throws SQLException {

        Vehicle vehicle = new Vehicle();

        vehicle.setVehicleId(rs.getInt("Id_vehicle"));
        vehicle.setRegistrationNumber(rs.getString("RegistrationNumber"));
        vehicle.setModel(rs.getString("Model"));
        vehicle.setBrand(rs.getString("Brand"));
        vehicle.setColour(rs.getString("Colour"));

        return vehicle;
    }

    public static void mapObjectToRow(Vehicle entity, PreparedStatement stmt) throws SQLException {

        stmt.setString(1, entity.getRegistrationNumber());
        stmt.setString(2, entity.getModel());
        stmt.setString(3, entity.getBrand());
        stmt.setString(4, entity.getColour());
    }
}
