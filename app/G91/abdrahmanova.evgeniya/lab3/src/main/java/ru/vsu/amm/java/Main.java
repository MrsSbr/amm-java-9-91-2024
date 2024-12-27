package ru.vsu.amm.java;

import ru.vsu.amm.java.entities.Perfomance;
import ru.vsu.amm.java.entities.Student;
import ru.vsu.amm.java.entities.TicketCount;
import ru.vsu.amm.java.enums.PerfomanceName;
import ru.vsu.amm.java.service.TheatreService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
    public static final int NUMBER_STUDENTS = 10000;
    private static List<Perfomance> choices;

    public static void main(String[] args) {
        List<Student> students = new ArrayList<>();
        for (int i = 0; i < NUMBER_STUDENTS; i++) {
            students.add(new Student(choices));
        }

        TheatreService theatreService = new TheatreService();
        List<TicketCount> ticketsCount = theatreService.getCountTicketsForPerfomance(students);


        System.out.println("Perfomances with tickets count: \n");
        ticketsCount.forEach(System.out::println);

        printMostPopularPerformances(theatreService, ticketsCount);


        System.out.println("\nPerfomances, in which tickets was purchased: \n");
        List<PerfomanceName> purchasedPerfomance = theatreService.getPurchasedPerfomances(students);
        purchasedPerfomance.forEach(System.out::println);
    }

    private static void printMostPopularPerformances(TheatreService theatreService, List<TicketCount> ticketsCount) {
        List<PerfomanceName> mostPopularPerformances = theatreService.getMostPopularPerfomance(
                ticketsCount.stream().map(TicketCount::getCount).collect(Collectors.toList())
        );

        StringBuilder sb = new StringBuilder("\nMost popular performance(s): ");
        if (!mostPopularPerformances.isEmpty()) {
            for (int i = 0; i < mostPopularPerformances.size(); i++) {
                sb.append(mostPopularPerformances.get(i));
                if (i < mostPopularPerformances.size() - 1) {
                    sb.append(", ");
                }
            }
        } else {
            sb.append("None");
        }
        System.out.println(sb.toString());
    }
}