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
import java.util.logging.Level;
import java.util.logging.Logger;

public class VehicleRepository implements ParkingRepository<Vehicle> {

    private static final Logger logger = Logger.getLogger(VehicleRepository.class.getName());
    private final DataSource dataSource;

    public VehicleRepository() {
        dataSource = DatabaseConfiguration.getDataSource();
    }

    @Override
    public Optional<Vehicle> getById(int id) {

        String sql = """
                SELECT Id_vehicle, RegistrationNumber, Model, Brand, Colour
                FROM Vehicle
                WHERE Id_vehicle = ?
                """;

        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, id);

            ResultSet rs = stmt.executeQuery();

            VehicleMapper vehicleMapper = new VehicleMapper();

            if (rs.next()) {
                return Optional.of(vehicleMapper.mapRowToObject(rs));
            }

        } catch (SQLException e) {

            logger.log(Level.SEVERE, e.getMessage(), e);

        }

        return Optional.empty();

    }

    @Override
    public List<Vehicle> getAll() {

        String sql = """
                SELECT Id_vehicle, RegistrationNumber, Model, Brand, Colour
                FROM Vehicle
                """;

        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            ResultSet rs = stmt.executeQuery();

            List<Vehicle> vehicles = new ArrayList<>();

            VehicleMapper vehicleMapper = new VehicleMapper();

            while (rs.next()) {
                vehicles.add(vehicleMapper.mapRowToObject(rs));
            }

            return vehicles;

        } catch (SQLException e) {

            logger.log(Level.SEVERE, e.getMessage(), e);

        }

        return null;

    }

    @Override
    public void save(Vehicle entity) {

        String sql = """
                INSERT INTO Vehicle (RegistrationNumber, Model, Brand, Colour)
                VALUES (?, ?, ?, ?)
                """;

        try (Connection connection = dataSource.getConnection()) {

            VehicleMapper vehicleMapper = new VehicleMapper();
            PreparedStatement stmt = vehicleMapper.mapObjectToRow(entity, connection, sql);
            stmt.execute();

        } catch (SQLException e) {

            logger.log(Level.SEVERE, e.getMessage(), e);

        }
    }

    @Override
    public void update(Vehicle entity) {

        String sql = """
                UPDATE Vehicle
                SET RegistrationNumber = ?, Model = ?, Brand = ?, Colour = ?
                WHERE Id_vehicle = ?
                """;

        try (Connection connection = dataSource.getConnection()) {

            VehicleMapper vehicleMapper = new VehicleMapper();
            PreparedStatement stmt = vehicleMapper.mapObjectToRow(entity, connection, sql);
            stmt.setInt(5, entity.getVehicleId());
            stmt.execute();

        } catch (SQLException e) {

            logger.log(Level.SEVERE, e.getMessage(), e);

        }
    }

    @Override
    public void delete(Vehicle entity) {

        String sql = """
                DELETE FROM Vehicle
                WHERE Id_vehicle = ?
                """;

        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, entity.getVehicleId());
            stmt.execute();

        } catch (SQLException e) {

            logger.log(Level.SEVERE, e.getMessage(), e);

        }
    }
}
