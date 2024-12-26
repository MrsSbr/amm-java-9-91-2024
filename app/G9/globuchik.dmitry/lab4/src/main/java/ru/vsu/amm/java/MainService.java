package ru.vsu.amm.java;

import ru.vsu.amm.java.Exceptions.InvalidOrderSize;
import ru.vsu.amm.java.Exceptions.InvalidRestarauntName;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import static ru.vsu.amm.java.OrderFileService.saveToFile;
import static ru.vsu.amm.java.OrderService.generateOrders;
import static ru.vsu.amm.java.OrderService.findCourier;
import static ru.vsu.amm.java.OrderService.findMostPopular;
import static ru.vsu.amm.java.OrderService.findLaziestMonth;

public class MainService {
    private final static Logger logger = Logger.getLogger(MainService.class.getName());

    public static void main(String[] args) throws InvalidRestarauntName, InvalidOrderSize, FileNotFoundException {
        List<Courier> couriers = new ArrayList<>();
        Courier courier1 = new Courier("Dmitry", "Karachev", 1);
        Courier courier2 = new Courier("Alexander", "Levin", 2);
        Courier courier3 = new Courier("Alexei", "Shibanov", 3);
        couriers.add(courier1);
        couriers.add(courier2);
        couriers.add(courier3);

        List<Order> orders = new ArrayList<Order>();
        orders.add(new Order.OrderBuilder(courier1).restarauntName().deliveryTime(10, 1).positions(5).build());

        orders = generateOrders(couriers, 1000);

        saveToFile(orders);
        //orders = loadFromFile();


        System.out.println(findMostPopular(orders));
        System.out.println(findLaziestMonth(orders));
        System.out.println(findCourier(orders));
    }
}
