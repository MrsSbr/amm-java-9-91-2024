import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.vsu.amm.java.entity.BeautyBox;
import ru.vsu.amm.java.service.BeautyBoxService;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BeautyBoxApplicationTest {

    private BeautyBoxService beautyBoxService;

    @BeforeEach
    void setup() {
        beautyBoxService = new BeautyBoxService();
    }

    @Test
    void testProductFrequency() {
        List<BeautyBox> boxes = List.of(
                new BeautyBox(LocalDate.of(2024, 1, 1), 100, List.of("Cream", "Lotion")),
                new BeautyBox(LocalDate.of(2024, 2, 1), 200, List.of("Lotion", "Shampoo"))
        );
        Map<String, Long> result = beautyBoxService.calculateProductFrequency(boxes);
        assertEquals(3, result.size());
        assertEquals(1L, result.get("Cream"));
        assertEquals(2L, result.get("Lotion"));
        assertEquals(1L, result.get("Shampoo"));
    }

    @Test
    void testProductFrequencyEmpty() {
        List<BeautyBox> boxes = Collections.emptyList();
        Map<String, Long> result = beautyBoxService.calculateProductFrequency(boxes);
        assertTrue(result.isEmpty());
    }

    @Test
    void testFindBestSalesMonth_SingleBestMonth() {
        List<BeautyBox> boxes = List.of(
                new BeautyBox(LocalDate.of(2024, 1, 1), 100, List.of("Cream")),
                new BeautyBox(LocalDate.of(2024, 2, 1), 200, List.of("Shampoo"))
        );
        String result = beautyBoxService.findBestSalesMonth(boxes);
        assertEquals("February", result);
    }

    @Test
    void testFindBestSalesMonthEmpty() {
        List<BeautyBox> boxes = Collections.emptyList();
        String result = beautyBoxService.findBestSalesMonth(boxes);
        assertEquals("Unknown", result);
    }

    @Test
    void testFindFirstAppearance_ProductInMultipleMonths() {
        List<BeautyBox> boxes = List.of(
                new BeautyBox(LocalDate.of(2024, 2, 1), 100, List.of("Cream")),
                new BeautyBox(LocalDate.of(2024, 1, 1), 200, List.of("Cream"))
        );
        Map<String, String> result = beautyBoxService.findFirstAppearanceByMonth(boxes);
        assertEquals("January", result.get("Cream"));
    }

    @Test
    void testFindFirstAppearance_EmptyList() {
        List<BeautyBox> boxes = Collections.emptyList();
        Map<String, String> result = beautyBoxService.findFirstAppearanceByMonth(boxes);
        assertTrue(result.isEmpty());
    }

}
