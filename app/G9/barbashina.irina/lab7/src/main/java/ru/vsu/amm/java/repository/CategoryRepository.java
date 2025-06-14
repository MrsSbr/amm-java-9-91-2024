package ru.vsu.amm.java.repository;

import ru.vsu.amm.java.configuration.DatabaseConfiguration;
import ru.vsu.amm.java.entities.Category;

import javax.sql.DataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CategoryRepository implements CrudRepository<Category> {
    private final DataSource dataSource;

    public CategoryRepository() {
        dataSource = DatabaseConfiguration.getDataSource();
    }

    @Override
    public Optional<Category> findById(Long id) throws SQLException {
        final String query = "SELECT id_category, name_category FROM Category WHERE id_category = ?";
        Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setLong(1, id);
        preparedStatement.execute();

        ResultSet resultSet = preparedStatement.getResultSet();
        if (resultSet.next()) {
            return Optional.of(new Category(
                    resultSet.getLong("id_category"),
                    resultSet.getString("name_category")
            ));
        }
        return Optional.empty();
    }

    @Override
    public List<Category> findAll() throws SQLException {
        final String query = "SELECT id_category, name_category FROM Category";
        Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.execute();

        ResultSet resultSet = preparedStatement.getResultSet();
        List<Category> categories = new ArrayList<>();
        while (resultSet.next()) {
            categories.add(new Category(
                    resultSet.getLong("id_category"),
                    resultSet.getString("name_category")
            ));
        }
        return categories;
    }

    @Override
    public void save(Category entity) throws SQLException {
        final String query = "INSERT INTO Category (name_category) VALUES (?)";
        Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setString(1, entity.getName());
        preparedStatement.executeUpdate();

        try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                entity.setId(generatedKeys.getLong(1));
            }
        }
    }

    @Override
    public void update(Category entity) throws SQLException {
        final String query = "UPDATE Category SET name_category = ? WHERE id_category = ?";
        Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, entity.getName());
        preparedStatement.setLong(2, entity.getId());
        preparedStatement.executeUpdate();
    }

    @Override
    public void delete(Category entity) throws SQLException {
        final String query = "DELETE FROM Category WHERE id_category = ?";
        Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setLong(1, entity.getId());
        preparedStatement.executeUpdate();
    }
}
