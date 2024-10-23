package ru.vsu.amm.java.winners;

import java.util.List;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Collections;

public class StorageWinners<W extends WinnerInterface> {
    private final List<W> storage;

    public StorageWinners() {
        storage = new LinkedList<>();
    }

    public void add(W t) {
        storage.add(t);
    }

    public List<String> findMostFrequentDepartment() {
        // Хранение департаментов и их частоты
        List<String> departments = new ArrayList<>();
        List<Long> counts = new ArrayList<>();

        // Считаем количество упоминаний каждого департамента
        storage.forEach(winner -> {
            String dept = winner.getDepartmentName();
            if (departments.contains(dept)) {
                // Если департамент уже есть, увеличиваем его счетчик
                int index = (departments).indexOf(dept);
                counts.set(index, counts.get(index) + 1);
            } else {
                // Если департамент новый, добавляем его
                departments.add(dept);
                counts.add(1L);
            }
        });

        // Находим максимальное количество
        long maxCount = counts.stream().max(Long::compareTo).orElse(0L);

        // Ищем наиболее частые департаменты с использованием стримов
        return counts.stream()
                .filter(count -> count == maxCount)
                .map(c -> departments.get(counts.indexOf(c)))
                .toList();
    }

    public void printFindMostFrequentDepartment() {
        findMostFrequentDepartment().forEach(System.out::println);
    }

    public List<String> listWinners() {
        return storage.stream()
                .map(W::getName)
                .distinct()
                .toList();
    }

    public void printWinners() {
        listWinners().forEach(System.out::println);
    }

    public void printCountOneTimeWinners() {
        System.out.println(countOneTimeWinners());
    }

    public int countOneTimeWinners() {
        long count = storage.stream()
                .filter(e -> Collections.frequency(storage, e) == 1)
                .count();

        if (count >= Integer.MAX_VALUE) {
            // Обработка случая переполнения, например, возвращаем Integer.MAX_VALUE
            return Integer.MAX_VALUE;
        }
        return (int) count; // Приведение к int безопасно
    }
}