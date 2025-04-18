package ru.vsu.amm.java.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger logger = LoggerFactory.getLogger(CategoryRepository.class);
    private final DataSource dataSource;

    public CategoryRepository() {
        this.dataSource = DatabaseConfiguration.getDataSource();
    }

    @Override
    public Optional<Category> findById(Long id) throws SQLException {
        final String query = "SELECT categoryId, title FROM Category WHERE categoryId = ?";
        logger.info("Executing findById query: {} with id: {}", query, id);

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                Category category = new Category(
                        resultSet.getLong("categoryId"),
                        resultSet.getString("title")
                );
                logger.info("Category found: {}", category);
                return Optional.of(category);
            }
            logger.info("No category found with id: {}", id);
            return Optional.empty();
        }
    }

    @Override
    public List<Category> findAll() throws SQLException {
        final String query = "SELECT categoryId, title FROM Category";
        logger.info("Executing findAll query: {}", query);

        List<Category> categories = new ArrayList<>();

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                Category category = new Category(
                        resultSet.getLong("categoryId"),
                        resultSet.getString("title")
                );
                categories.add(category);
                logger.info("Category retrieved: {}", category);
            }
        }
        logger.info("Total categories found: {}", categories.size());
        return categories;
    }

    @Override
    public void save(Category entity) throws SQLException {
        final String query = "INSERT INTO Category (title) VALUES (?)";
        logger.info("Executing save query: {} with title: {}", query, entity.getTitle());

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, entity.getTitle());
            int rowsAffected = preparedStatement.executeUpdate();
            logger.info("Category saved. Rows affected: {}", rowsAffected);
        }
    }

    @Override
    public void delete(Category entity) throws SQLException {
        final String query = "DELETE FROM Category WHERE categoryId = ?";
        logger.info("Executing delete query: {} with categoryId: {}", query, entity.getCategoryId());

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, entity.getCategoryId());
            int rowsAffected = preparedStatement.executeUpdate();
            logger.info("Deleted category with id: {}. Rows affected: {}", entity.getCategoryId(), rowsAffected);
        }
    }

    public List<Category> findByUserId(Long userId) throws SQLException {
        final String query = "SELECT categoryId, title FROM Category where userid = ?";
        logger.info("Executing findByUserId query: {} for userId: {}", query, userId);

        List<Category> categories = new ArrayList<>();

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setLong(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Category category = new Category(
                        resultSet.getLong("categoryId"),
                        resultSet.getString("title")
                );
                categories.add(category);
            }
        }
        logger.info("Total categories found: {} for user with id: {}", categories.size(), userId);
        return categories;
    }
}