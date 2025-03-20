package ru.vsu.amm.java.repository;

import ru.vsu.amm.java.configuration.DatabaseConfiguration;
import ru.vsu.amm.java.entities.Vehicle;
import ru.vsu.amm.java.mapper.VehicleMapper;

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

        String sql = "SELECT Id_vehicle, RegistrationNumber, Model, Brand, Colour FROM Vehicle WHERE Id_vehicle = ?";

        Connection connection = dataSource.getConnection();

        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setInt(1, id);

        ResultSet rs = stmt.executeQuery();

        VehicleMapper vehicleMapper = new VehicleMapper();

        if (rs.next()) {
            return Optional.of(vehicleMapper.mapRowToObject(rs));
        }

        return Optional.empty();
    }

    @Override
    public List<Vehicle> getAll() throws SQLException {

        String sql = "SELECT Id_vehicle, RegistrationNumber, Model, Brand, Colour FROM Vehicle";

        Connection connection = dataSource.getConnection();
        PreparedStatement stmt = connection.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();

        List<Vehicle> vehicles = new ArrayList<>();

        VehicleMapper vehicleMapper = new VehicleMapper();

        while (rs.next()) {
            vehicles.add(vehicleMapper.mapRowToObject(rs));
        }

        return vehicles;
    }

    @Override
    public void save(Vehicle entity) throws SQLException {

        String sql = "INSERT INTO Vehicle (RegistrationNumber, Model, Brand, Colour) VALUES (?, ?, ?, ?)";

        Connection connection = dataSource.getConnection();
        PreparedStatement stmt = connection.prepareStatement(sql);

        VehicleMapper vehicleMapper = new VehicleMapper(entity);
        vehicleMapper.mapObjectToRow(stmt);
        stmt.execute();
    }

    @Override
    public void update(Vehicle entity) throws SQLException {

        String sql = "UPDATE Vehicle SET RegistrationNumber = ?, Model = ?, Brand = ?, Colour = ? " +
                "WHERE Id_vehicle = ?";

        Connection connection = dataSource.getConnection();
        PreparedStatement stmt = connection.prepareStatement(sql);

        VehicleMapper vehicleMapper = new VehicleMapper(entity);
        vehicleMapper.mapObjectToRow(stmt);
        stmt.setInt(5, entity.getVehicleId());
        stmt.execute();
    }

    @Override
    public void delete(Vehicle entity) throws SQLException {

        String sql = "DELETE FROM Vehicle WHERE Id_vehicle = ?";

        Connection connection = dataSource.getConnection();
        PreparedStatement stmt = connection.prepareStatement(sql);

        stmt.setInt(1, entity.getVehicleId());
        stmt.execute();
    }
}
