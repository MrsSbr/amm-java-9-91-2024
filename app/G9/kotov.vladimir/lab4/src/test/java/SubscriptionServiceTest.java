import ru.vsu.amm.java.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

public class SubscriptionServiceTest {

    private SubscriptionService service;
    private List<SubscriberRecord> records;

    @BeforeEach
    public void init() {
        service = new SubscriptionService();
        records = List.of(
                new SubscriberRecord("John Doe", "North", "10 North St", 2, List.of(
                        new PublicationSubscription("Daily Times", EditionType.NEWSPAPER, LocalDate.of(2024, 7, 1), LocalDate.of(2024, 12, 31)),
                        new PublicationSubscription("Weekly News", EditionType.MAGAZINE, LocalDate.of(2024, 7, 1), LocalDate.of(2024, 9, 30))
                )),
                new SubscriberRecord("Jane Smith", "South", "20 South St", 1, List.of(
                        new PublicationSubscription("Daily Times", EditionType.NEWSPAPER, LocalDate.of(2024, 8, 1), LocalDate.of(2024, 12, 31))
                )),
                new SubscriberRecord("Alice Brown", "East", "30 East St", 1, List.of(
                        new PublicationSubscription("Fashion Today", EditionType.MAGAZINE, LocalDate.of(2024, 7, 1), LocalDate.of(2024, 12, 31))
                ))
        );
    }

    @Test
    public void testGetDeliveryCount() {
        long count = service.getDeliveryCount(records, ReportMonth.AUGUST, "Daily Times");
        assertEquals(2, count);
    }

    @Test
    public void testGetPublicationsBySubscriber() {
        List<PublicationSubscription> pubs = service.getPublicationsBySubscriber(records, "John Doe");
        assertEquals(2, pubs.size());
    }

    @Test
    public void testGetZoneWithMaxDeliveries() {
        Optional<String> zone = service.getZoneWithMaxDeliveries(records, ReportMonth.JULY, "Daily Times");
        assertTrue(zone.isPresent());
        assertEquals("North", zone.get());
    }
}
