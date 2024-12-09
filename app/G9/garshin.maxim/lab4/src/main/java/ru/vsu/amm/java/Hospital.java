package ru.vsu.amm.java;

import ru.vsu.amm.java.entity.HospitalReception;
import ru.vsu.amm.java.service.HospitalReceptionsStatsService;
import ru.vsu.amm.java.util.FileWorker;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class Hospital {

    private final static Logger logger = Logger.getLogger(Hospital.class.getName());

    private final static String PATH = "app/G9/garshin.maxim/lab4/src/main/java/ru/vsu/amm/java/resources/Receptions.txt";

    static {
        try {
            LogManager.getLogManager().readConfiguration(new FileInputStream("app/G9/garshin.maxim/lab4/src/main/java/ru/vsu/amm/java/resources/logging.properties"));
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Failed to load logger config");
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {

        try {
            FileWorker.generateFile(PATH, 500);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Can't create file.\n" + Arrays.toString(e.getStackTrace()));
            throw new RuntimeException(e);
        }

        List<HospitalReception> receptions;
        try {
            receptions = FileWorker.getFromFile(PATH);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Can't fill List from this path.\n" + Arrays.toString(e.getStackTrace()));
            throw new RuntimeException(e);
        }

        try {
            System.out.println("The income of each specialization for the last year:");
            System.out.println(HospitalReceptionsStatsService.sumIncomeBySpecializationByLastYear(receptions));
        } catch (NullPointerException e) {
            logger.log(Level.SEVERE, "Null pointer was passed to method.\n" + Arrays.toString(e.getStackTrace()));
            throw new RuntimeException(e);
        }

        try {
            System.out.println("Patients who visited all doctors:");
            System.out.println(HospitalReceptionsStatsService.findPatientsVisitedAllDoctors(receptions));
        } catch (NullPointerException e) {
            logger.log(Level.SEVERE, "Null pointer was passed to method.\n" + Arrays.toString(e.getStackTrace()));
            throw new RuntimeException(e);
        }

        try {
            System.out.println("Patients who visited a doctor last year but did not visit this year:");
            System.out.println(HospitalReceptionsStatsService.findPatientsVisitedDoctorsLastYearAndNotThisYear(receptions));
        } catch (NullPointerException e) {
            logger.log(Level.SEVERE, "Null pointer was passed to method.\n" + Arrays.toString(e.getStackTrace()));
            throw new RuntimeException(e);
        }
    }
}