package ru.vsu.amm.java.util;

import ru.vsu.amm.java.entity.Sale;
import ru.vsu.amm.java.enums.Car;
import ru.vsu.amm.java.enums.Equipment;
import ru.vsu.amm.java.enums.Showroom;

import java.io.*;
import java.time.LocalDate;
import java.util.List;

public class FileWorker {

    public FileWorker() {
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
    }
}
