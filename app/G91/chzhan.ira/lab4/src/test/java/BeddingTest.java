import ru.vsu.amm.java.enums.*;
import ru.vsu.amm.java.entity.BeddingRecord;
import ru.vsu.amm.java.service.BeddingAnalysis;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class BeddingTest {
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
        assertTrue(result.get(Material.LINEN).contains(Size.KING));;
    }

    @Test
    void testFindFullColorSetNoMatches() {
        List<BeddingRecord> records = Arrays.asList(
                new BeddingRecord(LocalDate.now(),"A",  Size.DOUBLE,  Colors.BLUE, Material.COTTON),
                new BeddingRecord(LocalDate.now(),"B", Size.KING, Colors.RED, Material.LINEN)
        );
        BeddingAnalysis analysis = new BeddingAnalysis();
        Set<Colors> allColors = Set.of(Colors.BLUE, Colors.RED, Colors.WHITE);
        Set<String> result = analysis.findfullColorSets(records, allColors);
        assertTrue(result.isEmpty());
    }

    @Test
    void testFindFullColorSetHappyPath() {
        List<BeddingRecord> records = Arrays.asList(
                new BeddingRecord(LocalDate.now(),"A",  Size.DOUBLE,  Colors.BLUE, Material.COTTON),
                new BeddingRecord(LocalDate.now(),"A", Size.KING, Colors.RED, Material.LINEN)
        );
        BeddingAnalysis analysis = new BeddingAnalysis();
        Set<Colors> allColors = Set.of(Colors.BLUE, Colors.RED);
        Set<String> result = analysis.findfullColorSets(records, allColors);
        assertTrue(result.contains("A"));
    }

    @Test
    void testCountRecordsByQuarter() {
        List<BeddingRecord> records = Arrays.asList(
                new BeddingRecord(LocalDate.of(2024, 1, 15),"A",  Size.DOUBLE,  Colors.BLUE, Material.COTTON),
                new BeddingRecord(LocalDate.of(2024, 4, 20),"B", Size.KING, Colors.RED, Material.LINEN),
                new BeddingRecord(LocalDate.of(2024, 7, 10),"C",  Size.KING,  Colors.BLUE, Material.COTTON),
                new BeddingRecord(LocalDate.of(2024, 10, 25),"D", Size.DOUBLE, Colors.WHITE, Material.SILK)
        );
        BeddingAnalysis analysis = new BeddingAnalysis();
        Map<Integer, Long> result = analysis.countSalesQuarter(records);
        assertEquals(4, result.size());
        assertEquals(1L, result.get(1));
        assertEquals(1L, result.get(2));
        assertEquals(1L, result.get(3));
        assertEquals(1L, result.get(4));
    }
}