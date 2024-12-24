package ru.vsu.amm.java.util;

import ru.vsu.amm.java.constants.Constants;
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

    private SalesGenerator() {
    }

    public static Sale generateSale() {
        Random random = new Random();

        int randomYear = random.nextInt(Constants.MIN_YEAR, LocalDate.now().getYear());
        int randomMonth = random.nextInt(1, Constants.MONTH_COUNT);
        int randomDay = random.nextInt(1, Constants.DAY_COUNT);

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

        int markup = random.nextInt(Constants.MIN_MARKUP, Constants.MAX_MARKUP);

        return new Sale(dateOfSale, showroom, car, equipment, markup);
    }

    public static List<Sale> generateSale(int n) {
        return IntStream.range(0, n)
                .mapToObj(x -> generateSale())
                .collect(Collectors.toList());
    }
}
