package ru.vsu.amm.java.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import ru.vsu.amm.java.enums.Ingredients;
import ru.vsu.amm.java.enums.TypeDish;

import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Dish {
    private TypeDish dish;
    private List<Ingredients> ingredients;
    private double price;

    @Override
    public String toString() {
        return "dish: " + dish + ", ingredients: " + ingredients + ", price: " + price;
    }
}
