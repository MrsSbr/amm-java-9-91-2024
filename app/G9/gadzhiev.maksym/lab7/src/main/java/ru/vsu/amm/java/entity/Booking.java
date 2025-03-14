package ru.vsu.amm.java.entity;

import java.time.LocalDateTime;

public class Booking {
    private Long id;
    private LocalDateTime checkInDate;
    private LocalDateTime checkOutDate;
    private Long price;
    private boolean status;
    private Long userId;
    private Long realEstateId;
}
