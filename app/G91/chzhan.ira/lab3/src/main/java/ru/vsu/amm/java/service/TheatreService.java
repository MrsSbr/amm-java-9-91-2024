package ru.vsu.amm.java.service;

import ru.vsu.amm.java.entities.Perfomance;
import ru.vsu.amm.java.entities.Student;

import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collectors;

public class TheatreService {
    private static final int NUMBER_PERFOMANCE = 10;

    public List<Integer> getCountTicketsForPerfomance(List<Student> students, int numberOfPerformances) {
        List<Integer> ticketCounts = new ArrayList<>(Collections.nCopies(numberOfPerformances, 0));
        students.stream()
                .map(Student::getPerfomanceChoices)
                .flatMap(List::stream)
                .forEach(perfomance ->
                        ticketCounts.set(perfomance.getNumber() - 1,
                                ticketCounts.get(perfomance.getNumber() - 1) + 1));

        return ticketCounts;
    }

    public List<Integer> getMostPopularPerfomance(List<Integer> ticketCounts) {
        if (ticketCounts == null) {
            return null;
        }
        Optional<Integer> maxTickets = ticketCounts.stream().max(Integer::compareTo);
        if (maxTickets.isPresent()) {
            return ticketCounts.stream().filter(count -> count == maxTickets.get()).map(count -> ticketCounts.indexOf(count) + 1).toList();
        } else {
            return null;
        }
    }

    public List<Integer> getPurchasedPerfomances(List<Student> students) {
        if (students == null) {
            return null;
        }
        return students.stream().map(Student::getPerfomanceChoices).flatMap(List::stream)
                .map(Perfomance::getNumber).distinct().sorted().toList();
    }
}
