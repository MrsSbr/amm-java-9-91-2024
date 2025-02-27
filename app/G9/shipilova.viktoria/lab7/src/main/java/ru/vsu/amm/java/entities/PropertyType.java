package ru.vsu.amm.java.entities;

import ru.vsu.amm.java.enams.PropertyTypeName;

public class PropertyType {
    private long propertyTypeID;
    private PropertyTypeName propertyTypeName;
    private long nextDestinationID;
    private int storageDays;
    private int storageCost;

    public PropertyType() {}

    public long getPropertyTypeID() {
        return propertyTypeID;
    }

    public void setPropertyTypeID(long propertyTypeID) {
        this.propertyTypeID = propertyTypeID;
    }

    public PropertyTypeName getPropertyTypeName() {
        return propertyTypeName;
    }

    public void setPropertyTypeName(PropertyTypeName propertyTypeName) {
        this.propertyTypeName = propertyTypeName;
    }

    public long getNextDestinationID() {
        return nextDestinationID;
    }

    public void setNextDestinationID(long nextDestinationID) {
        this.nextDestinationID = nextDestinationID;
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
}
