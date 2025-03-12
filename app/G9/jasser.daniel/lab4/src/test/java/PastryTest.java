import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.vsu.amm.java.entities.PastryRecord;
import ru.vsu.amm.java.enums.PastryName;
import ru.vsu.amm.java.services.PastryRecordAnalyze;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class PastryTest {
    PastryRecordAnalyze pastryRecordAnalyze = new PastryRecordAnalyze();
    List<PastryRecord> records;

    @BeforeEach
    void init() {
        records = List.of(
                new PastryRecord(LocalDate.of(2023, 1, 15), PastryName.CHEESE_CAKE,
                        200, 2000),
                new PastryRecord(LocalDate.of(2023, 1, 20), PastryName.CREAM_SLICE,
                        300, 3000),
                new PastryRecord(LocalDate.of(2023, 2, 5), PastryName.WEDDING_CAKE,
                        50, 1000),
                new PastryRecord(LocalDate.of(2023, 2, 10), PastryName.TART,
                        400, 300)
        );
    }

    @Test
    void testFindMonthWithLowestIncomeNull() {

        assertThrows(NullPointerException.class, () ->
                pastryRecordAnalyze.findMonthWithLowestIncome(null));
    }

    @Test
    void testFindMonthWithLowestIncomeEmptyList() {
        List<PastryRecord> emptyList = new ArrayList<>();
        Month result = pastryRecordAnalyze.findMonthWithLowestIncome(emptyList);
        assertNull(result, "Expected null for empty list");
    }

    @Test
    void testFindMonthWithLowestIncomeSingleEntry() {
        List<PastryRecord> singleEntryList = List.of(new PastryRecord(LocalDate.of(2023, 1, 15),
                PastryName.BIRTHDAY_CAKE,100, 200));
        Month result = pastryRecordAnalyze.findMonthWithLowestIncome(singleEntryList);
        assertEquals(Month.JANUARY, result, "Expected January for single entry");
    }

    @Test
    void testFindMonthWithLowestIncomeMultipleEntries() {
        Month result = pastryRecordAnalyze.findMonthWithLowestIncome(records);
        assertEquals(Month.FEBRUARY, result, "Expected February as the month with lowest income");
    }

    @Test
    void testFindHeaviestPastryPerMonthNull() {

        assertThrows(NullPointerException.class, () ->
                pastryRecordAnalyze.findHeaviestPastryPerMonth(null));
    }

    @Test
    void testFindHeaviestPastryPerMonthEmptyList() {
        List<PastryRecord> emptyList = new ArrayList<>();
        Map<Month, Integer> result = pastryRecordAnalyze.findHeaviestPastryPerMonth(emptyList);
        assertTrue(result.isEmpty(), "Expected empty map for empty list");
    }

    @Test
    void testFindHeaviestPastryPerMonthSingleEntry() {
        List<PastryRecord> singleEntryList = List.of(new PastryRecord(LocalDate.of(2023, 1, 15),
                PastryName.CHEESE_CAKE,200, 1200));
        Map<Month, Integer> result = pastryRecordAnalyze.findHeaviestPastryPerMonth(singleEntryList);
        assertEquals(1, result.size(), "Expected map size of 1 for single entry");
        assertEquals(200, result.get(Month.JANUARY).intValue(), "Expected weight of 200 for January");
    }

    @Test
    void testFindHeaviestPastryPerMonthMultipleEntries() {
        Map<Month, Integer> result = pastryRecordAnalyze.findHeaviestPastryPerMonth(records);
        assertEquals(2, result.size(), "Expected map size of 2 for two months");
        assertEquals(300, result.get(Month.JANUARY).intValue(), "Expected heaviest weight of 300 for January");
        assertEquals(400, result.get(Month.FEBRUARY).intValue(), "Expected heaviest weight of 400 for February");
    }

    @Test
    void  testFindAllRecordsByMonthNull() {

        assertThrows(NullPointerException.class, () ->
                pastryRecordAnalyze.findAllRecordsByMonth(null));
    }

    @Test
    void testFindAllRecordsByMonthEmptyList() {
        List<PastryRecord> emptyList = new ArrayList<>();
        Map<Month, List<PastryRecord>> result = pastryRecordAnalyze.findAllRecordsByMonth(emptyList);
        assertTrue(result.isEmpty(), "Expected empty map for empty list");
    }

    @Test
    void testFindAllRecordsByMonthSingleEntry() {
        List<PastryRecord> singleEntryList = List.of(new PastryRecord(LocalDate.of(2023, 1, 15),
                PastryName.CREAM_SLICE,100, 200));
        Map<Month, List<PastryRecord>> result = pastryRecordAnalyze.findAllRecordsByMonth(singleEntryList);
        assertEquals(1, result.size(), "Expected map size of 1 for single entry");
        assertEquals(1, result.get(Month.JANUARY).size(), "Expected one record for January");
    }

    @Test
    void testFindAllRecordsByMonthMultipleEntries() {
        Map<Month, List<PastryRecord>> result = pastryRecordAnalyze.findAllRecordsByMonth(records);
        assertEquals(2, result.size(), "Expected map size of 2 for two months");
        assertEquals(2, result.get(Month.JANUARY).size(), "Expected two records for January");
        assertEquals(2, result.get(Month.FEBRUARY).size(), "Expected two records for February");
    }
}