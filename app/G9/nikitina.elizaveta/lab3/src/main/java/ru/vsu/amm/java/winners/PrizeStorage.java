package ru.vsu.amm.java.winners;

import java.util.List;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Collections;

public class PrizeStorage<R extends RecipientInterface> {
    private final List<R> data;

    public PrizeStorage() {
        data = new LinkedList<>();
    }

    public void add(R t) {
        data.add(t);
    }

    public List<String> findMostRecurringGroup() {
        //  Хранение групп и их частоты
        List<String> groups = new ArrayList<>();
        List<Long> counts = new ArrayList<>();

        // Считаем количество упоминаний каждой группы
        data.forEach(recipient -> {
            String grp = recipient.getDepartmentName();
            if (groups.contains(grp)) {
                // Если группа уже есть, увеличиваем его счетчик
                int index = groups.indexOf(grp);
                counts.set(index, counts.get(index) + 1);
            } else {
                // Если группа новый, добавляем его
                groups.add(grp);
                counts.add(1L);
            }
        });

        // Находим максимальное количество
        long maxCount = counts.stream().max(Long::compareTo).orElse(0L);

        // Ищем наиболее частые группы с использованием стримов
        return counts.stream()
                .filter(count -> count == maxCount)
                .map(c -> groups.get(counts.indexOf(c)))
                .toList();
    }

    public void printMostRecurringGroup() {
        findMostRecurringGroup().forEach(System.out::println);
    }

    public List<String> listRecipients() {
        return data.stream()
                .map(R::getName)
                .distinct()
                .toList();
    }

    public void printRecipients() {
        listRecipients().forEach(System.out::println);
    }

    public void printUniqueRecipientCount() {
        System.out.println(countUniqueRecipients());
    }

    public int countUniqueRecipients() {
        long count = data.stream()
                .filter(e -> Collections.frequency(data, e) == 1)
                .count();

        if (count >= Integer.MAX_VALUE) {
            // Обработка случая переполнения, например, возвращаем Integer.MAX_VALUE
            return Integer.MAX_VALUE;
        }
        return (int) count;
    }
}