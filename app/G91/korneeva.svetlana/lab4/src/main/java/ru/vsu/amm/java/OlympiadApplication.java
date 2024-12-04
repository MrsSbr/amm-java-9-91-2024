package ru.vsu.amm.java;

import ru.vsu.amm.java.entity.OlympiadRecord;
import ru.vsu.amm.java.service.OlympiadStatsService;
import ru.vsu.amm.java.util.FileHelper;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Year;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class OlympiadApplication {
    private static final Logger logger = Logger.getLogger(OlympiadApplication.class.getName());

    private static final String PATH = "app/G91/korneeva.svetlana/lab4/src/main/java/ru/vsu/amm/java/resources/olympiads.txt";

    static {
        try {
            LogManager.getLogManager().readConfiguration(new FileInputStream("app/G91/korneeva.svetlana/lab4/src/main/java/ru/vsu/amm/java/resources/log.config"));
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Failed to load logger config", e);
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        try {
            FileHelper.generateFile(PATH, 100);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Unable to generate file.", e);
            throw new RuntimeException(e);
        }

        List<OlympiadRecord> records;
        try {
            records = FileHelper.getFromFile(PATH);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Failed to read from file.", e);
            throw new RuntimeException(e);
        }

        try {
            List<String> consistentWinners = OlympiadStatsService.findStudentsWinningEveryYear(records);
            System.out.println("Students winning every year: " + consistentWinners + '\n');
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error while finding students winning every year.", e);
            throw new RuntimeException(e);
        }

        try {
            var winnersBySubject = OlympiadStatsService.findWinnersBySubjectLast10Years(records, Year.now());
            System.out.println("Winners by subject in the last 10 years:");
            winnersBySubject.forEach((subject, winners) ->
                    System.out.println(subject + ": " + winners));
            System.out.println();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error while finding winners by subject.", e);
            throw new RuntimeException(e);
        }

        try {
            String topStudent = OlympiadStatsService.findStudentWithMostSubjects(records);
            System.out.println("Student with the most subjects: " + topStudent);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error while finding student with the most subjects.", e);
            throw new RuntimeException(e);
        }
    }
}
