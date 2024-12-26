package ru.vsu.amm.java.main;

import ru.vsu.amm.java.main.FileReader.FileReader;
import ru.vsu.amm.java.main.OlympicRecord.OlympicMedalsRecord;
import ru.vsu.amm.java.main.Service.OlympicStatsService;

import java.util.List;

public class Main {
    private static final String PATH = "C:/Users/777/IdeaProjects/amm-java-9-91-2024" +
            "/app/G9/gavrilov.mihail/lab4/src/main/java/ru/vsu/amm/java/main/Task.txt";

    private static final String COUNTRY_WITH_MEDALS_3 = "Страны с лучшим медальным зачетом ";
    private static final String ATHLETES_WITH_SPORTS = "Атлеты с видом спортом и медалью ";
    private static final String ATHLETES_WITH_MAX_MEDALS = " Атлеты с максимальным количеством медалей";

    public static void main(String[] args) {

        FileReader reader = new FileReader();
        OlympicStatsService service = new OlympicStatsService();
        List<OlympicMedalsRecord> entities = reader.read(PATH);


        System.out.println(COUNTRY_WITH_MEDALS_3 + service.TopMedalCountryResult(entities));
        System.out.println(ATHLETES_WITH_SPORTS + service.AthletesWithMedalsAndSport(entities).toString());
        System.out.println(ATHLETES_WITH_MAX_MEDALS + service.AthleteWithMaxMedals(entities));
    }

}