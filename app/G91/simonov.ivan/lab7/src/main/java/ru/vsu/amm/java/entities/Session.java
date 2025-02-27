package ru.vsu.amm.java.entities;

import java.time.LocalDateTime;

public class Session {

    private int sessionId;
    private int userId;
    private int vehicleId;
    private double parkingPrice;
    private LocalDateTime entryDate;
    private LocalDateTime exitDate;

    public Session() {}

    public int getSessionId() {
        return sessionId;
    }

    public void setSessionId(int sessionId) {
        this.sessionId = sessionId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(int vehicleId) {
        this.vehicleId = vehicleId;
    }

    public double getParkingPrice() {
        return parkingPrice;
    }

    public void setParkingPrice(double parkingPrice) {

        if (parkingPrice >= 0) {
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
        if (entryDate != null && exitDate.isAfter(entryDate)) {
            this.exitDate = exitDate;
        } else {
            throw new IllegalArgumentException("Invalid exit date!");
        }
    }
}
