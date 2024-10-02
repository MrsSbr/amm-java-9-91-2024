package ru.vsu.amm.java.classes;

import ru.vsu.amm.java.abstractClasses.Car;

import java.time.LocalDateTime;

import static java.time.temporal.ChronoUnit.SECONDS;

public class RentalOrder {
    private Car car;
    private Client client;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private double totalPrice;

    public RentalOrder(Car car, Client client, LocalDateTime startDate, LocalDateTime endDate) {
        this.car = car;
        this.client = client;
        this.startDate = startDate;
        this.endDate = endDate;
        this.totalPrice = SECONDS.between(startDate, endDate) * car.calculatePrice();
    }

    public Car getCar() {
        return car;
    }

    public Client getClient() {
        return client;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

}
