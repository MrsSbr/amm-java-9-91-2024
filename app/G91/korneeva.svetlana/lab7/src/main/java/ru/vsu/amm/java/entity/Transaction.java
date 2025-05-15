package ru.vsu.amm.java.entity;

import lombok.Builder;
import ru.vsu.amm.java.enums.Category;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
public class Transaction {

    private Long id;

    private Long userId;

    private BigDecimal amount;

    private Boolean type;

    private LocalDateTime date;

    private Category category;

    public Transaction() {
    }

    public Transaction(Long id, Long userId, BigDecimal amount, Boolean type, LocalDateTime date, Category category) {
        this.id = id;
        this.userId = userId;
        this.amount = amount;
        this.type = type;
        this.date = date;
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

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Boolean getType() {
        return type;
    }

    public void setType(Boolean type) {
        this.type = type;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
