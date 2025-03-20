package ru.vsu.amm.java.mapper;

import ru.vsu.amm.java.entities.Session;
import ru.vsu.amm.java.entities.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class SessionMapper implements EntityMapper<Session> {

    public SessionMapper() {}

    @Override
    public Session mapRowToObject(ResultSet rs) throws SQLException {

        Session session = new Session();

        session.setSessionId(rs.getInt("Id_session"));
        session.setUserId(rs.getInt("Id_user"));
        session.setVehicleId(rs.getInt("Id_vehicle"));
        session.setParkingPrice(rs.getBigDecimal("ParkingPrice"));
        session.setEntryDate(rs.getTimestamp("EntryDate").toLocalDateTime());
        session.setExitDate(rs.getTimestamp("ExitDate").toLocalDateTime());

        return session;
    }

    @Override
    public PreparedStatement mapObjectToRow(Session entity,
                                            Connection connection,
                                            String sql) throws SQLException {

        PreparedStatement stmt = connection.prepareStatement(sql);

        stmt.setInt(1, entity.getUserId());
        stmt.setInt(2, entity.getVehicleId());
        stmt.setBigDecimal(3, entity.getParkingPrice());
        stmt.setTimestamp(4, Timestamp.valueOf(entity.getEntryDate()));
        stmt.setTimestamp(5, Timestamp.valueOf(entity.getExitDate()));

        return stmt;
    }
}
