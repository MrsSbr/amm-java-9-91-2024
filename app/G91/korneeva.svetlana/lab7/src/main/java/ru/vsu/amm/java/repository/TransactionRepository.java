package ru.vsu.amm.java.repository;

import ru.vsu.amm.java.config.DbConfig;
import ru.vsu.amm.java.entity.Transaction;
import ru.vsu.amm.java.enums.Category;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TransactionRepository {

    private final DataSource dataSource;

    public TransactionRepository() {
        dataSource = DbConfig.getDataSource();
    }

    public Optional<Transaction> findById(Long id) throws SQLException {
        final String query = "SELECT id, user_id, amount, type, date, category FROM transactions WHERE id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return Optional.of(new Transaction(
                        resultSet.getLong("id"),
                        resultSet.getLong("user_id"),
                        resultSet.getInt("amount"),
                        resultSet.getBoolean("type"),
                        resultSet.getTimestamp("date").toLocalDateTime(),
                        Category.valueOf(resultSet.getString("category"))
                ));
            }
        }
        return Optional.empty();
    }

    public List<Transaction> findByUserId(Long userId) throws SQLException {
        List<Transaction> transactions = new ArrayList<>();
        final String query = "SELECT id, user_id, amount, type, date, category FROM transactions WHERE user_id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                transactions.add(new Transaction(
                        resultSet.getLong("id"),
                        resultSet.getLong("user_id"),
                        resultSet.getInt("amount"),
                        resultSet.getBoolean("type"),
                        resultSet.getTimestamp("date").toLocalDateTime(),
                        Category.valueOf(resultSet.getString("category"))
                ));
            }
        }
        return transactions;
    }

    public void save(Transaction transaction) throws SQLException {
        final String query = "INSERT INTO transactions (user_id, amount, type, date, category) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, transaction.getUserId());
            preparedStatement.setInt(2, transaction.getAmount());
            preparedStatement.setBoolean(3, transaction.getType());
            preparedStatement.setTimestamp(4, java.sql.Timestamp.valueOf(transaction.getDate()));
            preparedStatement.setString(5, transaction.getCategory().name());
            preparedStatement.executeUpdate();
        }
    }

    public void update(Transaction transaction) throws SQLException {
        final String query = "UPDATE transactions SET user_id = ?, amount = ?, type = ?, date = ?, category = ? WHERE id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, transaction.getUserId());
            preparedStatement.setInt(2, transaction.getAmount());
            preparedStatement.setBoolean(3, transaction.getType());
            preparedStatement.setTimestamp(4, java.sql.Timestamp.valueOf(transaction.getDate()));
            preparedStatement.setString(5, transaction.getCategory().name());
            preparedStatement.setLong(6, transaction.getId());
            preparedStatement.executeUpdate();
        }
    }

    public void deleteById(Long id) throws SQLException {
        final String query = "DELETE FROM transactions WHERE id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        }
    }
}