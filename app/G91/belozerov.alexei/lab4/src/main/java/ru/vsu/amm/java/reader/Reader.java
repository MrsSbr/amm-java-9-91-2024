package ru.vsu.amm.java.reader;

import ru.vsu.amm.java.enums.Nationality;
import ru.vsu.amm.java.enums.ShipType;
import ru.vsu.amm.java.model.Ship;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class Reader {

    public static final int DATE_INDEX = 0;
    public static final int SHIP_TYPE_INDEX = 1;
    public static final int NATIONALITY_INDEX = 2;
    public static final int GOLD_INDEX = 3;
    public static final int RUM_BARREL_INDEX = 4;
    public static final int BOARDING_INDEX = 5;

    public List<Ship> read(String path) throws IOException {
        try(BufferedReader br = new BufferedReader(new FileReader(path))) {
            return br.lines().map(line -> {
                        String[] fields = line.split(";");
                        return new Ship(LocalDate.parse(fields[DATE_INDEX]),
                                ShipType.valueOf(fields[SHIP_TYPE_INDEX]),
                                Nationality.valueOf(fields[NATIONALITY_INDEX]),
                                Long.parseLong(fields[GOLD_INDEX]),
                                Long.parseLong(fields[RUM_BARREL_INDEX]),
                                Boolean.parseBoolean(fields[BOARDING_INDEX]));
            }
            ).toList();
        }
    }
}
