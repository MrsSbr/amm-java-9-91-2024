package ru.vsu.amm.java.Entities;

public class SmartphoneInOrder {
    private long smartphoneInOrderId;
    private long orderNum;
    private long smartphoneId;
    public int amount;

    public SmartphoneInOrder() {
    }

    public SmartphoneInOrder(long orderNum, long smartphoneId, int amount) {
        this.orderNum = orderNum;
        this.smartphoneId = smartphoneId;
        this.amount = amount;
    }

    public long getSmartphoneInOrderId() {
        return smartphoneInOrderId;
    }

    public void setSmartphoneInOrderId(long smartphoneInOrderId) {
        this.smartphoneInOrderId = smartphoneInOrderId;
    }

    public long getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(long orderNum) {
        this.orderNum = orderNum;
    }

    public long getSmartphoneId() {
        return smartphoneId;
    }

    public void setSmartphoneId(long smartphoneId) {
        this.smartphoneId = smartphoneId;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
