package ru.vsu.amm.java.repository;

import ru.vsu.amm.java.configuration.DatabaseConfiguration;
import ru.vsu.amm.java.entities.Session;
import ru.vsu.amm.java.mappers.SessionMapper;

import javax.sql.DataSource;
import java.sql.*;
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

        String sql = "SELECT Id_session, Id_user, Id_vehicle, ParkingPrice, EntryDate, ExitDate FROM Session WHERE Id_session = ?";

        Connection connection = dataSource.getConnection();

        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setInt(1, id);

        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            return Optional.of(SessionMapper.mapRowToObject(rs));
        }

        return Optional.empty();
    }

    @Override
    public List<Session> getAll() throws SQLException {

        String sql = "SELECT Id_session, Id_user, Id_vehicle, ParkingPrice, EntryDate, ExitDate FROM Session";

        Connection connection = dataSource.getConnection();
        PreparedStatement stmt = connection.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();

        List<Session> sessions = new ArrayList<>();

        while (rs.next()) {
            sessions.add(SessionMapper.mapRowToObject(rs));
        }

        return sessions;
    }

    @Override
    public void save(Session entity) throws SQLException {

        String sql = "INSERT INTO Session (Id_user, Id_vehicle, ParkingPrice, EntryDate, ExitDate) VALUES (?, ?, ?, ?, ?)";

        Connection connection = dataSource.getConnection();
        PreparedStatement stmt = connection.prepareStatement(sql);

        SessionMapper.mapObjectToRow(entity, stmt);
        stmt.execute();
    }
}
