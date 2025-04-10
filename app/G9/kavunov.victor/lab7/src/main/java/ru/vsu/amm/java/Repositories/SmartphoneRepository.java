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

    public SmartphoneRepository() {
        dataSource = DbConfiguration.getDataSource();
    }

    @Override
    public Optional<Smartphone> findById(Long id) {
        String query = "SELECT SmartphoneID, Brand, Model, RAM, StorageMemory, MainCameraResolution, " +
                "ScreenSize, Color, Price, Amount " +
                "FROM Smartphones WHERE SmartphoneId = ?;";
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
        String query = "SELECT SmartphoneID, Brand, Model, RAM, StorageMemory, MainCameraResolution, " +
                "ScreenSize, Color, Price, Amount " +
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
        String query = "INSERT INTO Smartphones(Brand, Model, RAM, StorageMemory, MainCameraResolution, " +
                "ScreenSize, Color, Price, Amount) " +
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
                "SET Brand = ?, Model = ?, RAM = ?, StorageMemory = ?, MainCameraResolution = ?, " +
                "ScreenSize = ?, Color = ?, Price = ?, Amount = ? " +
                "WHERE SmartphoneID = ?;";

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
        String query = "DELETE FROM Smartphones WHERE SmartphoneId = ?;";
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, id);
            statement.execute();
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
    }

    public List<Smartphone> findAllWithBrand(String brand) {
        String query = "SELECT SmartphoneID, Brand, Model, RAM, StorageMemory, MainCameraResolution, " +
                "ScreenSize, Color, Price, Amount " +
                "FROM Smartphones WHERE Brand = ?;";
        List<Smartphone> result = new ArrayList<>();
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, brand);
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

    public List<Smartphone> findAllWithBrandAndModel(String brand, String model) {
        String query = "SELECT SmartphoneID, Brand, Model, RAM, StorageMemory, MainCameraResolution, " +
                "ScreenSize, Color, Price, Amount " +
                "FROM Smartphones WHERE Brand = ? AND Model = ?;";
        List<Smartphone> result = new ArrayList<>();
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, brand);
            statement.setString(2, model);
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

    public List<Smartphone> findAllWithScreenSize(float minSize, float maxSize) {
        String query = "SELECT SmartphoneID, Brand, Model, RAM, StorageMemory, MainCameraResolution, " +
                "ScreenSize, Color, Price, Amount " +
                "FROM Smartphones WHERE ScreenSize IS BETWEEN ? AND ?;";
        List<Smartphone> result = new ArrayList<>();
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setFloat(1, minSize);
            statement.setFloat(2, maxSize);
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

    public List<Smartphone> findAllWithRam(int minSize, int maxSize) {
        String query = "SELECT SmartphoneID, Brand, Model, RAM, StorageMemory, MainCameraResolution, " +
                "ScreenSize, Color, Price, Amount " +
                "FROM Smartphones WHERE RAM IS BETWEEN ? AND ?;";
        List<Smartphone> result = new ArrayList<>();
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, minSize);
            statement.setInt(2, maxSize);
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

    public List<Smartphone> findAllWithStorageMemory(int minSize, int maxSize) {
        String query = "SELECT SmartphoneID, Brand, Model, RAM, StorageMemory, MainCameraResolution, " +
                "ScreenSize, Color, Price, Amount " +
                "FROM Smartphones WHERE StorageMemory IS BETWEEN ? AND ?;";
        List<Smartphone> result = new ArrayList<>();
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, minSize);
            statement.setInt(2, maxSize);
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

    public List<Smartphone> findAllWithMainCameraResolution(float minResolution, float maxResolution) {
        String query = "SELECT SmartphoneID, Brand, Model, RAM, StorageMemory, MainCameraResolution, " +
                "ScreenSize, Color, Price, Amount " +
                "FROM Smartphones WHERE MainCameraResolution IS BETWEEN ? AND ?;";
        List<Smartphone> result = new ArrayList<>();
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setFloat(1, minResolution);
            statement.setFloat(2, maxResolution);
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

    public List<Smartphone> findAllWithPrice(float minPrice, float maxPrice) {
        String query = "SELECT SmartphoneID, Brand, Model, RAM, StorageMemory, MainCameraResolution, " +
                "ScreenSize, Color, Price, Amount " +
                "FROM Smartphones WHERE Price IS BETWEEN ? AND ?;";
        List<Smartphone> result = new ArrayList<>();
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setFloat(1, minPrice);
            statement.setFloat(2, maxPrice);
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
}
