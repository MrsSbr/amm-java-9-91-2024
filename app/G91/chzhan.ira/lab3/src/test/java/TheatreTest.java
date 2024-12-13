
import org.junit.jupiter.api.Test;
import ru.vsu.amm.java.TheatrePerfomance;
import ru.vsu.amm.java.entities.Perfomance;
import ru.vsu.amm.java.entities.Student;
import ru.vsu.amm.java.service.TheatreService;

import java.util.List;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class TheatreTest {
    public static final int NUMBER_PERFOMANCE = 10;
    private static final int STUDENT_INDEX_0 = 0;
    private static final int STUDENT_INDEX_1 = 1;
    private static final int STUDENT_INDEX_2 = 2;

    private List<Student> initializeStudents() {
        List<Student> students = new ArrayList<>();
        students.add(new Student());
        students.add(new Student());
        students.add(new Student());
        students.add(new Student());
        students.get(STUDENT_INDEX_0).getPerfomanceChoices().add(new Perfomance(1));
        students.get(STUDENT_INDEX_0).getPerfomanceChoices().add(new Perfomance(2));
        students.get(STUDENT_INDEX_0).getPerfomanceChoices().add(new Perfomance(3));
        students.get(STUDENT_INDEX_0).getPerfomanceChoices().add(new Perfomance(4));
        students.get(STUDENT_INDEX_1).getPerfomanceChoices().add(new Perfomance(1));
        students.get(STUDENT_INDEX_1).getPerfomanceChoices().add(new Perfomance(2));
        students.get(STUDENT_INDEX_2).getPerfomanceChoices().add(new Perfomance(1));
        students.get(STUDENT_INDEX_2).getPerfomanceChoices().add(new Perfomance(3));

        return students;
    }

    @Test
    void testCountTicketsForPerfomance() {
        List<Student> students = initializeStudents();
        TheatreService theatreService = new TheatreService();
        List<Integer> ticketCounts = theatreService.getCountTicketsForPerfomance(students, NUMBER_PERFOMANCE);
        assertEquals(4, ticketCounts.get(0).intValue());
        assertEquals(2, ticketCounts.get(1).intValue());
        assertEquals(2, ticketCounts.get(2).intValue());
        assertEquals(1, ticketCounts.get(3).intValue());
        for (int i = 4; i < TheatrePerfomance.NUMBER_PERFOMANCE; i++) {
            assertEquals(0, ticketCounts.get(i).intValue());
        }
    }

    @Test
    void testFindMostPopularPerfomance() {
        List<Integer> ticketCounts = List.of(4, 2, 2, 1, 0, 0, 0, 0, 0, 0);
        TheatreService theatreService = new TheatreService();
        List<Integer> mostPopularPerfomances = theatreService.getMostPopularPerfomance(ticketCounts);
        assertEquals(List.of(1), mostPopularPerfomances);
    }

    @Test
    void testFindMostPopularPerfomanceEmpty() {
        TheatreService theatreService = new TheatreService();
        List<Integer> mostPopularPerfomances = theatreService.getMostPopularPerfomance(new ArrayList<>());
        assertTrue(mostPopularPerfomances.isEmpty());
    }

    @Test
    void testFindMostPopularPerfomanceNull() {
        TheatreService theatreService = new TheatreService();
        List<Integer> mostPopularPerfomances = theatreService.getMostPopularPerfomance(null);
        assertNull(mostPopularPerfomances);
    }

    @Test
    void testGetPurchasedPerfomances() {
        List<Student> students = initializeStudents();
        TheatreService theatreService = new TheatreService();
        List<Integer> purchasedPerfomances = theatreService.getPurchasedPerfomances(students);
        assertEquals(List.of(1, 2, 3, 4), purchasedPerfomances);
    }

    @Test
    void testGetPurchasedPerfomancesNull() {
        TheatreService theatreService = new TheatreService();
        List<Integer> purchasedPerfomances = theatreService.getPurchasedPerfomances(null);
        assertNull(purchasedPerfomances);
    }

    @Test
    void testGetPurchasedPerfomancesEmpty() {
        TheatreService theatreService = new TheatreService();
        List<Integer> purchasedPerfomances = theatreService.getPurchasedPerfomances(new ArrayList<>());
        assertTrue(purchasedPerfomances.isEmpty());
    }
}
