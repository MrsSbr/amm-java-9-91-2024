package ru.vsu.amm.java.Repositories;

import lombok.extern.slf4j.Slf4j;
import ru.vsu.amm.java.Configuration.DbConfiguration;
import ru.vsu.amm.java.Entities.Order;
import ru.vsu.amm.java.Entities.User;
import ru.vsu.amm.java.Mappers.OrderMapper;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
public class OrderRepository implements CrudRepository<Order> {
    private final DataSource dataSource;

    public OrderRepository() {
        dataSource = DbConfiguration.getDataSource();
    }

    @Override
    public Optional<Order> findById(Long id) {
        String query = "SELECT Order_id, User_id, Is_paid, Registration_date, Smartphone_id FROM Orders WHERE Order_id = ?;";
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            OrderMapper orderMapper = new OrderMapper();

            if (resultSet.next()) {
                return Optional.of(orderMapper.mapRowToObject(resultSet));
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public List<Order> findAll() {
        String query = "SELECT Order_id, User_id, Smartphone_id, Is_paid, Registration_date FROM Orders;";
        List<Order> result = new ArrayList<>();
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            OrderMapper orderMapper = new OrderMapper();
            while (resultSet.next()) {
                Order order = orderMapper.mapRowToObject(resultSet);
                result.add(order);
            }
            return result;
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
        return new ArrayList<>();
    }

    @Override
    public void save(Order entity) {
        String query = "INSERT INTO Orders(User_id, Smartphone_id, Is_paid, Registration_date) VALUES(?, ?, ?, ?);";
        try (Connection connection = dataSource.getConnection()) {
            OrderMapper orderMapper = new OrderMapper();
            PreparedStatement statement = orderMapper.mapObjectToRow(entity, connection, query);
            statement.execute();
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
    }

    @Override
    public void update(Order entity) {
        String query = "UPDATE Orders SET User_id = ?, Smartphone_id = ?, is_paid = ?, Registration_date = ? WHERE Order_id = ?;";

        try (Connection connection = dataSource.getConnection()) {
            OrderMapper orderMapper = new OrderMapper();
            PreparedStatement statement = orderMapper.mapObjectToRow(entity, connection, query);
            statement.setLong(5, entity.getId());
            statement.execute();
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
    }

    @Override
    public void delete(Long id) {
        String query = "DELETE FROM Orders WHERE Order_id = ?;";
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, id);
            statement.execute();
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
    }

    public List<Order> findAllByUserAndPaid(Long userId, boolean paid) {
        String query = "SELECT Order_id, User_id, Smartphone_id, Is_paid, Registration_date FROM Orders WHERE User_id = ? AND Is_paid = ?;";
        List<Order> result = new ArrayList<>();
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, userId);
            statement.setBoolean(2, paid);
            ResultSet resultSet = statement.executeQuery();
            OrderMapper orderMapper = new OrderMapper();

            while (resultSet.next()) {
                Order order = orderMapper.mapRowToObject(resultSet);
                result.add(order);
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
        return result;
    }

    public List<Order> findAllByUser(Long userId) {
        String query = "SELECT Order_id, User_id, Smartphone_id, Is_paid, Registration_date FROM Orders WHERE User_id = ?;";
        List<Order> result = new ArrayList<>();
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, userId);
            ResultSet resultSet = statement.executeQuery();
            OrderMapper orderMapper = new OrderMapper();
            while (resultSet.next()) {
                Order order = orderMapper.mapRowToObject(resultSet);
                result.add(order);
            }
            return result;
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
        return new ArrayList<>();
    }
}
