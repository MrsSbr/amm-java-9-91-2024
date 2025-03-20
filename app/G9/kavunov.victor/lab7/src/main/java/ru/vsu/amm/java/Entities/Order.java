package ru.vsu.amm.java.Entities;

import java.util.Date;

public class Order {
    private long orderNum;
    private long userId;
    private Date orderDate;
    private float totalCost;

    public Order() {}

    public Order(long orderNum, long userId, Date orderDate, float totalCost) {
        this.orderNum = orderNum;
        this.userId = userId;
        this.orderDate = orderDate;
        this.totalCost = totalCost;
    }

    public long getOrderNum() { return orderNum; }
    public void setOrderNum(long orderNum) { this.orderNum = orderNum; }

    public long getUserId() { return userId; }
    public void setUserId(long userId) { this.userId = userId; }

    public Date getOrderDate() { return orderDate; }
    public void setOrderDate(Date orderDate) { this.orderDate = orderDate; }

    public float getTotalCost() { return totalCost; }
    public void setTotalCost(float totalCost) { this.totalCost = totalCost; }
}
