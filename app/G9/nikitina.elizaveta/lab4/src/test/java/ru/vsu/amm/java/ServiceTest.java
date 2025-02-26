package ru.vsu.amm.java;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.vsu.amm.java.Model.Month;
import ru.vsu.amm.java.Model.Publication;
import ru.vsu.amm.java.Model.PublicationType;
import ru.vsu.amm.java.Model.Subscriber;
import ru.vsu.amm.java.Service.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ServiceTest {

    private Service service;
    private List<Subscriber> subscribers;

    @BeforeEach
    public void setUp() {
        service = new Service();
        subscribers = getSubscribers();
    }

    // Тест 1: Проверка количества экземпляров по месяцу и названию
    @Test
    public void testGetSubscriptionCountByMonthAndTitle() {
        long count = service.getSubscriptionCountByMonthAndTitle(subscribers, "Library Journal", Month.JULY);
        assertEquals(1, count);

        count = service.getSubscriptionCountByMonthAndTitle(subscribers, "Daily News", Month.JULY);
        assertEquals(4, count);
    }

    // Тест 2: Получение списка изданий по ФИО подписчика
    @Test
    public void testGetPublicationsBySubscriberName() {
        List<Publication> expected = List.of(
                new Publication("Library Journal", PublicationType.MAGAZINE, LocalDate.of(2024, 7, 1), LocalDate.of(2024, 12, 31)),
                new Publication("Daily News", PublicationType.NEWSPAPER, LocalDate.of(2024, 7, 1), LocalDate.of(2024, 12, 31)),
                new Publication("a", PublicationType.MAGAZINE, LocalDate.of(2024, 7, 1), LocalDate.of(2024, 12, 31)),
                new Publication("b", PublicationType.NEWSPAPER, LocalDate.of(2024, 7, 1), LocalDate.of(2024, 12, 31)));

        List<Publication> publications = service.getPublicationsBySubscriberName(subscribers, "Alice");
        assertTrue(expected.containsAll((publications)) && publications.containsAll(expected));

        publications = service.getPublicationsBySubscriberName(subscribers, "David");
        expected = List.of(
                new Publication("Health Journal", PublicationType.MAGAZINE, LocalDate.of(2024, 7, 1), LocalDate.of(2024, 12, 31)),
                new Publication("Daily News", PublicationType.NEWSPAPER, LocalDate.of(2024, 7, 1), LocalDate.of(2024, 12, 31)),
                new Publication("Fashion Weekly", PublicationType.MAGAZINE, LocalDate.of(2024, 7, 1), LocalDate.of(2024, 12, 31))
        );
        assertTrue(expected.containsAll((publications)) && publications.containsAll(expected));
    }

    // Тест 3: Определение участка с наибольшим количеством подписок по названию и месяцу
    @Test
    public void testGetAreaWithMostSubscriptions() {
        String area = service.getAreaWithMostSubscriptions(subscribers, "Fashion Weekly", Month.JULY).orElse("");
        assertEquals("East", area);

        area = service.getAreaWithMostSubscriptions(subscribers, "Health Journal", Month.JULY).orElse("");
        assertEquals("South", area);

        Optional<String> nullArea = service.getAreaWithMostSubscriptions(subscribers, "null", Month.JULY);
        assertTrue(nullArea.isEmpty());
    }

    private List<Subscriber> getSubscribers() {
        return List.of(
                new Subscriber("Alice", "Central", "123 Main St", 2, List.of(
                        new Publication("Library Journal", PublicationType.MAGAZINE, LocalDate.of(2024, 7, 1), LocalDate.of(2024, 12, 31)),
                        new Publication("Daily News", PublicationType.NEWSPAPER, LocalDate.of(2024, 7, 1), LocalDate.of(2024, 12, 31))
                )),
                new Subscriber("Alice", "Central", "123 Main St", 2, List.of(
                        new Publication("a", PublicationType.MAGAZINE, LocalDate.of(2024, 7, 1), LocalDate.of(2024, 12, 31)),
                        new Publication("b", PublicationType.NEWSPAPER, LocalDate.of(2024, 7, 1), LocalDate.of(2024, 12, 31))
                )),
                new Subscriber("Bob", "North", "456 Elm St", 1, List.of(
                        new Publication("Fashion Weekly", PublicationType.MAGAZINE, LocalDate.of(2024, 7, 1), LocalDate.of(2024, 12, 31))
                )),
                new Subscriber("David", "South", "789 Oak St", 3, List.of(
                        new Publication("Health Journal", PublicationType.MAGAZINE, LocalDate.of(2024, 7, 1), LocalDate.of(2024, 12, 31)),
                        new Publication("Daily News", PublicationType.NEWSPAPER, LocalDate.of(2024, 7, 1), LocalDate.of(2024, 12, 31)),
                        new Publication("Fashion Weekly", PublicationType.MAGAZINE, LocalDate.of(2024, 7, 1), LocalDate.of(2024, 12, 31))
                )),
                new Subscriber("Eve", "East", "101 Pine St", 2, List.of(
                        new Publication("Fashion Weekly", PublicationType.MAGAZINE, LocalDate.of(2024, 7, 1), LocalDate.of(2024, 12, 31)),
                        new Publication("Morning Herald", PublicationType.NEWSPAPER, LocalDate.of(2024, 7, 1), LocalDate.of(2024, 12, 31))
                )),
                new Subscriber("John", "East", "101 Pine St", 2, List.of(
                        new Publication("Fashion Weekly", PublicationType.MAGAZINE, LocalDate.of(2024, 7, 1), LocalDate.of(2024, 12, 31))
                )),
                new Subscriber("John", "West", "202 Cedar St", 1, List.of(
                        new Publication("Daily News", PublicationType.NEWSPAPER, LocalDate.of(2024, 7, 1), LocalDate.of(2024, 12, 31)),
                        new Publication("Daily News", PublicationType.NEWSPAPER, LocalDate.of(2024, 6, 1), LocalDate.of(2024, 12, 31))
                ))
        );
    }
}
