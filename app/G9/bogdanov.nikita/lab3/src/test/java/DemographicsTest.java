import org.junit.jupiter.api.Test;

import ru.vsu.amm.java.classes.entity.DataPerMonth;
import ru.vsu.amm.java.classes.service.Demographics;
import ru.vsu.amm.java.classes.enums.Gender;
import ru.vsu.amm.java.classes.utils.Generator;
import ru.vsu.amm.java.classes.entity.Student;

import java.util.List;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.*;

public class DemographicsTest {

    @Test
    public void testGenerate() {
        List<Student> students = Generator.generateStud(100);
        assertEquals(100, students.size());
    }

    @Test
    public void testStudPerMonth() {
        List<Student> students = List.of(
                new Student("Ivan", Gender.Male, Month.JANUARY),
                new Student("Anna", Gender.Female, Month.JANUARY),
                new Student("Oleg", Gender.Male, Month.FEBRUARY),
                new Student("Sveta", Gender.Female, Month.JANUARY),
                new Student("Dima", Gender.Male, Month.MARCH)
        );

        List<DataPerMonth> result = Demographics.studPerMonth(students);

        assertEquals(12, result.size());

        DataPerMonth january = result.get(0);
        DataPerMonth february = result.get(1);
        DataPerMonth march = result.get(2);

        assertEquals(1, january.getMele());
        assertEquals(1, january.getFemale());

        assertEquals(1, february.getMele());
        assertEquals(1, february.getFemale());

        assertEquals(1, march.getMele());
        assertEquals(0, march.getFemale());
    }

    @Test
    public void testStudPerMonthFalse() {
        List<Student> students = List.of(
                new Student("Ivan", Gender.Male, Month.JANUARY),
                new Student("Anna", Gender.Female, Month.JANUARY),
                new Student("Oleg", Gender.Male, Month.FEBRUARY),
                new Student("Sveta", Gender.Female, Month.JANUARY),
                new Student("Dima", Gender.Male, Month.MARCH)
        );

        List<DataPerMonth> result = Demographics.studPerMonth(students);

        assertEquals(12, result.size());

        DataPerMonth january = result.get(0);
        DataPerMonth february = result.get(1);
        DataPerMonth march = result.get(2);

        assertEquals(0, january.getMele());
        assertEquals(0, january.getFemale());

        assertEquals(0, february.getMele());
        assertEquals(1, february.getFemale());

        assertEquals(0, march.getMele());
        assertEquals(1, march.getFemale());
    }

    @Test
    public void testFemaleMoreThenMale() {
        List<Student> students = List.of(
                new Student("Ivan", Gender.Male, Month.JANUARY),
                new Student("Anna", Gender.Female, Month.JANUARY),
                new Student("Oleg", Gender.Male, Month.FEBRUARY),
                new Student("Sveta", Gender.Female, Month.FEBRUARY),
                new Student("Ira", Gender.Female, Month.FEBRUARY)
        );
        List<Month> result = Demographics.femaleMoreThenMale(students);

        assertFalse(result.contains(Month.of(1)));
        assertTrue(result.contains(Month.of(2)));
    }

    @Test
    public void testFemaleMoreThenMaleFalse() {
        List<Student> students = List.of(
                new Student("Ivan", Gender.Male, Month.JANUARY),
                new Student("Anna", Gender.Female, Month.JANUARY),
                new Student("Oleg", Gender.Male, Month.FEBRUARY),
                new Student("Sveta", Gender.Female, Month.FEBRUARY),
                new Student("Ira", Gender.Female, Month.FEBRUARY)
        );
        List<Month> result = Demographics.femaleMoreThenMale(students);

        assertTrue(result.contains(Month.of(1)));
    }
}
