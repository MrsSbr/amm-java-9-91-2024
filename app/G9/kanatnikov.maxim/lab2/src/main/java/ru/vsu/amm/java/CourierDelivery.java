package ru.vsu.amm.java;

import java.util.Objects;

public class CourierDelivery extends Delivery {
    public String name;

    public CourierDelivery(String address, double cost, int deliveringTimeDays, String name) {
        super(address, cost, deliveringTimeDays);
        this.name = name;
    }

    public CourierDelivery() {}

    public String getName() {
        return name;
    }

    @Override
    public void deliver() {
        System.out.println("Я " +  name
                + ", курьер, привёз вам доставку!");
    }

    @Override
    public String toString() {
        return super.toString() + "\nимя: " + name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof CourierDelivery)) {
            return false;
        }

        CourierDelivery that = (CourierDelivery) o;
        return super.equals(o) && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name);
    }
}
