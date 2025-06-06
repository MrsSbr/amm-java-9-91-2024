package ru.vsu.amm.java.repository;

import ru.vsu.amm.java.dbConnection.DatabaseConfiguration;
import ru.vsu.amm.java.entities.HotelEntity;

import javax.sql.DataSource;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
    public Optional<HotelEntity> getById(int id, boolean isForUpdate) throws SQLException {
        String query = """
                SELECT hotel_id, hotel_name, address, email, phone_number, opening_date
                FROM hotel
                WHERE hotel_id = ?""";
        query = makeSelectQueryForUpdateOrNot(query, isForUpdate);

        var connection = dataSource.getConnection();

        var preparedStatement = connection.prepareStatement(query);
        preparedStatement.setLong(1, id);
        preparedStatement.execute();

        var resultSet = preparedStatement.getResultSet();
        if (resultSet.next()) {
            return Optional.of(configureHotelEntityFromResultSet(resultSet));
        }

        return Optional.empty();
    }

    @Override
    public List<HotelEntity> getAll(boolean isForUpdate) throws SQLException {
        String query = """
                SELECT hotel_id, hotel_name, address, email, phone_number, opening_date
                FROM hotel""";
        query = makeSelectQueryForUpdateOrNot(query, isForUpdate);

        var connection = dataSource.getConnection();

        var preparedStatement = connection.prepareStatement(query);
        preparedStatement.execute();

        List<HotelEntity> entityList = new ArrayList<>();

        var resultSet = preparedStatement.getResultSet();
        while (resultSet.next()) {
            var entity = configureHotelEntityFromResultSet(resultSet);
            entityList.add(entity);
        }

        return entityList;
    }

    @Override
    public void update(HotelEntity entity) throws SQLException {
        final String query = """
                UPDATE hotel SET hotel_name = ?, address = ?, email = ?, phone_number = ?, opening_date = ? 
                WHERE hotel_id = ?""";
        var connection = dataSource.getConnection();

        var preparedStatement = connection.prepareStatement(query);
        setPreparedStatement(preparedStatement, entity);
        preparedStatement.setInt(6, entity.getId());
        preparedStatement.execute();
    }

    @Override
    public HotelEntity save(HotelEntity entity) throws SQLException {
        final String query = """
                INSERT INTO hotel (hotel_name, address, email, phone_number, opening_date)
                VALUES (?, ?, ?, ?, ?)""";
        var connection = dataSource.getConnection();

        var preparedStatement = connection.prepareStatement(query);
        setPreparedStatement(preparedStatement, entity);
        preparedStatement.execute();

        return configureHotelEntityFromResultSet(preparedStatement.getResultSet());
    }

    @Override
    public void delete(int id) throws SQLException {
        final String query = "DELETE FROM hotel WHERE hotel_id = ?";
        var connection = dataSource.getConnection();

        var preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, id);
        preparedStatement.execute();
    }

    private String makeSelectQueryForUpdateOrNot(String query, boolean isForUpdate) {
        if (isForUpdate) {
            query += " FOR UPDATE";
        }
        return query;
    }

    private void setPreparedStatement(PreparedStatement preparedStatement, HotelEntity entity) throws SQLException {
        preparedStatement.setString(1, entity.getName());
        preparedStatement.setString(2, entity.getAddress());
        preparedStatement.setString(3, entity.getEmail());
        preparedStatement.setString(4, entity.getPhoneNumber());
        preparedStatement.setDate(5, Date.valueOf(entity.getOpeningDate()));
    }

    private HotelEntity configureHotelEntityFromResultSet(ResultSet resultSet) throws SQLException {
        return new HotelEntity(
                resultSet.getInt("hotel_id"),
                resultSet.getString("hotel_name"),
                resultSet.getString("address"),
                resultSet.getString("email"),
                resultSet.getString("phone_number"),
                resultSet.getDate("opening_date").toLocalDate()
        );
    }
}
