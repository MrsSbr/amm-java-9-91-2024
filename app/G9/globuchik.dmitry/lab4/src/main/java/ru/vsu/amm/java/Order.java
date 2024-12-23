package ru.vsu.amm.java;

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

    private Order(Builder builder) {
        courier = builder.courier;
        orderDate = builder.orderDate;
        deliveryTime = builder.deliveryTime;
        positions = builder.positions;
        restarauntName = builder.restaurantName;
    }

    public static class Builder {
        private final Courier courier;
        private final RestaurantNames restaurantName;
        private LocalDateTime deliveryTime;

        private LocalDateTime orderDate = LocalDateTime.now();
        private Set<Positions> positions = new HashSet<>();

        public Builder(Courier courier, String restaurantName) throws InvalidRestarauntName {
            this.courier = courier;
            this.restaurantName = RestaurantNames.valueOf(restaurantName);
        }

        public Builder orderDate(LocalDateTime orderDate) {
            this.orderDate = orderDate;
            return this;
        }

        public Builder deliveryTime(LocalDateTime deliveryTime) {
            this.deliveryTime = deliveryTime;
            return this;
        }

        public Builder deliveryTime(int addMinutes, int addHours) {
            this.deliveryTime = orderDate;
            deliveryTime = deliveryTime.plusMinutes(addMinutes);
            deliveryTime = deliveryTime.plusHours(addHours);
            return this;
        }

        public Builder positions(Set<Positions> positions) {
            this.positions = positions;
            return this;
        }

        public Builder positions(int maxPositions) throws InvalidMaxOrderSize {
            generatePositions(maxPositions);
            return this;
        }

        public Order build() {
            return new Order(this);
        }
    }

    public static void generatePositions(int maxPositions) throws InvalidMaxOrderSize {
        if (maxPositions > 15) {
            throw new InvalidMaxOrderSize("maxPositions must be less than 15");
        }
        Random random = new Random();
        Set<Positions> positions = new HashSet<>();
        int size = random.nextInt(maxPositions);

        while (positions.size() < size) {
            positions.add(Positions.values()[random.nextInt(Positions.values().length)]);
        }
    }
}

