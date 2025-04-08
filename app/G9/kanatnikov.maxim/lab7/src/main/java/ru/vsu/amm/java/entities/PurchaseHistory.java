package ru.vsu.amm.java.entities;

public class PurchaseHistory {
    private Long orderNumber;
    private int payment;
    private Long userId;
    private Long boardGameId;

    public PurchaseHistory() {
    }

    public PurchaseHistory(Long orderNumber, int payment, Long userId, Long boardGameId) {
        this.orderNumber = orderNumber;
        this.payment = payment;
        this.userId = userId;
        this.boardGameId = boardGameId;
    }

    public Long getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(Long orderNumber) {
        this.orderNumber = orderNumber;
    }

    public int getPayment() {
        return payment;
    }

    public void setPayment(int payment) {
        this.payment = payment;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getBoardGameId() {
        return boardGameId;
    }

    public void setBoardGameId(Long boardGameId) {
        this.boardGameId = boardGameId;
    }
}
