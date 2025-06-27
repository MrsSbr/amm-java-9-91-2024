package ru.vsu.amm.java.services.flights;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import ru.vsu.amm.java.IntegrationTest;
import ru.vsu.amm.java.entities.FlightEntity;
import ru.vsu.amm.java.models.dto.FlightDto;
import ru.vsu.amm.java.repositories.impl.FlightRepository;
import ru.vsu.amm.java.services.flights.impl.FlightServiceImpl;
import ru.vsu.amm.java.utils.SetupHelper;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class FlightServiceIntegrationTest extends IntegrationTest {

    private final FlightService service = new FlightServiceImpl();
    private final FlightRepository repository = new FlightRepository();

    @Test
    void getAllFlightsEmptyReturnsEmptyList() throws SQLException {
        List<FlightDto> flights = service.getAllFlights();
        assertTrue(flights.isEmpty());
    }

    @Test
    void getAllFlightsReturnsSavedFlights() throws SQLException {
        FlightEntity entity1 = SetupHelper.getFlightEntity();
        FlightEntity entity2 = SetupHelper.getFlightEntity();
        entity2.setFlightId(2L);
        repository.save(entity1);
        repository.save(entity2);

        List<FlightDto> flights = service.getAllFlights();

        assertEquals(2, flights.size());
        assertTrue(flights.stream().anyMatch(f -> f.flightId().equals(entity1.getFlightId())));
        assertTrue(flights.stream().anyMatch(f -> f.flightId().equals(entity2.getFlightId())));
    }
}
