import org.junit.jupiter.api.Test;
import ru.vsu.amm.java.Fio;
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
                new Review(5, Subjects.MATHEMATICAL_ANALYSIS, Fio.AKSENOV_MAKSIM_ALEKSANDROVICH, false),
                new Review(4, Subjects.MATHEMATICAL_ANALYSIS, Fio.GADSHIEV_MAKSIM_DENISOVICH, true),
                new Review(5, Subjects.MATHEMATICAL_ANALYSIS, Fio.GARSHIN_MAKSIM_SERGEEVICH, false)
        );

        int result = Service.numberUsefulReviews(Subjects.MATHEMATICAL_ANALYSIS, reviews);
        assertEquals(1, result);
    }

    @Test
    void testNoneOfManyStudentsFoundMathematicalAnaLysisUseful() {
        List<Review> reviews = List.of(
                new Review(4, Subjects.MATHEMATICAL_ANALYSIS, Fio.KANATNIKOV_MAKSIM_ANDREEVICH, false),
                new Review(4, Subjects.MATHEMATICAL_ANALYSIS, Fio.KIIKO_MAKSIM_VADIMOVICH, false),
                new Review(4, Subjects.MATHEMATICAL_ANALYSIS, Fio.POZNDIKOV_MAKSIM_MAKSIMOVICH, false)
        );

        int result = Service.numberUsefulReviews(Subjects.ALGEBRA, reviews);
        assertEquals(0, result);
    }

    @Test
    void testSubjectHighestScoreAlgebraAndFunctionalAnalysis() {
        List<Review> reviews = List.of(
                new Review(5, Subjects.ALGEBRA, Fio.KIIKO_MAKSIM_VADIMOVICH, true),
                new Review(3, Subjects.COMPUTER_SCIENCE, Fio.GADSHIEV_MAKSIM_DENISOVICH, false),
                new Review(4, Subjects.FUNCTIONAL_ANALYSIS, Fio.AKSENOV_MAKSIM_ALEKSANDROVICH, true)
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
                new Review(2, Subjects.NUMERICAL_METHODS, Fio.POZNDIKOV_MAKSIM_MAKSIMOVICH, false),
                new Review(2, Subjects.DIFFERENTIAL_EQUATIONS, Fio.GARSHIN_MAKSIM_SERGEEVICH, false),
                new Review(3, Subjects.PROBABILITY_THEORY, Fio.KANATNIKOV_MAKSIM_ANDREEVICH, false)
        );

        int result = Service.unusefulAllSubjects(reviews);
        assertEquals(3, result);
    }

    @Test
    void testAllStudentsRatedAllItemsPositively() {
        List<Review> reviews = List.of(
                new Review(5, Subjects.MATHEMATICAL_ANALYSIS, Fio.AKSENOV_MAKSIM_ALEKSANDROVICH, true),
                new Review(5, Subjects.PROBABILITY_THEORY, Fio.GADSHIEV_MAKSIM_DENISOVICH, true),
                new Review(5, Subjects.ALGEBRA, Fio.POZNDIKOV_MAKSIM_MAKSIMOVICH, true)
        );

        int result = Service.unusefulAllSubjects(reviews);
        assertEquals(0, result);
    }
}
