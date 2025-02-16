package ru.vsu.amm.java.entities;

import java.time.LocalDate;
import java.time.LocalTime;

public class FoundProperty {
    private int foundPropertyID;
    private int propertyTypeID;
    private LocalDate dateOfFinding;
    private LocalTime timeOfFinding;
    private String returnStatus;
    private String placeOfFinding;
    private String description;
    private int userID;

    public FoundProperty() {}

    public int getFoundPropertyID() {
        return foundPropertyID;
    }

    public void setFoundPropertyID(int foundPropertyID) {
        this.foundPropertyID = foundPropertyID;
    }

    public int getPropertyTypeID() {
        return propertyTypeID;
    }

    public void setPropertyTypeID(int propertyTypeID) {
        this.propertyTypeID = propertyTypeID;
    }

    public LocalDate getDateOfFinding() {
        return dateOfFinding;
    }

    public void setDateOfFinding(LocalDate dateOfFinding) {
        this.dateOfFinding = dateOfFinding;
    }

    public LocalTime getTimeOfFinding() {
        return timeOfFinding;
    }

    public void setTimeOfFinding(LocalTime timeOfFinding) {
        this.timeOfFinding = timeOfFinding;
    }

    public String getReturnStatus() {
        return returnStatus;
    }

    public void setReturnStatus(String returnStatus) {
        this.returnStatus = returnStatus;
    }

    public String getPlaceOfFinding() {
        return placeOfFinding;
    }

    public void setPlaceOfFinding(String placeOfFinding) {
        this.placeOfFinding = placeOfFinding;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }
}
