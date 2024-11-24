import org.junit.jupiter.api.Test;
import ru.vsu.amm.java.entity.EggProductionRecord;
import ru.vsu.amm.java.enums.BirdType;
import ru.vsu.amm.java.service.EggProductionService;

import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public final class EggProductionTest {

    @Test
    public void testFindBirdTypesWithHighProduction() {

        List<EggProductionRecord> records = List.of(
                new EggProductionRecord(LocalDate.now().minusDays(10), BirdType.Chicken, 21),
                new EggProductionRecord(LocalDate.now().minusDays(10), BirdType.Duck, 25),
                new EggProductionRecord(LocalDate.now().minusDays(30), BirdType.Goose, 5)
        );
        var result = new HashSet<>(EggProductionService.getHighProductionBirdType(records)); // Преобразуем в Set
        var expected = Set.of(BirdType.Chicken, BirdType.Duck); // Используем Set для сравнения
        assertEquals(expected, result);
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

    @Test
    public void testFindBirdTypesWithHighProductionEmptyList() {
        List<EggProductionRecord> records = List.of();
        var result = EggProductionService.getHighProductionBirdType(records);
        assertEquals(Set.of(), new HashSet<>(result));
    }

    @Test
    public void testFindBirdTypesWithHighProductionNullList() {
        List<EggProductionRecord> records = null;
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> EggProductionService.getHighProductionBirdType(records)
        );
        assertEquals("Записи не могут быть null", exception.getMessage());
    }

    @Test
    public void testFindBirdTypesWithHighProductionNoHighProduction() {
        List<EggProductionRecord> records = List.of(
                new EggProductionRecord(LocalDate.now().minusDays(30), BirdType.Chicken, 10),
                new EggProductionRecord(LocalDate.now().minusDays(40), BirdType.Duck, 15),
                new EggProductionRecord(LocalDate.now().minusDays(50), BirdType.Goose, 5)
        );
        var result = EggProductionService.getHighProductionBirdType(records);
        assertEquals(Set.of(), result);
    }

    @Test
    public void testFindBirdTypesWithHighProductionBoundary() {
        List<EggProductionRecord> records = List.of(
                new EggProductionRecord(LocalDate.now().minusDays(5), BirdType.Chicken, 20),
                new EggProductionRecord(LocalDate.now().minusDays(5), BirdType.Duck, 21)
        );
        var result = EggProductionService.getHighProductionBirdType(records);
        assertEquals(Set.of(BirdType.Duck), result);
    }


}