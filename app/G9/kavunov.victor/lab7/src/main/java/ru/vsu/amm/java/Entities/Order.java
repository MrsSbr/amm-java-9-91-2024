package ru.vsu.amm.java.Entities;

import java.time.LocalDateTime;

public class Order {
    private long orderNum;
    private long userId;
    private LocalDateTime registrationDate;
    private float totalCost;

    public Order() {
    }

    public Order(long orderNum, long userId, LocalDateTime registrationDate, float totalCost) {
        this.orderNum = orderNum;
        this.userId = userId;
        this.registrationDate = registrationDate;
        this.totalCost = totalCost;
    }

    public long getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(long orderNum) {
        this.orderNum = orderNum;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public LocalDateTime getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDateTime registrationDate) {
        this.registrationDate = registrationDate;
    }

    public float getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(float totalCost) {
        this.totalCost = totalCost;
    }
}
