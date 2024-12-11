import org.example.DayStatistic;
import org.example.FarmService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class FarmApplicationTest {

    private List<DayStatistic> test Statstics;

    @BeforeEach
    void setup(){
        testStatistics = getTestStatistics();
    }

    @Test
    public void testFindBestMonth(){
        Month bestMonth = FarmService.findBestMonth(testStatistics);
        assertEquals(Month.MARCH, bestMonth);
    }

    @Test
    public void testAverageMilkPerWeek() {
        double averageMilk = FarmService.averageMilkPerWeek(testStatistics);
        assertEquals(18.6, averageMilk, 0.1);
    }

    @Test
    public void testTotalFeedConsumed() {
        double totalFeed = FarmService.totalFeedConsumed(testStatistics);
        assertEquals(75, totalFeed, 0.1);
    }

    @Test
    public void testFindBestMonthEmptyList() {
        List<DayStatistic> emptyRecords = Collections.emptyList();
        Month bestMonth = FarmService.findBestMonth(emptyRecords);
        assertNull(bestMonth, "Best month should be null for an empty list.");
    }

    @Test
    public void testAverageMilkPerWeekEmptyList(){
        List<DayStatistic> emptyRecords = Collections.emptyList();
        double averageMilk = FarmService.averageMilkPerWeek(emptyRecords);
        assertEquals(0.0, averageMilk, 0.1);
    }

    private List<DayStatistic> getTestStatistics() {
        return Arrays.asList(
                new DayStatistic(LocalDate.of(2024, 1, 1), 15, 10),
                new DayStatistic(LocalDate.of(2024, 1, 2), 20, 15),
                new DayStatistic(LocalDate.of(2024, 2, 1), 10, 8),
                new DayStatistic(LocalDate.of(2024, 2, 2), 25, 18),
                new DayStatistic(LocalDate.of(2024, 3, 1), 5, 5)
        );
    }

}
