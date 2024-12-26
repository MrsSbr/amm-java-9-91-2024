import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.vsu.amm.java.Breed;
import ru.vsu.amm.java.CatExhibitionAnalyzer;
import ru.vsu.amm.java.CatWinner;
import ru.vsu.amm.java.Gender;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CatExhibitionAnalyzerTest {
    private List<CatWinner> winners;

    @BeforeEach
    public void setup() {
        winners = Arrays.asList(
                new CatWinner("Kitty", Breed.SIAMESE, Gender.FEMALE),
                new CatWinner("Tom", Breed.PERSIAN, Gender.MALE),
                new CatWinner("Milo", Breed.SIAMESE, Gender.MALE),
                new CatWinner("Luna", Breed.MAINE_COON, Gender.FEMALE),
                new CatWinner("Oliver", Breed.SPHYNX, Gender.MALE)
        );
    }

    @Test
    public void testCalculateGenderRatio() {
        setup();
        double ratio = CatExhibitionAnalyzer.calculateGenderRatio(winners);
        assertEquals(3.0 / 2.0, ratio, 0.01, "The gender ratio should be correctly calculated");
    }

    @Test
    public void testCalculateBreedStatistics() {
        List<String> statistics = CatExhibitionAnalyzer.calculateBreedStatistics(winners);

        assertEquals(4, statistics.size(), "Statistics should include all breeds");
        assertTrue(statistics.contains("SIAMESE: 2"), "SIAMESE count should be 2");
        assertTrue(statistics.contains("PERSIAN: 1"), "PERSIAN count should be 1");
        assertTrue(statistics.contains("MAINE_COON: 1"), "MAINE_COON count should be 1");
        assertTrue(statistics.contains("SPHYNX: 1"), "SPHYNX count should be 1");
    }

    @Test
    public void testGetWinnerList() {
        winners = Collections.emptyList();

        double ratio = CatExhibitionAnalyzer.calculateGenderRatio(winners);
        assertEquals(0, ratio, "The gender ratio should be 0 for an empty list");

        List<String> statistics = CatExhibitionAnalyzer.calculateBreedStatistics(winners);
        assertTrue(statistics.isEmpty(), "Breed statistics should be empty for an empty list");

        Set<String> femaleWinners = CatExhibitionAnalyzer.getUniqueFemaleWinners(winners);
        assertTrue(femaleWinners.isEmpty(), "The list of female winners should be empty for an empty list");
    }

    @Test
    public void testAllMales() {
        winners = Arrays.asList(
                new CatWinner("Tom", Breed.SIAMESE, Gender.MALE),
                new CatWinner("Milo", Breed.PERSIAN, Gender.MALE)
        );

        double ratio = CatExhibitionAnalyzer.calculateGenderRatio(winners);
        assertEquals(Double.POSITIVE_INFINITY, ratio, "The gender ratio should be infinite when there aren't females");

        Set<String> femaleWinners = CatExhibitionAnalyzer.getUniqueFemaleWinners(winners);
        assertTrue(femaleWinners.isEmpty(), "The list of female winners should be empty when there aren't females");
    }

    @Test
    public void testAllFemales() {
        winners = Arrays.asList(
                new CatWinner("Kitty", Breed.SIAMESE, Gender.FEMALE),
                new CatWinner("Luna", Breed.MAINE_COON, Gender.FEMALE)
        );

        double ratio = CatExhibitionAnalyzer.calculateGenderRatio(winners);
        assertEquals(0, ratio, "The gender ratio should be 0 when there aren't males");

        Set<String> femaleWinners = CatExhibitionAnalyzer.getUniqueFemaleWinners(winners);
        assertEquals(2, femaleWinners.size(), "The list of female winners should contain all female names");
    }

    @Test
    public void testInvalidData() {
        winners = Arrays.asList(
                new CatWinner(null, Breed.SIAMESE, Gender.FEMALE),
                new CatWinner("Tom", null, Gender.MALE),
                new CatWinner("Milo", Breed.SIAMESE, null)
        );

        assertThrows(IllegalArgumentException.class, () -> CatExhibitionAnalyzer.calculateGenderRatio(winners),
                "Should throw exception for null fields in data");

        assertThrows(IllegalArgumentException.class, () -> CatExhibitionAnalyzer.calculateBreedStatistics(winners),
                "Should throw exception for null fields in data");
    }

    @Test
    public void testNullList() {

        assertThrows(IllegalArgumentException.class, () -> CatExhibitionAnalyzer.calculateGenderRatio(null),
                "Should throw NullPointerException for null list");

    }
}

