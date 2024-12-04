import org.junit.jupiter.api.Test;
import ru.vsu.amm.java.entity.OlympiadRecord;
import ru.vsu.amm.java.enums.Subjects;
import ru.vsu.amm.java.service.OlympiadStatsService;
import ru.vsu.amm.java.util.OlympiadRecordFactory;

import java.time.Year;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
public class OlympidRecordTests {
    private static final List<OlympiadRecord> nullList = null;
    private static final List<OlympiadRecord> emptyList = List.of();
    private static final List<OlympiadRecord> sampleRecords = List.of(
            new OlympiadRecord(Year.of(2020), Subjects.Math, 10, List.of("Student1", "Student2")),
            new OlympiadRecord(Year.of(2021), Subjects.Math, 10, List.of("Student1")),
            new OlympiadRecord(Year.of(2022), Subjects.History, 10, List.of("Student3")),
            new OlympiadRecord(Year.of(2021), Subjects.Chemistry, 11, List.of("Student3")),
            new OlympiadRecord(Year.of(2022), Subjects.Math, 9, List.of("Student1", "Student4"))
    );

    @Test
    public void testFindStudentsWinningEveryYearWithNull() {
        assertThrows(
                IllegalArgumentException.class,
                () -> OlympiadStatsService.findStudentsWinningEveryYear(nullList)
        );
    }

    @Test
    public void testFindStudentsWinningEveryYearWithEmptyList() {
        assertThrows(
                IllegalArgumentException.class,
                () -> OlympiadStatsService.findStudentsWinningEveryYear(emptyList)
        );
    }

    @Test
    public void testFindStudentsWinningEveryYearWithSampleRecords() {
        List<String> winners = OlympiadStatsService.findStudentsWinningEveryYear(sampleRecords);
        assertEquals(List.of("Student1"), winners);
    }

    @Test
    public void testFindWinnersBySubjectLast10YearsWithNull() {
        assertThrows(
                IllegalArgumentException.class,
                () -> OlympiadStatsService.findWinnersBySubjectLast10Years(nullList, Year.now())
        );
    }

    @Test
    public void testFindWinnersBySubjectLast10YearsWithEmptyList() {
        assertThrows(
                IllegalArgumentException.class,
                () -> OlympiadStatsService.findWinnersBySubjectLast10Years(emptyList, Year.now())
        );
    }

    @Test
    public void testFindWinnersBySubjectLast10YearsWithSampleRecords() {
        Map<String, List<String>> winnersBySubject = OlympiadStatsService.findWinnersBySubjectLast10Years(sampleRecords, Year.of(2022));

        assertTrue(winnersBySubject.containsKey("Math"));

        List<String> sortedMathWinners = winnersBySubject.get("Math").stream().sorted().toList();
        List<String> expectedMathWinners = List.of("Student1", "Student2", "Student4").stream().sorted().toList();

        assertEquals(expectedMathWinners, sortedMathWinners);

    }

    @Test
    public void testFindStudentWithMostSubjectsWithNull() {
        assertThrows(
                IllegalArgumentException.class,
                () -> OlympiadStatsService.findStudentWithMostSubjects(nullList)
        );
    }

    @Test
    public void testFindStudentWithMostSubjectsWithEmptyList() {
        assertThrows(
                IllegalArgumentException.class,
                () -> OlympiadStatsService.findStudentWithMostSubjects(emptyList)
        );
    }

    @Test
    public void testFindStudentWithMostSubjectsWithSampleRecords() {
        String topStudent = OlympiadStatsService.findStudentWithMostSubjects(sampleRecords);
        assertEquals("Student3", topStudent);
    }

}
