package ru.vsu.amm.java.mapper;

import ru.vsu.amm.java.entities.Session;
import ru.vsu.amm.java.entities.User;
import ru.vsu.amm.java.entities.Vehicle;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class SessionMapper implements EntityMapper<Session> {

    public SessionMapper() {}

    @Override
    public Session mapRowToObject(ResultSet rs) throws SQLException {

        Session session = new Session();

        User user = new User();
        user.setUserId(rs.getInt("Id_user"));
        session.setUser(user);

        Vehicle vehicle = new Vehicle();
        vehicle.setVehicleId(rs.getInt("Id_vehicle"));
        session.setVehicle(vehicle);

        session.setSessionId(rs.getInt("Id_session"));
        session.setParkingPrice(rs.getBigDecimal("ParkingPrice"));
        session.setEntryDate(rs.getTimestamp("EntryDate").toLocalDateTime());

        Timestamp exitDateTimestamp = rs.getTimestamp("ExitDate");
        LocalDateTime exitDate = (exitDateTimestamp == null) ? null : exitDateTimestamp.toLocalDateTime();
        session.setExitDate(exitDate);

        return session;
    }

    @Override
    public PreparedStatement mapObjectToRow(Session entity,
                                            Connection connection,
                                            String sql) throws SQLException {

        PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

        stmt.setInt(1, entity.getUser().getUserId());
        stmt.setInt(2, entity.getVehicle().getVehicleId());
        stmt.setBigDecimal(3, entity.getParkingPrice());
        stmt.setTimestamp(4, Timestamp.valueOf(entity.getEntryDate()));

        LocalDateTime exitDate = entity.getExitDate();
        stmt.setTimestamp(5, exitDate == null ? null : Timestamp.valueOf(entity.getExitDate()));

        return stmt;
    }
}
