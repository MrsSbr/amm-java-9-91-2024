import org.junit.jupiter.api.Test;
import ru.vsu.amm.java.Review;
import ru.vsu.amm.java.Service;
import ru.vsu.amm.java.Subjects;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SubjectsTest {
    @Test
    void testHowManyStudentsFoundMathematicalAnaLysisUseful() {
        List<Review> reviews = List.of(
                new Review(5, Subjects.MATHEMATICAL_ANALYSIS, "Гаршин Максим Сергеевич", false),
                new Review(4, Subjects.MATHEMATICAL_ANALYSIS, "Гаджиев Максим Денисович", true),
                new Review(5, Subjects.MATHEMATICAL_ANALYSIS,  "Аксенов Максим Александрович", false)
        );

        int result = Service.numberUsefulReviews(Subjects.MATHEMATICAL_ANALYSIS, reviews);
        assertEquals(1, result);
    }

    @Test
    void testNoneOfManyStudentsFoundMathematicalAnaLysisUseful() {
        List<Review> reviews = List.of(
                new Review(4, Subjects.MATHEMATICAL_ANALYSIS, "Кийко Максим Вадимович", false),
                new Review(4, Subjects.MATHEMATICAL_ANALYSIS, "Канатников Максим Андреевич", false),
                new Review(4, Subjects.MATHEMATICAL_ANALYSIS,"Поздников Максим Максимович", false)
        );

        int result = Service.numberUsefulReviews(Subjects.ALGEBRA, reviews);
        assertEquals(0, result);
    }

    @Test
    void testSubjectHighestScoreAlgebraAndFunctionalAnalysis() {
        List<Review> reviews = List.of(
                new Review(5, Subjects.ALGEBRA, "Поздников Максим Максимович", true),
                new Review(3, Subjects.COMPUTER_SCIENCE, "Гаджиев Максим Денисович", false),
                new Review(5, Subjects.FUNCTIONAL_ANALYSIS, "Кийко Максим Вадимович", true)
        );

        Set<Subjects> result = Service.subjectsWithMaxMark(reviews);
        assertEquals(Set.of(Subjects.ALGEBRA, Subjects.FUNCTIONAL_ANALYSIS), result);
    }

    @Test
    void testSubjectHighestScoreEmpty() {
        Set<Subjects> result = Service.subjectsWithMaxMark(List.of());
        assertTrue(result.isEmpty());
    }

    @Test
    void testAllStudentsNoRateNotOneSubjectPositively() {
        List<Review> reviews = List.of(
                new Review(2, Subjects.NUMERICAL_METHODS,"Гаршин Максим Сергеевич",  false),
                new Review(2, Subjects.DIFFERENTIAL_EQUATIONS, "Канатников Максим Андреевич", false),
                new Review(3, Subjects.PROBABILITY_THEORY, "Аксенов Максим Александрович", false)
        );

        int result = Service.unusefulAllSubjects(reviews);
        assertEquals(3, result);
    }

    @Test
    void testAllStudentsRatedAllItemsPositively() {
        List<Review> reviews = List.of(
                new Review(5, Subjects.MATHEMATICAL_ANALYSIS, "Канатников Максим Андреевич", true),
                new Review(5, Subjects.PROBABILITY_THEORY, "Кийко Максим Вадимович", true),
                new Review(5, Subjects.ALGEBRA, "Поздников Максим Максимович", true)
        );

        int result = Service.unusefulAllSubjects(reviews);
        assertEquals(0, result);
    }
}
