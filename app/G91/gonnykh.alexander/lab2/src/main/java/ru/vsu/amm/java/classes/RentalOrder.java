package ru.vsu.amm.java.classes;

import ru.vsu.amm.java.abstractClasses.Car;

import java.util.Date;

public class RentalOrder {
    private Car car;
    private Client client;
    private Date startDate;
    private Date endDate;
    private float totalPrice;

    public RentalOrder(Car car, Client client, Date startDate, Date endDate) {
        this.car = car;
        this.client = client;
        this.startDate = startDate;
        this.endDate = endDate;
        this.totalPrice = (endDate.getTime() - startDate.getTime()) * car.calculatePrice();
    }

    public Car getCar() {
        return car;
    }

    public Client getClient() {
        return client;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public float getTotalPrice() {
        return totalPrice;
    }

}
