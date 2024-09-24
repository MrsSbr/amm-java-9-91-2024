package ru.vsu.amm.java;

import java.util.List;
import java.util.Objects;

public class Truck extends Car implements Cargable {
    private final int loadCapacity;  // Грузоподъемность в тоннах
    private final int numberOfWheels; // Количество колес
    private final List<Cargo> cargos;

    public Truck(String model, String color, int loadCapacity, int numberOfWheels, List<Cargo> cargo) {
        super(model, color);
        this.loadCapacity = loadCapacity;
        this.numberOfWheels = numberOfWheels;
        this.cargos = cargo;
    }

    public int getLoadCapacity() {
        return loadCapacity;
    }

    public int getNumberOfWheels() {
        return numberOfWheels;
    }

    @Override
    public void beep() {
        System.out.println("Бип-бип! Я грузовик!");
    }

    @Override
    public String toString() {
        return super.toString() +
                "load capacity: " + loadCapacity + " tons" +
                "\nnumber of wheels: " + numberOfWheels +
                "\ncargo: " + cargos;
    }

    @Override
    public boolean equals(Object o) {
        boolean result = false;
        if (this == o)
            result = true;
        else if (o == null || getClass() != o.getClass())
            result = false;
        else {
            Truck truck = (Truck) o;
            result = loadCapacity == truck.loadCapacity && numberOfWheels == truck.numberOfWheels && super.equals(o);
        }
        return result;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), loadCapacity, numberOfWheels);
    }

    @Override
    public double getVolume() {
        return loadCapacity * 3.5; // Примерный объем в кубических метрах (возьмем средний коэффициент)
    }

    @Override
    public String getDescription() {
        return "Грузовик модели " + super.getModel() + " (" + getColor() + "), предназначенный для перевозки грузов";
    }

    @Override
    public boolean isFragile() {
        int sum = 0;
        for (Cargo cargo : cargos) {
            sum += cargo.getCargoWeight();
        }
        return sum <= getLoadCapacity();
    }

    @Override
    public double calculateShippingCost(double ratePerUnit) {
        return getVolume() * ratePerUnit; // Рассчитываем стоимость доставки исходя из объема и ставки
    }

}