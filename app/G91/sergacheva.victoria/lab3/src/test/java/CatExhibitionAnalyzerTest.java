import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.vsu.amm.java.Breed;
import ru.vsu.amm.java.CatExhibitionAnalyzer;
import ru.vsu.amm.java.CatGenerator;
import ru.vsu.amm.java.CatWinner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CatExhibitionAnalyzerTest {
private List<CatWinner> winners;

@BeforeEach
    public void setup(){
    winners = CatGenerator.generateWinners(20);
}
@Test
    public void testCalculateGenderRatio(){
    double ratio = CatExhibitionAnalyzer.calculateGenderRatio(winners);
    assertTrue(ratio >=0, "The gender ratio should be non-negative");
}

@Test
    public void testCalculateBreedStatistics(){
    List<String> statistics = CatExhibitionAnalyzer.calculateBreedStatistics(winners);
    assertEquals(Breed.values().length, statistics.size(), "There should be statistics for each breed");
        statistics.forEach(start-> assertTrue(start.contains(": "), "Each statistic entry should follow the format 'Breed: count'"));
}

@Test
    public void testGetUniqueFemaleWinners(){
    List<String> femaleWinners = CatExhibitionAnalyzer.getUniqueFemaleWinners(winners);
    assertNotNull(femaleWinners, "The list of female winners should not be null");
    assertTrue(femaleWinners.size() <= winners.size(), "The list of female winners cannot exceed the total number of winners");
}
}
