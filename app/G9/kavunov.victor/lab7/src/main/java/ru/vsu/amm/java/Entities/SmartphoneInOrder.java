package ru.vsu.amm.java.Entities;

public class SmartphoneInOrder {
    private long orderNum;
    private int smartphoneId;
    public int amount;

    public SmartphoneInOrder() {}

    public SmartphoneInOrder(long orderNum, int smartphoneId, int amount) {
        this.orderNum = orderNum;
        this.smartphoneId = smartphoneId;
        this.amount = amount;
    }

    public long getOrderNum() { return orderNum; }
    public void setOrderNum(long orderNum) { this.orderNum = orderNum; }

    public long getSmartphoneId() { return smartphoneId; }
    public void setSmartphoneId(int smartphoneId) { this.smartphoneId = smartphoneId; }

    public int getAmount() { return amount; }
    public void setAmount(int amount) { this.amount = amount; }
}
