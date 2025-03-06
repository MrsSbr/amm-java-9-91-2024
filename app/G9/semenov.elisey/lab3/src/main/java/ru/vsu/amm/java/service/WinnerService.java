package ru.vsu.amm.java.service;

import ru.vsu.amm.java.entity.Winner;
import ru.vsu.amm.java.enums.Department;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

public class WinnerService {
    private static final Logger logger = Logger.getLogger(WinnerService.class.getName());

    public List<Department> getDepartmentsWithMostWins(List<Winner> winners) {
        logger.info("call getDepartmentsWithMostWins");
        List<Department> departments = winners.stream()
                .map(Winner::getDepartment)
                .distinct()
                .toList();

        List<Integer> winCounts = departments.stream()
                .map(dept -> (int) winners.stream()
                        .filter(w -> w.getDepartment().equals(dept))
                        .count())
                .toList();

        int maxWins = winCounts.stream().max(Integer::compare).orElse(0);

        List<Department> result = new ArrayList<>();
        for (int i = 0; i < departments.size(); i++) {
            if (winCounts.get(i) == maxWins) {
                result.add(departments.get(i));
            }
        }
        return result;
    }

    public List<String> getUniqueWinners(List<Winner> winners) {
        logger.info("call getUniqueWinners");
        return winners.stream()
                .map(Winner::getName)
                .distinct()
                .toList();
    }

    public int getOneTimeWinnersCount(List<Winner> winners) {
        logger.info("call getOneTimeWinnersCount");
        List<String> names = winners.stream()
                .map(Winner::getName)
                .toList();

        Set<String> uniqueNames = new HashSet<>(names);

        int oneTimeCount = 0;
        for (String name : uniqueNames) {
            long winCount = names.stream().filter(n -> n.equals(name)).count();
            if (winCount == 1) {
                oneTimeCount++;
            }
        }
        return oneTimeCount;
    }
}