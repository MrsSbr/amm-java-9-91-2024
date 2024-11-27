package ru.vsu.amm.java.entity;

import lombok.*;

import java.util.ArrayList;
import java.util.stream.Collectors;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    private ArrayList<Dish> dishes;

    public double getTotalPrice() {
        return dishes.stream().mapToDouble(Dish::getPrice).sum();
    }

    @Override
    public String toString() {
        String dishesString = dishes.stream()
                .map(Dish::toString)
                .collect(Collectors.joining(",\n", "Order:\n", "\ntotal price: " + getTotalPrice() + "\n"));
        return dishesString;
    }

    public boolean containsDish(String dishName) {
        return dishes.stream().anyMatch(dish -> dish.getDish().name().equals(dishName));
    }
}
