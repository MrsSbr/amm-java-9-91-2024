package ru.vsu.amm.java.entities;

import java.time.LocalDate;
import java.time.LocalTime;

import ru.vsu.amm.java.enams.ReturnStatus;

public class FoundProperty {
    private long id;
    private PropertyType propertyType;
    private LocalDate dateOfFinding;
    private LocalTime timeOfFinding;
    private ReturnStatus returnStatus;
    private String placeOfFinding;
    private String description;
    private User user;

    public FoundProperty() {}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public PropertyType getPropertyType() {
        return propertyType;
    }

    public void setPropertyType(PropertyType propertyType) {
        this.propertyType = propertyType;
    }

    public LocalDate getDateOfFinding() {
        return dateOfFinding;
    }

    public void setDateOfFinding(LocalDate dateOfFinding) throws IllegalArgumentException {
        if (dateOfFinding.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Date of Finding cannot be in the future");
        }
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "FoundProperty{" +
                "id=" + id +
                ", propertyType=" + propertyType +
                ", dateOfFinding=" + dateOfFinding +
                ", timeOfFinding=" + timeOfFinding +
                ", returnStatus=" + returnStatus +
                ", placeOfFinding='" + placeOfFinding + '\'' +
                ", description='" + description + '\'' +
                ", user=" + user +
                '}';
    }
}
