package ru.vsu.amm.java.converters;

import ru.vsu.amm.java.entities.BookingEntity;
import ru.vsu.amm.java.models.dto.BookingDto;

public class BookingConverter {

    public static BookingDto toDto(BookingEntity bookingEntity) {
        return new BookingDto(
                bookingEntity.getBookingId(),
                bookingEntity.getTicketNumber(),
                bookingEntity.getSeatNumber(),
                bookingEntity.getFlightId(),
                bookingEntity.getClientId()
        );
    }

    public static BookingEntity toEntity(BookingDto bookingDto) {
        return new BookingEntity(
                bookingDto.clientId(),
                bookingDto.flightId(),
                bookingDto.seatNumber(),
                bookingDto.ticketNumber()
        );
    }
}
