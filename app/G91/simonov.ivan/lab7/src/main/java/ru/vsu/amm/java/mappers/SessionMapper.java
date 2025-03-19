package ru.vsu.amm.java.mappers;

import ru.vsu.amm.java.entities.Session;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SessionMapper {

    public static Session mapRowToObject(ResultSet rs) throws SQLException {

        Session session = new Session();

        session.setSessionId(rs.getInt("Id_session"));
        session.setUserId(rs.getInt("Id_user"));
        session.setVehicleId(rs.getInt("Id_vehicle"));
        session.setParkingPrice(rs.getBigDecimal("ParkingPrice"));
        session.setEntryDate(rs.("EntryDate"));
        session.setExitDate(rs.getString("ExitDate"));

        return session;
    }

}
