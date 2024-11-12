package ru.vsu.amm.java;

import java.util.*;
import java.util.stream.Collectors;

public class RestaurantService {
    public Set<String> getUniqueDishes(List<Order> orders) {
        return orders.stream()
                .flatMap(order -> order.getDishes().stream())
                .map(dish -> dish.getDish().toString())
                .collect(Collectors.toSet());
    }

    public int detTotalEarning(List<Order> orders) {
        return orders.stream()
                .mapToInt(Order::getTotalPrice)
                .sum();
    }

    public Set<String> getMostExpensiveDishes(List<Order> orders) {
        int maxPrice = orders.stream()
                .flatMap(order -> order.getDishes().stream())
                .mapToInt(Dish::getPrice)
                .max()
                .orElseThrow(NoSuchElementException::new);

        return orders.stream()
                .flatMap(order -> order.getDishes().stream())
                .filter(dish -> dish.getPrice() == maxPrice)
                .map(dish -> dish.getDish().toString())
                .collect(Collectors.toSet());
    }
}
