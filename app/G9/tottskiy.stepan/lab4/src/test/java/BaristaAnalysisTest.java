import org.junit.jupiter.api.Test;
import ru.vsu.amm.java.entity.DrinkRecord;
import ru.vsu.amm.java.enums.DrinkName;
import ru.vsu.amm.java.service.BaristaService;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;


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

        assertIterableEquals(Arrays.asList(DrinkName.ESPRESSO, DrinkName.LATTE), result);
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
        assertFalse(DrinkName.isValidDrinkName(""));
        assertFalse(DrinkName.isValidDrinkName(null));
        assertTrue(DrinkName.isValidDrinkName("Капучино"));
        assertTrue(DrinkName.isValidDrinkName("Латте"));
    }

    @Test
    void testEmptyDrinkRecords() {
        BaristaService service = new BaristaService();
        assertTrue(service.getMorningDrinks(List.of()).isEmpty(), "Результат должен быть пустым для пустого списка");
        assertTrue(service.getDrinksNotOrderedLast3Months(List.of()).isEmpty(), "Результат должен быть пустым для пустого списка");
        assertEquals(0, service.countCappuccino(List.of()), "Количество капучино должно быть 0 для пустого списка");
    }

    @Test
    void testNullDrinkRecords() {
        BaristaService service = new BaristaService();
        assertTrue(service.getMorningDrinks(null).isEmpty(), "Результат должен быть пустым для null");
        assertTrue(service.getDrinksNotOrderedLast3Months(null).isEmpty(), "Результат должен быть пустым для null");
        assertEquals(0, service.countCappuccino(null), "Количество капучино должно быть 0 для null");
    }

    @Test
    void testBoundaryMorningDrinks() {
        List<DrinkRecord> records = Arrays.asList(
                new DrinkRecord(DrinkName.ESPRESSO, LocalDateTime.of(2024, 11, 21, 6, 59)),
                new DrinkRecord(DrinkName.CAPPUCHINO, LocalDateTime.of(2024, 11, 21, 7, 0)),
                new DrinkRecord(DrinkName.LATTE, LocalDateTime.of(2024, 11, 21, 8, 59)),
                new DrinkRecord(DrinkName.AMERICANO, LocalDateTime.of(2024, 11, 21, 9, 0))
        );

        BaristaService service = new BaristaService();
        List<DrinkName> result = service.getMorningDrinks(records);

        assertTrue(result.contains(DrinkName.CAPPUCHINO), "Капучино должно быть учтено");
        assertTrue(result.contains(DrinkName.LATTE), "Латте должно быть учтено");
        assertFalse(result.contains(DrinkName.ESPRESSO), "Эспрессо не должен быть учтен (до 7:00)");
        assertFalse(result.contains(DrinkName.AMERICANO), "Американо не должен быть учтен (после 9:00)");
    }

    @Test
    void testLogging() {
        List<DrinkRecord> records = List.of(); // пустой список
        BaristaService service = new BaristaService();


        service.getMorningDrinks(records);
        service.getDrinksNotOrderedLast3Months(records);
        service.countCappuccino(records);
    }
}

