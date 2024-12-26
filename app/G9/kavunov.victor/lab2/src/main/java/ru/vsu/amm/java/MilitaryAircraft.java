package ru.vsu.amm.java;

import java.util.Objects;
import java.util.Random;

public abstract class MilitaryAircraft implements Flyable {
    protected String name;
    protected int maxSpeed;
    protected int fuelCapacity;

    public MilitaryAircraft(String name, int maxSpeed, int fuelCapacity) {
        this.name = name;
        this.maxSpeed = maxSpeed;
        this.fuelCapacity = fuelCapacity;
    }

    public abstract void attack();

    @Override
    public void fly() {
        System.out.println(name);
        int time = (new Random().nextInt(25)) + 5;
        System.out.println("Время в полёте: " + time + " минут");
        int speed = maxSpeed * 3 / 4;
        System.out.println("Средняя скорость: " + speed + " м/c");
        int distance = speed * time * 60;
        System.out.println("Пройденное расстояние: " + distance + " метров");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MilitaryAircraft that = (MilitaryAircraft) o;
        return name.equals(that.name) && maxSpeed == that.maxSpeed && fuelCapacity == that.fuelCapacity;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, maxSpeed, fuelCapacity);
    }
}
