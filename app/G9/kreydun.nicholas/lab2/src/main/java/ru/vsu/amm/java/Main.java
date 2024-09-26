package ru.vsu.amm.java;

import ru.vsu.amm.java.car.*;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("Passenger cars:");
        PassengerCar passengerFirst = new PassengerCar("Ford Mustang", "black",2024, 200);
        PassengerCar passengerSecond = new PassengerCar("Ford Mustang", "red",2022, 200);
        System.out.println(passengerFirst);
        passengerFirst.beep();
        passengerFirst.beep();
        if (passengerFirst.equals(passengerSecond))
            System.out.println("passengerCar == pSecond");
        else
            System.out.println("passengerCar != pSecond");
        System.out.println(passengerFirst.hashCode());
        System.out.println(passengerSecond.hashCode());
        System.out.println("\nSport Car");
        SportCar sportFirst = new SportCar("Ford Mustang", "black",2024, 200,100, "something");
        System.out.println(sportFirst);
        sportFirst.beep();
        System.out.println("Is sportFirst == Car?") ;
        System.out.println(sportFirst instanceof Car);
        System.out.println("Is sportFirst == Passanger car?") ;
        System.out.println(sportFirst instanceof PassengerCar);
        System.out.println("\nTruck Car");
        List<Cargo> cargos = new ArrayList<>();
        cargos.add(new Cargo("milk", 100, 150));
        cargos.add(new Cargo("bread", 20, 100));
        cargos.add(new Cargo("apples", 250, 300));
        Truck truckFirt = new Truck("KAMAZ", "white",1000, 6,cargos);
        truckFirt.beep();
        System.out.println(truckFirt.getDescription());
        System.out.println("Вмешается ли груз?\n" + truckFirt.isFragile());
        System.out.println("Стоимость доставки\n" + truckFirt.calculateShippingCost(10.05));
        System.out.println("\nElectric Car");
        ElectricCar electricFirst = new ElectricCar("Tesla","yellow",500,1000);
        ElectricCar electricSecond = new ElectricCar("BMW","black",300,700);
        System.out.println(electricFirst);
        electricFirst.charge(100);
        System.out.println("Can reach distination 1500? " + electricFirst.canReachDestination(1500));
        electricFirst.beep();
        System.out.println(electricFirst.compareWith(truckFirt));
        System.out.println(electricFirst.compareWith(sportFirst));
        System.out.println(electricFirst.compareWith(passengerFirst));
        System.out.println(electricFirst.compareWith(electricSecond));
    }
}