package ru.vsu.amm.java.services.bookings;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import ru.vsu.amm.java.converters.BookingConverter;
import ru.vsu.amm.java.entities.BookingEntity;
import ru.vsu.amm.java.entities.ClientEntity;
import ru.vsu.amm.java.exceptions.DataAccessException;
import ru.vsu.amm.java.models.dto.BookingDto;
import ru.vsu.amm.java.repositories.impl.BookingRepository;
import ru.vsu.amm.java.repositories.impl.ClientRepository;
import ru.vsu.amm.java.services.bookings.impl.BookingServiceImpl;
import ru.vsu.amm.java.utils.SeatsGenerator;
import ru.vsu.amm.java.utils.SetupHelper;
import ru.vsu.amm.java.utils.TestApplicationConstants;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

public class BookingServiceTest {

    private final BookingRepository bookingRepository = mock(BookingRepository.class);
    private final ClientRepository clientRepository = mock(ClientRepository.class);
    private final BookingServiceImpl service = new BookingServiceImpl(bookingRepository, clientRepository);

    @Test
    void getBookingsByEmail_clientNotFound_returnsEmptyList() throws SQLException {
        when(clientRepository.findByEmail("no@mail")).thenReturn(Optional.empty());

        List<BookingDto> result = service.getBookingsByEmail("no@mail");
        assertTrue(result.isEmpty());

        verify(clientRepository).findByEmail("no@mail");
        verifyNoMoreInteractions(bookingRepository);
    }

    @Test
    void getBookingsByEmail_success_returnsSortedDtos() throws SQLException {
        ClientEntity client = SetupHelper.getClientEntity();
        client.setClientId(42L);
        when(clientRepository.findByEmail("e@mail")).thenReturn(Optional.of(client));

        BookingEntity b1 = SetupHelper.getBookingEntity();
        b1.setBookingId(2L);
        BookingEntity b2 = SetupHelper.getBookingEntity();
        b2.setBookingId(1L);
        when(bookingRepository.findAllByClientIdAndDateGreaterThanCurrent(42L)).thenReturn(List.of(b1, b2));

        BookingDto dto1 = new BookingDto(
                TestApplicationConstants.BOOKING_ID,
                TestApplicationConstants.TICKET_NUMBER,
                TestApplicationConstants.SEAT_NUMBER,
                TestApplicationConstants.B_FLIGHT_ID,
                TestApplicationConstants.B_CLIENT_ID
        );
        BookingDto dto2 = new BookingDto(
                2L,
                TestApplicationConstants.TICKET_NUMBER,
                TestApplicationConstants.SEAT_NUMBER,
                TestApplicationConstants.B_FLIGHT_ID,
                TestApplicationConstants.B_CLIENT_ID
        );

        try (MockedStatic<BookingConverter> conv = Mockito.mockStatic(BookingConverter.class)) {
            conv.when(() -> BookingConverter.toDto(b1)).thenReturn(dto1);
            conv.when(() -> BookingConverter.toDto(b2)).thenReturn(dto2);

            List<BookingDto> out = service.getBookingsByEmail("e@mail");
            assertEquals(2, out.size());
            assertEquals(2L, out.get(0).bookingId());
            assertEquals(1L, out.get(1).bookingId());

            conv.verify(() -> BookingConverter.toDto(b1), times(1));
            conv.verify(() -> BookingConverter.toDto(b2), times(1));
        }
    }

    @Test
    void getBookingsByEmail_throwsDataAccessException_onSQLException() throws SQLException {
        when(clientRepository.findByEmail("err@mail"))
                .thenThrow(new SQLException("DB fail"));

        DataAccessException ex = assertThrows(
                DataAccessException.class,
                () -> service.getBookingsByEmail("err@mail")
        );
        assertEquals("Failed to fetch bookings by client email", ex.getMessage());
    }

    @Test
    void getAvailableSeatsByFlightId_noBookings_returnsAllSeats() throws SQLException {
        Long flightId = 10L;
        when(bookingRepository.findAllByFlightIdAndDateGreaterThanCurrent(flightId)).thenReturn(List.of());

        List<String> available = service.getAvailableSeatsByFlightId(flightId);
        List<String> expected = SeatsGenerator.generateSeats(10, 10);

        assertEquals(expected, available);
    }

    @Test
    void getAvailableSeatsByFlightId_filtersOutBookedSeats() throws SQLException {
        Long flightId = 20L;
        when(bookingRepository.findAllByFlightIdAndDateGreaterThanCurrent(flightId))
                .thenReturn(List.of("A1", "B2"));

        List<String> available = service.getAvailableSeatsByFlightId(flightId);
        assertFalse(available.contains("A1"));
        assertFalse(available.contains("B2"));

        SeatsGenerator.generateSeats(10, 10).stream()
                .filter(s -> !s.equals("A1") && !s.equals("B2"))
                .forEach(s -> assertTrue(available.contains(s)));
    }

    @Test
    void getAvailableSeatsByFlightId_throwsDataAccessException_onSQLException() throws SQLException {
        when(bookingRepository.findAllByFlightIdAndDateGreaterThanCurrent(30L))
                .thenThrow(new SQLException("DB err"));

        assertThrows(
                DataAccessException.class,
                () -> service.getAvailableSeatsByFlightId(30L)
        );
    }

    @Test
    void bookSeat_success_savesEntity() throws SQLException {
        BookingDto dto = new BookingDto(
                TestApplicationConstants.BOOKING_ID,
                TestApplicationConstants.TICKET_NUMBER,
                TestApplicationConstants.SEAT_NUMBER,
                TestApplicationConstants.B_FLIGHT_ID,
                TestApplicationConstants.B_CLIENT_ID
        );
        BookingEntity ent = SetupHelper.getBookingEntity();
        try (MockedStatic<BookingConverter> conv = Mockito.mockStatic(BookingConverter.class)) {
            conv.when(() -> BookingConverter.toEntity(dto)).thenReturn(ent);

            assertDoesNotThrow(() -> service.bookSeat(dto));
            verify(bookingRepository).save(ent);
        }
    }

    @Test
    void bookSeat_throwsDataAccessException_onSQLException() throws SQLException {
        BookingDto dto = new BookingDto(
                TestApplicationConstants.BOOKING_ID,
                TestApplicationConstants.TICKET_NUMBER,
                TestApplicationConstants.SEAT_NUMBER,
                TestApplicationConstants.B_FLIGHT_ID,
                TestApplicationConstants.B_CLIENT_ID
        );
        BookingEntity ent = SetupHelper.getBookingEntity();
        try (MockedStatic<BookingConverter> conv = Mockito.mockStatic(BookingConverter.class)) {
            conv.when(() -> BookingConverter.toEntity(dto)).thenReturn(ent);
            doThrow(new SQLException("fail")).when(bookingRepository).save(ent);
            assertThrows(DataAccessException.class, () -> service.bookSeat(dto));
        }
    }
}
