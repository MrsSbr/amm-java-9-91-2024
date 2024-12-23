package ru.vsu.amm.java;

import ru.vsu.amm.java.Enums.RestaurantNames;
import ru.vsu.amm.java.Exceptions.InvalidMaxOrderSize;
import ru.vsu.amm.java.Exceptions.InvalidRestarauntName;
import static ru.vsu.amm.java.OrderFileService.*;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class MainService {

    private final static Logger logger = Logger.getLogger(MainService.class.getName());

    public static void main(String[] args) throws InvalidRestarauntName, InvalidMaxOrderSize, FileNotFoundException {
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

        for (Order order : orders) {
            System.out.println(order.toString());
        }
        try {
           orders = loadFromFile();
        } catch (FileNotFoundException e) {
            logger.log(Level.WARNING, e.getMessage());
        }

        saveToFile(orders);
    }
}
