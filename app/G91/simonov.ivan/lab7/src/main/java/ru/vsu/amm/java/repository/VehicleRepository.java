package ru.vsu.amm.java.repository;

import ru.vsu.amm.java.configuration.DatabaseConfiguration;
import ru.vsu.amm.java.entities.Vehicle;
import ru.vsu.amm.java.exceptions.AddException;
import ru.vsu.amm.java.exceptions.DeleteException;
import ru.vsu.amm.java.exceptions.UpdateException;
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

import static ru.vsu.amm.java.utils.LoggerInitializer.initializeLogger;

public class VehicleRepository implements ParkingRepository<Vehicle> {

    private static final Logger logger = initializeLogger(
            "app/G91/simonov.ivan/lab7/src/main/java/ru/vsu/amm/java/logs/vehicle-repository-logs.log",
            SessionRepository.class.getName());

    private final DataSource dataSource;

    public VehicleRepository() {
        dataSource = DatabaseConfiguration.getMainDataSource();
    }

    public Optional<Vehicle> getByInfo(Vehicle entity) {

        String sql = """
                SELECT Id_vehicle, RegistrationNumber, Model, Brand, Colour
                FROM Vehicle
                WHERE RegistrationNumber = ? AND Model = ? AND Brand = ? AND Colour = ?
                """;

        try (Connection connection = dataSource.getConnection()) {

            VehicleMapper vehicleMapper = new VehicleMapper();
            PreparedStatement stmt = vehicleMapper.mapObjectToRow(entity, connection, sql);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return Optional.of(vehicleMapper.mapRowToObject(rs));
            }

        } catch (SQLException e) {

            logger.log(Level.SEVERE, e.getMessage(), e);

        }

        return Optional.empty();

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
    public int save(Vehicle entity) {

        String sql = """
                INSERT INTO Vehicle (RegistrationNumber, Model, Brand, Colour)
                VALUES (?, ?, ?, ?)
                """;

        try (Connection connection = dataSource.getConnection()) {

            VehicleMapper vehicleMapper = new VehicleMapper();
            PreparedStatement stmt = vehicleMapper.mapObjectToRow(entity, connection, sql);
            stmt.execute();

            ResultSet generatedKeys = stmt.getGeneratedKeys();

            if (generatedKeys.next()) {
                return generatedKeys.getInt(1);
            }

        } catch (SQLException e) {

            logger.log(Level.SEVERE, e.getMessage(), e);
            throw new AddException(e.getMessage());

        }

        return 0;
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
            throw new UpdateException(e.getMessage());

        }
    }

    @Override
    public void delete(int id) {

        String sql = """
                DELETE FROM Vehicle
                WHERE Id_vehicle = ?
                """;

        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.execute();

        } catch (SQLException e) {

            logger.log(Level.SEVERE, e.getMessage(), e);
            throw new DeleteException(e.getMessage());

        }
    }
}
