package ru.vsu.amm.java.entities;

import java.time.LocalDate;
import java.time.LocalTime;

import ru.vsu.amm.java.enams.ReturnStatus;

public class FoundProperty {
    private long foundPropertyID;
    private long propertyTypeID;
    private LocalDate dateOfFinding;
    private LocalTime timeOfFinding;
    private ReturnStatus returnStatus;
    private String placeOfFinding;
    private String description;
    private long userID;

    public FoundProperty() {}

    public long getFoundPropertyID() {
        return foundPropertyID;
    }

    public void setFoundPropertyID(long foundPropertyID) {
        this.foundPropertyID = foundPropertyID;
    }

    public long getPropertyTypeID() {
        return propertyTypeID;
    }

    public void setPropertyTypeID(long propertyTypeID) {
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

    public ReturnStatus getReturnStatus() {
        return returnStatus;
    }

    public void setReturnStatus(ReturnStatus returnStatus) {
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

    public long getUserID() {
        return userID;
    }

    public void setUserID(long userID) {
        this.userID = userID;
    }
}
