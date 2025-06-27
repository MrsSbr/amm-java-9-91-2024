package ru.vsu.amm.java.services.flights;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import ru.vsu.amm.java.converters.FlightConverter;
import ru.vsu.amm.java.entities.FlightEntity;
import ru.vsu.amm.java.exceptions.DataAccessException;
import ru.vsu.amm.java.models.dto.FlightDto;
import ru.vsu.amm.java.repositories.impl.FlightRepository;
import ru.vsu.amm.java.services.flights.impl.FlightServiceImpl;
import ru.vsu.amm.java.utils.TestApplicationConstants;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

public class FlightServiceTest {

    private final FlightRepository repository = mock(FlightRepository.class);
    private final FlightService service = new FlightServiceImpl(repository);

    @Test
    void getAllFlightsSuccess() throws SQLException {
        FlightEntity flight1 = mock(FlightEntity.class);
        FlightEntity flight2 = mock(FlightEntity.class);
        when(repository.findAll()).thenReturn(List.of(flight1, flight2));

        FlightDto dto1 = new FlightDto(
                TestApplicationConstants.FLIGHT_ID,
                TestApplicationConstants.ORIGIN,
                TestApplicationConstants.DESTINATION,
                TestApplicationConstants.DEPARTURE_TIME,
                TestApplicationConstants.ARRIVAL_TIME,
                TestApplicationConstants.AIRPLANE_MODEL,
                TestApplicationConstants.CAPACITY,
                TestApplicationConstants.PRICE
        );
        FlightDto dto2 = new FlightDto(
                2L,
                TestApplicationConstants.ORIGIN,
                TestApplicationConstants.DESTINATION,
                TestApplicationConstants.DEPARTURE_TIME,
                TestApplicationConstants.ARRIVAL_TIME,
                TestApplicationConstants.AIRPLANE_MODEL,
                TestApplicationConstants.CAPACITY,
                TestApplicationConstants.PRICE
        );

        try (MockedStatic<FlightConverter> conv = Mockito.mockStatic(FlightConverter.class)) {
            conv.when(() -> FlightConverter.toDto(flight1)).thenReturn(dto1);
            conv.when(() -> FlightConverter.toDto(flight2)).thenReturn(dto2);

            List<FlightDto> result = service.getAllFlights();
            assertEquals(2, result.size());
            assertEquals(dto1, result.get(0));
            assertEquals(dto2, result.get(1));

            conv.verify(() -> FlightConverter.toDto(flight1), times(1));
            conv.verify(() -> FlightConverter.toDto(flight2), times(1));
        }
    }

    @Test
    void getAllFlightsThrowsDataAccessException() throws SQLException {
        when(repository.findAll()).thenThrow(new SQLException("DB unavailable"));
        DataAccessException ex = assertThrows(
                DataAccessException.class,
                service::getAllFlights
        );
        assertEquals("Failed to fetch flights", ex.getMessage());
        assertInstanceOf(SQLException.class, ex.getCause());
    }
}
