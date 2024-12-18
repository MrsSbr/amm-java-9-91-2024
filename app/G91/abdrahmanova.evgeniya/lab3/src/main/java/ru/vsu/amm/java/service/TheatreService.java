package ru.vsu.amm.java.service;

/*• количество билетов, заказанных на каждый спектакль;
• самый популярный спектакль (следует учесть вариант, что может быть несколько таких спектаклей);
• спектакль (спектакли), на который решили приобрести билеты*/

import ru.vsu.amm.java.entities.Perfomance;
import ru.vsu.amm.java.enums.PerfomanceName;
import ru.vsu.amm.java.entities.Student;

import java.util.*;
import java.util.stream.Collectors;

public class TheatreService {

    public List<Integer> getCountTicketsForPerfomance(List<Student> students) {
        List<Integer> ticketsCounts = new ArrayList<>(Collections.nCopies(PerfomanceName.values().length, 0));
        students.stream()
                .map(Student::getPerfomanceChoice)
                .flatMap(List::stream)
                .forEach(perfomance -> {
                    int index = perfomance.getName().ordinal();
                    ticketsCounts.set(index, ticketsCounts.get(index) + 1);
                });
        return ticketsCounts;
    }


    public List<Perfomance> getMostPopularPerfomance(List<Integer> ticketsCount) {
        if (ticketsCount == null || ticketsCount.isEmpty()) {
            return new ArrayList<>();
        }
        Optional<Integer> maxTickets = ticketsCount.stream().max(Integer::compareTo);
        if (maxTickets.isPresent()) {
            return ticketsCount.stream()
                    .filter(count -> count == maxTickets.get())
                    .map(count -> Perfomance.values()[ticketsCount.indexOf(count)])
                    .collect(Collectors.toList());
        } else {
            return new ArrayList<>();
        }
    }

    public List<PerfomanceName> getPurchasedPerfomances(List<Student> students) {
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
