package ru.vsu.amm.java.util;

import ru.vsu.amm.java.entity.Sale;
import ru.vsu.amm.java.enums.Car;
import ru.vsu.amm.java.enums.Equipment;
import ru.vsu.amm.java.enums.Showroom;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileReader;
import java.time.LocalDate;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileWorker {

    private static final Logger logger = Logger.getLogger(FileWorker.class.getName());

    public FileWorker(Logger logger) {
    }

    public static void generateFile(String path, int n) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
            for (int i = 0; i < n; i++) {
                Sale sale = SalesGenerator.generateSale();
                writer.write(String.format(
                        "%s;%s;%s;%s;%s%n",
                        sale.getDateOfSale(),
                        sale.getDealCenter(),
                        sale.getCar(),
                        sale.getEquipment(),
                        sale.getMarkup()
                ));
            }
        }
        catch (IOException e) {
            logger.log(Level.SEVERE, e.toString());
            throw e;
        }
    }

    public static List<Sale> getFromFile(String path) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            return reader.lines().map(line -> {
                String[] parts = line.split(";");
                LocalDate dateOfSale = LocalDate.parse(parts[0]);
                Showroom showroom = Showroom.valueOf(parts[1]);
                Car car = Car.valueOf(parts[2]);
                Equipment equipment = Equipment.valueOf(parts[3]);
                Integer markup = Integer.parseInt(parts[4]);
                return new Sale(dateOfSale, showroom, car, equipment, markup);
            }).toList();
        }
        catch (IOException e) {
            logger.log(Level.SEVERE, e.toString());
            throw e;
        }
    }
}
