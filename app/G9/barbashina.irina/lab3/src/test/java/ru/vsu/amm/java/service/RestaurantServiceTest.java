package ru.vsu.amm.java.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.vsu.amm.java.entity.Dish;
import ru.vsu.amm.java.entity.Order;
import ru.vsu.amm.java.enums.Ingredients;
import ru.vsu.amm.java.enums.TypeDish;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static ru.vsu.amm.java.fortest.GetSomeOrders.getSomeOrders;

public class RestaurantServiceTest {
    private RestaurantService restaurantService;
    private List<Order> orders;

    @BeforeEach
    public void setup() {
        restaurantService = new RestaurantService();
        orders = getSomeOrders();
    }

    @AfterEach
    public void free(){
        restaurantService = null;
        orders = null;
    }

    @Test
    public void testGetUniqueDishes() {
        Set<String> uniqueDishes = restaurantService.getUniqueDishes(orders);

        assertEquals(3, uniqueDishes.size());
        assertTrue(uniqueDishes.contains("BORCH"));
        assertTrue(uniqueDishes.contains("KARBONARA"));
        assertTrue(uniqueDishes.contains("CHARLOTTE"));
        assertFalse(uniqueDishes.contains("STEW"));
    }

    @Test
    public void testGetTotalEarning() {
        double totalEarnings = restaurantService.detTotalEarning(orders);
        assertEquals(100 + 200 + 300, totalEarnings);
    }

    @Test
    public void testGetMostExpensiveDishes() {
        Set<String> mostExpensiveDishes = restaurantService.getMostExpensiveDishes(orders);
        assertEquals(1, mostExpensiveDishes.size());
        assertTrue(mostExpensiveDishes.contains("CHARLOTTE"));
    }

    // Negative Tests
    public void testGetCountMostExpensiveDishes() {
        Set<String> mostExpensiveDishes = restaurantService.getMostExpensiveDishes(orders);
        assertEquals(2, mostExpensiveDishes.size());
    }

    public void testContainsDish() {
        Order firstOrder = orders.get(0);
        assertTrue(firstOrder.containsDish("CAESAR"));
    }

    // Edge Case Tests
    @Test
    public void testGetUniqueDishesWithNullOrders() {
        assertThrows(NullPointerException.class, () -> {
            restaurantService.getUniqueDishes(orders);
        });
    }

    @Test
    public void testGetUniqueDishesWithSingleOrderWithSingleDish() {
        Dish singleDish = Dish.builder()
                .dish(TypeDish.BORCH)
                .ingredients(new ArrayList<>(Arrays.asList(Ingredients.BEET, Ingredients.CABBAGE)))
                .price(100)
                .build();
        Order singleOrder = Order.builder().dishes(new ArrayList<>(Arrays.asList(singleDish))).build();
        List<Order> singleOrderList = Arrays.asList(singleOrder);

        Set<String> uniqueDishes = restaurantService.getUniqueDishes(singleOrderList);
        assertEquals(1, uniqueDishes.size());
        assertTrue(uniqueDishes.contains("BORCH"));
    }

    @Test
    public void testGetUniqueDishesWithMultipleOrdersWithTheSameDish() {
        Dish sameDish = Dish.builder()
                .dish(TypeDish.BORCH)
                .ingredients(new ArrayList<>(Arrays.asList(Ingredients.BEET, Ingredients.CABBAGE)))
                .price(100)
                .build();
        Order order1 = Order.builder().dishes(new ArrayList<>(Arrays.asList(sameDish))).build();
        Order order2 = Order.builder().dishes(new ArrayList<>(Arrays.asList(sameDish))).build();
        List<Order> ordersWithSameDish = Arrays.asList(order1, order2);

        Set<String> uniqueDishes = restaurantService.getUniqueDishes(ordersWithSameDish);
        assertEquals(1, uniqueDishes.size());
        assertTrue(uniqueDishes.contains("BORCH"));
    }

}
