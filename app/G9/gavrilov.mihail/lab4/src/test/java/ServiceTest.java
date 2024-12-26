import org.junit.jupiter.api.Test;
import ru.vsu.amm.java.main.Enum.KindOfSport;
import ru.vsu.amm.java.main.OlympicRecord.OlympicMedalsRecord;
import ru.vsu.amm.java.main.Service.OlympicStatsService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.vsu.amm.java.main.Enum.KindOfSport.*;

public class ServiceTest {
    private final OlympicStatsService service = new OlympicStatsService();


    private final List<OlympicMedalsRecord> nullEntities = null;

    @Test
    public void NullTopMedalCountryResult() {
        List<String> result = service.TopMedalCountryResult(nullEntities);
        assertEquals(result, new ArrayList<>());
    }

    @Test
    public void OneCountryTopMedalCountryResult() {
        List<OlympicMedalsRecord> data = List.of(
                new OlympicMedalsRecord("Russia", SWIMMING, "Feshenko", 1),
                new OlympicMedalsRecord("Russia", RUNNING, "Gavrilov", 2));
        List<String> result = service.TopMedalCountryResult(data);

        assertEquals(1, result.size());

    }

    @Test
    public void ManyCountriesTopMedalCountryResult() { //Если количество стран, которые
        //могут войти в топ > 3
        List<OlympicMedalsRecord> data = List.of(
                new OlympicMedalsRecord("Russia", SWIMMING, "Kurpatov", 2),
                new OlympicMedalsRecord("Italy", HIGH_JUMP, "Artelli", 3),
                new OlympicMedalsRecord("America", SWIMMING, "Feshenko", 1),
                new OlympicMedalsRecord("Russia", RUNNING, "Gavrilov", 2),
                new OlympicMedalsRecord("Kazakhstan", RUNNING, "Gadziev", 3)
        );

        List<String> result = service.TopMedalCountryResult(data);

        assertEquals(4, result.size());

    }

    @Test
    public void NullAthleteWithMaxMedals() {

        List<String> result = service.AthleteWithMaxMedals(nullEntities);
        assertEquals(result, new ArrayList<>());
    }

    @Test
    public void OneAthleteWithMaxMedals() {
        List<OlympicMedalsRecord> data = List.of(
                new OlympicMedalsRecord("Russia", SWIMMING, "Kurpatov", 2),
                new OlympicMedalsRecord("Russia", SWIMMING, "Kurpatov", 3),
                new OlympicMedalsRecord("Russia", SWIMMING, "Feshenko", 1));
        List<String> result = service.AthleteWithMaxMedals(data);
        List<String> trueList = List.of("Kurpatov");
        assertEquals(result, trueList);
    }

    @Test
    public void ManyAthleteWithMaxMedals() {
        List<OlympicMedalsRecord> data = List.of(
                new OlympicMedalsRecord("Russia", SWIMMING, "Kurpatov", 2),
                new OlympicMedalsRecord("Russia", SWIMMING, "Kurpatov", 3),
                new OlympicMedalsRecord("Russia", RUNNING, "Gavrilov", 1),
                new OlympicMedalsRecord("Russia", HIGH_JUMP, "Gavrilov", 2),
                new OlympicMedalsRecord("Italy", SWIMMING, "Arnes", 3));
        List<String> result = service.AthleteWithMaxMedals(data);
        List<String> trueList = List.of("Kurpatov", "Gavrilov");
        assertEquals(result, trueList);
    }

    @Test
    public void NullAthletesWithMedalsAndSport() {
        Map<KindOfSport, List<String>> result = service.AthletesWithMedalsAndSport(nullEntities);
        assertEquals(result, new HashMap<>());
    }

    @Test //один атлет и много видов спорта
    public void OneAthleteAndManySportsAthletesWithMedalsAndSport() {
        List<OlympicMedalsRecord> data = List.of(
                new OlympicMedalsRecord("Russia", SWIMMING, "Gavrilov", 2),
                new OlympicMedalsRecord("Russia", RUNNING, "Gavrilov", 1),
                new OlympicMedalsRecord("Russia", HIGH_JUMP, "Gavrilov", 2),
                new OlympicMedalsRecord("Russia", BOXING, "Gavrilov", 3));
        Map<KindOfSport, List<String>> result = service.AthletesWithMedalsAndSport(data);
        Map<KindOfSport, List<String>> trueList = Map.of(
                SWIMMING, new ArrayList<String>(List.of("Gavrilov")),
                BOXING, new ArrayList<String>(List.of("Gavrilov")),
                HIGH_JUMP, new ArrayList<String>(List.of("Gavrilov")),
                RUNNING, new ArrayList<String>(List.of("Gavrilov")));
        assertEquals(result, trueList);
    }

    @Test //один вид спорта и много атлетов
    public void ManyAthletesAndOneSportAthletesWithMedalsAndSport() {
        List<OlympicMedalsRecord> data = List.of(
                new OlympicMedalsRecord("Russia", SWIMMING, "Gavrilov", 2),
                new OlympicMedalsRecord("Russia", SWIMMING, "Feshenko", 1),
                new OlympicMedalsRecord("Russia", SWIMMING, "Artemov", 2),
                new OlympicMedalsRecord("Italy", SWIMMING, "Kaevskiy", 3));

        Map<KindOfSport, List<String>> result = service.AthletesWithMedalsAndSport(data);
        Map<KindOfSport, List<String>> trueList = Map.of(
                SWIMMING, new ArrayList<String>(List.of("Gavrilov",
                        "Feshenko", "Artemov", "Kaevskiy")));
        assertEquals(result, trueList);
    }
}
