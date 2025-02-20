package ru.vsu.amm.java;

import ru.vsu.amm.java.Courier;
import ru.vsu.amm.java.Enums.Positions;
import ru.vsu.amm.java.Enums.RestaurantNames;
import ru.vsu.amm.java.Exceptions.InvalidOrderSize;
import ru.vsu.amm.java.Exceptions.InvalidRestarauntName;
import ru.vsu.amm.java.Order;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import static ru.vsu.amm.java.Order.generatePositions;
import static ru.vsu.amm.java.Order.generateRestaurantNames;

public class OrderBuilder {
    final Courier courier;

    LocalDateTime deliveryTime = LocalDateTime.now();
    LocalDateTime orderDate = LocalDateTime.now();
    Set<Positions> positions = new HashSet<>();
    RestaurantNames restaurantName = generateRestaurantNames();

    public OrderBuilder(Courier courier) throws InvalidRestarauntName {
        this.courier = courier;
    }

    public OrderBuilder restarauntName(String restaurantName) throws InvalidRestarauntName {
        this.restaurantName = RestaurantNames.valueOf(restaurantName);
        return this;
    }

    public OrderBuilder restarauntName(int id) throws InvalidRestarauntName {
        this.restaurantName = RestaurantNames.fromId(id);
        return this;
    }

    public OrderBuilder orderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
        return this;
    }

    public OrderBuilder deliveryTime(LocalDateTime deliveryTime) {
        this.deliveryTime = deliveryTime;
        return this;
    }

    public OrderBuilder orderDate(int remMinutes, int remHours, int remDays, int remMonths) {
        this.deliveryTime = orderDate;
        orderDate = orderDate.minusMinutes(remMinutes);
        orderDate = orderDate.minusHours(remHours);
        orderDate = orderDate.minusDays(remDays);
        orderDate = orderDate.minusMonths(remMonths);
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

    public OrderBuilder positions(int maxPositions) throws InvalidOrderSize {
        this.positions = generatePositions(maxPositions);
        return this;
    }

    public Order build() {
        return new Order(this);
    }
}