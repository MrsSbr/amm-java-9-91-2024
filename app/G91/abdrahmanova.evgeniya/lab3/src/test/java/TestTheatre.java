import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import ru.vsu.amm.java.entities.TicketCount;
import ru.vsu.amm.java.enums.PerfomanceName;
import ru.vsu.amm.java.entities.Perfomance;
import ru.vsu.amm.java.service.TheatreService;
import ru.vsu.amm.java.entities.Student;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

public class TestTheatre {

    // список студентов пуст
    @Test
    void getCountTicketsForPerfomance_emptyStudentList_returnsZeroCounts() {
        TheatreService theatreService = new TheatreService();
        List<Student> students = Collections.emptyList();
        List<TicketCount> ticketCounts = theatreService.getCountTicketsForPerfomance(students);

        PerfomanceName.getAllPerfomanceNames().forEach(perfomanceName -> {
            Optional<TicketCount> ticketCount = ticketCounts.stream().filter(t -> t.getPerfomanceName() == perfomanceName).findFirst();
            assertTrue(ticketCount.isPresent());
            assertEquals(0, ticketCount.get().getCount());

        });

        assertEquals(PerfomanceName.values().length, ticketCounts.size());
    }

    // количество билетов пусто
    @Test
    void getMostPopularPerfomance_emptyList_returnsEmptyList() {
        TheatreService theatreService = new TheatreService();
        List<PerfomanceName> mostPopular = theatreService.getMostPopularPerfomance(Collections.emptyList());
        assertTrue(mostPopular.isEmpty());
    }

    // список студентов пуст
    @Test
    void getPurchasedPerfomances_nullStudents_returnsNull() {
        TheatreService theatreService = new TheatreService();
        List<PerfomanceName> purchased = theatreService.getPurchasedPerfomances(null);
        assertNull(purchased);
    }

    // все доступные спектакли
    @Test
    void getCountTicketsForPerfomance_studentWithAllChoices_returnsCorrectCounts() {
        Student student = new Student(null);
        List<PerfomanceName> allPerformances = PerfomanceName.getAllPerfomanceNames();

        student.setPerfomanceChoice(allPerformances.stream()
                .map(Perfomance::new)
                .collect(Collectors.toList()));

        List<Student> students = Collections.singletonList(student);

        List<TicketCount> ticketCounts = TheatreService.getCountTicketsForPerfomance(students);

        allPerformances.forEach(perfomanceName -> {
            Optional<TicketCount> ticketCount = ticketCounts.stream().filter(t -> t.getPerfomanceName() == perfomanceName).findFirst();
            assertTrue(ticketCount.isPresent());
            assertEquals(1, ticketCount.get().getCount());
        });
    }

    // один и тот же спектакль несколько раз
    @Test
    void getCountTicketsForPerfomance_studentWithDuplicateChoices_returnsCorrectCounts() {
        Student student = new Student(null);
        student.setPerfomanceChoice(Arrays.asList(PerfomanceName.NIGTH_BEFORE_CHRISTMAS, PerfomanceName.NIGTH_BEFORE_CHRISTMAS, PerfomanceName.SHELCUNCHIC).stream().map(Perfomance::new).collect(Collectors.toList()));

        List<Student> students = Collections.singletonList(student);


        List<TicketCount> ticketCounts = TheatreService.getCountTicketsForPerfomance(students);

        Optional<TicketCount> nightBeforeChristmasCount = ticketCounts.stream()
                .filter(tc -> tc.getPerfomanceName() == PerfomanceName.NIGTH_BEFORE_CHRISTMAS)
                .findFirst();
        assertTrue(nightBeforeChristmasCount.isPresent());
        assertEquals(2, nightBeforeChristmasCount.get().getCount());


        Optional<TicketCount> shelcunchicCount = ticketCounts.stream()
                .filter(tc -> tc.getPerfomanceName() == PerfomanceName.SHELCUNCHIC)
                .findFirst();

        assertTrue(shelcunchicCount.isPresent());
        assertEquals(1, shelcunchicCount.get().getCount());

        PerfomanceName.getAllPerfomanceNames().forEach(perfomanceName -> {
            if (perfomanceName != PerfomanceName.NIGTH_BEFORE_CHRISTMAS && perfomanceName != PerfomanceName.SHELCUNCHIC) {
                Optional<TicketCount> ticketCount = ticketCounts.stream().filter(t -> t.getPerfomanceName() == perfomanceName).findFirst();
                assertTrue(ticketCount.isPresent());
                assertEquals(0, ticketCount.get().getCount());
            }
        });
        assertEquals(PerfomanceName.values().length, ticketCounts.size());
    }

    // один спектакль с наибольшим количеством билетов
    @Test
    void getMostPopularPerfomance_singleMax_returnsCorrectPerfomance() {
        List<Integer> ticketsCount = Arrays.asList(2, 1, 3, 1);
        TheatreService theatreService = new TheatreService();
        List<PerfomanceName> mostPopular = theatreService.getMostPopularPerfomance(ticketsCount);
        assertEquals(1, mostPopular.size());
        assertEquals(PerfomanceName.values()[2], mostPopular.get(0));
    }

    // несколько спектаклей с одинаковым (максимальным) количеством билетов
    @Test
    void getMostPopularPerfomance_multipleMax_returnsCorrectPerformances() {
        List<Integer> ticketsCount = Arrays.asList(2, 3, 3, 1);
        TheatreService theatreService = new TheatreService();
        List<PerfomanceName> mostPopular = theatreService.getMostPopularPerfomance(ticketsCount);

        assertEquals(2, mostPopular.size());
        assertTrue(mostPopular.contains(PerfomanceName.values()[1]));
        assertTrue(mostPopular.contains(PerfomanceName.values()[2]));
    }

    // один студент - один выбор
    @Test
    void getPurchasedPerfomances_singleStudentSingleChoice_returnsCorrectPerfomance() {
        Student student = new Student(null);
        student.setPerfomanceChoice(Collections.singletonList(new Perfomance(PerfomanceName.HAMLET)));
        List<Student> students = Collections.singletonList(student);

        List<PerfomanceName> purchased = TheatreService.getPurchasedPerfomances(students);
        assertEquals(1, purchased.size());
        assertEquals(PerfomanceName.HAMLET, purchased.get(0));
    }

    // выбирают одинаковые спектакли
    @Test
    void getPurchasedPerfomances_duplicateChoices_returnsCorrectPerformances() {
        Student student1 = new Student(null);
        student1.setPerfomanceChoice(Arrays.asList(new Perfomance(PerfomanceName.HAMLET), new Perfomance(PerfomanceName.SHELCUNCHIC)));
        Student student2 = new Student(null);
        student2.setPerfomanceChoice(Arrays.asList(new Perfomance(PerfomanceName.HAMLET), new Perfomance(PerfomanceName.SHELCUNCHIC)));
        List<Student> students = Arrays.asList(student1, student2);
        List<PerfomanceName> purchased = TheatreService.getPurchasedPerfomances(students);

        assertEquals(2, purchased.size());
        assertEquals(PerfomanceName.HAMLET, purchased.get(0));
        assertEquals(PerfomanceName.SHELCUNCHIC, purchased.get(1));
    }
}
