package ru.vsu.amm.java.repository;

import ru.vsu.amm.java.dbConnection.DatabaseConfiguration;
import ru.vsu.amm.java.entities.HotelRoomEntity;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
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
    public Optional<HotelRoomEntity> getById(int id) throws SQLException {
        final String query = """
                SELECT id, hotelId, roomNumber, floorNumber, bedsCount, specifications
                FROM hotel_room WHERE id = ?""";
        var connection = dataSource.getConnection();

        var preparedStatement = connection.prepareStatement(query);
        preparedStatement.setLong(1, id);
        preparedStatement.execute();

        var resultSet = preparedStatement.getResultSet();
        if (resultSet.next()) {
            return Optional.of(new HotelRoomEntity(
                    resultSet.getInt("id"),
                    resultSet.getInt("hotelId"),
                    resultSet.getInt("roomNumber"),
                    resultSet.getInt("floorNumber"),
                    resultSet.getInt("bedsCount"),
                    resultSet.getString("specifications"))
            );
        }

        return Optional.empty();
    }

    @Override
    public List<HotelRoomEntity> getAll() throws SQLException {
        final String query = "SELECT id, hotelId, roomNumber, floorNumber, bedsCount, specifications FROM hotel_room";
        var connection = dataSource.getConnection();

        var preparedStatement = connection.prepareStatement(query);
        preparedStatement.execute();

        List<HotelRoomEntity> entityList = new ArrayList<>();

        var resultSet = preparedStatement.getResultSet();
        while (resultSet.next()) {
            var entity = new HotelRoomEntity(
                    resultSet.getInt("id"),
                    resultSet.getInt("hotelId"),
                    resultSet.getInt("roomNumber"),
                    resultSet.getInt("floorNumber"),
                    resultSet.getInt("bedsCount"),
                    resultSet.getString("specifications"));
            entityList.add(entity);
        }

        return entityList;
    }

    @Override
    public void update(HotelRoomEntity entity) throws SQLException {
        final String query = """
                UPDATE hotel_room SET hotelId = ?, roomNumber = ?, floorNumber = ?,
                bedsCount = ?, specifications = ? WHERE id = ?""";
        var connection = dataSource.getConnection();

        var preparedStatement = connection.prepareStatement(query);
        setPreparedStatement(preparedStatement, entity);
        preparedStatement.setInt(6, entity.getId());
        preparedStatement.execute();
    }

    @Override
    public void save(HotelRoomEntity entity) throws SQLException {
        final String query = """
                INSERT INTO hotel_room (hotelId, roomNumber, floorNumber, bedsCount, specifications)
                VALUES (?, ?, ?, ?, ?)""";
        var connection = dataSource.getConnection();

        var preparedStatement = connection.prepareStatement(query);
        setPreparedStatement(preparedStatement, entity);
        preparedStatement.execute();
    }

    @Override
    public void delete(int id) throws SQLException {
        final String query = "DELETE FROM hotel_room WHERE id = ?";
        var connection = dataSource.getConnection();

        var preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, id);
        preparedStatement.execute();
    }

    private void setPreparedStatement(PreparedStatement preparedStatement, HotelRoomEntity entity) throws SQLException {
        preparedStatement.setInt(1, entity.getHotelId());
        preparedStatement.setInt(2, entity.getRoomNumber());
        preparedStatement.setInt(3, entity.getFloorNumber());
        preparedStatement.setInt(4, entity.getBedsCount());
        preparedStatement.setString(5, entity.getSpecifications());
    }
}
