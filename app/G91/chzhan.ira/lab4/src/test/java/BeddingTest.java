import ru.vsu.amm.java.enums.Material;
import ru.vsu.amm.java.enums.Colors;
import ru.vsu.amm.java.enums.Size;
import ru.vsu.amm.java.entity.BeddingRecord;
import ru.vsu.amm.java.service.BeddingAnalysis;
import org.junit.jupiter.api.Test;
import ru.vsu.amm.java.service.BeddingData;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BeddingTest {
    private static final String emptyPath = "app\\G91\\chzhan.ira\\lab4\\empty.txt";

    @Test
    void TestFindMaterialSizeHappyPath() {
        List<BeddingRecord> records = Arrays.asList(
                new BeddingRecord(LocalDate.now(), "A", Size.DOUBLE, Colors.BLUE, Material.COTTON),
                new BeddingRecord(LocalDate.now(), "B", Size.KING, Colors.RED, Material.LINEN),
                new BeddingRecord(LocalDate.now(), "C", Size.KING, Colors.BLUE, Material.COTTON)
        );
        BeddingAnalysis analysis = new BeddingAnalysis();
        Map<Material, Set<Size>> result = analysis.findMaterialSizes(records);
        assertEquals(3, records.size());
        assertTrue(result.get(Material.COTTON).containsAll(Set.of(Size.DOUBLE, Size.KING)));
        assertTrue(result.get(Material.LINEN).contains(Size.KING));
        ;
    }

    @Test
    void testFindFullColorSetNoMatches() {
        List<BeddingRecord> records = Arrays.asList(
                new BeddingRecord(LocalDate.now(), "A", Size.DOUBLE, Colors.BLUE, Material.COTTON),
                new BeddingRecord(LocalDate.now(), "B", Size.KING, Colors.RED, Material.LINEN)
        );
        BeddingAnalysis analysis = new BeddingAnalysis();
        Set<Colors> allColors = Set.of(Colors.BLUE, Colors.RED, Colors.WHITE);
        Set<String> result = analysis.findfullColorSets(records, allColors);
        assertTrue(result.isEmpty());
    }

    @Test
    void testFindFullColorSetHappyPath() {
        List<BeddingRecord> records = Arrays.asList(
                new BeddingRecord(LocalDate.now(), "A", Size.DOUBLE, Colors.BLUE, Material.COTTON),
                new BeddingRecord(LocalDate.now(), "A", Size.KING, Colors.RED, Material.LINEN)
        );
        BeddingAnalysis analysis = new BeddingAnalysis();
        Set<Colors> allColors = Set.of(Colors.BLUE, Colors.RED);
        Set<String> result = analysis.findfullColorSets(records, allColors);
        assertTrue(result.contains("A"));
    }

    @Test
    void testCountRecordsByQuarter() {
        List<BeddingRecord> records = Arrays.asList(
                new BeddingRecord(LocalDate.of(2024, 1, 15), "A", Size.DOUBLE, Colors.BLUE, Material.COTTON),
                new BeddingRecord(LocalDate.of(2024, 4, 20), "B", Size.KING, Colors.RED, Material.LINEN),
                new BeddingRecord(LocalDate.of(2024, 7, 10), "C", Size.KING, Colors.BLUE, Material.COTTON),
                new BeddingRecord(LocalDate.of(2024, 10, 25), "D", Size.DOUBLE, Colors.WHITE, Material.SILK)
        );
        BeddingAnalysis analysis = new BeddingAnalysis();
        Map<Integer, Long> result = analysis.countSalesQuarter(records);
        assertEquals(4, result.size());
        assertEquals(1L, result.get(1));
        assertEquals(1L, result.get(2));
        assertEquals(1L, result.get(3));
        assertEquals(1L, result.get(4));
    }

    @Test
    void testFindMaterialSizesOneRecord() {
        BeddingAnalysis analysis = new BeddingAnalysis();
        BeddingRecord record = new BeddingRecord(LocalDate.now(), "Sheet", Size.SINGLE, Colors.WHITE, Material.COTTON);
        Map<Material, Set<Size>> result = analysis.findMaterialSizes(List.of(record));
        assertEquals(1, result.size());
        assertEquals(Set.of(Size.SINGLE), result.get(Material.COTTON));
    }

    @Test
    void testFindFullColorSetsEmpty() {
        BeddingAnalysis analysis = new BeddingAnalysis();
        Set<String> result = analysis.findfullColorSets(List.of(), Set.of(Colors.WHITE, Colors.BLUE));
        assertTrue(result.isEmpty());
    }

    @Test
    void testCountSalesQuarterEmpty() {
        BeddingAnalysis analysis = new BeddingAnalysis();
        Map<Integer, Long> result = analysis.countSalesQuarter(List.of());
        assertTrue(result.isEmpty());
    }

    @Test
    void testBeddingDataLoadEmptyFile() {
        BeddingData loader = new BeddingData();
        List<BeddingRecord> records = loader.loadData(emptyPath);
        assertTrue(records.isEmpty());
    }

    @Test
    void testFindMaterialSizesEmpty() {
        BeddingAnalysis analysis = new BeddingAnalysis();
        Map<Material, Set<Size>> result = analysis.findMaterialSizes(List.of());
        assertTrue(result.isEmpty());
    }
}