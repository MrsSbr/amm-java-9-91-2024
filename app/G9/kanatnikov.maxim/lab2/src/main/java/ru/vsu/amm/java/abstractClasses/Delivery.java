package ru.vsu.amm.java.abstractClasses;

import ru.vsu.amm.java.interfaces.Delivering;

import java.util.Objects;

public abstract class Delivery implements Delivering {
    protected String address;
    protected double cost;
    protected int deliveringTimeDays;

    public Delivery(String address, double cost, int deliveringTimeDays) {

        this.address = address;
        this.cost = cost;
        this.deliveringTimeDays = deliveringTimeDays;
    }

    public Delivery() {}
    public String getAddress() {
        return address;
    }

    public double getCost() {
        return cost;
    }

    public int getDeliveringTimeDays() {
        return deliveringTimeDays;
    }

    @Override
    public String toString() {
        return "адрес: " + address
                + "\nстоимость: " + cost
                + "\nвремя доставки в днях: " + deliveringTimeDays;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Delivery delivery = (Delivery) o;
        return Double.compare(cost, delivery.cost) == 0
                && deliveringTimeDays == delivery.deliveringTimeDays
                && Objects.equals(address, delivery.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(address, cost, deliveringTimeDays);
    }
}
