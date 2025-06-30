package ru.vsu.amm.java.Repositories;

import lombok.extern.slf4j.Slf4j;
import ru.vsu.amm.java.Configuration.DbConfiguration;
import ru.vsu.amm.java.Entities.Smartphone;
import ru.vsu.amm.java.Mappers.SmartphoneMapper;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
public class SmartphoneRepository implements CrudRepository<Smartphone> {
    private final DataSource dataSource;

    public SmartphoneRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public SmartphoneRepository() {
        dataSource = DbConfiguration.getDataSource();
    }

    @Override
    public Optional<Smartphone> findById(Long id) {
        String query = "SELECT Smartphone_id, Brand, Model, Ram, Storage_memory, Main_camera_resolution, " +
                "Screen_size, Color, Price, Amount " +
                "FROM Smartphones WHERE Smartphone_id = ?;";
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            SmartphoneMapper smartphoneMapper = new SmartphoneMapper();

            if (resultSet.next()) {
                return Optional.of(smartphoneMapper.mapRowToObject(resultSet));
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public List<Smartphone> findAll() {
        String query = "SELECT Smartphone_id, Brand, Model, Ram, Storage_memory, Main_camera_resolution, " +
                "Screen_size, Color, Price, Amount " +
                "FROM Smartphones;";
        List<Smartphone> result = new ArrayList<>();
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            SmartphoneMapper smartphoneMapper = new SmartphoneMapper();
            while (resultSet.next()) {
                Smartphone smartphone = smartphoneMapper.mapRowToObject(resultSet);
                result.add(smartphone);
            }
            return result;
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
        return new ArrayList<>();
    }

    @Override
    public void save(Smartphone entity) {
        String query = "INSERT INTO Smartphones(Brand, Model, Ram, Storage_memory, Main_camera_resolution, " +
                "Screen_size, Color, Price, Amount) " +
                "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?);";
        try (Connection connection = dataSource.getConnection()) {
            SmartphoneMapper smartphoneMapper = new SmartphoneMapper();
            PreparedStatement statement = smartphoneMapper.mapObjectToRow(entity, connection, query);
            statement.execute();
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
    }

    @Override
    public void update(Smartphone entity) {
        String query = "UPDATE Smartphones " +
                "SET Brand = ?, Model = ?, Ram = ?, Storage_memory = ?, Main_camera_resolution = ?, " +
                "ScreenSize = ?, Color = ?, Price = ?, Amount = ? " +
                "WHERE Smartphone_id = ?;";

        try (Connection connection = dataSource.getConnection()) {
            SmartphoneMapper smartphoneMapper = new SmartphoneMapper();
            PreparedStatement statement = smartphoneMapper.mapObjectToRow(entity, connection, query);
            statement.setLong(10, entity.getSmartphoneId());
            statement.execute();
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
    }

    @Override
    public void delete(Long id) {
        String query = "DELETE FROM Smartphones WHERE Smartphone_id = ?;";
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, id);
            statement.execute();
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
    }
}
