package ru.vsu.amm.java;

import ru.vsu.amm.java.Enums.Positions;
import ru.vsu.amm.java.Enums.RestaurantNames;
import ru.vsu.amm.java.Exceptions.InvalidOrderSize;
import ru.vsu.amm.java.Exceptions.InvalidRestarauntName;

import java.time.Month;
import java.util.*;

public class OrderService {
    private OrderService(){};

    public static List<Order> generateOrders(List<Courier> couriers, int count) throws InvalidRestarauntName, InvalidOrderSize {
        List<Order> orders = new ArrayList<>();
        Random rand = new Random();
        for (int i = 0; i < count; i++) {
            orders.add(new Order.OrderBuilder(couriers.get(rand.nextInt(couriers.size())))
                    .deliveryTime(rand.nextInt(50), rand.nextInt(5))
                    .positions(rand.nextInt(Positions.values().length) + 1)
                    .orderDate(rand.nextInt(50), rand.nextInt(5), rand.nextInt(30), rand.nextInt(5))
                    .restarauntName()
                    .build());
        }
        return orders;
    }

    public static Set<Courier> findCourier(List<Order> orders) {
        Set<Courier> couriers = new HashSet<>();
        Set<RestaurantNames> allRestaraunts = new HashSet<>();
        for (RestaurantNames restaurantName : RestaurantNames.values()) {
            allRestaraunts.add(RestaurantNames.valueOf(restaurantName.name()));
        }

        Map<Courier, Set<RestaurantNames>> couriersMap = new HashMap<>();
        for (Order order : orders) {
            Courier courier = order.getCourier();
            couriersMap.putIfAbsent(courier, new HashSet<>());
            couriersMap.get(courier).add(order.getRestarauntName());
        }

        for (var entry : couriersMap.entrySet()) {
            if (entry.getValue().containsAll(allRestaraunts)) {
                couriers.add(entry.getKey());
            }
        }

        return couriers;
    }

    public static RestaurantNames findMostPopular(List<Order> orders) {
        HashMap<RestaurantNames, Integer> namesList = new HashMap<>();
        RestaurantNames mostPopular = null;

        for (RestaurantNames name: RestaurantNames.values()) {
            namesList.put(name, 0);
        }

        for (Order order : orders) {
            namesList.put(order.getRestarauntName(), namesList.get(order.getRestarauntName()) + 1);
        }

        int maxCount = -1;

        for (var entry : namesList.entrySet()) {
            if (entry.getValue() > maxCount) {
                mostPopular = entry.getKey();
                maxCount = entry.getValue();
            }
        }
        return mostPopular;
    }

    public static Month findLaziestMonth(List<Order> Orders){
        HashMap<Month, Long> deliveryTimeByMonth = new HashMap<>();

        for(Order order : Orders){
            long deliveryDuration = java.time.Duration.between(order.getOrderDate(), order.getDeliveryTime()).toMillis();
            Month month = order.getOrderDate().getMonth();
            deliveryTimeByMonth.put(month, deliveryTimeByMonth.getOrDefault(month, 0L) + deliveryDuration);
        }
        Month maxMonth = null;
        long maxTime = 0;

        for (Map.Entry<Month, Long> entry : deliveryTimeByMonth.entrySet()) {
            if (entry.getValue() > maxTime) {
                maxMonth = entry.getKey();
                maxTime = entry.getValue();
            }
        }

        return maxMonth;
    }
}
