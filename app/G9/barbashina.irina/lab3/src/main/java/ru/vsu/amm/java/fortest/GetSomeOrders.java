package ru.vsu.amm.java.fortest;

import ru.vsu.amm.java.entity.Dish;
import ru.vsu.amm.java.entity.Order;
import ru.vsu.amm.java.enums.Ingredients;
import ru.vsu.amm.java.enums.TypeDish;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GetSomeOrders {
    public static List<Order> getSomeOrders() {
        Dish dish1 = Dish.builder()
                .dish(TypeDish.BORCH)
                .ingredients(new ArrayList<>(Arrays.asList(Ingredients.BEET, Ingredients.CABBAGE)))
                .price(100)
                .build();

        Dish dish2 = Dish.builder()
                .dish(TypeDish.KARBONARA)
                .ingredients(new ArrayList<>(Arrays.asList(Ingredients.PASTA, Ingredients.CREAM)))
                .price(200)
                .build();

        Dish dish3 = Dish.builder()
                .dish(TypeDish.CHARLOTTE)
                .ingredients(new ArrayList<>(Arrays.asList(Ingredients.APPLE, Ingredients.FLOUR)))
                .price(300)
                .build();

        Order order1 = Order.builder()
                .dishes(new ArrayList<>(Arrays.asList(dish1, dish2)))
                .build();

        Order order2 = Order.builder()
                .dishes(new ArrayList<>(Arrays.asList(dish3)))
                .build();

        return Arrays.asList(order1, order2);
    }
}
