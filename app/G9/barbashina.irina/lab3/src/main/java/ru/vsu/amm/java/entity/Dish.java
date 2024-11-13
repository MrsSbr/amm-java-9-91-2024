package ru.vsu.amm.java.entity;

import lombok.*;
import ru.vsu.amm.java.enums.Ingredients;
import ru.vsu.amm.java.enums.TypeDish;

import java.util.ArrayList;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Dish {
    private TypeDish dish;
    private ArrayList<Ingredients> ingredients;
    private int price;

    @Override
    public String toString() {
        return "dish: " + dish + ", ingredients: " + ingredients + ", price: " + price;
    }
}
