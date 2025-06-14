package ru.vsu.amm.java.services.bookings;

import ru.vsu.amm.java.models.dto.BookingDto;

import java.util.List;

public interface BookingService {

    List<BookingDto> getBookingsByEmail(String email);

    List<String> getAvailableSeatsByFlightId(Long flightId);

    void bookSeat(BookingDto bookingDto);
}
