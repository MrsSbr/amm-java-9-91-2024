package ru.vsu.amm.java.winners;


import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class StorageWinners<W extends Winner> {
    private final List<W> storage;

    public StorageWinners() {
        storage = new LinkedList<>();
    }

    public void add(W t) {
        storage.add(t);
    }

    public List<String> findMostFrequentDepartment() {
        List<String> departments = new ArrayList<>();
        List<Long> counts = new ArrayList<>();

        storage.forEach(winner -> {
            String dept = winner.getDepartmentName();
            int index = departments.indexOf(dept);
            if (index == -1) {
                departments.add(dept);
                counts.add(1L);
            } else {
                counts.set(index, counts.get(index) + 1);
            }
        });

        long maxCount = counts.stream().max(Long::compareTo).orElse(0L);
        List<String> mostFrequentDepartments = new ArrayList<>();
        int size = counts.size();
        IntStream.range(0, size)
                .filter(i -> counts.get(i) == maxCount)
                .mapToObj(departments::get)
                .forEach(mostFrequentDepartments::add);
        return mostFrequentDepartments;
    }

    public void printFindMostFrequentDepartment() {
        findMostFrequentDepartment().forEach(System.out::println);
    }

    public List<String> listWinners() {
        return storage.stream()
                .map(W::getName)
                .distinct()
                .collect(Collectors.toList());
    }

    public void printWinners() {
        listWinners().forEach(System.out::println);
    }

    public void printCountOneTimeWinners() {
        System.out.println(countOneTimeWinners());
    }

    public int countOneTimeWinners() {
        return (int) storage.stream()
                .filter(e -> Collections.frequency(storage, e) == 1)
                .count();
    }
}

