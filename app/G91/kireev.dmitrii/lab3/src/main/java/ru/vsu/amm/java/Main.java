package ru.vsu.amm.java;

import ru.vsu.amm.java.Model.Patient;
import ru.vsu.amm.java.Service.PatientService;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Comparator;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));


        PatientService service = new PatientService();

        List<Patient> mightBeHealthy = service.findByMightBeHealthy();

        List<Patient> healthy = service.findByIsHealthy();

        List<Patient> all = service.findAll();

        System.out.println("-----------------------------все------------------------------------------");
        for (var x : all) {
            System.out.println(x);
        }

        System.out.println("------здоровые------");

        for (var x : healthy) {
            System.out.println(x);
        }

        System.out.println("------возможно здоровые------");

        for (var x : mightBeHealthy) {
            System.out.println(x);
        }


        System.out.println("----- список людей, которые проходили обследование за последние 3 года------");

        List<Patient> task2 = service.findByDateAfter().stream().sorted(Comparator.comparing(Patient::name)).toList();

        for (var x : task2) {
            System.out.println(x);
        }

        System.out.println("------список людей, которые проходили обследование последние 5 лет, но не проходили последние 2 года--------");

        List<Patient> task3 = service.findByDateBetween();

        for (var x : task3) {
            System.out.println(x);
        }
    }
}