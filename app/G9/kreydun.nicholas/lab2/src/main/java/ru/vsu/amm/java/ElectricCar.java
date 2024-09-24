package ru.vsu.amm.java;

import java.util.Objects;

public class ElectricCar extends Car {
    private final int batteryCapacity; // Емкость батареи в кВтч
    private  int range; // Запас хода в километрах

    public ElectricCar(String model, String color, int batteryCapacity, int range) {
        super(model, color);
        this.batteryCapacity = batteryCapacity;
        this.range = range;
    }

    public int getBatteryCapacity() {
        return batteryCapacity;
    }

    public int getRange() {
        return range;
    }

    @Override
    public void beep() {
        System.out.println("Бззззз! Я электромобиль!");
    }

    @Override
    public String toString() {
        return "Электромобиль модели " + super.getModel() + " (" + getColor() + "), с батареей на " + batteryCapacity + " кВтч";
    }

    // Новый метод: Проверка возможности добраться до точки
    public boolean canReachDestination(int distance) {
        return distance <= range;
    }

    // Новый метод: Зарядка
    public void charge(int chargingTime) {
        // Предположим, что 1 час зарядки дает 50 км запаса хода
        int additionalRange = chargingTime * 50;
        range += additionalRange;
        System.out.println("Электромобиль заряжен! Добавлен запас хода: " + additionalRange + " км.");
    }

    // Метод для сравнения электромобиля с другим автомобилем (используем instanceof)
    public String compareWith(Car otherCar) {
        if (otherCar instanceof ElectricCar) {
            ElectricCar otherElectricCar = (ElectricCar) otherCar;
            if (batteryCapacity > otherElectricCar.batteryCapacity) {
                return "Мой электромобиль имеет большую емкость батареи.";
            } else if (batteryCapacity < otherElectricCar.batteryCapacity) {
                return "У другого электромобиля батарея мощнее.";
            } else {
                return "У нас одинаковая емкость батареи.";
            }
        } else if (otherCar instanceof Truck) {
            return "Я электромобиль, а это грузовик. У нас разные задачи.";
        } else if (otherCar instanceof PassengerCar) {
            return "Я электромобиль, а это обычная машина. У меня нет выбросов!";
        } else {
            return "Я электромобиль, а это что-то другое...";
        }
    }

    @Override
    public boolean equals(Object o) {
        boolean flag = false;
        if (this == o)
            flag = true;
        else if (o == null || getClass() != o.getClass())
            flag =  false;
        else if (!super.equals(o))
            flag = false;
        else {
            ElectricCar that = (ElectricCar) o;
            flag =  batteryCapacity == that.batteryCapacity && range == that.range;
        }
        return flag;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), batteryCapacity, range);
    }
}