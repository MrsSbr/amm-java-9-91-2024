import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.vsu.amm.java.entity.FeedRecord;
import ru.vsu.amm.java.service.FeedService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FeedApplicationTest {
    private List<FeedRecord> records;

    @BeforeEach
    void setup() {
        records = new ArrayList<>();
        records.add(new FeedRecord(
                LocalDate.of(2025, 1, 5),
                "Tiger",
                List.of("meat", "fish"),
                25.0));
        records.add(new FeedRecord(
                LocalDate.of(2025, 1, 6),
                "Elephant",
                List.of("fruits", "hay"),
                50.0));
        records.add(new FeedRecord(
                LocalDate.of(2025, 1, 7),
                "Monkey",
                List.of("bananas", "apples", "nuts"),
                15.0));
        records.add(new FeedRecord(
                LocalDate.of(2025, 2, 1),
                "Tiger",
                List.of("meat", "poultry"),
                20.0));
        records.add(new FeedRecord(
                LocalDate.of(2025, 2, 2),
                "Elephant",
                List.of("hay", "vegetables"),
                45.0));
    }

    @Test
    void testFindMostEatingAnimal() {
        FeedService service = new FeedService();
        String result = service.findMostEatingAnimal(records);
        assertEquals("Elephant", result, "РЎР»РѕРЅ РґРѕР»Р¶РµРЅ Р±С‹С‚СЊ СЃР°РјС‹Рј РµРґСЏС‰РёРј РІ РїСЂРѕС€Р»РѕРј РјРµСЃСЏС†Рµ");
    }

    @Test
    void testFindMostEatingAnimalEmpty() {
        FeedService service = new FeedService();
        List<FeedRecord> emptyRecords = new ArrayList<>();
        String result = service.findMostEatingAnimal(emptyRecords);
        assertEquals("РќРµС‚ РґР°РЅРЅС‹С…", result, "Р”РѕР»Р¶РЅРѕ РІРµСЂРЅСѓС‚СЊСЃСЏ 'РќРµС‚ РґР°РЅРЅС‹С…' РґР»СЏ РїСѓСЃС‚РѕРіРѕ СЃРїРёСЃРєР°");
    }

    @Test
    void testFindMostDiverseProductsMonth() {
        FeedService service = new FeedService();
        String result = service.findMostDiverseProductsMonth(records);
        assertEquals("2025-1", result, "РЇРЅРІР°СЂСЊ РґРѕР»Р¶РµРЅ РёРјРµС‚СЊ СЃР°РјРѕРµ СЂР°Р·РЅРѕРѕР±СЂР°Р·РЅРѕРµ РїРёС‚Р°РЅРёРµ");
    }

    @Test
    void testFindMostDiverseProductsMonthEmpty() {
        FeedService service = new FeedService();
        List<FeedRecord> emptyRecords = new ArrayList<>();
        String result = service.findMostDiverseProductsMonth(emptyRecords);
        assertEquals("РќРµС‚ РґР°РЅРЅС‹С…", result, "Р”РѕР»Р¶РЅРѕ РІРµСЂРЅСѓС‚СЊСЃСЏ 'РќРµС‚ РґР°РЅРЅС‹С…' РґР»СЏ РїСѓСЃС‚РѕРіРѕ СЃРїРёСЃРєР°");
    }

    @Test
    void testFindMissingProductsEmpty() {
        List<FeedRecord> emptyRecords = new ArrayList<>();
        Map<String, List<String>> result = FeedService.findMissingProducts(emptyRecords);
        assertTrue(result.isEmpty(), "Р РµР·СѓР»СЊС‚Р°С‚ РґРѕР»Р¶РµРЅ Р±С‹С‚СЊ РїСѓСЃС‚С‹Рј РґР»СЏ РїСѓСЃС‚РѕРіРѕ СЃРїРёСЃРєР°");
    }

    @Test
    void testFindMissingProductsNegativeNoChanges() {
        List<FeedRecord> sameProductsRecords = List.of(
                new FeedRecord(LocalDate.of(2025, 1, 1), "Tiger", List.of("meat"), 10.0),
                new FeedRecord(LocalDate.of(2025, 2, 1), "Tiger", List.of("meat"), 10.0)
        );
        Map<String, List<String>> result = FeedService.findMissingProducts(sameProductsRecords);
        assertTrue(result.isEmpty(), "Р РµР·СѓР»СЊС‚Р°С‚ РґРѕР»Р¶РµРЅ Р±С‹С‚СЊ РїСѓСЃС‚С‹Рј, РµСЃР»Рё РїСЂРѕРґСѓРєС‚С‹ РЅРµ РёР·РјРµРЅРёР»РёСЃСЊ");
    }

    @Test
    void testFindMissingProductsNoThisMonth() {
        List<FeedRecord> onlyLastMonthRecords = List.of(
                new FeedRecord(LocalDate.of(2025, 1, 1), "Tiger", List.of("meat", "fish"), 10.0)
        );
        Map<String, List<String>> result = FeedService.findMissingProducts(onlyLastMonthRecords);
        assertTrue(result.isEmpty(), "Р РµР·СѓР»СЊС‚Р°С‚ РґРѕР»Р¶РµРЅ Р±С‹С‚СЊ РїСѓСЃС‚С‹Рј, РµСЃР»Рё РЅРµС‚ РґР°РЅРЅС‹С… Р·Р° СЌС‚РѕС‚ РјРµСЃСЏС†");
    }
}