package ru.vsu.amm.java.entities;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BookingEntity {
    private Long bookingId;
    private Long clientId;
    private Long flightId;
    private String seatNumber;
    private String ticketNumber;
}
