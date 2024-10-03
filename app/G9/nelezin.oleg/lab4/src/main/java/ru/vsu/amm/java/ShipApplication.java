package ru.vsu.amm.java;

import ru.vsu.amm.java.entity.RobbedShip;
import ru.vsu.amm.java.enums.Nationality;
import ru.vsu.amm.java.service.RobbedShipService;
import ru.vsu.amm.java.util.FileWorker;
import ru.vsu.amm.java.util.RobbedShipFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ShipApplication {

    private static final String path = "app/G9/nelezin.oleg/lab4/ships.txt";

    public static void main(String[] args) {
        FileWorker fileWorker = new FileWorker();
        for (int i = 0; i < 20; i++) {
            fileWorker.saveToFile(path, 20);
        }
        List<RobbedShip> ships = fileWorker.loadFromFile(path);
        RobbedShipService robbedShipService = new RobbedShipService();

        System.out.println(robbedShipService.boardedShipsByNationality(ships) + "\n---------------------");
        System.out.println(robbedShipService.leastProfitableMonth(ships) + "\n----------------------");
        List<RobbedShip> ships3 = robbedShipService.topRumShips(ships);
        for (int i = 0; i < ships3.size(); i++) {
            System.out.println(ships3.get(i).toString());
        }
    }
}