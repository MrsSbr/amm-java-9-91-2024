import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import ru.vsu.amm.java.enums.KindOfSport;
import ru.vsu.amm.java.util.MedalFactory;
import ru.vsu.amm.java.enums.Country;
import ru.vsu.amm.java.entity.Medal;
import ru.vsu.amm.java.analyzer.MedalAnalyzer;

public class MedalTest {
    private MedalAnalyzer medalAnalyzer;
    private List<Medal> medals;
    private List<Medal> medalsGenerate;

    @BeforeEach
    void setUp() {
        medalAnalyzer = new MedalAnalyzer();
        medals = List.of(
                new Medal(Country.USA, KindOfSport.ATHLETICS, "John Doe", 1),
                new Medal(Country.USA, KindOfSport.BASKETBALL, "John Doe", 2),
                new Medal(Country.CHN, KindOfSport.SWIMMING, "Jane Smith", 1),
                new Medal(Country.CHN, KindOfSport.CYCLING, "Lory Smith", 1),
                new Medal(Country.JPN, KindOfSport.FENCING, "Mike Brown", 3)
        );
        medalsGenerate = MedalFactory.generateMedal();
    }

    @Test
    public void testGenerateMedal() {
        List<Medal> medals = MedalFactory.generateMedal();
        assertNotNull(medals);
        assertEquals(MedalFactory.COUNT_MEDALS, medals.size());
    }

    @Test
    public void testMedalCreation() {
        Medal medal = new Medal(Country.USA, KindOfSport.ATHLETICS, "John Doe", 1);
        assertNotNull(medal);
        assertEquals(Country.USA, medal.getCountry());
        assertEquals(KindOfSport.ATHLETICS, medal.getKindOfSport());
        assertEquals("John Doe", medal.getAthlete());
        assertEquals(1, medal.getPlace());
    }

    @Test
    public void testToString(){
        Medal medal = new Medal(Country.USA, KindOfSport.ATHLETICS, "John Doe", 1);
        String expected = "USA;ATHLETICS;John Doe;1";
        assertEquals(expected, medal.toString());
    }

    @Test
    public void testAthleteWithTheMostMedals(){
        List<String> topAthletes = medalAnalyzer.athleteWithTheMostMedals(medals);
        assertEquals(1, topAthletes.size());
        assertEquals("John Doe", topAthletes.get(0));
    }

    @Test
    public void testTopThreeInMedals(){
        List<Country> topThree = medalAnalyzer.topThreeInMedals(medals);
        assertEquals(3, topThree.size());
        assertTrue(topThree.contains(Country.USA));
        assertTrue(topThree.contains(Country.CHN));
        assertTrue(topThree.contains(Country.JPN));
    }

    @Test
    public void testAthletesWhoTookPlaces() {
        Map<KindOfSport, List<String>> athletesBySport = medalAnalyzer.athletesWhoTookPlaces(medals);
        assertEquals(5, athletesBySport.size());

        assertEquals(List.of("John Doe"), athletesBySport.get(KindOfSport.ATHLETICS));
        assertEquals(List.of("Jane Smith"), athletesBySport.get(KindOfSport.SWIMMING));
        assertEquals(List.of("John Doe"), athletesBySport.get(KindOfSport.BASKETBALL));
        assertEquals(List.of("Lory Smith"), athletesBySport.get(KindOfSport.CYCLING));
        assertEquals(List.of("Mike Brown"), athletesBySport.get(KindOfSport.FENCING));
    }

    @Test
    public void testAthleteWithTheMostMedalsEmpty(){
        medals = Collections.emptyList();
        List<Country> topAthletes = medalAnalyzer.topThreeInMedals(medals);
        assertTrue(topAthletes.isEmpty());
    }


    @Test
    public void testAthleteWithTheMostMedalsGenerate(){
        List<String> topAthletes = medalAnalyzer.athleteWithTheMostMedals(medalsGenerate);
        assertNotNull(topAthletes);
        assertFalse(topAthletes.isEmpty());
    }

    @Test
    public void testTopThreeInMedalsGenerate(){
        List<Country> topThree = medalAnalyzer.topThreeInMedals(medalsGenerate);
        assertNotNull(topThree);
        assertEquals(3, topThree.size());
    }

    @Test
    public void testAthletesWhoTookPlacesGenerate() {
        Map<KindOfSport, List<String>> athletesBySport = medalAnalyzer.athletesWhoTookPlaces(medalsGenerate);
        assertEquals(5, athletesBySport.size());
        athletesBySport.values().forEach(list -> assertEquals(3, list.size()));
    }
}
