package ru.vsu.amm.java.entities;

public class PurchaseHistory {
    private long orderNumber;
    private int payment;
    private long userId;
    private long boardGameId;

    public PurchaseHistory() {
    }

    public PurchaseHistory(long orderNumber, int payment, long userId, long boardGameId) {
        this.orderNumber = orderNumber;
        this.payment = payment;
        this.userId = userId;
        this.boardGameId = boardGameId;
    }

    public long getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(long orderNumber) {
        this.orderNumber = orderNumber;
    }

    public int getPayment() {
        return payment;
    }

    public void setPayment(int payment) {
        this.payment = payment;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getBoardGameId() {
        return boardGameId;
    }

    public void setBoardGameId(long boardGameId) {
        this.boardGameId = boardGameId;
    }
}
