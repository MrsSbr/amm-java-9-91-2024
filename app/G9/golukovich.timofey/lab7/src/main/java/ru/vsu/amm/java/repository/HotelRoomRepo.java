package ru.vsu.amm.java.repository;

import ru.vsu.amm.java.dbConnection.DatabaseConfiguration;
import ru.vsu.amm.java.entities.HotelRoomEntity;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class HotelRoomRepo implements CrudRepo<HotelRoomEntity> {
    private final DataSource dataSource;

    public HotelRoomRepo() {
        dataSource = DatabaseConfiguration.getDataSource();
    }

    @Override
    public Optional<HotelRoomEntity> getById(int id, boolean isForUpdate) throws SQLException {
        String query = """
                SELECT room_id, hotel_id, room_number, floor_number, beds_count, specifications
                FROM hotel_room
                WHERE room_id = ?""";
        query = makeSelectQueryForUpdateOrNot(query, isForUpdate);

        var connection = dataSource.getConnection();

        var preparedStatement = connection.prepareStatement(query);
        preparedStatement.setLong(1, id);
        preparedStatement.execute();

        var resultSet = preparedStatement.getResultSet();
        if (resultSet.next()) {
            return Optional.of(configureHotelRoomEntityFromResultSet(resultSet));
        }

        return Optional.empty();
    }

    @Override
    public List<HotelRoomEntity> getAll(boolean isForUpdate) throws SQLException {
        String query = """
                SELECT room_id, hotel_id, room_number, floor_number, beds_count, specifications
                FROM hotel_room""";
        query = makeSelectQueryForUpdateOrNot(query, isForUpdate);

        var connection = dataSource.getConnection();

        var preparedStatement = connection.prepareStatement(query);
        preparedStatement.execute();

        List<HotelRoomEntity> entityList = new ArrayList<>();

        var resultSet = preparedStatement.getResultSet();
        while (resultSet.next()) {
            var entity = configureHotelRoomEntityFromResultSet(resultSet);
            entityList.add(entity);
        }

        return entityList;
    }

    public List<HotelRoomEntity> getAllByHotelId(int hotelId, boolean isForUpdate) throws SQLException {
        String query = """
                SELECT room_id, hotel_id, room_number, floor_number, beds_count, specifications
                FROM hotel_room
                WHERE hotel_id = ?""";
        query = makeSelectQueryForUpdateOrNot(query, isForUpdate);

        var connection = dataSource.getConnection();

        var preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, hotelId);
        preparedStatement.execute();

        List<HotelRoomEntity> entityList = new ArrayList<>();

        var resultSet = preparedStatement.getResultSet();
        while (resultSet.next()) {
            var entity = configureHotelRoomEntityFromResultSet(resultSet);
            entityList.add(entity);
        }

        return entityList;
    }

    @Override
    public void update(HotelRoomEntity entity) throws SQLException {
        final String query = """
                UPDATE hotel_room SET hotel_id = ?, room_number = ?, floor_number = ?,
                beds_count = ?, specifications = ? WHERE room_id = ?""";
        var connection = dataSource.getConnection();

        var preparedStatement = connection.prepareStatement(query);
        setPreparedStatement(preparedStatement, entity);
        preparedStatement.setInt(6, entity.getId());
        preparedStatement.execute();
    }

    @Override
    public HotelRoomEntity save(HotelRoomEntity entity) throws SQLException {
        final String query = """
                INSERT INTO hotel_room (hotel_id, room_number, floor_number, beds_count, specifications)
                VALUES (?, ?, ?, ?, ?)""";
        var connection = dataSource.getConnection();

        var preparedStatement = connection.prepareStatement(query);
        setPreparedStatement(preparedStatement, entity);
        preparedStatement.execute();

        return configureHotelRoomEntityFromResultSet(preparedStatement.getResultSet());
    }

    @Override
    public void delete(int id) throws SQLException {
        final String query = "DELETE FROM hotel_room WHERE room_id = ?";
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

    private void setPreparedStatement(PreparedStatement preparedStatement, HotelRoomEntity entity) throws SQLException {
        preparedStatement.setInt(1, entity.getHotelId());
        preparedStatement.setInt(2, entity.getRoomNumber());
        preparedStatement.setInt(3, entity.getFloorNumber());
        preparedStatement.setInt(4, entity.getBedsCount());
        preparedStatement.setString(5, entity.getSpecifications());
    }

    private HotelRoomEntity configureHotelRoomEntityFromResultSet(ResultSet resultSet) throws SQLException {
        return new HotelRoomEntity(
                resultSet.getInt("room_id"),
                resultSet.getInt("hotel_id"),
                resultSet.getInt("room_number"),
                resultSet.getInt("floor_number"),
                resultSet.getInt("beds_count"),
                resultSet.getString("specifications")
        );
    }
}
