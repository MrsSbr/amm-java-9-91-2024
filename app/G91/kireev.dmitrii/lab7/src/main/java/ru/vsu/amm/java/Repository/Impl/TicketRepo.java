package ru.vsu.amm.java.Repository.Impl;

import lombok.AllArgsConstructor;
import ru.vsu.amm.java.Mapper.Impl.TicketMapper;
import ru.vsu.amm.java.Model.Entity.TicketEntity;
import ru.vsu.amm.java.Repository.Interface.CrudRepo;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class TicketRepo implements CrudRepo<TicketEntity> {

    private final DataSource dataSource;
    private final TicketMapper ticketMapper;

    @Override
    public Optional<TicketEntity> findById(Long id) throws SQLException {
        final String query = "SELECT ticketId, status, hallNumber, placeNumber, startTime, endTime, userId, filmId FROM Ticket WHERE ticketId = ?";
        try (Connection connection = dataSource.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(ticketMapper.resultSetToEntity(resultSet));
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public List<TicketEntity> findAll() throws SQLException {
        final String query = "SELECT ticketId, status, hallNumber, placeNumber, startTime, endTime, userId, filmId FROM Ticket";
        try (Connection connection = dataSource.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                List<TicketEntity> tickets = new ArrayList<>();
                while (resultSet.next()) {
                    tickets.add(ticketMapper.resultSetToEntity(resultSet));
                }
                return tickets;
            }
        }
    }

    @Override
    public void save(TicketEntity entity) throws SQLException {
        final String query = "INSERT INTO Ticket(status, hallNumber, placeNumber, startTime, endTime, userId, filmId) VALUES(?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = dataSource.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, entity.getStatus().name());
            preparedStatement.setInt(2, entity.getHallNumber());
            preparedStatement.setInt(3, entity.getPlaceNumber());
            preparedStatement.setObject(4, entity.getStartTime());
            preparedStatement.setObject(5, entity.getEndTime());
            preparedStatement.setLong(6, entity.getUserId());
            preparedStatement.setLong(7, entity.getFilmId());
            preparedStatement.executeUpdate();

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    entity.setTicketId(generatedKeys.getLong(1));
                }
            }
        }
    }

    @Override
    public void update(TicketEntity entity) throws SQLException {
        final String query = "UPDATE Ticket SET status = ?, hallNumber = ?, placeNumber = ?, startTime = ?, endTime = ?, userId = ?, filmId = ? WHERE ticketId = ?";
        try (Connection connection = dataSource.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, entity.getStatus().name());
            preparedStatement.setInt(2, entity.getHallNumber());
            preparedStatement.setInt(3, entity.getPlaceNumber());
            preparedStatement.setObject(4, entity.getStartTime());
            preparedStatement.setObject(5, entity.getEndTime());
            preparedStatement.setLong(6, entity.getUserId());
            preparedStatement.setLong(7, entity.getFilmId());
            preparedStatement.setLong(8, entity.getTicketId());
            preparedStatement.executeUpdate();
        }
    }

    @Override
    public void delete(Long id) throws SQLException {
        final String query = "DELETE FROM Ticket WHERE ticketId = ?";
        try (Connection connection = dataSource.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        }
    }
}
