import org.junit.jupiter.api.Test;
import ru.vsu.amm.java.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import java.util.Set;



public class EvaluationServiceTest {

    @Test
    void testCountHelpfulEvaluations() {
        List<Evaluation> evaluations = List.of(
                new Evaluation(5, Course.MATHEMATICAL_ANALYSIS, "abc", false),
                new Evaluation(4, Course.MATHEMATICAL_ANALYSIS, "def", true),
                new Evaluation(3, Course.MATHEMATICAL_ANALYSIS, "ghi", false)
        );
        long count = EvaluationService.countHelpfulEvaluations(Course.MATHEMATICAL_ANALYSIS, evaluations);
        assertEquals(1, count);
    }

    @Test
    void testNoHelpfulEvaluationsForDifferentCourse() {
        List<Evaluation> evaluations = List.of(
                new Evaluation(3, Course.ALGEBRA, "abc", false),
                new Evaluation(4, Course.ALGEBRA, "def", false),
                new Evaluation(5, Course.ALGEBRA, "ghi", false)
        );
        long count = EvaluationService.countHelpfulEvaluations(Course.COMPUTER_SCIENCE, evaluations);
        assertEquals(0, count);
    }

    @Test
    void testCoursesWithHighestScore() {
        List<Evaluation> evaluations = List.of(
                new Evaluation(5, Course.ALGEBRA, "abc", true),
                new Evaluation(3, Course.COMPUTER_SCIENCE, "def", false),
                new Evaluation(5, Course.FUNCTIONAL_ANALYSIS, "ghi", true)
        );
        Set<Course> expected = Set.of(Course.ALGEBRA, Course.FUNCTIONAL_ANALYSIS);
        Set<Course> result = EvaluationService.coursesWithHighestScore(evaluations);
        assertEquals(expected, result);
    }

    @Test
    void testCoursesWithHighestScoreEmptyList() {
        Set<Course> result = EvaluationService.coursesWithHighestScore(List.of());
        assertTrue(result.isEmpty());
    }

    @Test
    void testCountNotHelpfulEvaluations() {
        List<Evaluation> evaluations = List.of(
                new Evaluation(2, Course.NUMERICAL_METHODS, "abc", false),
                new Evaluation(2, Course.DIFFERENTIAL_EQUATIONS, "def", false),
                new Evaluation(3, Course.PROBABILITY_THEORY, "ghi", false)
        );
        long count = EvaluationService.countNotHelpfulEvaluations(evaluations);
        assertEquals(3, count);
    }

    @Test
    void testCountNotHelpfulEvaluationsWhenAllHelpful() {
        List<Evaluation> evaluations = List.of(
                new Evaluation(5, Course.MATHEMATICAL_ANALYSIS, "abc", true),
                new Evaluation(5, Course.PROBABILITY_THEORY, "def", true),
                new Evaluation(5, Course.ALGEBRA, "ghi", true)
        );
        long count = EvaluationService.countNotHelpfulEvaluations(evaluations);
        assertEquals(0, count);
    }
}
