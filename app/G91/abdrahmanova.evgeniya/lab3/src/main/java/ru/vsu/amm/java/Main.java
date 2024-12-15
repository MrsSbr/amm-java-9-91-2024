package ru.vsu.amm.java;

import ru.vsu.amm.java.entities.Perfomance;
import ru.vsu.amm.java.entities.Student;
import ru.vsu.amm.java.enums.PerfomanceName;
import ru.vsu.amm.java.service.TheatreService;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static final int NUMBER_STUDENTS = 10000;
    private static List<Perfomance> choices;

    public static void main(String[] args) {
        List<Student> students = new ArrayList<>();
        for (int i = 0; i < NUMBER_STUDENTS; i++) {
            students.add(new Student(choices));
        }

        TheatreService theatreService = new TheatreService();
        List<Integer> ticketsCount = theatreService.getCountTicketsForPerfomance(students);

        PerfomanceName[] perfomances = PerfomanceName.values();

        System.out.println("Perfomances with tickets count: \n");
        for (int i = 0; i < perfomances.length; i++) {
            System.out.println("Perfomance " + perfomances[i]/*.getName()*/ + ": " + ticketsCount.get(i));
        }

        List<Perfomance> mostPopularPerformances = theatreService.getMostPopularPerfomance(ticketsCount);
        StringBuilder sb = new StringBuilder("\nMost popular performance(s): ");
        if (!mostPopularPerformances.isEmpty()) {
            for (int i = 0; i < mostPopularPerformances.size(); i++) {
                sb.append(mostPopularPerformances.get(i).getName());
                if (i < mostPopularPerformances.size() - 1) {
                    sb.append(", ");
                }
            }
        } else {
            sb.append("None");
        }
        System.out.println(sb.toString());


        System.out.println("\nPerfomances, in which tickets was purchased: \n");
        List<PerfomanceName> purchasedPerfomance = theatreService.getPurchasedPerfomances(students);
        purchasedPerfomance.forEach(perfomanceNumber -> System.out.println(perfomanceNumber));
    }
}