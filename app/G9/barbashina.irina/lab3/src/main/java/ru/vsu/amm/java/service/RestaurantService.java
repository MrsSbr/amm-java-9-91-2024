package ru.vsu.amm.java.service;

import ru.vsu.amm.java.entity.Dish;
import ru.vsu.amm.java.entity.Order;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.NoSuchElementException;

public class RestaurantService {
    public Set<String> getUniqueDishes(List<Order> orders) {
        return orders.stream()
                .flatMap(order -> order.getDishes().stream())
                .map(dish -> dish.getDish().toString())
                .collect(Collectors.toSet());
    }

    public double detTotalEarning(List<Order> orders) {
        return orders.stream()
                .mapToDouble(Order::getTotalPrice)
                .sum();
    }

    public Set<String> getMostExpensiveDishes(List<Order> orders) {
        double maxPrice = orders.stream()
                .flatMap(order -> order.getDishes().stream())
                .mapToDouble(Dish::getPrice)
                .max()
                .orElseThrow(NoSuchElementException::new);

        return orders.stream()
                .flatMap(order -> order.getDishes().stream())
                .filter(dish -> dish.getPrice() == maxPrice)
                .map(dish -> dish.getDish().toString())
                .collect(Collectors.toSet());
    }
}
