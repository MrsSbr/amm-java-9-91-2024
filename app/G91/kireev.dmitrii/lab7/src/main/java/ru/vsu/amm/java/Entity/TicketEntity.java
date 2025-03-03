package ru.vsu.amm.java.Entity;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class TicketEntity {


    private long ticketId;
    private String status;
    private int placeNumber;
    private Timestamp startTime;
    private Timestamp endTime;

    private long userId;
    private long filmId;

    public TicketEntity(String status, int placeNumber, Timestamp startTime, Timestamp endTime, long userId, long filmId) {
        this.status = status;
        this.placeNumber = placeNumber;
        this.startTime = startTime;
        this.endTime = endTime;
        this.userId = userId;
        this.filmId = filmId;
    }



}
