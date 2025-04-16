package ru.vsu.amm.java.repository;

import ru.vsu.amm.java.configuration.DatabaseConfiguration;
import ru.vsu.amm.java.entities.Author;

import javax.sql.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AuthorRepository implements CrudRepository<Author>{
    private final DataSource dataSource;

    public AuthorRepository() {
        dataSource = DatabaseConfiguration.getDataSource();
    }

    @Override
    public Optional<Author> findById(Long id) throws SQLException {
        final String query = "SELECT id_author, surname, name_author, patronymic, registration_date " +
                "FROM Author " +
                "WHERE id_author = ?";
        Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setLong(1, id);
        preparedStatement.execute();

        ResultSet resultSet = preparedStatement.getResultSet();
        if (resultSet.next()) {
            return Optional.of(new Author(
                    resultSet.getLong("id_author"),
                    resultSet.getString("surname"),
                    resultSet.getString("name_author"),
                    resultSet.getString("patronymic"),
                    resultSet.getDate("registration_date")
            ));
        }
        return Optional.empty();
    }

    @Override
    public List<Author> findAll() throws SQLException {
        final String query = "SELECT id_author, surname, name_author, patronymic, registration_date " +
                "FROM Author";
        Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.execute();

        ResultSet resultSet = preparedStatement.getResultSet();
        List<Author> authors = new ArrayList<>();
        while (resultSet.next()) {
            authors.add(new Author(
                    resultSet.getLong("id_author"),
                    resultSet.getString("surname"),
                    resultSet.getString("name_author"),
                    resultSet.getString("patronymic"),
                    resultSet.getDate("registration_date")
            ));
        }
        return authors;
    }

    @Override
    public void save(Author entity) throws SQLException {
        final String query = "INSERT INTO Author (surname, name_author, patronymic, registration_date) " +
                "VALUES (?, ?, ?, ?)";
        Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setString(1, entity.getSurname());
        preparedStatement.setString(2, entity.getName());
        preparedStatement.setString(3, entity.getPatronymic());
        preparedStatement.setDate(4, new java.sql.Date(entity.getRegistrationDate().getTime()));
        preparedStatement.executeUpdate();

        try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                entity.setId(generatedKeys.getLong(1));
            }
        }
    }

    @Override
    public void update(Author entity) throws SQLException {
        final String query = "UPDATE Author" +
                "SET surname = ?, name_author = ?, patronymic = ?, registration_date = ? " +
                "WHERE id_author = ?";
        Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, entity.getSurname());
        preparedStatement.setString(2, entity.getName());
        preparedStatement.setString(3, entity.getPatronymic());
        preparedStatement.setDate(4, new java.sql.Date(entity.getRegistrationDate().getTime()));
        preparedStatement.setLong(5, entity.getId());
        preparedStatement.executeUpdate();
    }

    @Override
    public void delete(Author entity) throws SQLException {
        final String query = "DELETE FROM Author WHERE id_author = ?";
        Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setLong(1, entity.getId());
        preparedStatement.executeUpdate();
    }
}
