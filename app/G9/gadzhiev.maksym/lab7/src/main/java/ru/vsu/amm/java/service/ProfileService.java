package ru.vsu.amm.java.service;

import ru.vsu.amm.java.entity.Booking;
import ru.vsu.amm.java.repository.BookingRepository;
import ru.vsu.amm.java.repository.UserEntityRepository;

import java.sql.SQLException;
import java.util.List;

public class ProfileService {
    private final BookingRepository bookingRepository;
    private final UserEntityRepository userEntityRepository;

    public ProfileService() {
        this.bookingRepository = new BookingRepository();
        this.userEntityRepository = new UserEntityRepository();
    }
    public List<Booking> getUserBookings(String userLogin) throws SQLException {
        Long userId = userEntityRepository.findByLogin(userLogin).orElseThrow(
                () -> new SQLException("User not found")
        ).getId();
        return bookingRepository.findByUserId(userId);
    }

    public void cancelBooking(Long bookingId) throws SQLException {
        bookingRepository.updateStatus(bookingId, "отменено");
    }
}

