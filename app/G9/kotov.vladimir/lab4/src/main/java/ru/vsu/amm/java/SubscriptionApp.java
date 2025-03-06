package ru.vsu.amm.java;

import ru.vsu.amm.java.SubscriptionFileLoader;
import ru.vsu.amm.java.ReportMonth;
import ru.vsu.amm.java.SubscriberRecord;
import ru.vsu.amm.java.SubscriptionService;
import java.util.List;

public class SubscriptionApp {
    private static final String FILE_PATH = "app/G9/kotov.vladimir/lab4/src/main/java/ru/vsu/amm/java/subscriptions.txt";
    private static final String TITLE_FOR_DELIVERY = "Daily Times";
    private static final String SUBSCRIBER_NAME = "John Doe";
    private static final String TITLE_FOR_ZONE = "Daily Times";

    public static void main(String[] args) {
        SubscriptionFileLoader loader = new SubscriptionFileLoader();
        SubscriptionService service = new SubscriptionService();
        List<SubscriberRecord> records = loader.loadFile(FILE_PATH);

        long deliveryCount = service.getDeliveryCount(records, ReportMonth.AUGUST, TITLE_FOR_DELIVERY);
        System.out.println("Total copies to deliver for \"" + TITLE_FOR_DELIVERY + "\" in August: " + deliveryCount);

        System.out.println("Subscriptions for " + SUBSCRIBER_NAME + ":");
        service.getPublicationsBySubscriber(records, SUBSCRIBER_NAME)
                .forEach(System.out::println);

        service.getZoneWithMaxDeliveries(records, ReportMonth.JULY, TITLE_FOR_ZONE)
                .ifPresentOrElse(
                        zone -> System.out.println("Delivery zone with max copies for \"" + TITLE_FOR_ZONE + "\" in July: " + zone),
                        () -> System.out.println("No deliveries found for \"" + TITLE_FOR_ZONE + "\" in July")
                );
    }
}
