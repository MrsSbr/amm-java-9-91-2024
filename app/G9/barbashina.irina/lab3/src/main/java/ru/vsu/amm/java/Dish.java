package ru.vsu.amm.java;

import lombok.*;

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
}
