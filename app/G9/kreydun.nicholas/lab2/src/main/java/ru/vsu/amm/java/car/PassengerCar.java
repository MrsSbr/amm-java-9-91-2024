package ru.vsu.amm.java.car;

import java.util.Objects;

public class PassengerCar extends Car {
    private final int speed;
    private final int year;
    public PassengerCar(String model, String color, int year, int speed) {
        super(model, color);
        this.year = year;
        this.speed = speed;
    }
    public int getSpeed() {
        return speed;
    }
    public int getYear() {
        return year;
    }
    public void beep(){
        System.out.println("Виу!!!!");
    }
    @Override
    public String toString() {
        return super.toString()+ "speed " + speed + "\nyear " + year;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        else if (o == null || getClass() != o.getClass())
            return false;
        else if (!super.equals(o))
            return false;
        else {
            PassengerCar sportCar = (PassengerCar) o;
            return speed == sportCar.speed && year == sportCar.year;
        }
    }
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), speed, year);
    }
}
