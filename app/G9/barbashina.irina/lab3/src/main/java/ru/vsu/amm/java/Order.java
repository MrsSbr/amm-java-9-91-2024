package ru.vsu.amm.java;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    private Dish dish;
    private int price;
}
