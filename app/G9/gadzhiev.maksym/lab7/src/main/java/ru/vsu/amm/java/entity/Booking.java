package ru.vsu.amm.java.entity;

import java.time.LocalDate;

public class Booking {
    private Long id;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private String status;
    private Long userId;
    private Long realEstateId;

    public Booking(long id, LocalDate checkInDate, LocalDate checkOutDate,
                   String status, long userId, long realEstateId) {
        this.id = id;
        this.checkInDate = LocalDate.from(checkInDate);
        this.checkOutDate = LocalDate.from(checkOutDate);
        this.status = status;
        this.userId = userId;
        this.realEstateId = realEstateId;

    }
    public Booking(LocalDate checkInDate, LocalDate checkOutDate,
                   String status, long userId, long realEstateId) {
        this.checkInDate = LocalDate.from(checkInDate);
        this.checkOutDate = LocalDate.from(checkOutDate);
        this.status = status;
        this.userId = userId;
        this.realEstateId = realEstateId;

    }
    public Long getId() {
        return id;
    }

    public LocalDate getCheckInDate() {
        return checkInDate;
    }

    public LocalDate getCheckOutDate() {
        return checkOutDate;
    }

    public String getStatus() {
        return status;
    }

    public Long getUserId() {
        return userId;
    }

    public Long getRealEstateId() {
        return realEstateId;
    }
}
