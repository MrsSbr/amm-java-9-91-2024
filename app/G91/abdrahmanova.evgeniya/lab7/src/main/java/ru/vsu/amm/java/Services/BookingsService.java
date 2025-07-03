package ru.vsu.amm.java.Services;

import ru.vsu.amm.java.Dtos.BookingDto;
import ru.vsu.amm.java.Dtos.UserDto;
import ru.vsu.amm.java.Entities.Booking;
import ru.vsu.amm.java.Enums.Role;
import ru.vsu.amm.java.Enums.Status;
import ru.vsu.amm.java.Exceptions.*;
import ru.vsu.amm.java.Mappers.BookingDtoMapper;
import ru.vsu.amm.java.Repository.BookingRepository;
import ru.vsu.amm.java.Repository.TourRepository;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.logging.Logger;

public class BookingsService {
    private static final Logger log = Logger.getLogger(BookingsService.class.getName());

    private final BookingRepository bookingRepository;
    private final TourRepository tourRepository;

    public BookingsService() {
        bookingRepository = new BookingRepository();
        tourRepository = new TourRepository();
    }

    public List<BookingDto> getUserBookings(int userId) {
        try {
            var bookings = bookingRepository.findAllByUserId(userId);
            return bookings.stream().map(BookingDtoMapper::mapFromEntity).toList();
        } catch (SQLException e) {
            log.severe(e.getMessage());
            throw new DatabaseException(e.getMessage());
        }
    }

    public void createBooking(UserDto user, String tourIdStr, String dateStr, String participantsStr) {
        if (user.userRole() != Role.USER) {
            log.info("Только USER может бронировать туры");
            throw new AuthorizationException("Только USER может создавать туры");
        }

        try {
            var tourId = Integer.parseInt(tourIdStr);

            var participants = Integer.parseInt(participantsStr);
            if (participants <= 0) {
                log.info("Количество участников должно быть больше 0");
                throw new InvalidBookingDataException("Количество участников должно быть больше 0");
            }

            var date = LocalDate.parse(dateStr, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            if (date.isBefore(LocalDate.now())) {
                log.info("Дата брони должна быть в будущем");
                throw new InvalidBookingDataException("Дата брони должна быть в будущем");
            }

            var tourOpt = tourRepository.findById(tourId);
            if (tourOpt.isEmpty()) {
                log.info("Тура " + tourId + " не существует");
                throw new TourNotFoundException("Тура " + tourId + " не существует");
            }
            var tour = tourOpt.get();

            if (participants > tour.getMaxParticipants()) {
                log.info("Количество участников должно быть меньше максимального количества участников тура");
                throw new InvalidBookingDataException("Количество участников должно быть меньше максимального количества участников тура");
            }

            var booking = new Booking(0, tour.getId(), user.id(), date, participants, participants * tour.getPrice(), Status.CONFIRMED);
            bookingRepository.save(booking);

        } catch (SQLException e) {
            log.severe(e.getMessage());
            throw new DatabaseException(e.getMessage());
        } catch (IllegalArgumentException | NullPointerException | DateTimeParseException e) {
            log.info(e.getMessage());
            throw new InvalidBookingDataException(e.getMessage());
        }
    }

    public void cancelBooking(UserDto user, String bookingIdStr) {

    }
}

