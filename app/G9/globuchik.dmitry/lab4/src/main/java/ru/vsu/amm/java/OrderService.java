package ru.vsu.amm.java;

import ru.vsu.amm.java.Enums.Positions;
import ru.vsu.amm.java.Enums.RestaurantNames;
import ru.vsu.amm.java.Exceptions.InvalidOrderSize;
import ru.vsu.amm.java.Exceptions.InvalidRestarauntName;

import java.time.Duration;
import java.time.Month;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;
import java.util.Set;
import java.util.HashSet;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;

public class OrderService {
    private OrderService() {
    }

    public static List<Order> generateOrders(List<Courier> couriers, int count) throws InvalidRestarauntName, InvalidOrderSize {
        List<Order> orders = new ArrayList<>();
        Random rand = new Random();
        for (int i = 0; i < count; i++) {
            orders.add(new Order.OrderBuilder(couriers.get(rand.nextInt(couriers.size())))
                    .deliveryTime(rand.nextInt(50), rand.nextInt(5))
                    .positions(rand.nextInt(Positions.values().length) + 1)
                    .orderDate(rand.nextInt(50), rand.nextInt(5), rand.nextInt(30), rand.nextInt(5))
                    .build());
        }
        return orders;
    }

    public static Set<Courier> findCourier(List<Order> orders) {
        Set<RestaurantNames> allRestaraunts = new HashSet<>();
        for (RestaurantNames restaurantName : RestaurantNames.values()) {
            allRestaraunts.add(RestaurantNames.valueOf(restaurantName.name()));
        }

        return orders.stream().collect(Collectors.groupingBy(Order::getCourier, Collectors.mapping(Order::getRestarauntName, Collectors.toSet())))
                .entrySet().stream()
                .filter(entry -> entry.getValue().containsAll(allRestaraunts))
                .map(Map.Entry::getKey)
                .collect(Collectors.toSet());
    }

    public static RestaurantNames findMostPopular(List<Order> orders) {
        return orders.stream()
                .collect(Collectors.groupingBy(Order::getRestarauntName, Collectors.counting()))
                .entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null);
    }

    public static Month findLaziestMonth(List<Order> orders) {
        return orders.stream()
                .collect(Collectors.groupingBy(order -> order.getOrderDate().getMonth(),
                        Collectors.summingLong(order -> Duration.between(order.getOrderDate(), order.getDeliveryTime()).toMillis())))
                .entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null);
    }
}
