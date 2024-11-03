package ru.vsu.amm.java.service;
import ru.vsu.amm.java.entities.Perfomance;
import ru.vsu.amm.java.entities.Student;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;
public class TheatreService {
    private static final int NUMBER_PERFOMANCE = 10;
    public List<Integer> getCountTicketsForPerfomance(List<Student> students)
    {
        List<Integer> ticketCounts = new ArrayList<>();
        for (int i = 0; i < NUMBER_PERFOMANCE; i++)
        {
            ticketCounts.add(0);
        }
        students.stream().map(Student::getPerfomanceChoices).flatMap(List::stream).forEach(perfomance ->
                ticketCounts.set(perfomance.getNumber() - 1, ticketCounts.get(perfomance.getNumber() - 1) + 1));
        return ticketCounts;
    }

    public List<Integer> getMostPopularPerfomance(List<Integer> ticketCounts)
    {
        int maxTickets = ticketCounts.stream().max(Integer::compareTo).get();
        return ticketCounts.stream().filter(count -> count == maxTickets).map(count ->
                ticketCounts.indexOf(count) + 1).collect(Collectors.toList());
    }

    public List<Integer> getPurchasedPerfomances(List<Student> students)
    {
        return students.stream().map(Student::getPerfomanceChoices).flatMap(List::stream)
                .map(Perfomance::getNumber).distinct().sorted().collect(Collectors.toList());
    }
}
