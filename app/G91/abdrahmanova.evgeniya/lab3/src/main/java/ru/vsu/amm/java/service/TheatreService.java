package ru.vsu.amm.java.service;

/*• количество билетов, заказанных на каждый спектакль;
• самый популярный спектакль (следует учесть вариант, что может быть несколько таких спектаклей);
• спектакль (спектакли), на который решили приобрести билеты*/

import ru.vsu.amm.java.entities.TicketCount;
import ru.vsu.amm.java.enums.PerfomanceName;
import ru.vsu.amm.java.entities.Student;
import ru.vsu.amm.java.entities.Perfomance;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TheatreService {

    public static List<TicketCount> getCountTicketsForPerfomance(List<Student> students) {
        List<TicketCount> ticketsCounts = PerfomanceName.getAllPerfomanceNames().stream()
                .map(perfomanceName -> new TicketCount(perfomanceName, 0))
                .collect(Collectors.toList());

        students.stream()
                .map(Student::getPerfomanceChoice)
                .flatMap(List::stream)
                .forEach(perfomance -> {
                    for (TicketCount ticketCount : ticketsCounts) {
                        if (ticketCount.getPerfomanceName() == perfomance.getName()) {
                            ticketCount.incrementCount();
                            break;
                        }
                    }
                });

        return ticketsCounts;
    }

    public List<PerfomanceName> getMostPopularPerfomance(List<Integer> ticketsCount) {
        if (ticketsCount == null || ticketsCount.isEmpty()) {
            return new ArrayList<>();
        }

        Optional<Integer> maxTickets = ticketsCount.stream().max(Integer::compareTo);

        if (maxTickets.isPresent()) {
            int maxCount = maxTickets.get();
            List<PerfomanceName> result = IntStream.range(0, ticketsCount.size())
                    .filter(i -> Integer.compare(ticketsCount.get(i), maxCount) == 0)
                    .mapToObj(i -> PerfomanceName.values()[i])
                    .toList();

            return result;
        } else {
            return new ArrayList<>();
        }
    }

    public static List<PerfomanceName> getPurchasedPerfomances(List<Student> students) {
        if (students == null || students.isEmpty()) {
            return null;
        }

        return students.stream()
                .map(Student::getPerfomanceChoice)
                .flatMap(List::stream)
                .map(Perfomance::getName)
                .distinct()
                .sorted(Comparator.comparing(PerfomanceName::name))
                .collect(Collectors.toList());
    }
}