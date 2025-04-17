package ru.vsu.amm.java.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Booking {
    private Long id;
    private LocalDateTime checkInDate;
    private LocalDateTime checkOutDate;
    private Long price;
    private String status;
    private Long userId;
    private Long realEstateId;

    public Booking(long id, LocalDate checkInDate, LocalDate checkOutDate, long price,
                   String status, long userId, long realEstateId) {
        this.id = id;
        this.checkInDate = LocalDateTime.from(checkInDate);
        this.checkOutDate = LocalDateTime.from(checkOutDate);
        this.price = price;
        this.status = status;
        this.userId = userId;
        this.realEstateId = realEstateId;

    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getCheckInDate() {
        return checkInDate;
    }

    public LocalDateTime getCheckOutDate() {
        return checkOutDate;
    }

    public Long getPrice() {
        return price;
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
