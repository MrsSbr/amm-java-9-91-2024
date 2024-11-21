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

    public List<Ship> read(String path) throws IOException {
        try(BufferedReader br = new BufferedReader(new FileReader(path))) {
            return br.lines().map(line -> {
                        String[] elems = line.split(";");
                        return new Ship(LocalDate.parse(elems[0]),
                                ShipType.valueOf(elems[1]),
                                Nationality.valueOf(elems[2]),
                                Long.parseLong(elems[3]),
                                Long.parseLong(elems[4]),
                                Boolean.parseBoolean(elems[5]));
            }
            ).toList();
        }
    }
}
