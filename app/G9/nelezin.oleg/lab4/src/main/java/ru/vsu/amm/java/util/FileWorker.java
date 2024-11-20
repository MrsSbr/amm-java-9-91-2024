package ru.vsu.amm.java.util;

import ru.vsu.amm.java.entity.RobbedShip;
import ru.vsu.amm.java.enums.Nationality;
import ru.vsu.amm.java.enums.ShipClass;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;

public class FileWorker {

    public void saveToFile(String path, int n) {
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(path))) {
            for (int i = 0; i < n; i++) {
                RobbedShip robbedShip = RobbedShipFactory.generateRobbedShip();
                writer.write(String.format("%s;%s;%s;%d;%d;%b%n",
                        robbedShip.getRobbedDate(),
                        robbedShip.getShipClass(),
                        robbedShip.getNationality(),
                        robbedShip.getGoldCount(),
                        robbedShip.getBarrelCount(),
                        robbedShip.isBoarding()));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<RobbedShip> loadFromFile(String path) {
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            return reader.lines()
                    .map(line -> {
                        String[] parts = line.split(";");
                        LocalDate robbedDate = LocalDate.parse(parts[0]);
                        String shipClass = parts[1];
                        String nationality = parts[2];
                        int goldCount = Integer.parseInt(parts[3]);
                        int barrelCount = Integer.parseInt(parts[4]);
                        boolean isBoarding = Boolean.parseBoolean(parts[5]);
                        return RobbedShip.builder()
                                .robbedDate(robbedDate)
                                .shipClass(ShipClass.valueOf(shipClass))
                                .nationality(Nationality.valueOf(nationality))
                                .goldCount(goldCount)
                                .barrelCount(barrelCount)
                                .isBoarding(isBoarding)
                                .build();
                    }).toList();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
