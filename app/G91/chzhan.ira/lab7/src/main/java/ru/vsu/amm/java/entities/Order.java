package ru.vsu.amm.java.entities;

import java.math.BigDecimal;

public class Order {
    private Long id;
    private Long customerId;
    private Long toyId;
    private int quantity;
    private BigDecimal totalPrice;

    public Order(Long id, Long customerId, Long toyId, int quantity, BigDecimal totalPrice) {
        this.id = id;
        this.customerId = customerId;
        this.toyId = toyId;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getToyId() {
        return toyId;
    }

    public void setToyId(Long toyId) {
        this.toyId = toyId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }
}
