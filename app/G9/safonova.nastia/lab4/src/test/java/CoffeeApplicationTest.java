import ru.vsu.amm.java.CoffeeService;
import ru.vsu.amm.java.ProcessingType;
import ru.vsu.amm.java.Producer;
import ru.vsu.amm.java.Sort;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class CoffeeApplicationTest {
    @Test
    public void testGetSortsByProcessingType(){
        List<Producer> producerList = List.of(
                new Producer(Sort.ARABICA, "Brazil", "Farm 1", ProcessingType.WASHED, 1200),
                new Producer(Sort.ARABICA, "Colombia", "Farm 2", ProcessingType.WASHED, 1800),
                new Producer(Sort.ROBUSTA, "Vietnam", "Farm 3", ProcessingType.NATURAL, 1600),
                new Producer(Sort.ARABICA, "Brazil", "Farm 1", ProcessingType.WASHED, 1200),
                new Producer(Sort.LIBERICA, "Kenya", "Farm 4", ProcessingType.HONEY, 2000),
                new Producer(Sort.EXCELSA, "Ethiopia", "Farm 5", ProcessingType.NATURAL, 1500));
        Map<ProcessingType,  List<Sort>> result =
                CoffeeService.getSortsByProcessingType(producerList);

        assertEquals(3,result.size());

        assertTrue(result.get(ProcessingType.WASHED).contains(Sort.ARABICA));
        assertEquals(1,result.get(ProcessingType.WASHED).size());

        assertTrue(result.get(ProcessingType.NATURAL).contains(Sort.ROBUSTA));
        assertTrue(result.get(ProcessingType.NATURAL).contains(Sort.EXCELSA));
        assertEquals(2, result.get(ProcessingType.NATURAL).size());

        assertTrue(result.get(ProcessingType.HONEY).contains(Sort.LIBERICA));
        assertEquals(1,result.get(ProcessingType.HONEY).size());
    }

    @Test
    public void testGetSortsByProcesingTypeNull(){
        List<Producer> producersWithNullSorts = List.of(
                new Producer(null, "Brazil", "Farm 1", ProcessingType.WASHED, 1200),
                new Producer(Sort.ROBUSTA, "Vietnam", "Farm 3", ProcessingType.NATURAL, 1600));

        Map<ProcessingType, List<Sort>> result = CoffeeService.getSortsByProcessingType(producersWithNullSorts);

        assertTrue(result.containsKey(ProcessingType.NATURAL));
        assertEquals(List.of(Sort.ROBUSTA), result.get(ProcessingType.NATURAL));

        assertFalse(result.containsKey(ProcessingType.WASHED));
        assertTrue(result.getOrDefault(ProcessingType.HONEY, List.of()).isEmpty());
    }

    @Test
    public void testGetCoffeeCountByFarm() {
        List<Producer> producers = List.of(
                new Producer(Sort.ARABICA, "Brazil", "Farm 1", ProcessingType.WASHED, 1200),
                new Producer(Sort.ARABICA, "Colombia", "Farm 2", ProcessingType.WASHED, 1800),
                new Producer(Sort.ROBUSTA, "Vietnam", "Farm 3", ProcessingType.NATURAL, 1600),
                new Producer(Sort.LIBERICA, "Kenya", "Farm 4", ProcessingType.HONEY, 2000),
                new Producer(Sort.EXCELSA, "Ethiopia", "Farm 5", ProcessingType.NATURAL, 1500),
                new Producer(Sort.ROBUSTA, "Guatemala", "Farm 6", ProcessingType.NATURAL, 1400)
        );
        Map<String, Long> result = CoffeeService.getCoffeeCountByFarm(producers);

        assertEquals(6,result.size());

        assertEquals(Optional.of(1L).get(), result.get("Farm 1"));
        assertEquals(Optional.of(1L).get(), result.get("Farm 2"));
        assertEquals(Optional.of(1L).get(), result.get("Farm 3"));
        assertEquals(Optional.of(1L).get(), result.get("Farm 4"));
        assertEquals(Optional.of(1L).get(), result.get("Farm 5"));
        assertEquals(Optional.of(1L).get(), result.get("Farm 6"));
    }

    @Test
    public void testGetCountriesWithHighAltitude() {
        List<Producer> producers = List.of(
                new Producer(Sort.ARABICA, "Brazil", "Farm 1", ProcessingType.WASHED, 1200),
                new Producer(Sort.ROBUSTA, "Vietnam", "Farm 3", ProcessingType.NATURAL, 1600),
                new Producer(Sort.LIBERICA, "Kenya", "Farm 4", ProcessingType.HONEY, 2000),
                new Producer(Sort.EXCELSA, "Ethiopia", "Farm 5", ProcessingType.NATURAL, 1500)
        );
        Set<String> result = CoffeeService.getCountriesWithHighAltitude(producers);

        assertEquals(Set.of("Vietnam", "Kenya"), result);
    }
}
