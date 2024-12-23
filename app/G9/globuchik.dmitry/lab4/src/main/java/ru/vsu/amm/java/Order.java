package ru.vsu.amm.java;

import ru.vsu.amm.java.Enums.Positions;
import ru.vsu.amm.java.Enums.RestaurantNames;
import ru.vsu.amm.java.Exceptions.InvalidMaxOrderSize;
import ru.vsu.amm.java.Exceptions.InvalidRestarauntName;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Order {
    private final Courier courier;
    private final LocalDateTime orderDate;
    private LocalDateTime deliveryTime;
    private final Set<Positions> positions;
    private final RestaurantNames restarauntName;

    private Order(OrderBuilder builder) {
        courier = builder.courier;
        orderDate = builder.orderDate;
        deliveryTime = builder.deliveryTime;
        positions = builder.positions;
        restarauntName = builder.restaurantName;
    }

    public static Set<Positions> generatePositions(int maxPositions) throws InvalidMaxOrderSize {
        if (maxPositions > Positions.values().length) {
            throw new InvalidMaxOrderSize("Positions count must be less than " + Positions.values().length);
        }
        Random random = new Random();
        Set<Positions> positions = new HashSet<>();

        while (positions.size() < maxPositions) {
            positions.add(Positions.values()[random.nextInt(Positions.values().length)]);
        }
        return positions;
    }

    public static class OrderBuilder { //нужно ли выносить билдер в отдельный класс, Блох пишет, что можно вложенным
        private final Courier courier;
        private final RestaurantNames restaurantName;
        private LocalDateTime deliveryTime;

        private LocalDateTime orderDate = LocalDateTime.now();
        private Set<Positions> positions = new HashSet<>();

        public OrderBuilder(Courier courier, String restaurantName) throws InvalidRestarauntName {
            this.courier = courier;
            this.restaurantName = RestaurantNames.valueOf(restaurantName);
        }

        public OrderBuilder orderDate(LocalDateTime orderDate) {
            this.orderDate = orderDate;
            return this;
        }

        public OrderBuilder deliveryTime(LocalDateTime deliveryTime) {
            this.deliveryTime = deliveryTime;
            return this;
        }

        public OrderBuilder deliveryTime(int addMinutes, int addHours) {
            this.deliveryTime = orderDate;
            deliveryTime = deliveryTime.plusMinutes(addMinutes);
            deliveryTime = deliveryTime.plusHours(addHours);
            return this;
        }

        public OrderBuilder positions(Set<Positions> positions) {
            this.positions = positions;
            return this;
        }

        public OrderBuilder positions(int maxPositions) throws InvalidMaxOrderSize {
            this.positions = generatePositions(maxPositions);
            return this;
        }

        public Order build() {
            return new Order(this);
        }
    }

    @Override
    public String toString() {
        return "Order{" +
                "courier:\n" + courier +
                "\norderDate=" + orderDate +
                "\ndeliveryTime=" + deliveryTime +
                "\npositions=" + positions +
                "\nrestarauntName=" + restarauntName +
                "\n}\n";
    }
}

