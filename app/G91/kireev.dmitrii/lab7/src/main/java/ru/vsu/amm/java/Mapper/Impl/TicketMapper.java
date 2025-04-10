package ru.vsu.amm.java.Mapper.Impl;

import ru.vsu.amm.java.Mapper.Interface.Mapper;
import ru.vsu.amm.java.Model.Entity.TicketEntity;
import ru.vsu.amm.java.Model.Enum.TicketStatus;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.OffsetDateTime;

public class TicketMapper implements Mapper<TicketEntity> {

    @Override
    public TicketEntity resultSetToEntity(ResultSet rs) throws SQLException {
        return new TicketEntity(rs.getLong("ticketId"),
                TicketStatus.valueOf(rs.getString("status")),
                rs.getInt("hallNumber"),
                rs.getInt("placeNumber"),
                OffsetDateTime.parse(rs.getString(" startTime")),
                OffsetDateTime.parse(rs.getString(" endTime")),
                rs.getLong("userId"),
                rs.getLong("filmId"));

    }
}
