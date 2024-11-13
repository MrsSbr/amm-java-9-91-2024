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

    @Override
    public String toString() {
        StringBuilder orderString = new StringBuilder("Order:\n");
        for (int i = 0; i<dishes.size(); ++i) {
           orderString.append(dishes.get(i).toString());
           if(i<dishes.size() - 1) {
               orderString.append(", ");
           }
            orderString.append("\n");
        }
        orderString.append("total price: ").append(getTotalPrice()).append("\n");
        return orderString.toString();
    }
}
