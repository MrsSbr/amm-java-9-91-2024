package ru.vsu.amm.java.repository;

import ru.vsu.amm.java.config.DbConfig;
import ru.vsu.amm.java.entity.Transaction;
import ru.vsu.amm.java.enums.Category;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class TransactionRepository {

    private final DataSource dataSource;

    public TransactionRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Optional<Transaction> findById(Long id) throws SQLException {
        final String query = "SELECT id, user_id, amount, type, date_create, category FROM transactions WHERE id = ?";
        Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setLong(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            return Optional.of(new Transaction(
                    resultSet.getLong("id"),
                    resultSet.getLong("user_id"),
                    resultSet.getBigDecimal("amount"),
                    resultSet.getBoolean("type"),
                    resultSet.getTimestamp("date_create").toLocalDateTime(),
                    Category.valueOf(resultSet.getString("category"))
            ));
        }

        return Optional.empty();
    }

    public List<Transaction> findByUserId(Long userId) throws SQLException {
        List<Transaction> transactions = new ArrayList<>();
        final String query = "SELECT id, user_id, amount, type, date_create, category FROM transactions WHERE user_id = ?";
        Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setLong(1, userId);
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            transactions.add(new Transaction(
                    resultSet.getLong("id"),
                    resultSet.getLong("user_id"),
                    resultSet.getBigDecimal("amount"),
                    resultSet.getBoolean("type"),
                    resultSet.getTimestamp("date_create").toLocalDateTime(),
                    Category.valueOf(resultSet.getString("category"))
            ));
        }
        return transactions;
    }

    public List<Transaction> findByUserIdAndTimeRange(Long userId, LocalDateTime start, LocalDateTime end) throws SQLException {
        List<Transaction> transactions = new ArrayList<>();
        final String query = """
                               SELECT id, user_id, amount, type, date_create, category
                               FROM transactions
                               WHERE user_id = ? AND date_create BETWEEN ? AND ?
                               """;
        Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setLong(1, userId);
        preparedStatement.setTimestamp(2, Timestamp.valueOf(start));
        preparedStatement.setTimestamp(3, Timestamp.valueOf(end));

        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            transactions.add(new Transaction(
                    resultSet.getLong("id"),
                    resultSet.getLong("user_id"),
                    resultSet.getBigDecimal("amount"),
                    resultSet.getBoolean("type"),
                    resultSet.getTimestamp("date_create").toLocalDateTime(),
                    Category.valueOf(resultSet.getString("category"))
            ));
        }
        return transactions;
    }

    public void save(Transaction transaction) throws SQLException {
        final String query = "INSERT INTO transactions (user_id, amount, type, date_create, category) VALUES (?, ?, ?, ?, ?)";
        Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setLong(1, transaction.getUserId());
        preparedStatement.setBigDecimal(2, transaction.getAmount());
        preparedStatement.setBoolean(3, transaction.getType());
        preparedStatement.setTimestamp(4, java.sql.Timestamp.valueOf(transaction.getDate()));
        preparedStatement.setString(5, transaction.getCategory().name());
        preparedStatement.executeUpdate();

    }

    public void update(Transaction transaction) throws SQLException {
        final String query = "UPDATE transactions SET user_id = ?, amount = ?, type = ?, date_create = ?, category = ? WHERE id = ?";
        Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setLong(1, transaction.getUserId());
        preparedStatement.setBigDecimal(2, transaction.getAmount());
        preparedStatement.setBoolean(3, transaction.getType());
        preparedStatement.setTimestamp(4, java.sql.Timestamp.valueOf(transaction.getDate()));
        preparedStatement.setString(5, transaction.getCategory().name());
        preparedStatement.setLong(6, transaction.getId());
        preparedStatement.executeUpdate();
    }

    public void deleteById(Long id) throws SQLException {
        final String query = "DELETE FROM transactions WHERE id = ?";
        Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setLong(1, id);
        preparedStatement.executeUpdate();
    }
}