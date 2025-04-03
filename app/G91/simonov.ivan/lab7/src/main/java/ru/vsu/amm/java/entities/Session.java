package ru.vsu.amm.java.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Session {

    private int sessionId;
    private User user;
    private Vehicle vehicle;
    private BigDecimal parkingPrice;
    private LocalDateTime entryDate;
    private LocalDateTime exitDate;

    public Session() {}

    public int getSessionId() {
        return sessionId;
    }

    public void setSessionId(int sessionId) {
        this.sessionId = sessionId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public BigDecimal getParkingPrice() {
        return parkingPrice;
    }

    public void setParkingPrice(BigDecimal parkingPrice) {

        if (parkingPrice.compareTo(BigDecimal.ZERO) >= 0) {
            this.parkingPrice = parkingPrice;
        } else {
            throw new IllegalArgumentException("Invalid parking price!");
        }
    }

    public LocalDateTime getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(LocalDateTime entryDate) {
        this.entryDate = entryDate;
    }

    public LocalDateTime getExitDate() {
        return exitDate;
    }

    public void setExitDate(LocalDateTime exitDate) {

        if (exitDate != null && entryDate != null && exitDate.isBefore(entryDate)) {
            throw new IllegalArgumentException("Invalid exit date!");
        }

        this.exitDate = exitDate;
    }
}
