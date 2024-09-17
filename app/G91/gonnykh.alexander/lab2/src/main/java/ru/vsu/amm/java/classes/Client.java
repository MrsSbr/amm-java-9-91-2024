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

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getDriverLicense() {
        return driverLicense;
    }

    public void setDriverLicense(String driverLicense) {
        this.driverLicense = driverLicense;
    }

    public void addRentalOrder(RentalOrder rentalOrder) {
        rentalOrderList.add(rentalOrder);
    }

    public List<RentalOrder> getRentalOrderList() {
        return rentalOrderList;
    }

    public void setRentalOrderList(List<RentalOrder> rentalOrderList) {
        this.rentalOrderList = rentalOrderList;
    }
}
