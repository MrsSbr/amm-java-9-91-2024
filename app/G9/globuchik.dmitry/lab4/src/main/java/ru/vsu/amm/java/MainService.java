package ru.vsu.amm.java;

import ru.vsu.amm.java.Enums.Positions;
import ru.vsu.amm.java.Enums.RestaurantNames;
import ru.vsu.amm.java.Exceptions.InvalidOrderSize;
import ru.vsu.amm.java.Exceptions.InvalidRestarauntName;

import javax.crypto.Cipher;

import static ru.vsu.amm.java.OrderFileService.*;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;


public class MainService {

    private final static Logger logger = Logger.getLogger(MainService.class.getName());

    public static List<Order> generateOrders(List<Courier> couriers, int count) throws InvalidRestarauntName, InvalidOrderSize {
        List<Order> orders = new ArrayList<>();
        Random rand = new Random();
        for(int i = 0; i < count; i++) {
            orders.add(new Order.OrderBuilder(couriers.get(rand.nextInt(couriers.size())))
                    .deliveryTime(rand.nextInt(50), rand.nextInt(5))
                    .positions(rand.nextInt(Positions.values().length) + 1)
                    .orderDate(rand.nextInt(50), rand.nextInt(5), rand.nextInt(30), rand.nextInt(5))
                    .restarauntName()
                    .build());
        }

        return orders;
    }

    public static void main(String[] args) throws InvalidRestarauntName, InvalidOrderSize, FileNotFoundException {
        List<Courier> couriers = new ArrayList<>();
        Courier courier1 = new Courier("Dmitry", "Karachev", 1);
        Courier courier2 = new Courier("Alexander", "Levin", 2);
        Courier courier3 = new Courier("Alexei", "Shibanov", 3);
        couriers.add(courier1);
        couriers.add(courier2);
        couriers.add(courier3);

        List<Order> orders = new ArrayList<Order>();
        orders.add(new Order.OrderBuilder(courier1, "STEAKHOUSE")
                .deliveryTime(10, 1)
                .positions(5)
                .build());

        orders = generateOrders(couriers, 1000);

        saveToFile(orders);
        orders = loadFromFile();

        for(Order order : orders) {
            System.out.println(order.toString());
        }
    }
}
