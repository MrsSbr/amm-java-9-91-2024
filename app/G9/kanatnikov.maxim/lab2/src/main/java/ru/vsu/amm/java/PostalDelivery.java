package ru.vsu.amm.java;

public class PostalDelivery extends Delivery {

    public PostalDelivery(String address, double cost,
                          int deliveringTimeDays, String postAddress) {
        super(address, cost, deliveringTimeDays);
    }

    public PostalDelivery() {
    }

    @Override
    public void deliver() {
        System.out.println("Заберите посылку на почте по адресу "
                + address);
    }

    @Override
    public String toString() {
        return super.toString() + "\nадрес почты: " + address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof PostalDelivery)) {
            return false;
        }

        PostalDelivery that = (PostalDelivery) o;
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
