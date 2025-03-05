package ru.vsu.amm.java;

import java.util.List;

public record SubscriberRecord(
        String fullName,
        String deliveryZone,
        String address,
        int subscriptionCount,
        List<PublicationSubscription> publications
) { }
