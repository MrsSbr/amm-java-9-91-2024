package ru.vsu.amm.java.Model;


import java.util.List;

public record Subscriber(
        String fullName,
        String deliveryArea,
        String address,
        int numberOfSubscriptions,
        List<Publication> publications
) {
}
