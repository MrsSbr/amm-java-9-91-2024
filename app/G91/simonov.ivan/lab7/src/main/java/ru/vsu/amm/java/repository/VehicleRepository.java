package ru.vsu.amm.java.repository;

import ru.vsu.amm.java.configuration.DatabaseConfiguration;
import ru.vsu.amm.java.entities.Vehicle;
import ru.vsu.amm.java.mappers.VehicleMapper;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class VehicleRepository implements ParkingRepository<Vehicle> {

    private final DataSource dataSource;

    public VehicleRepository() {
        dataSource = DatabaseConfiguration.getDataSource();
    }

    @Override
    public Optional<Vehicle> getById(int id) throws SQLException {

        String sql = "SELECT * FROM Vehicle WHERE Id_vehicle = ?";

        Connection connection = dataSource.getConnection();

        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setInt(1, id);

        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            return Optional.of(VehicleMapper.mapRowToObject(rs));
        }

        return Optional.empty();
    }

    @Override
    public List<Vehicle> getAll() throws SQLException {

        String sql = "SELECT * FROM Vehicle";

        Connection connection = dataSource.getConnection();
        PreparedStatement stmt = connection.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();

        List<Vehicle> vehicles = new ArrayList<>();

        while (rs.next()) {
            vehicles.add(VehicleMapper.mapRowToObject(rs));
        }

        return vehicles;
    }
}
