import ru.vsu.amm.java.ApiaryService;
import ru.vsu.amm.java.Constants;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ApiaryTest {

    @Test
    void testCalculateYearlyTotals() {
        List<List<Double>> honeyData = List.of(
                List.of(10.0, 20.0, 30.0),
                List.of(15.0, 25.0, 35.0)
        );
        List<Double> expectedTotals = List.of(60.0, 70.0);
        List<Double> yearlyTotals = ApiaryService.calculateYearlyTotals(honeyData);
        assertEquals(expectedTotals, yearlyTotals, "Yearly totals calculation is incorrect");
    }

    @Test
    void testCalculateTotalHoneyPerHive() {
        List<List<Double>> honeyData = List.of(
                Collections.nCopies(35, 10.0),
                Collections.nCopies(35, 15.0)
        );
        List<Double> expectedTotals = Collections.nCopies(35, 25.0);

        List<Double> totals = ApiaryService.calculateTotalHoneyPerHive(honeyData);
        assertEquals(expectedTotals, totals, "Calculation of the total volume of honey per hive is incorrect");
    }

    @Test
    void testCalculateYearlyTotalsEmptyData() {
        List<List<Double>> honeyData = Collections.emptyList();
        List<Double> yearlyTotals = ApiaryService.calculateYearlyTotals(honeyData);
        assertTrue(yearlyTotals.isEmpty(), "Yearly totals should be empty for empty input");
    }

    @Test
    void testCalculateTotalHoneyPerHiveEmptyData() {
        List<List<Double>> honeyData = Collections.emptyList();
        List<Double> totalHoneyPerHive = ApiaryService.calculateTotalHoneyPerHive(honeyData);
        assertEquals(Constants.NUMBER_OF_HIVES, totalHoneyPerHive.size(), " Total honey per hive should have size equal to number of hives");
        assertTrue(totalHoneyPerHive.stream().allMatch(total -> total == 0.0), "All hive totals should be 0.0 for empty data");

    }

    @Test
    void testCalculateTotalHoneyPerHiveMismatchedData() {
        List<List<Double>> honeyData = List.of(
                List.of(10.0, 15.0, 20.0),
                List.of(5.0, 10.0)
        );
        Exception exception = assertThrows(IndexOutOfBoundsException.class, () ->
                ApiaryService.calculateTotalHoneyPerHive(honeyData));
        assertTrue(exception.getMessage().contains("Index"), "Exception message should indicate index out of bounds");
    }
}

