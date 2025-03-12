package ru.vsu.amm.java.utils;

import ru.vsu.amm.java.entity.PlantLogEntry;
import ru.vsu.amm.java.enums.Fertilizer;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class Reader {
    private static final int NAME_INDEX = 0;
    private static final int WATER_AMOUNT_INDEX = 1;
    private static final int DATE_INDEX = 2;
    private static final int FERTILIZER_INDEX = 3;


    public static List<PlantLogEntry> read(String path) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            return br.lines().map(line ->
                    {
                        String[] fields = line.split(";");
                        String name = fields[NAME_INDEX];
                        Double waterAmount = Double.parseDouble(fields[WATER_AMOUNT_INDEX]);
                        LocalDate date = LocalDate.parse(fields[DATE_INDEX]);
                        Fertilizer fertilizer = Fertilizer.valueOf(fields[FERTILIZER_INDEX]);
                        return new PlantLogEntry(name, waterAmount, date, fertilizer);
                    }
            ).toList();
        }
    }
}
