import org.junit.jupiter.api.Test;
import java.util.*;

import ru.vsu.amm.java.classes.service.Demographics;
import ru.vsu.amm.java.classes.enums.Gender;
import ru.vsu.amm.java.classes.utils.Generator;
import ru.vsu.amm.java.classes.entity.Student;


import static org.junit.jupiter.api.Assertions.*;

public class DemographicsTest {

    @Test
    public void testGenerate() {
        List<Student> students = Generator.generateStud(100);
        assertEquals(100, students.size());
        assertTrue(students.stream().allMatch(s -> s.getMonthOfBirth() > 0 && s.getMonthOfBirth() < 13));
    }

    @Test
    public void testStudPerMonth() {
        List<Student> students = List.of(
                new Student("Ivan", Gender.Male, 1),
                new Student("Anna", Gender.Female, 1),
                new Student("Oleg", Gender.Male, 2),
                new Student("Sveta", Gender.Female, 2),
                new Student("Dima", Gender.Male, 3)
        );

        List<int[]> result = Demographics.studPerMonth(students);

        assertEquals(12, result.size());

        int[] january = result.get(0);
        int[] february = result.get(1);
        int[] march = result.get(2);

        assertEquals(1, january[1]);
        assertEquals(1, january[2]);

        assertEquals(1, february[1]);
        assertEquals(1, february[2]);

        assertEquals(1, march[1]);
        assertEquals(0, march[2]);
    }

    @Test
    public void testStudPerMonthFalse() {
        List<Student> students = List.of(
                new Student("Ivan", Gender.Male, 1),
                new Student("Anna", Gender.Female, 1),
                new Student("Oleg", Gender.Male, 2),
                new Student("Sveta", Gender.Female, 2),
                new Student("Dima", Gender.Male, 3)
        );

        List<int[]> result = Demographics.studPerMonth(students);

        assertEquals(12, result.size());

        int[] january = result.get(0);
        int[] february = result.get(1);
        int[] march = result.get(2);

        assertEquals(0, january[1]);
        assertEquals(0, january[2]);

        assertEquals(1, february[1]);
        assertEquals(0, february[2]);

        assertEquals(0, march[1]);
        assertEquals(1, march[2]);
    }

    @Test
    public void testFemaleMoreThenMale() {
        List<Student> students = List.of(
                new Student("Ivan", Gender.Male, 1),
                new Student("Anna", Gender.Female, 1),
                new Student("Oleg", Gender.Male, 2),
                new Student("Sveta", Gender.Female, 2),
                new Student("Ira", Gender.Female, 2)
        );
        List<Integer> result = Demographics.femaleMoreThenMale(students);

        assertFalse(result.contains(1));
        assertTrue(result.contains(2));
    }

    @Test
    public void testFemaleMoreThenMaleFalse() {
        List<Student> students = List.of(
                new Student("Ivan", Gender.Male, 1),
                new Student("Anna", Gender.Female, 1),
                new Student("Oleg", Gender.Male, 2),
                new Student("Sveta", Gender.Female, 2),
                new Student("Ira", Gender.Female, 2)
        );
        List<Integer> result = Demographics.femaleMoreThenMale(students);

        assertTrue(result.contains(1));
    }
}
