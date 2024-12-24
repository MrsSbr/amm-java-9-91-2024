package ru.vsu.amm.java;

import java.util.ArrayList;
import java.util.List;

public class CarSharing {
    public static void main(String[] args) {
        List<Vehicle> vehicles = new ArrayList<Vehicle>();

        vehicles.add(new Car("Toyota", "Camry", 40, 5));
        vehicles.add(new Car("Ford", "Mustang", 70, 4));
        vehicles.add(new Motorcycle("Harley-Davidson", "Street 750", 1, 25));
        vehicles.add(new Motorcycle("Kawasaki", "Ninja ZX-6R", 1, 30));

        for (Vehicle vehicle : vehicles) {
            System.out.println(vehicle);
        }

        System.out.println("\n\nVehicle:");
        for (Vehicle vehicle : vehicles) {
            System.out.println(vehicle);
            if (vehicle instanceof Car) {
                vehicle.rent();
            }
            System.out.println("Price for 10 days = " + vehicle.calculateRentalCost(10));
        }

    }
}