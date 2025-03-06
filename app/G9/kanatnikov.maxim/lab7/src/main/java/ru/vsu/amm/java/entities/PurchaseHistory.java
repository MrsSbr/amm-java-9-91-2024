package ru.vsu.amm.java.entities;

public class PurchaseHistory {
    private int purchaseHistoryId;
    private int orderNumber;
    private int payment;
    private int userId;
    private int boardGameId;

    public PurchaseHistory() {
    }

    public PurchaseHistory(int purchaseHistoryId, int orderNumber, int payment, int userId, int boardGameId) {
        this.purchaseHistoryId = purchaseHistoryId;
        this.orderNumber = orderNumber;
        this.payment = payment;
        this.userId = userId;
        this.boardGameId = boardGameId;
    }

    public int getPurchaseHistoryId() {
        return purchaseHistoryId;
    }

    public void setPurchaseHistoryId(int purchaseHistoryId) {
        this.purchaseHistoryId = purchaseHistoryId;
    }

    public int getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }

    public int getPayment() {
        return payment;
    }

    public void setPayment(int payment) {
        this.payment = payment;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getBoardGameId() {
        return boardGameId;
    }

    public void setBoardGameId(int boardGameId) {
        this.boardGameId = boardGameId;
    }
}
