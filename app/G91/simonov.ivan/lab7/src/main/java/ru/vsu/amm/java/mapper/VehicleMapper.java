package ru.vsu.amm.java.mapper;

import ru.vsu.amm.java.entities.Vehicle;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class VehicleMapper implements EntityMapper<Vehicle> {

    public VehicleMapper() {}

    @Override
    public Vehicle mapRowToObject(ResultSet rs) throws SQLException {

        Vehicle vehicle = new Vehicle();

        vehicle.setVehicleId(rs.getInt("Id_vehicle"));
        vehicle.setRegistrationNumber(rs.getString("RegistrationNumber"));
        vehicle.setModel(rs.getString("Model"));
        vehicle.setBrand(rs.getString("Brand"));
        vehicle.setColour(rs.getString("Colour"));

        return vehicle;
    }

    @Override
    public PreparedStatement mapObjectToRow(Vehicle entity,
                                            Connection connection,
                                            String sql) throws SQLException {

        PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

        stmt.setString(1, entity.getRegistrationNumber());
        stmt.setString(2, entity.getModel());
        stmt.setString(3, entity.getBrand());
        stmt.setString(4, entity.getColour());

        return stmt;
    }
}
