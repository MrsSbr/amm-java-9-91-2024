package ru.vsu.amm.java.Services;

import ru.vsu.amm.java.Dtos.TourDto;
import ru.vsu.amm.java.Dtos.UserDto;
import ru.vsu.amm.java.Entities.Booking;
import ru.vsu.amm.java.Entities.Tour;
import ru.vsu.amm.java.Enums.Language;
import ru.vsu.amm.java.Enums.Role;
import ru.vsu.amm.java.Exceptions.AuthorizationException;
import ru.vsu.amm.java.Exceptions.DatabaseException;
import ru.vsu.amm.java.Exceptions.InvalidTourDataException;
import ru.vsu.amm.java.Exceptions.TourNotFoundException;
import ru.vsu.amm.java.Mappers.TourDtoMapper;
import ru.vsu.amm.java.Repository.BookingRepository;
import ru.vsu.amm.java.Repository.TourRepository;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;

public class ToursService {
    private static final Logger log = Logger.getLogger(ToursService.class.getName());

    private final TourRepository tourRepository;
    private final BookingRepository bookingRepository;

    public ToursService() {
        tourRepository = new TourRepository();
        bookingRepository = new BookingRepository();
    }

    public List<TourDto> getAllTours() {
        try {
            var tours = tourRepository.findAll();
            return tours.stream().map(TourDtoMapper::mapFromEntity).toList();
        } catch (SQLException e) {
            log.severe(e.getMessage());
            throw new DatabaseException(e.getMessage());
        }
    }

    public List<Integer> getAllToursBookedByUser(int userId) {
        try {
            var bookings = bookingRepository.findAllByUserId(userId);
            return bookings.stream().map(Booking::getIdTour).toList();
        } catch (SQLException e) {
            log.severe(e.getMessage());
            throw new DatabaseException(e.getMessage());
        }
    }

    public void createTour(UserDto guide, String title, String description, String durationStr,
                           String priceStr, String maxParticipantsStr, String startLocation, String languagesName) {
        if (guide.userRole() != Role.GUIDE) {
            log.info("Только GUIDE может создавать туры");
            throw new AuthorizationException("Только GUIDE может создавать туры");
        }

        try {
            var idGuide = guide.id();

            if (title == null || title.isBlank()) {
                log.info("Title не может быть пустым или null!");
                throw new InvalidTourDataException("Title не может быть пустым!");
            }

            if (description == null) {
                log.info("Description не может быть null!");
                throw new InvalidTourDataException("Description не может быть null!");
            }

            var duration = Float.parseFloat(durationStr);
            if (duration <= 0) {
                log.info("Продолжительность должна быть больше 0");
                throw new InvalidTourDataException("Продолжительность должна быть больше 0");
            }

            var price = Integer.parseInt(priceStr);
            if (price <= 0) {
                log.info("Стоимость должна быть больше 0");
                throw new InvalidTourDataException("Стоимость должна быть больше 0");
            }

            var maxParticipants = Integer.parseInt(maxParticipantsStr);
            if (maxParticipants <= 0) {
                log.info("Максимальное количество участников должна быть больше 0");
                throw new InvalidTourDataException("Максимальное количество участников должна быть больше 0");
            }

            if (startLocation == null || startLocation.isBlank()) {
                log.info("Старовая токчка не может быть пустой или null!");
                throw new InvalidTourDataException("Старовая токчка не может быть пустой или null!");
            }

            var languages = Language.valueOf(languagesName);

            var newTour = new Tour(0, idGuide, title, description, duration, price, maxParticipants, startLocation, languages);
            tourRepository.save(newTour);

        } catch (SQLException e) {
            log.severe(e.getMessage());
            throw new DatabaseException(e.getMessage());
        } catch (IllegalArgumentException | NullPointerException e) {
            log.info(e.getMessage());
            throw new InvalidTourDataException(e.getMessage());
        }
    }

    public void deleteTour(UserDto guide, String tourIdStr) {
        if (guide.userRole() != Role.GUIDE) {
            log.info("Только GUIDE может создавать туры");
            throw new AuthorizationException("Только GUIDE может создавать туры");
        }

        try {
            var tourId = Integer.parseInt(tourIdStr);

            var tourOpt = tourRepository.findById(tourId);
            if (tourOpt.isEmpty()) {
                log.info("Тура " + tourId + " не существует");
                throw new TourNotFoundException("Тура " + tourId + " не существует");
            }

            tourRepository.delete(tourOpt.get());
        } catch (SQLException e) {
            log.severe(e.getMessage());
            throw new DatabaseException(e.getMessage());
        } catch (IllegalArgumentException | NullPointerException e) {
            log.info(e.getMessage());
            throw new InvalidTourDataException(e.getMessage());
        }
    }
}
