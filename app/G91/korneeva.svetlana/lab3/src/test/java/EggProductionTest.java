import org.junit.jupiter.api.Test;
import ru.vsu.amm.java.entity.EggProductionRecord;
import ru.vsu.amm.java.enums.BirdType;
import ru.vsu.amm.java.service.EggProductionService;

import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public final class EggProductionTest {

    @Test
    public void testFindBirdTypesWithHighProduction() {

        List<EggProductionRecord> records = List.of(
                new EggProductionRecord(LocalDate.now().minusDays(10), BirdType.Chicken, 21),
                new EggProductionRecord(LocalDate.now().minusDays(10), BirdType.Duck, 25),
                new EggProductionRecord(LocalDate.now().minusDays(30), BirdType.Goose, 5)
        );
        var result = EggProductionService.getHighProductionBirdType(records).stream().sorted().toList();
        assertEquals(List.of(BirdType.Chicken, BirdType.Duck), result);
    }

    @Test
    public void testFindMostProductiveMonth() {
        List<EggProductionRecord> records = List.of(
                new EggProductionRecord(LocalDate.of(2022, 1, 10), BirdType.Chicken, 15),
                new EggProductionRecord(LocalDate.of(2022, 2, 10), BirdType.Duck, 25),
                new EggProductionRecord(LocalDate.of(2022, 2, 15), BirdType.Goose, 35)
        );
        var result = EggProductionService.getMostProductiveMonth(records);
        assertEquals(Month.FEBRUARY, result);
    }

    @Test
    public void testGetTotalEggsProduced() {
        List<EggProductionRecord> records = List.of(
                new EggProductionRecord(LocalDate.now(), BirdType.Chicken, 15),
                new EggProductionRecord(LocalDate.now(), BirdType.Duck, 25)
        );
        assertEquals(40, EggProductionService.getTotalEggsProduced(records));
    }
    @Test
    public void testGetTotalEggsProducedEmptyList() {
        List<EggProductionRecord> records = List.of();
        assertEquals(0, EggProductionService.getTotalEggsProduced(records));  //0 яиц
    }

}