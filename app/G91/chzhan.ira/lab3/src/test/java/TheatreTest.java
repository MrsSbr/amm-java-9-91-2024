
import org.junit.jupiter.api.Test;
import ru.vsu.amm.java.TheatrePerfomance;
import ru.vsu.amm.java.entities.Perfomance;
import ru.vsu.amm.java.entities.Student;
import ru.vsu.amm.java.service.TheatreService;
import java.util.List;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;
public class TheatreTest {
    @Test
    void testCountTicketsForPerfomance() {
        List<Student> students = new ArrayList<>();
        students.add(new Student());
        students.add(new Student());
        students.add(new Student());
        students.add(new Student());
        students.get(0).getPerfomanceChoices().add(new Perfomance(1));
        students.get(0).getPerfomanceChoices().add(new Perfomance(2));
        students.get(0).getPerfomanceChoices().add(new Perfomance(3));
        students.get(0).getPerfomanceChoices().add(new Perfomance(4));
        students.get(1).getPerfomanceChoices().add(new Perfomance(1));
        students.get(1).getPerfomanceChoices().add(new Perfomance(2));
        students.get(2).getPerfomanceChoices().add(new Perfomance(1));
        students.get(2).getPerfomanceChoices().add(new Perfomance(3));

        TheatreService theatreService = new TheatreService();
        List<Integer> ticketCounts = theatreService.getCountTicketsForPerfomance(students);
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
    void testGetPurchasedPerfomances() {
        List<Student> students = new ArrayList<>();
        students.add(new Student());
        students.add(new Student());
        students.add(new Student());
        students.add(new Student());
        students.get(0).getPerfomanceChoices().add(new Perfomance(1));
        students.get(0).getPerfomanceChoices().add(new Perfomance(2));
        students.get(0).getPerfomanceChoices().add(new Perfomance(3));
        students.get(0).getPerfomanceChoices().add(new Perfomance(4));
        students.get(1).getPerfomanceChoices().add(new Perfomance(1));
        students.get(1).getPerfomanceChoices().add(new Perfomance(2));
        students.get(2).getPerfomanceChoices().add(new Perfomance(1));
        students.get(2).getPerfomanceChoices().add(new Perfomance(3));

        TheatreService theatreService = new TheatreService();
        List<Integer> purchasedPerfomances = theatreService.getPurchasedPerfomances(students);
        assertEquals(List.of(1, 2, 3, 4), purchasedPerfomances);
    }
}
