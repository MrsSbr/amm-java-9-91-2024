package ru.vsu.amm.java.utils;

import ru.vsu.amm.java.entity.PlantLogEntry;
import ru.vsu.amm.java.enums.Fertilizer;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class Reader {
    public static List<PlantLogEntry> read(String path) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            return br.lines().map(line ->
                    {
                        String[] parts = line.split(";");
                        String name = parts[0];
                        Double waterAmount = Double.parseDouble(parts[1]);
                        LocalDate date = LocalDate.parse(parts[2]);
                        Fertilizer fertilizer = Fertilizer.valueOf(parts[3]);
                        return new PlantLogEntry(name, waterAmount, date, fertilizer);
                    }
            ).toList();
        }
    }
}
