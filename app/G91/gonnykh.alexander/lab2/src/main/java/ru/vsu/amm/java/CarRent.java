package ru.vsu.amm.java;

import ru.vsu.amm.java.abstractClasses.Car;
import ru.vsu.amm.java.classes.Client;
import ru.vsu.amm.java.classes.PassengerCar;
import ru.vsu.amm.java.classes.Rent;
import ru.vsu.amm.java.classes.Truck;
import ru.vsu.amm.java.enums.ComfortLevel;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class CarRent {
    public static void main(String[] args) {
        Rent rent = new Rent();
        Car passengerCar = new PassengerCar("BMW", "M5", 2022,
                "V6", 1000, ComfortLevel.COMFORT, "M063MM");
        Car truck = new Truck("KAMAZ", "АЦ-9", 1999,
                "OM 457", 5000, true, "У156СТ");
        passengerCar.startCar();
        truck.startCar();
        System.out.println(passengerCar.calculatePrice());
        System.out.println(truck.calculatePrice());
        rent.addCar(passengerCar);
        rent.addCar(truck);

        Client firstClient = new Client("Алексей", 20, "12343");
        Client secondClient = new Client("Александр", 19, "15554");
        rent.addCustomer(firstClient);
        rent.addCustomer(secondClient);
        hashCheck("Хэш легкового автомобиля: ", passengerCar);
        hashCheck("Хэш грузового автомобиля: ", truck);
        equalsCheck(passengerCar, truck);
        equalsCheck(truck, truck);
        typeCheck(truck);
        arendCheck(rent, passengerCar, firstClient, secondClient);
    }

    public static void hashCheck(String message, Car car) {
        System.out.println(message + car.hashCode());
    }

    public static void equalsCheck(Car firstCar, Car secondCar) {
        if (firstCar.equals(secondCar)) {
            System.out.println("Равны!");
        } else {
            System.out.println("Неравны!");
        }
    }

    public static void typeCheck(Object object) {
        if (object instanceof Car) {
            System.out.println("Это машина!");
            if (object instanceof PassengerCar) {
                System.out.println("Это легковая машина!");
            } else if (object instanceof Truck) {
                System.out.println("Это грузовая машина!");
            }
        } else {
            System.out.println("Это точно что-то другое!");
        }

    }

    public static void arendCheck(Rent rent, Car passengerCar, Client firstClient, Client secondClient) {
        String firstClientName = firstClient.getName();
        String secondClientName = secondClient.getName();
        LocalDateTime firstClientDataBegin = inputDate(firstClientName);
        LocalDateTime firstClientDataEnd = inputDate(firstClientName);
        LocalDateTime secondClientDataBegin = inputDate(secondClientName);
        LocalDateTime secondClientDataEnd = inputDate(secondClientName);
        rent.addOrder(passengerCar, firstClient, firstClientDataBegin, firstClientDataEnd);
        if (!rent.addOrder(passengerCar, secondClient, secondClientDataBegin, secondClientDataEnd)) {
            System.out.println("Не удалось арендовать транспорт, в данным момент он недоступен!");
        } else {
            System.out.println("Успешно!");
        }
    }

    public static LocalDateTime inputDate(String name) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        System.out.println("Шаблон yyyy-MM-dd HH:mm:ss");
        LocalDateTime date = LocalDateTime.now();
        Scanner scanner = new Scanner(System.in);
        try {
            System.out.printf("Введи дату для %s: ", name);
            date = LocalDateTime.parse(scanner.nextLine(), formatter);
        } catch (DateTimeParseException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return date;
    }
}