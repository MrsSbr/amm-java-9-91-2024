package ru.vsu.amm.java.Entity;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class TicketEntity {


    private long ticketId;
    private String status;
    private int hallNumber;
    private int placeNumber;
    private Timestamp startTime;
    private Timestamp endTime;

    private long userId;
    private long filmId;

    public TicketEntity(String status,int hallNumber, int placeNumber, Timestamp startTime, Timestamp endTime, long userId, long filmId) {
        this.status = status;
        this.hallNumber = hallNumber;
        this.placeNumber = placeNumber;
        this.startTime = startTime;
        this.endTime = endTime;
        this.userId = userId;
        this.filmId = filmId;
    }



}
