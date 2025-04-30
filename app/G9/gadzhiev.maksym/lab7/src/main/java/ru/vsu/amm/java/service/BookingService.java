package ru.vsu.amm.java.service;

import ru.vsu.amm.java.Exceptions.BookingException;
import ru.vsu.amm.java.entity.Booking;
import ru.vsu.amm.java.entity.RealEstate;
import ru.vsu.amm.java.entity.UserEntity;
import ru.vsu.amm.java.repository.BookingRepository;
import ru.vsu.amm.java.repository.RealEstateRepository;
import ru.vsu.amm.java.repository.UserEntityRepository;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Optional;

public class BookingService {
    private final BookingRepository bookingRepository;
    private final RealEstateRepository realEstateRepository;
    private final UserEntityRepository userEntityRepository;
    public BookingService() {
        this.bookingRepository = new BookingRepository();
        this.userEntityRepository = new UserEntityRepository();
        this.realEstateRepository = new RealEstateRepository();
    }

    public void createBooking(String login, Long estateId, LocalDate checkIn,
                              LocalDate checkOut, int guests) {
        try {
            Optional<UserEntity> userEntityOptional = userEntityRepository.findByLogin(login);
            Optional<RealEstate> realEstateOptional = realEstateRepository.findById(estateId);
            RealEstate realEstate = realEstateOptional.get();
            UserEntity user = userEntityOptional.get();
            if(guests < 1 || guests > realEstate.getMaximumNumberOfGuests()) {
                throw new BookingException("wrong number of people");
            }
            if(checkIn.isBefore(LocalDate.now())) {
                throw new BookingException("Incorrect check-in date");
            }
            if (checkOut.isBefore(checkIn)) {
                throw new BookingException("The check-out date must be after the check-in date");

            }
            if (bookingRepository.checkBusy(estateId, checkIn, checkOut)) {
                throw new BookingException("These dates are already taken");
            }
            Booking booking = new Booking(checkIn, checkOut, "Забронировано", user.getId(), estateId);
            bookingRepository.save(booking);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}
