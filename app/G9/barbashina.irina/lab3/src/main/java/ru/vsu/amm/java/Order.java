package ru.vsu.amm.java;

import lombok.*;

import java.util.ArrayList;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    private ArrayList<Dish> dishes;

    public int getTotalPrice() {
        return dishes.stream().mapToInt(Dish::getPrice).sum();
    }
}
