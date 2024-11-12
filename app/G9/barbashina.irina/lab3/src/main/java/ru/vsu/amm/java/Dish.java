package ru.vsu.amm.java;

import lombok.*;

import java.util.ArrayList;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Dish {
    private Dish nameDish;
    private ArrayList<Ingredients> ingredients;
}
