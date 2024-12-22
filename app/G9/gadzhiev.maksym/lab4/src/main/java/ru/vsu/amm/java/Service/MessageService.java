package ru.vsu.amm.java.Service;

import ru.vsu.amm.java.Entity.Message;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class MessageService {
    private final static String[] emojis = {"️✌️", "❤️", "👍"};
    public static Set<String> manWithLongestMessage(List<Message> messages) {
        if (messages == null) {
            throw new NullPointerException();
        }

        int maxLength = messages.stream()
                .mapToInt(m-> m.text().length())
                .max()
                .orElse(0);
        return messages.stream()
                .filter(m-> m.text().length() == maxLength)
                .map(Message::fio)
                .collect(Collectors.toSet());
    }

    public static Set<DayOfWeek> dayWithMostWord(List<Message> messages, String word) {
        if (messages == null || word == null) {
            throw new NullPointerException();
        }

        Map<DayOfWeek, Long> dayCounts = messages.stream()
                .filter(m -> m.text().toLowerCase().contains(word.toLowerCase()))
                .collect(Collectors.groupingBy(m -> m.date().getDayOfWeek(), Collectors.counting()));

        long maxCount = dayCounts.values().stream()
                .mapToLong(Long::longValue).max().orElse(0);

        return dayCounts.entrySet().stream()
                .filter(entry -> entry.getValue() == maxCount)
                .map(Map.Entry::getKey)
                .collect(Collectors.toSet());
    }

    public static String leastUsedEmotionsInTheLastYear(List<Message> messages) {
        if (messages == null) {
            throw new NullPointerException();
        }

        LocalDate oneYearAgo = LocalDate.now().minusYears(1);

        Map<String, Long> emojiCounts = messages.stream()
                .filter(message -> message.date().isAfter(oneYearAgo))
                .flatMap(message -> Arrays.stream(emojis)
                        .filter(emoji -> message.text().contains(emoji)))
                .collect(Collectors.groupingBy(e -> e, Collectors.counting()));

        return emojiCounts.entrySet().stream()
                .min(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey).orElse("");
    }
}
