package ru.vsu.amm.java.entities;

import java.time.LocalDate;

public class PropertyType {
    private int propertyTypeID;
    private String propertyTypeName;
    private int nextDestinationID;
    private LocalDate lastOfStorageDate;
    private int storageCost;

    public PropertyType() {}

    public int getPropertyTypeID() {
        return propertyTypeID;
    }

    public void setPropertyTypeID(int propertyTypeID) {
        this.propertyTypeID = propertyTypeID;
    }

    public String getPropertyTypeName() {
        return propertyTypeName;
    }

    public void setPropertyTypeName(String propertyTypeName) {
        this.propertyTypeName = propertyTypeName;
    }

    public int getNextDestinationID() {
        return nextDestinationID;
    }

    public void setNextDestinationID(int nextDestinationID) {
        this.nextDestinationID = nextDestinationID;
    }

    public LocalDate getLastOfStorageDate() {
        return lastOfStorageDate;
    }

    public void setLastOfStorageDate(LocalDate lastOfStorageDate) {
        this.lastOfStorageDate = lastOfStorageDate;
    }

    public int getStorageCost() {
        return storageCost;
    }

    public void setStorageCost(int storageCost) {
        this.storageCost = storageCost;
    }
}
