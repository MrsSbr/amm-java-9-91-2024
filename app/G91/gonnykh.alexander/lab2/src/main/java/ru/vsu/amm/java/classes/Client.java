package ru.vsu.amm.java.classes;

import java.util.ArrayList;
import java.util.List;

public class Client {
    private String name;
    private int age;
    private String driverLicense;
    private List<RentalOrder> rentalOrderList = new ArrayList<>();

    public Client(String name, int age, String driverLicense) {
        this.name = name;
        this.age = age;
        this.driverLicense = driverLicense;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getDriverLicense() {
        return driverLicense;
    }

    public List<RentalOrder> getRentalOrderList() {
        return rentalOrderList;
    }

    public void addRentalOrder(RentalOrder rentalOrder) {
        rentalOrderList.add(rentalOrder);
    }
}
