package ru.vsu.amm.java;

import ru.vsu.amm.java.Enums.Positions;
import ru.vsu.amm.java.Exceptions.InvalidMaxOrderSize;
import ru.vsu.amm.java.Exceptions.InvalidRestarauntName;

import javax.print.attribute.standard.OrientationRequested;
import java.io.*;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class OrderFileService {
    private final static Logger logger = Logger.getLogger(OrderFileService.class.getName());
    private final static String PATH = "app/G9/globuchik.dmitry/lab4/orders.txt";

    private OrderFileService() {};

    public static void saveToFile(List<Order> orders) {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(PATH))) {
            for (Order order : orders) {
                bufferedWriter.write(order.toString());
            }
            bufferedWriter.flush();
        }
        catch (IOException e) {
            logger.log(Level.WARNING, e.getMessage());
        }
    }

    public static List<Order> loadFromFile() throws FileNotFoundException {
        List<Order> orders = new ArrayList<Order>();

        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(PATH))){
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            line = bufferedReader.readLine();
            while (line.startsWith("Order")) {
                StringBuilder orderBuilder = new StringBuilder();
                orderBuilder.append(line).append("\n");
                for (int i = 0; i < 8; ++i) {
                    line = bufferedReader.readLine();
                    orderBuilder.append(line).append("\n");
                }

                orders.add(parseOrder(String.valueOf(orderBuilder)));
            }
        } catch (IOException | InvalidRestarauntName |InvalidMaxOrderSize | ParseException e) {
            logger.log(Level.WARNING, e.getMessage());
        }

        return orders;
    }

    private static Order parseOrder(String line) throws ParseException, InvalidRestarauntName, InvalidMaxOrderSize {
        String[] words = line.split("\n" );
        String firstName = words[1].split("=")[1].trim();
        String lastName = words[2].split("=")[1].trim();
        int id = Integer.parseInt(words[3].split("=")[1].trim());

        Courier courier = new Courier(firstName, lastName, id);

        LocalDateTime orderDate = LocalDateTime.parse(words[4].split("=")[1].trim());
        LocalDateTime deliveryTime = LocalDateTime.parse(words[5].split("=")[1].trim());

        String positionsList = words[6].split("=")[1].trim();
        positionsList = positionsList.substring(1, positionsList.length() - 1);
        String[] positions = positionsList.split(", ");
        Set<Positions> positionsSet = new HashSet<Positions>();
        for (String position : positions) {
            positionsSet.add(Positions.valueOf(position));
        }
        String restarauntName = words[7].split("=")[1].trim();

        return new Order.OrderBuilder(courier, restarauntName)
                .deliveryTime(deliveryTime)
                .orderDate(orderDate)
                .positions(positionsSet)
                .build();
    }
}
