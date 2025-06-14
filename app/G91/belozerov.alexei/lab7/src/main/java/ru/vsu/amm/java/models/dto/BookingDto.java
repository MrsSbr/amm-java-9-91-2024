package ru.vsu.amm.java.models.dto;

public record BookingDto(
        Long bookingId,
        String ticketNumber,
        String seatNumber,
        Long flightId,
        Long clientId
) {

    public BookingDto(
            String ticketNumber,
            String seatNumber,
            Long flightId,
            Long clientId
    ) {
        this(null, ticketNumber, seatNumber, flightId, clientId);
    }
}
