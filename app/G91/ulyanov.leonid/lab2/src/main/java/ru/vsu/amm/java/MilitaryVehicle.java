package ru.vsu.amm.java;

import java.util.Objects;

public abstract class MilitaryVehicle implements Machinery {
    private String armament;
    private int capacity;
    private int speed;
    private Status currentStatus;

    public MilitaryVehicle() {
    }

    public MilitaryVehicle(String armament,
                           int capacity,
                           int speed) {
        this.armament = armament;
        this.capacity = capacity;
        this.speed = speed;
        currentStatus = Status.Off;
    }

    public String getArmament() {
        return armament;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getSpeed() {
        return speed;
    }

    public Status getStatus() {
        return currentStatus;
    }

    public void setArmament(String armament) {
        this.armament = armament;
    }


    public void turnOn() {
        if (currentStatus == Status.On) {
            System.out.println("The vehicle is already on!");
        } else {
            currentStatus = Status.On;
            System.out.println("Turning on the vehicle!");
        }
    }

    public void turnOff() {
        if (currentStatus == Status.Off) {
            System.out.println("The vehicle is already off!");
        } else {
            currentStatus = Status.Off;
            System.out.println("Turning off the vehicle!");
        }
    }

    public void doWork() {
        if (currentStatus == Status.Working) {
            System.out.println("The vehicle is already working!");
        } else if (currentStatus == Status.Off) {
            System.out.println("You need to turn on the vehicle!");
        } else {
            currentStatus = Status.Working;
            System.out.println("Vehicle is now working!");
        }
    }

    @Override
    public String toString() {
        return "Armament:\t" + armament
                + "\nCapacity:\t" + capacity
                + "\nSpeed:\t" + speed
                + "\nStatus:\t" + currentStatus;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        MilitaryVehicle other = (MilitaryVehicle) obj;
        return other.getCapacity() == this.getCapacity()
                && other.getSpeed() == this.getSpeed()
                && other.getStatus() == this.getStatus()
                && other.getArmament().equals(this.getArmament());
    }

    @Override
    public int hashCode() {
        return Objects.hash(armament, capacity, speed);
    }
}
