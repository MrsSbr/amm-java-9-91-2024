package ru.vsu.amm.java.entity;


public record Route(int number, double price) {

    @Override
    public String toString() {
        return "Маршрут: " + number()
                + "\nСтоимость проезда: " + price();
    }
}
