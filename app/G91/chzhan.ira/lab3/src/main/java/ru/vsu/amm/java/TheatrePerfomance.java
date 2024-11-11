package ru.vsu.amm.java;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import ru.vsu.amm.java.entities.Perfomance;
import ru.vsu.amm.java.entities.Student;
import ru.vsu.amm.java.service.TheatreService;

public class TheatrePerfomance {
    public static final int NUMBER_PERFOMANCE = 10;
    public static final int NUMBER_STUDENTS = 10000;

    public static void main(String[] args) {
        List<Student> students = new ArrayList<>();
        for (int i = 0; i < NUMBER_STUDENTS; i++) {
            students.add(new Student());
        }

        TheatreService theatreService = new TheatreService();
        List<Integer> ticketCounts = theatreService.getCountTicketsForPerfomance(students, NUMBER_PERFOMANCE);
        List<Integer> mostPopularPerfomance = theatreService.getMostPopularPerfomance(ticketCounts);
        List<Integer> purchasedPerfomance = theatreService.getPurchasedPerfomances(students);
        System.out.println("Count of tickets for all perfomances: ");
        for (int i = 0; i < NUMBER_PERFOMANCE; i++) {
            System.out.println("Perfomance " + (i + 1) + ": " + ticketCounts.get(i));
        }

        System.out.println("\nMost popular perfomance: ");
        mostPopularPerfomance.forEach(perfomanceNumber -> System.out.println(perfomanceNumber));

        System.out.println("List of performances for which tickets have been purchased: ");
        purchasedPerfomance.forEach(perfomanceNumber -> System.out.println(perfomanceNumber));
    }
}