package ru.vsu.amm.java.Mapper.Impl;

import ru.vsu.amm.java.Mapper.Interface.Mapper;
import ru.vsu.amm.java.Model.Entity.TicketEntity;
import ru.vsu.amm.java.Model.Enum.TicketStatus;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;


public class TicketMapper implements Mapper<TicketEntity> {

    @Override
    public TicketEntity resultSetToEntity(ResultSet rs) throws SQLException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        // Parse as LocalDateTime first
        LocalDateTime startLocal = LocalDateTime.parse(rs.getString("start_time"), formatter);
        LocalDateTime endLocal = LocalDateTime.parse(rs.getString("end_time"), formatter);

        // Convert to OffsetDateTime (using UTC as default)
        OffsetDateTime startTime = startLocal.atOffset(ZoneOffset.UTC);
        OffsetDateTime endTime = endLocal.atOffset(ZoneOffset.UTC);

        return new TicketEntity(
                rs.getLong("ticket_id"),
                TicketStatus.valueOf(rs.getString("status")),
                rs.getInt("hall_num"),
                rs.getInt("place_num"),
                startTime,
                endTime,
                rs.getLong("film_id"),
                rs.getLong("user_id"),
                rs.getString("film_name")
        );
    }
}
