package ru.vsu.amm.java.repository;

import ru.vsu.amm.java.configuration.DatabaseConfiguration;
import ru.vsu.amm.java.entities.Сategory;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CategoryRepository implements CrudRepository<Сategory> {
    private final DataSource dataSource;

    public CategoryRepository() {
        dataSource = DatabaseConfiguration.getDataSource();
    }

    @Override
    public Optional<Сategory> findById(Long id) throws SQLException {
        final String query = "SELECT id_category, name_category FROM Сategory WHERE id_category = ?";
        Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setLong(1, id);
        preparedStatement.execute();

        ResultSet resultSet = preparedStatement.getResultSet();
        if (resultSet.next()) {
            return Optional.of(new Сategory(
                    resultSet.getLong("id_category"),
                    resultSet.getString("name_category")
            ));
        }
        return Optional.empty();
    }

    @Override
    public List<Сategory> findAll() throws SQLException {
        final String query = "SELECT id_category, name_category FROM Сategory";
        Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.execute();

        ResultSet resultSet = preparedStatement.getResultSet();
        List<Сategory> categories = new ArrayList<>();
        while (resultSet.next()) {
            categories.add(new Сategory(
                    resultSet.getLong("id_category"),
                    resultSet.getString("name_category")
            ));
        }
        return categories;
    }

    @Override
    public void save(Сategory entity) throws SQLException {
        final String query = "INSERT INTO Сategory (name_category) VALUES (?)";
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
    public void update(Сategory entity) throws SQLException {
        final String query = "UPDATE Сategory SET name_category = ? WHERE id_category = ?";
        Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, entity.getName());
        preparedStatement.setLong(2, entity.getId());
        preparedStatement.executeUpdate();
    }

    @Override
    public void delete(Сategory entity) throws SQLException {
        final String query = "DELETE FROM Сategory WHERE id_category = ?";
        Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setLong(1, entity.getId());
        preparedStatement.executeUpdate();
    }
}
