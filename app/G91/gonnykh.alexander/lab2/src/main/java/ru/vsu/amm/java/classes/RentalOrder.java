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

    public void setCar(Car car) {
        this.car = car;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
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
