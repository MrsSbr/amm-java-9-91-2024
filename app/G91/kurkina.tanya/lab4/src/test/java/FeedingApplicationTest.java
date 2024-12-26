import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.vsu.amm.java.classes.FeedingRecord;
import ru.vsu.amm.java.services.FeedingService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FeedingApplicationTest {
    private List<FeedingRecord> mockRecords;

    @BeforeEach
    void setUp() {
        mockRecords = Arrays.asList(
                new FeedingRecord(LocalDate.of(2024, 11, 15), "Tiger", Arrays.asList("meat", "fish"), 25.0),
                new FeedingRecord(LocalDate.of(2024, 11, 16), "Elephant", Arrays.asList("fruits", "vegetables", "hay"), 50.0),
                new FeedingRecord(LocalDate.of(2024, 11, 17), "Monkey", Arrays.asList("fruits", "apples", "nuts"), 10.5),
                new FeedingRecord(LocalDate.of(2024, 12, 01), "Tiger", Arrays.asList("meat", "poultry"), 22.0),
                new FeedingRecord(LocalDate.of(2024, 12, 02), "Elephant", Arrays.asList("vegetables", "hay", "fruits"), 48.0),
                new FeedingRecord(LocalDate.of(2024, 12, 03), "Monkey", Arrays.asList("apples", "bananas"), 12.0)
        );
    }

    @Test
    void testFindAnimalWithMostFoodLastMonth() {
        String result = FeedingService.findAnimalWithMostFoodLastMonth(mockRecords);
        assertEquals("Elephant", result, "The elephant should be the animal that ate the most food in the last month.");
    }

    @Test
    void testFindAnimalWithMostFoodLastMonthNoData() {
        List<FeedingRecord> emptyRecords = new ArrayList<>();
        String result = FeedingService.findAnimalWithMostFoodLastMonth(emptyRecords);
        assertEquals("No data", result, "The result should be 'No data' if there is no data.");
    }

    @Test
    void testFindMonthWithMostVariety() {
        String result = FeedingService.findMonthWithMostVariety(mockRecords);
        assertEquals("NOVEMBER", result, "November should be the month with the most varied diet.");
    }

    @Test
    void testFindMonthWithMostVarietyNoData() {
        List<FeedingRecord> emptyRecords = new ArrayList<>();
        String result = FeedingService.findMonthWithMostVariety(emptyRecords);
        assertEquals("No data", result, "The result should be 'No data' if there is no data.");
    }

    @Test
    void testFindProductsNotRepeated() {
        Map<String, List<String>> result = FeedingService.findProductsNotRepeated(mockRecords);
        assertTrue(result.containsKey("Tiger"), "The result should contain 'Tiger'.");
        assertEquals(List.of("fish"), result.get("Tiger"), "'Tiger' should have the product 'bird', which does not repeat in this month.");
    }

    @Test
    void testFindProductsNotRepeatedNoLastMonthData() {
        List<FeedingRecord> thisMonthOnly = List.of(
                new FeedingRecord(LocalDate.of(2024, 12, 01), "Tiger", Arrays.asList("meat", "bird"), 22.0)
        );
        Map<String, List<String>> result = FeedingService.findProductsNotRepeated(thisMonthOnly);
        assertTrue(result.isEmpty(), "The result should be empty if there is no data from the previous month.");
    }
}
