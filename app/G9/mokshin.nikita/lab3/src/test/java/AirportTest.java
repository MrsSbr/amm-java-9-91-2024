import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.vsu.amm.java.entity.Family;
import ru.vsu.amm.java.entity.FlightData;
import ru.vsu.amm.java.service.FlightDataService;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import static org.junit.jupiter.api.Assertions.*;

public class AirportTest {
    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStream));
    }

    @AfterEach
    public void tearDown() {
        System.setOut(standardOut);
    }

    @Test
    public void countPassengersTest() {
        FlightData flightData = new FlightData();

        flightData.addFamily(new Family(1, 2));
        flightData.addFamily(new Family(1, 3));
        flightData.addFamily(new Family(1, 1));
        flightData.addFamily(new Family(2, 2));
        flightData.addFamily(new Family(2, 5));

        FlightDataService.printCountPassengers(flightData);

        assertEquals(outputStream.toString(),
                """
                        Flight №1 count passenger = 6
                        Flight №2 count passenger = 7
                        Flight №3 count passenger = 0
                        Flight №4 count passenger = 0
                        Flight №5 count passenger = 0
                        Flight №6 count passenger = 0
                        Flight №7 count passenger = 0
                        Flight №8 count passenger = 0
                        Flight №9 count passenger = 0
                        Flight №10 count passenger = 0
                        """);
    }

    @Test
    public void countEmptyPassengersTest() {
        FlightData flightData = new FlightData();
        FlightDataService.printCountPassengers(flightData);

        assertEquals(outputStream.toString(),
                """
                        Flight №1 count passenger = 0
                        Flight №2 count passenger = 0
                        Flight №3 count passenger = 0
                        Flight №4 count passenger = 0
                        Flight №5 count passenger = 0
                        Flight №6 count passenger = 0
                        Flight №7 count passenger = 0
                        Flight №8 count passenger = 0
                        Flight №9 count passenger = 0
                        Flight №10 count passenger = 0
                        """);
    }

    @Test
    public void countGeneratePassengersTest() {
        FlightData flightData = new FlightData();

        int count = 25;
        FlightDataService.fillRandomFamilies(flightData, count);

        assertEquals(count, flightData.getFamilies().size());
    }

    @Test void countZeroGeneratePassengersTest() {
        FlightData flightData = new FlightData();

        int count = 0;
        FlightDataService.fillRandomFamilies(flightData, count);

        assertEquals(count, flightData.getFamilies().size());
    }
}
