package ru.vsu.amm.java.services.bookings.impl;

import lombok.extern.slf4j.Slf4j;
import ru.vsu.amm.java.converters.BookingConverter;
import ru.vsu.amm.java.entities.BookingEntity;
import ru.vsu.amm.java.entities.ClientEntity;
import ru.vsu.amm.java.exceptions.DataAccessException;
import ru.vsu.amm.java.models.dto.BookingDto;
import ru.vsu.amm.java.repositories.impl.BookingRepository;
import ru.vsu.amm.java.repositories.impl.ClientRepository;
import ru.vsu.amm.java.services.bookings.BookingService;
import ru.vsu.amm.java.utils.SeatsGenerator;

import java.sql.SQLException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Slf4j
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final ClientRepository clientRepository;

    private static final List<String> seats = SeatsGenerator.generateSeats(10, 10);

    public BookingServiceImpl() {
        this.bookingRepository = new BookingRepository();
        this.clientRepository = new ClientRepository();
    }

    @Override
    public List<BookingDto> getBookingsByEmail(String email) {
        try {
            Optional<ClientEntity> clientOpt = clientRepository.findByEmail(email);
            if (clientOpt.isPresent()) {
                Long clientId = clientOpt.get().getClientId();
                return bookingRepository.findAllByClientIdAndDateGreaterThanCurrent(clientId).stream()
                        .sorted(Comparator.comparingLong(BookingEntity::getBookingId))
                        .map(BookingConverter::toDto)
                        .toList();
            } else {
                return Collections.emptyList();
            }
        } catch (SQLException e) {
            log.error("Error fetching bookings for client email={}", email, e);
            throw new DataAccessException("Failed to fetch bookings by client email", e);
        }
    }

    @Override
    public List<String> getAvailableSeatsByFlightId(Long flightId) {
        try {
            List<String> bookedSeats = bookingRepository.findAllByFlightIdAndDateGreaterThanCurrent(flightId);
            return seats.stream()
                    .filter(x -> !bookedSeats.contains(x))
                    .toList();
        } catch (SQLException e) {
            log.error("Error fetching bookings for flight id={}", flightId, e);
            throw new DataAccessException("Failed to fetch bookings by flight id", e);
        }
    }

    @Override
    public void bookSeat(BookingDto bookingDto) {
        try {
            bookingRepository.save(BookingConverter.toEntity(bookingDto));
        } catch (SQLException e) {
            log.error("Error booking: {}", bookingDto, e);
            throw new DataAccessException("Failed to book seat", e);
        }
    }
}
