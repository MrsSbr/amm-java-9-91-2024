package ru.vsu.amm.java.Service;

import ru.vsu.amm.java.Model.Publication;
import ru.vsu.amm.java.Model.Subscriber;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class Service {


    public List<Publication> getPublicationsBySubscriberName(List<Subscriber> subs, String fullName) {
        return subs.stream()
                .filter(sub -> sub.fullName().equals(fullName))
                .flatMap(sub -> sub.publications().stream())
                .collect(Collectors.toList());
    }

    public Optional<String> getAreaWithMostSubscriptions(List<Subscriber> subs, String title, int month) {
        return subs.stream()
                .collect(Collectors.groupingBy(
                        Subscriber::deliveryArea, // Группируем по имени области
                        Collectors.summingInt(sub -> (int) sub.publications().stream()
                                .filter(pub -> pub.title().equals(title) && pub.isInMonth(month)) // Фильтруем публикации по title и month
                                .count()) // Считаем количество таких публикаций
                ))
                .entrySet()
                .stream()
                .filter(entry -> entry.getValue() > 0)
                .max(Comparator.comparingInt(Map.Entry::getValue)) // Находим максимальное количество публикаций
                .map(Map.Entry::getKey); // Извлекаем имя области


    }

    public long getSubscriptionCountByMonthAndTitle(List<Subscriber> subs, String title, int month) {
        return subs.stream()
                .flatMap(sub -> sub.publications().stream())
                .filter(pub -> pub.title().equals(title) && pub.isInMonth(month))
                .count();
    }
}
