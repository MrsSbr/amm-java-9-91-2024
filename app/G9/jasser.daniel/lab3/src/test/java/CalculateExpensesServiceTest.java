import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.vsu.amm.java.services.CalculateExpensesService;
import ru.vsu.amm.java.entities.DateRange;
import ru.vsu.amm.java.entities.Driver;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CalculateExpensesServiceTest {

    static List<Driver> drivers;
    static CalculateExpensesService calc;
    static DateRange dateRange;

    @BeforeEach
    public void init() {
        calc = new CalculateExpensesService();
        drivers = new ArrayList<Driver>();

        dateRange = new DateRange(
                LocalDate.of(2023, Month.JANUARY, 1),
                LocalDate.of(2023, Month.JANUARY, 2)
        );

        drivers.add(new Driver(
                "A"
        ));

        drivers.add(new Driver(
                "B"
        ));

        drivers.add(new Driver(
                "C"
        ));

        drivers.add(new Driver(
                "D"
        ));

        drivers.add(new Driver(
                "E"
        ));
    }

    @Test
    public void testCalculate() {
        int numberWorkingDays = 1;
        double actualValue = calc.calculate(dateRange, numberWorkingDays, drivers);
        double maxExpectedValue = numberWorkingDays * drivers.size() * 100;

        assertTrue(actualValue  < maxExpectedValue, "actual value +  should be less than" +
                " max expected value");
    }

    @Test
    public void testCalculateWithoutUserInput() {
        int numberWorkingDays = dateRange.getEndDate().getDayOfMonth() - dateRange.getStartDate().getDayOfMonth();
        double maxExpectedValue = numberWorkingDays * drivers.size() * 100;
        double actualValue = calc.calculate(dateRange, -1, drivers);

        assertTrue(actualValue  < maxExpectedValue, "actual value +  should be less than" +
                " max expected value");
    }

    @Test
    public void testCalculateWithoutDrivers() {
        drivers = Collections.emptyList();

        int numberWorkingDays = 1;
        double actualValue = calc.calculate(dateRange, numberWorkingDays, drivers);

        assertEquals(0, actualValue, "Actual value should be zero for empty drivers");
    }

    @Test
    public void testCalculateWithoutDateRange() {
        dateRange = new DateRange();

        int numberWorkingDays = 1;
        double actualValue = calc.calculate(dateRange, numberWorkingDays, drivers);

        assertEquals(0, actualValue, "Actual value should be zero when no date range");
    }
}
