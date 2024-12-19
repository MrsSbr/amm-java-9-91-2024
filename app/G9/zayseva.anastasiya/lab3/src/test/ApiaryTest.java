import ru.vsu.amm.java.ApiaryDataFactory;
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
    void testGenerateHoneyData() {
        int years = 3;
        List<List<Double>> data = ApiaryDataFactory.generateHoneyData(years);

        assertEquals(years, data.size(), "Data size does not match number of years");
        assertTrue(data.stream().allMatch(year -> year.size() == Constants.NUMBER_OF_HIVES), "Each year doesn't have data for all hives");
    }

    @Test
    void testCalculateTotalHoneyPerHivePositive() {
        List<List<Double>> data = List.of(
                Collections.nCopies(Constants.NUMBER_OF_HIVES, 10.0),
                Collections.nCopies(Constants.NUMBER_OF_HIVES, 15.0)
        );

        List<Double> expectedTotals = Collections.nCopies(Constants.NUMBER_OF_HIVES, 25.0);

        List<Double> totals = ApiaryService.calculateTotalHoneyPerHive(data);
        assertEquals(expectedTotals, totals, "Total honey per hive calculation is incorrect");
    }

    @Test
    void testCalculateTotalHoneyPerHiveNegative() {
        Exception exception = assertThrows(IndexOutOfBoundsException.class, () -> {
            ApiaryService.calculateTotalHoneyPerHive(List.of(
                    List.of(10.0, 20.0),
                    List.of(15.0)
            ));
                });
        assertTrue(exception.getMessage().contains("Index"));
    }
}
