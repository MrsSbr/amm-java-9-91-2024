package ru.vsu.amm.java;

import ru.vsu.amm.java.*;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class SubscriptionService {
    public long getDeliveryCount(List<SubscriberRecord> records, ReportMonth month, String title) {
        return records.stream()
                .flatMap(record -> record.publications().stream())
                .filter(pub -> pub.title().equalsIgnoreCase(title) && pub.activeInMonth(month))
                .count();
    }

    public List<PublicationSubscription> getPublicationsBySubscriber(List<SubscriberRecord> records, String fullName) {
        return records.stream()
                .filter(record -> record.fullName().equalsIgnoreCase(fullName))
                .flatMap(record -> record.publications().stream())
                .collect(Collectors.toList());
    }

    public Optional<String> getZoneWithMaxDeliveries(List<SubscriberRecord> records, ReportMonth month, String title) {
        return records.stream()
                .collect(Collectors.groupingBy(SubscriberRecord::deliveryZone,
                        Collectors.summingInt(record ->
                                (int) record.publications().stream()
                                        .filter(pub -> pub.title().equalsIgnoreCase(title) && pub.activeInMonth(month))
                                        .count()
                        )))
                .entrySet().stream()
                .filter(entry -> entry.getValue() > 0)
                .max(Comparator.comparingInt(entry -> entry.getValue()))
                .map(entry -> entry.getKey());
    }
}
