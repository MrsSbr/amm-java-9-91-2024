package ru.vsu.amm.java.Repositories;

import lombok.extern.slf4j.Slf4j;
import ru.vsu.amm.java.Configuration.DbConfiguration;
import ru.vsu.amm.java.Entities.SmartphoneInOrder;
import ru.vsu.amm.java.Mappers.SmartphoneInOrderMapper;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
public class SmartphoneInOrderRepository implements CrudRepository<SmartphoneInOrder> {
    private final DataSource dataSource;

    public SmartphoneInOrderRepository() {
        dataSource = DbConfiguration.getDataSource();
    }

    @Override
    public Optional<SmartphoneInOrder> findById(Long id) {
        String query = "SELECT * FROM SmartphonesInOrders WHERE SmartphonesInOrdersID = ?;";
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            SmartphoneInOrderMapper smartphoneInOrderMapper = new SmartphoneInOrderMapper();

            if (resultSet.next()) {
                return Optional.of(smartphoneInOrderMapper.mapRowToObject(resultSet));
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public List<SmartphoneInOrder> findAll() {
        String query = "SELECT * FROM SmartphonesInOrders;";
        List<SmartphoneInOrder> result = new ArrayList<>();
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            SmartphoneInOrderMapper smartphoneInOrderMapper = new SmartphoneInOrderMapper();
            while (resultSet.next()) {
                SmartphoneInOrder smartphoneInOrder = smartphoneInOrderMapper.mapRowToObject(resultSet);
                result.add(smartphoneInOrder);
            }
            return result;
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
        return new ArrayList<>();
    }

    @Override
    public void save(SmartphoneInOrder entity) {
        String query = "INSERT INTO SmartphonesInOrders(OrderNum, SmartphoneID, Amount) VALUES(?, ?, ?);";
        try (Connection connection = dataSource.getConnection()) {
            SmartphoneInOrderMapper smartphoneInOrderMapper = new SmartphoneInOrderMapper();
            PreparedStatement statement = smartphoneInOrderMapper.mapObjectToRow(entity, connection, query);
            statement.execute();
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
    }

    @Override
    public void update(SmartphoneInOrder entity) {
        String query = "UPDATE SmartphonesInOrders " +
                "SET OrderNum = ?, SmartphoneID = ?, Amount = ? " +
                "WHERE SmartphoneInOrderID = ?;";

        try (Connection connection = dataSource.getConnection()) {
            SmartphoneInOrderMapper smartphoneInOrderMapper = new SmartphoneInOrderMapper();
            PreparedStatement statement = smartphoneInOrderMapper.mapObjectToRow(entity, connection, query);
            statement.setLong(4, entity.getOrderNum());
            statement.execute();
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
    }

    @Override
    public void delete(Long id) {
        String query = "DELETE FROM SmartphonesInOrders WHERE SmartphoneInOrderID = ?;";
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, id);
            statement.execute();
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
    }

    public List<SmartphoneInOrder> findAllByOrderNum(Long orderNum) {
        String query = "SELECT * FROM SmartphonesInOrders WHERE OrderNum = ?;";
        List<SmartphoneInOrder> result = new ArrayList<>();
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, orderNum);
            ResultSet resultSet = statement.executeQuery();
            SmartphoneInOrderMapper smartphoneInOrderMapper = new SmartphoneInOrderMapper();
            while (resultSet.next()) {
                SmartphoneInOrder smartphoneInOrder = smartphoneInOrderMapper.mapRowToObject(resultSet);
                result.add(smartphoneInOrder);
            }
            return result;
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
        return new ArrayList<>();
    }

    public List<SmartphoneInOrder> findAllBySmartphone(Long smartphoneId) {
        String query = "SELECT * FROM SmartphonesInOrders WHERE OrderNum = ?;";
        List<SmartphoneInOrder> result = new ArrayList<>();
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, smartphoneId);
            ResultSet resultSet = statement.executeQuery();
            SmartphoneInOrderMapper smartphoneInOrderMapper = new SmartphoneInOrderMapper();
            while (resultSet.next()) {
                SmartphoneInOrder smartphoneInOrder = smartphoneInOrderMapper.mapRowToObject(resultSet);
                result.add(smartphoneInOrder);
            }
            return result;
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
        return new ArrayList<>();
    }

}
