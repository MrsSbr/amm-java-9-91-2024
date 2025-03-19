package ru.vsu.amm.java.mappers;

import ru.vsu.amm.java.entities.Vehicle;

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
}
