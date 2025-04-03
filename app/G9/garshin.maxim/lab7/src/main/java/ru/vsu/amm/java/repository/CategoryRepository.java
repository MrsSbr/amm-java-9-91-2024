package ru.vsu.amm.java.repository;

import ru.vsu.amm.java.config.DatabaseConfiguration;
import ru.vsu.amm.java.entities.Category;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CategoryRepository implements Repository<Category> {
    private final DataSource dataSource;

    public CategoryRepository() {
        this.dataSource = DatabaseConfiguration.getDataSource();
    }

    @Override
    public Optional<Category> findById(Long id) throws SQLException {
        final String query = "SELECT categoryId, title FROM Category WHERE categoryId = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return Optional.of(new Category(
                        resultSet.getLong("categoryId"),
                        resultSet.getString("title")
                ));
            }
            
            return Optional.empty();
        }
    }

    @Override
    public List<Category> findAll() throws SQLException {
        final String query = "SELECT categoryId, title FROM Category";
        List<Category> categories = new ArrayList<>();

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                categories.add(new Category(
                        resultSet.getLong("categoryId"),
                        resultSet.getString("title")
                ));
            }
        }
        return categories;
    }

    @Override
    public void save(Category entity) throws SQLException {
        final String query = "INSERT INTO Category (title) VALUES (?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, entity.getTitle());
            preparedStatement.executeUpdate();
        }
    }

    @Override
    public void delete(Category entity) throws SQLException {
        final String query = "DELETE FROM Category WHERE categoryId = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, entity.getCategoryId());
            preparedStatement.executeUpdate();
        }
    }
}