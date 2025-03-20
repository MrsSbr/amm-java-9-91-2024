package ru.vsu.amm.java.repository;

import ru.vsu.amm.java.configuration.DatabaseConfiguration;
import ru.vsu.amm.java.entities.Session;
import ru.vsu.amm.java.mapper.SessionMapper;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SessionRepository implements ParkingRepository<Session> {

    private final DataSource dataSource;

    public SessionRepository() {
        dataSource = DatabaseConfiguration.getDataSource();
    }

    @Override
    public Optional<Session> getById(int id) throws SQLException {

        String sql = """
                SELECT Id_session, Id_user, Id_vehicle, ParkingPrice, EntryDate, ExitDate
                FROM "Session"
                WHERE Id_session = ?
                """;

        try (Connection connection = dataSource.getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, id);

            ResultSet rs = stmt.executeQuery();

            SessionMapper sessionMapper = new SessionMapper();

            if (rs.next()) {
                return Optional.of(sessionMapper.mapRowToObject(rs));
            }

            return Optional.empty();

        } catch (SQLException e) {

            throw new SQLException(e.getMessage());

        }
    }

    @Override
    public List<Session> getAll() throws SQLException {

        String sql = """
                SELECT Id_session, Id_user, Id_vehicle, ParkingPrice, EntryDate, ExitDate
                FROM "Session"
                """;

        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            ResultSet rs = stmt.executeQuery();

            List<Session> sessions = new ArrayList<>();

            SessionMapper sessionMapper = new SessionMapper();

            while (rs.next()) {
                sessions.add(sessionMapper.mapRowToObject(rs));
            }

            return sessions;

        } catch (SQLException e) {

            throw new SQLException(e.getMessage());

        }
    }

    @Override
    public void save(Session entity) throws SQLException {

        String sql = """
               INSERT INTO "Session" (Id_user, Id_vehicle, ParkingPrice, EntryDate, ExitDate)
               VALUES (?, ?, ?, ?, ?)
               """;

        try (Connection connection = dataSource.getConnection()) {

            SessionMapper sessionMapper = new SessionMapper();
            PreparedStatement stmt = sessionMapper.mapObjectToRow(entity, connection, sql);
            stmt.execute();

        } catch (SQLException e) {

            throw new SQLException(e.getMessage());

        }
    }

    @Override
    public void update(Session entity) throws SQLException {

        String sql = """
                UPDATE "Session" SET Id_user = ?, Id_vehicle = ?, ParkingPrice = ?, EntryDate = ?, ExitDate = ?
                WHERE Id_session = ?
                """;

        try (Connection connection = dataSource.getConnection()) {

            SessionMapper sessionMapper = new SessionMapper();
            PreparedStatement stmt = sessionMapper.mapObjectToRow(entity, connection, sql);

            stmt.setInt(6, entity.getSessionId());
            stmt.execute();

        } catch (SQLException e) {

            throw new SQLException(e.getMessage());

        }
    }

    @Override
    public void delete(Session entity) throws SQLException {

        String sql = """
                DELETE FROM "Session"
                WHERE Id_session = ?
                """;

        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, entity.getSessionId());
            stmt.execute();

        } catch (SQLException e) {

            throw new SQLException(e.getMessage());

        }
    }
}
