import org.junit.jupiter.api.Test;
import ru.vsu.amm.java.BaristaAnalysis;
import ru.vsu.amm.java.entity.DrinkRecord;
import ru.vsu.amm.java.enums.DrinkName;
import ru.vsu.amm.java.service.BaristaService;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BaristaAnalysisTest {

    @Test
    void testGetMorningDrinks() {
        List<DrinkRecord> records = Arrays.asList(
                new DrinkRecord(DrinkName.ESPRESSO, LocalDateTime.of(2024, 11, 21, 7, 30)),
                new DrinkRecord(DrinkName.LATTE, LocalDateTime.of(2024, 11, 21, 8, 15)),
                new DrinkRecord(DrinkName.AMERICANO, LocalDateTime.of(2024, 11, 21, 10, 0))
        );

        BaristaService service = new BaristaService();
        List<DrinkName> result = service.getMorningDrinks(records);

        assertEquals(Arrays.asList(DrinkName.ESPRESSO, DrinkName.LATTE), result);
    }

    @Test
    void testCountCappuccino() {
        List<DrinkRecord> records = Arrays.asList(
                new DrinkRecord(DrinkName.CAPPUCHINO, LocalDateTime.now()),
                new DrinkRecord(DrinkName.ESPRESSO, LocalDateTime.now()),
                new DrinkRecord(DrinkName.CAPPUCHINO, LocalDateTime.now())
        );

        BaristaService service = new BaristaService();
        long count = service.countCappuccino(records);

        assertEquals(2, count);
    }

    @Test
    void testDrinksNotOrderedLast3Months() {
        LocalDateTime now = LocalDateTime.now();
        List<DrinkRecord> records = Arrays.asList(
                new DrinkRecord(DrinkName.CAPPUCHINO, now.minusMonths(4)),
                new DrinkRecord(DrinkName.ESPRESSO, now.minusMonths(5)),
                new DrinkRecord(DrinkName.LATTE, now.minusWeeks(1))
        );

        BaristaService service = new BaristaService();
        List<DrinkName> result = service.getDrinksNotOrderedLast3Months(records);

        assertTrue(result.contains(DrinkName.CAPPUCHINO));
        assertTrue(result.contains(DrinkName.ESPRESSO));
        assertFalse(result.contains(DrinkName.LATTE));
    }

    @Test
    void testInvalidDrinkName() {
        assertFalse(DrinkName.isValidDrinkName("invalid"));
        assertTrue(DrinkName.isValidDrinkName("Капучино"));
    }
}
