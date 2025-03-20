package ru.vsu.amm.java.mappers;

import ru.vsu.amm.java.entities.Session;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class SessionMapper implements EntityMapper<Session> {

    private Session entity;

    public SessionMapper() {}

    public SessionMapper(Session session) {
        this.entity = session;
    }

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
    public void mapObjectToRow(PreparedStatement stmt) throws SQLException {

        stmt.setInt(1, entity.getUserId());
        stmt.setInt(2, entity.getVehicleId());
        stmt.setBigDecimal(3, entity.getParkingPrice());
        stmt.setTimestamp(4, Timestamp.valueOf(entity.getEntryDate()));
        stmt.setTimestamp(5, Timestamp.valueOf(entity.getExitDate()));
    }
}
