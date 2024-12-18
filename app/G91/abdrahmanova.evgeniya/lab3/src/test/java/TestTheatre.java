import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import ru.vsu.amm.java.enums.PerfomanceName;
import ru.vsu.amm.java.entities.Perfomance;
import ru.vsu.amm.java.service.TheatreService;
import ru.vsu.amm.java.entities.Student;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class TestTheatre {

    @Test //на пустой список студентов
    void testGetCountTicketsForPerfomance_emptyInput() {
        TheatreService service = new TheatreService();
        List<Integer> counts = service.getCountTicketsForPerfomance(new ArrayList<>());
        assertEquals(Arrays.asList(0, 0, 0, 0, 0, 0, 0, 0, 0, 0), counts);
    }

    @Test
    void testGetMostPopularPerfomance_emptyList() {
        TheatreService service = new TheatreService();
        List<Integer> emptyList = new ArrayList<>();
        List<Perfomance> result = service.getMostPopularPerfomance(emptyList);
        assertTrue(result.isEmpty());
    }

    @Test
    void testGetPurchasedPerfomances_allPerformances() {
        TheatreService service = new TheatreService();
        assertNull(service.getPurchasedPerfomances(null));
        assertNull(service.getPurchasedPerfomances(new ArrayList<>()));

        List<Student> students = new ArrayList<>();
        for (PerfomanceName pn : PerfomanceName.values()) {
            students.add(new Student(Collections.singletonList(new Perfomance(pn))));
        }

        List<PerfomanceName> purchased = service.getPurchasedPerfomances(students);
        List<PerfomanceName> expected = Arrays.asList(PerfomanceName.values());
        Collections.sort(expected, Comparator.comparing(PerfomanceName::name));
        assertEquals(expected, purchased);
    }
}


