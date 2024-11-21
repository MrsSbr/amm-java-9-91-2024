package ru.vsu.amm.java.util;

import ru.vsu.amm.java.entity.Sale;
import ru.vsu.amm.java.enums.Car;
import ru.vsu.amm.java.enums.Equipment;
import ru.vsu.amm.java.enums.Showroom;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class SalesGenerator {
    private final static Random random = new Random();

    private SalesGenerator() {
    }

    public static Sale generateSale() {

        int randomYear = random.nextInt(2000, LocalDate.now().getYear());
        int randomMonth = random.nextInt(1, 12);
        int randomDay = random.nextInt(1, 30);
        LocalDate dateOfSale = LocalDate.of(randomYear, randomMonth, randomDay);

        var showrooms = Showroom.values();
        int showroomIndex = random.nextInt(showrooms.length);
        Showroom showroom = showrooms[showroomIndex];

        var cars = Car.values();
        int carIndex = random.nextInt(cars.length);
        Car car = cars[carIndex];

        var equipments = Equipment.values();
        int equipmentIndex = random.nextInt(equipments.length);
        Equipment equipment = equipments[equipmentIndex];

        int markup = random.nextInt(10000, 1000000);

        return new Sale(dateOfSale, showroom, car, equipment, markup);
    }

    public static List<Sale> generateSale(int n) {
        return IntStream.range(0, n)
                .mapToObj(x -> generateSale())
                .collect(Collectors.toList());
    }
}
