import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.vsu.amm.java.entity.Dish;
import ru.vsu.amm.java.entity.Order;
import ru.vsu.amm.java.enums.Ingredients;
import ru.vsu.amm.java.enums.TypeDish;
import ru.vsu.amm.java.service.RestaurantService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class RestaurantServiceTest {
    private RestaurantService restaurantService;
    private List<Order> orders;

    private static List<Order> getSomeOrders() {
        Dish dish1 = Dish.builder()
                .dish(TypeDish.BORCH)
                .ingredients(new ArrayList<>(Arrays.asList(Ingredients.BEET, Ingredients.CABBAGE)))
                .price(100)
                .build();

        Dish dish2 = Dish.builder()
                .dish(TypeDish.PASTA)
                .ingredients(new ArrayList<>(Arrays.asList(Ingredients.PASTA, Ingredients.CREAM)))
                .price(200)
                .build();

        Dish dish3 = Dish.builder()
                .dish(TypeDish.CAKE)
                .ingredients(new ArrayList<>(Arrays.asList(Ingredients.APPLE, Ingredients.FLOUR)))
                .price(300)
                .build();

        Order order1 = Order.builder()
                .dishes(new ArrayList<>(Arrays.asList(dish1,dish2)))
                .build();

        Order order2 = Order.builder()
                .dishes(new ArrayList<>(Arrays.asList(dish3)))
                .build();

        return Arrays.asList(order1,order2);
    }

    @BeforeEach
    public void setup() {
        restaurantService = new RestaurantService();
        orders = getSomeOrders();
    }

    @Test
    public void testGetUniqueDishes() {
        Set<String> uniqueDishes = restaurantService.getUniqueDishes(orders);

        assertEquals(3, uniqueDishes.size());
        assertTrue(uniqueDishes.contains("BORCH"));
        assertTrue(uniqueDishes.contains("PASTA"));
        assertTrue(uniqueDishes.contains("CAKE"));
        assertFalse(uniqueDishes.contains("STEW"));
    }

    @Test
    public void testGetTotalEarning() {
        int totalEarnings = restaurantService.detTotalEarning(orders);
        assertEquals(100 + 200 + 300, totalEarnings);
    }

    @Test
    public void testGetMostExpensiveDishes() {
        Set<String> mostExpensiveDishes = restaurantService.getMostExpensiveDishes(orders);
        assertEquals(1, mostExpensiveDishes.size());
        assertTrue(mostExpensiveDishes.contains("CAKE"));
    }
}
