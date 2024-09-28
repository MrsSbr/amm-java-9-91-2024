package ru.vsu.amm.java.classes;

import ru.vsu.amm.java.abstractClasses.Car;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Rent {
    private List<Car> cars = new ArrayList<>();
    private List<Client> customers = new ArrayList<>();
    private List<RentalOrder> rentals = new ArrayList<>();

    public Rent() {
    }

    public void addCar(Car car) {
        cars.add(car);
    }

    public void addCustomer(Client car) {
        customers.add(car);
    }

    public List<Car> getCars() {
        return cars;
    }

    public List<Client> getCustomers() {
        return customers;
    }

    public List<RentalOrder> getRentals() {
        return rentals;
    }

    public boolean addOrder(Car car, Client client, LocalDateTime startDate, LocalDateTime endDate) {
        if (cars.contains(car) && customers.contains(client)
                && rentals.stream().noneMatch(r -> r.getEndDate().isAfter(startDate)
                && r.getStartDate().isBefore(endDate)
                && r.getStartDate().isEqual(startDate))) {
            rentals.add(new RentalOrder(car, client, startDate, endDate));
            return true;
        }
        return false;
    }
}
