package ru.vsu.amm.java.services.bookings;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import ru.vsu.amm.java.IntegrationTest;
import ru.vsu.amm.java.entities.BookingEntity;
import ru.vsu.amm.java.entities.ClientEntity;
import ru.vsu.amm.java.models.dto.BookingDto;
import ru.vsu.amm.java.repositories.impl.BookingRepository;
import ru.vsu.amm.java.repositories.impl.ClientRepository;
import ru.vsu.amm.java.services.bookings.impl.BookingServiceImpl;
import ru.vsu.amm.java.utils.SeatsGenerator;
import ru.vsu.amm.java.utils.SetupHelper;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BookingServiceIntegrationTest extends IntegrationTest {

    private final BookingService service = new BookingServiceImpl();
    private final BookingRepository bookingRepository = new BookingRepository();
    private final ClientRepository clientRepository = new ClientRepository();

    @Test
    void getBookingsByEmailWhenClientNotExists() throws SQLException {
        List<BookingDto> bookings = service.getBookingsByEmail("nonexistent@example.com");
        assertTrue(bookings.isEmpty());
    }

    @Test
    void getAvailableSeatsByFlightIdWithoutBookings() throws SQLException {
        Long flightId = 100L;
        List<String> available = service.getAvailableSeatsByFlightId(flightId);
        List<String> expected = SeatsGenerator.generateSeats(10, 10);
        assertEquals(expected, available);
    }

    @Test
    void getAvailableSeatsByFlightIdWithSomeBookings() throws SQLException {
        Long flightId = 200L;
        ClientEntity client = SetupHelper.getClientEntity();
        clientRepository.save(client);
        BookingEntity booked = SetupHelper.getBookingEntity();
        booked.setBookingId(100L);
        booked.setFlightId(flightId);
        booked.setClientId(client.getClientId());
        booked.setSeatNumber("A1");
        bookingRepository.save(booked);

        List<String> available = service.getAvailableSeatsByFlightId(flightId);
        List<String> allSeats = SeatsGenerator.generateSeats(10, 10);

        assertFalse(available.contains("A1"));

        allSeats.stream().filter(s -> !"A1".equals(s)).forEach(s -> assertTrue(available.contains(s)));
    }
}
