package ru.vsu.amm.java;

import ru.vsu.amm.java.Model.PatientDTO;
import ru.vsu.amm.java.Repository.PatientRepo;
import ru.vsu.amm.java.Service.PatientService;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        PatientService service = new PatientService();

        List<PatientDTO> mightBeHealthy = service.findByIsHealthy();

        List<PatientDTO> healthy = service.findByIsHealthyAfterDate();

        List<PatientDTO> test = Arrays.asList(
                new PatientDTO("ab", "cd", "ef", true, "rofler", LocalDate.now().minusMonths(3)),
                new PatientDTO("ab", "cd", "ef", true, "rofler", LocalDate.now().minusYears(3)));

        System.out.println("-----------------------------TASK1------------------------------------------");

        for (var x : mightBeHealthy) {
            System.out.println(x);
        }

        System.out.println("-----------------------------MIGHT BE------------------------------------------");

        for (var x : healthy) {
            System.out.println(x);
        }


        System.out.println("-----------------------------TASK2------------------------------------------");

        List<PatientDTO> task2 = service.findByDateAfter().stream().sorted(Comparator.comparing(PatientDTO::name)).toList();

        for (var x : task2) {
            System.out.println(x);
        }

        System.out.println("-----------------------------TASK3------------------------------------------");

        PatientService service2 = new PatientService(new PatientRepo(test));
        List<PatientDTO> task3 = service2.findByDateBetween();

        for (var x : task3) {
            System.out.println(x);
        }
    }
}