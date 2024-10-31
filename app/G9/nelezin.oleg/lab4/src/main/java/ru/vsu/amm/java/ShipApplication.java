package ru.vsu.amm.java;

import ru.vsu.amm.java.entity.RobbedShip;
import ru.vsu.amm.java.service.RobbedShipService;
import ru.vsu.amm.java.util.FileWorker;

import java.util.List;

public class ShipApplication {

    private static final String PATH = "app/G9/nelezin.oleg/lab4/ships.txt";

    public static void main(String[] args) {
        FileWorker fileWorker = new FileWorker();
        for (int i = 0; i < 20; i++) {
            fileWorker.saveToFile(PATH, 20);
        }
        List<RobbedShip> ships = fileWorker.loadFromFile(PATH);
        RobbedShipService robbedShipService = new RobbedShipService();

        System.out.println(robbedShipService.boardedShipsByNationality(ships) + "\n---------------------");
        System.out.println(robbedShipService.leastProfitableMonth(ships) + "\n----------------------");
        List<RobbedShip> topRumShips = robbedShipService.topRumShips(ships);
        for (int i = 0; i < topRumShips.size(); i++) {
            System.out.println(topRumShips.get(i).toString());
        }
    }
}