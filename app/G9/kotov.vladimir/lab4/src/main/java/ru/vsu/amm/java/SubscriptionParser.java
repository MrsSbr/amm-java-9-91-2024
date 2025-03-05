package ru.vsu.amm.java;

import ru.vsu.amm.java.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SubscriptionParser {

    public SubscriberRecord parseLine(String line) {
        String[] parts = line.split(";");
        if (parts.length < 5) {
            throw new IllegalArgumentException("Некорректный формат строки: " + line);
        }
        String fullName = parts[0].trim();
        String zone = parts[1].trim();
        String address = parts[2].trim();
        int count;
        try {
            count = Integer.parseInt(parts[3].trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Неверный формат количества подписок: " + parts[3]);
        }
        List<PublicationSubscription> pubs = new ArrayList<>();
       int fieldsPerPub = 4;
        int startIndex = 4;
        if ((parts.length - startIndex) % fieldsPerPub != 0) {
            throw new IllegalArgumentException("Неверное количество полей для изданий в строке: " + line);
        }
        for (int i = startIndex; i < parts.length; i += fieldsPerPub) {
            String title = parts[i].trim();
            EditionType type = EditionType.valueOf(parts[i + 1].trim().toUpperCase());
            LocalDate startDate = LocalDate.parse(parts[i + 2].trim());
            LocalDate endDate = LocalDate.parse(parts[i + 3].trim());
            pubs.add(new PublicationSubscription(title, type, startDate, endDate));
        }
        return new SubscriberRecord(fullName, zone, address, count, pubs);
    }
}
