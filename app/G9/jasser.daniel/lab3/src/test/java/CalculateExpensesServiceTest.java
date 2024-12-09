import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.vsu.amm.java.services.CalculateExpensesService;
import ru.vsu.amm.java.entities.DateRange;
import ru.vsu.amm.java.entities.Driver;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CalculateExpensesServiceTest {

    static List<Driver> drivers;
    static CalculateExpensesService calc;

    @BeforeAll
    public static void init() {
        calc = new CalculateExpensesService();
        drivers = new ArrayList<Driver>();

        drivers.add(new Driver(
                "A",
                1
        ));

        drivers.add(new Driver(
                "B",
                2
        ));

        drivers.add(new Driver(
                "C",
                3
        ));

        drivers.add(new Driver(
                "D",
                4
        ));

        drivers.add(new Driver(
                "E",
                5
        ));
    }

    @Test
    public void calculateTest() {
        DateRange dateRange = new DateRange(
                new GregorianCalendar(2023, Calendar.JANUARY, 1),
                new GregorianCalendar(2023, Calendar.JANUARY, 1)
        );
        int numberWorkingDays = 1;

        assertEquals(15, calc.calculate(dateRange, numberWorkingDays, drivers));
    }
}
