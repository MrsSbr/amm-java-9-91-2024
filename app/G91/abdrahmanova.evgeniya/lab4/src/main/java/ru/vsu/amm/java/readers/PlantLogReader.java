package ru.vsu.amm.java.readers;

import ru.vsu.amm.java.entites.PlantLog;
import ru.vsu.amm.java.enums.PlantType;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PlantLogReader {

    public static List<PlantLog> readPlantLogsFromFile(String filePath) throws IOException {
        List<PlantLog> plantLogs = new ArrayList<>();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        System.out.println("File path: " + filePath);
        Path path = Paths.get(filePath);

        try {
            plantLogs = Files.lines(path)
                    .map(line -> line.split(","))
                    .filter(parts -> parts.length == 5)
                    .map(parts -> {
                        try {
                            String name = parts[0].trim();
                            PlantType type = PlantType.valueOf(parts[1].trim().toUpperCase());
                            int sizePot = Integer.parseInt(parts[2].trim());
                            int price = Integer.parseInt(parts[3].trim());
                            LocalDate date = LocalDate.parse(parts[4].trim(), dateFormatter);
                            return new PlantLog(name, type, sizePot, price, date);

                        } catch (IllegalArgumentException | NullPointerException e) {
                            System.err.println("Skipping malformed line: " + String.join(",", parts));
                            return null;
                        }
                    })
                    .filter(plantLog -> plantLog != null)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
            throw e;
        }

        return plantLogs;
    }
}
