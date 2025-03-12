package ru.vsu.amm.java.entity;

import ru.vsu.amm.java.enums.Category;

import java.time.LocalDateTime;

public class Transaction {

    private  Long id;

    private Long userId;

    private Integer amount;

    private Boolean type;

    private LocalDateTime data;

    private Category category;

    public Transaction() {
    }

    public Transaction(Long id, Long userId, Integer amount, Boolean type, LocalDateTime data, Category category) {
        this.id = id;
        this.userId = userId;
        this.amount = amount;
        this.type = type;
        this.data = data;
        this.category = category;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Boolean getType() {
        return type;
    }

    public void setType(Boolean type) {
        this.type = type;
    }

    public LocalDateTime getDate() {
        return data;
    }


    public void setDate(LocalDateTime date) {
        this.data = date;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
