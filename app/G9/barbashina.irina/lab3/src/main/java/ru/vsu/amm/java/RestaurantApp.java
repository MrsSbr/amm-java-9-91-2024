package ru.vsu.amm.java;

import ru.vsu.amm.java.entity.Order;
import ru.vsu.amm.java.service.RestaurantService;
import ru.vsu.amm.java.util.DataGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class RestaurantApp {
    public static void main(String[] args) {
        List<Order> orders = new ArrayList<>(2341);
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter a number of orders: ");
        int n = scanner.nextInt();
        for(int i = 0; i < n; i++) {
            orders.add(DataGenerator.generateRandomOrder());
        }

        System.out.println("\nOrders: ");
        for(int i = 0; i < n; i++) {
            System.out.println(orders.get(i));
        }

        RestaurantService restaurantService = new RestaurantService();

        Set<String> uniqueDishes = restaurantService.getUniqueDishes(orders);
        System.out.println("Unique dishes: " + uniqueDishes);

        int totalEarnings = restaurantService.detTotalEarning(orders);
        System.out.println("The total earnings of the restaurant is: " + totalEarnings);

        Set<String> mostExpensiveDishes = restaurantService.getMostExpensiveDishes(orders);
        System.out.println("The most expensive dishes: " + mostExpensiveDishes);
    }
}