package ru.vsu.amm.java;

import ru.vsu.amm.java.model.Ship;
import ru.vsu.amm.java.reader.Reader;
import ru.vsu.amm.java.service.ShipService;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {

    private static final Logger logger = Logger.getLogger(Main.class.getName());
    private static final String PATH = "app/G91/belozerov.alexei/lab4/src/main/java/ru/vsu/amm/java/resources/ships.txt";

    public static void main(String[] args) {
        try {
            Reader reader = new Reader();
            List<Ship> ships = reader.read(PATH);

            System.out.println("Amount of ships boarded by nationality: "
                    + ShipService.boundingShipCountByNationality(ships) + '\n');

            System.out.println("Less profit month: "
                    + ShipService.lessProfitMonth(ships).toString() + '\n');

            System.out.println("Most rums stocks for last 3 years: "
                    + ShipService.mostRumsStocks(ships) + '\n');
        } catch (IOException e) {
            logger.log(Level.SEVERE, Arrays.toString(e.getStackTrace()));
            System.out.println("Can't read file: " + PATH + "\n");
        }
    }
}
