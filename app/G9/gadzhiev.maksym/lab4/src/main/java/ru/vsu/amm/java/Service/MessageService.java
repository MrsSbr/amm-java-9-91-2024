package ru.vsu.amm.java.Service;

import ru.vsu.amm.java.Entity.Message;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.Map;
import java.util.stream.Collectors;

import ru.vsu.amm.java.Util.MessageFactory;

public class MessageService {
    public static Set<String> manWithLongestMessage(List<Message> messages) {
        if (messages == null) {
            throw new NullPointerException();
        }

        int maxLength = messages.stream()
                .mapToInt(m -> m.text().length())
                .max()
                .orElse(0);

        if (maxLength == 0)
            return Collections.emptySet();

        return messages.stream()
                .filter(m -> m.text().length() == maxLength)
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

        if (maxCount == 0)
            return Collections.emptySet();

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

        Map<Integer, Long> emojiCounts = messages.stream()
                .filter(message -> message.date().isAfter(oneYearAgo))
                .flatMapToInt(message -> message.text().codePoints())
                .filter(MessageFactory::isEmoji).boxed()
                .collect(Collectors.groupingBy(e -> e, Collectors.counting()));

        if (emojiCounts.isEmpty())
            return "";

        return emojiCounts.entrySet().stream()
                .min(Map.Entry.comparingByValue())
                .map(entry -> Character.toString(entry.getKey())).orElse("");
    }
}
