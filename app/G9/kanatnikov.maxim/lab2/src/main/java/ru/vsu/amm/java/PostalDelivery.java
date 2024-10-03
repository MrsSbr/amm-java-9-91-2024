package ru.vsu.amm.java;

import java.util.Objects;

public class PostalDelivery extends Delivery {
    public String postAddress;

    public PostalDelivery(String address, double cost,
                          int deliveringTimeDays, String postAddress) {
        super(address, cost, deliveringTimeDays);
        this.postAddress = postAddress;
    }

    public PostalDelivery() {}

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof PostalDelivery)) {
            return false;
        }

        PostalDelivery that = (PostalDelivery) o;
        return super.equals(o) && Objects.equals(postAddress, that.postAddress);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), postAddress);
    }

    @Override
    public String toString() {
        return super.toString() + "\nадрес почты: " + postAddress;
    }

    @Override
    public void deliver() {
        System.out.println("Заберите посылку на почте по адресу "
                + postAddress);
    }
}
