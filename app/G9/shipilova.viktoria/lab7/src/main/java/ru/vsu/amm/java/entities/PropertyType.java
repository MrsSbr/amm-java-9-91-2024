package ru.vsu.amm.java.entities;

import ru.vsu.amm.java.enams.PropertyTypeName;

public class PropertyType {
    private long id;
    private PropertyTypeName propertyTypeName;
    private NextDestination nextDestination;
    private int storageDays;
    private int storageCost;

    public PropertyType() {}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public PropertyTypeName getPropertyTypeName() {
        return propertyTypeName;
    }

    public void setPropertyTypeName(PropertyTypeName propertyTypeName) {
        this.propertyTypeName = propertyTypeName;
    }

    public NextDestination getNextDestination() {
        return nextDestination;
    }

    public void setNextDestination(NextDestination nextDestination) {
        this.nextDestination = nextDestination;
    }

    public int getStorageDays() {
        return storageDays;
    }

    public void setStorageDays(int storageDays) {
        this.storageDays = storageDays;
    }

    public int getStorageCost() {
        return storageCost;
    }

    public void setStorageCost(int storageCost) {
        this.storageCost = storageCost;
    }

    @Override
    public String toString() {
        return "PropertyType{" +
                "id=" + id +
                ", propertyTypeName=" + propertyTypeName +
                ", nextDestination=" + nextDestination +
                ", storageDays=" + storageDays +
                ", storageCost=" + storageCost +
                '}';
    }
}
