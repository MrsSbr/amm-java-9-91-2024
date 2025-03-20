package ru.vsu.amm.java.repository;

import ru.vsu.amm.java.dbConnection.DatabaseConfiguration;
import ru.vsu.amm.java.entities.HotelEntity;

import javax.sql.DataSource;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class HotelRepo implements CrudRepo<HotelEntity> {
    private final DataSource dataSource;

    public HotelRepo() {
        dataSource = DatabaseConfiguration.getDataSource();
    }

    @Override
    public Optional<HotelEntity> getById(int id) throws SQLException {
        final String query = "SELECT id, name, address, email, phoneNumber, openingDate FROM hotel WHERE id = ?";
        var connection = dataSource.getConnection();

        var preparedStatement = connection.prepareStatement(query);
        preparedStatement.setLong(1, id);
        preparedStatement.execute();

        var resultSet = preparedStatement.getResultSet();
        if (resultSet.next()) {
            return Optional.of(new HotelEntity(
                    resultSet.getInt("id"),
                    resultSet.getString("name"),
                    resultSet.getString("address"),
                    resultSet.getString("email"),
                    resultSet.getString("phoneNumber"),
                    resultSet.getDate("openingDate").toLocalDate())
            );
        }

        return Optional.empty();
    }

    @Override
    public List<HotelEntity> getAll() throws SQLException {
        final String query = "SELECT id, name, address, email, phoneNumber, openingDate FROM hotel";
        var connection = dataSource.getConnection();

        var preparedStatement = connection.prepareStatement(query);
        preparedStatement.execute();

        List<HotelEntity> entityList = new ArrayList<>();

        var resultSet = preparedStatement.getResultSet();
        while (resultSet.next()) {
            var entity = new HotelEntity(
                    resultSet.getInt("id"),
                    resultSet.getString("name"),
                    resultSet.getString("address"),
                    resultSet.getString("email"),
                    resultSet.getString("phoneNumber"),
                    resultSet.getDate("openingDate").toLocalDate()
            );
            entityList.add(entity);
        }

        return entityList;
    }

    @Override
    public void update(HotelEntity entity) throws SQLException {
        final String query = """
                UPDATE hotel SET name = ?, address = ?, email = ?, phoneNumber = ?, openingDate = ? 
                WHERE id = ?""";
        var connection = dataSource.getConnection();

        var preparedStatement = connection.prepareStatement(query);
        setPreparedStatement(preparedStatement, entity);
        preparedStatement.setInt(6, entity.getId());
        preparedStatement.execute();
    }

    @Override
    public void save(HotelEntity entity) throws SQLException {
        final String query = """
                INSERT INTO hotel (name, address, email, phoneNumber, openingDate)
                VALUES (?, ?, ?, ?, ?)""";
        var connection = dataSource.getConnection();

        var preparedStatement = connection.prepareStatement(query);
        setPreparedStatement(preparedStatement, entity);
        preparedStatement.execute();
    }

    @Override
    public void delete(int id) throws SQLException {
        final String query = "DELETE FROM hotel WHERE id = ?";
        var connection = dataSource.getConnection();

        var preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, id);
        preparedStatement.execute();
    }

    private void setPreparedStatement(PreparedStatement preparedStatement, HotelEntity entity) throws SQLException {
        preparedStatement.setString(1, entity.getName());
        preparedStatement.setString(2, entity.getAddress());
        preparedStatement.setString(3, entity.getEmail());
        preparedStatement.setString(4, entity.getPhoneNumber());
        preparedStatement.setDate(5, Date.valueOf(entity.getOpeningDate()));
    }
}
