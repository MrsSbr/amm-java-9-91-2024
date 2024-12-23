package ru.vsu.amm.java;

import org.junit.jupiter.api.Test;
import ru.vsu.amm.java.Enums.Positions;
import ru.vsu.amm.java.Enums.RestaurantNames;
import ru.vsu.amm.java.Exceptions.InvalidOrderSize;
import ru.vsu.amm.java.Exceptions.InvalidRestarauntName;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static ru.vsu.amm.java.OrderService.*;

class OrderServiceTest {

    @Test
    void findCourierFalse() throws InvalidRestarauntName {
        Set<Courier> courierSet = new HashSet<>();
        Courier courier = new Courier("Alexei", "Smirnov", 1);
        courierSet.add(courier);
        List<Order> orders = new ArrayList<>();
        for(int i = 0; i < RestaurantNames.values().length - 1; i++) {
            orders.add(new Order.OrderBuilder(courier)
                    .restarauntName()
                    .build());
        }
        courierSet = findCourier(orders);
        assertFalse(courierSet.contains(courier));
    }

    @Test
    void findCourierTrue() throws InvalidRestarauntName {
        Set<Courier> courierSet = new HashSet<>();
        Courier courier = new Courier("Alexei", "Smirnov", 1);
        courierSet.add(courier);
        List<Order> orders = new ArrayList<>();
        for(int i = 0; i < RestaurantNames.values().length; i++) {
            orders.add(new Order.OrderBuilder(courier)
                    .restarauntName(i)
                    .build());
        }
        for (Order order : orders) {
            System.out.println(order);
        }
        courierSet = findCourier(orders);
        assertTrue(courierSet.contains(courier));
    }

    @Test
    void testfindMostPopular() throws InvalidRestarauntName {
        List<Order> orders = new ArrayList<>();
        RestaurantNames name = findMostPopular(orders);
        assertNull(name);

        orders.add(new Order.OrderBuilder(new Courier("Alexei", "Smirnov", 1), "BISTRO").build());
        name = findMostPopular(orders);
        assertNotNull(name);
        assertEquals("BISTRO", name.toString());
    }

    @Test
    void findLaziestMonthTest() throws InvalidRestarauntName {
        List<Order> orders = new ArrayList<>();
        Month month = findLaziestMonth(orders);
        assertNull(month);
        orders.add(new Order.OrderBuilder(new Courier("Alexei", "Smirnov", 1)).restarauntName().deliveryTime(1,1).build());
        month = findLaziestMonth(orders);
        System.out.println(month);
        assertNotNull(month);
        assertEquals(month, LocalDateTime.now().getMonth());
    }
}