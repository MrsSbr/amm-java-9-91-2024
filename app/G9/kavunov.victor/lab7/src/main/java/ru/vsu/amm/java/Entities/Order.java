package ru.vsu.amm.java.Entities;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Order {
    private Long id;
    private Long userId;
    private Long smartphoneId;
    private LocalDateTime registrationDate;
    private Boolean isPaid;

    public Order() {}

    public Order(long userId, long smartphoneId, LocalDateTime registrationDate, boolean isPaid) {
        this.userId = userId;
        this.smartphoneId = smartphoneId;
        this.registrationDate = registrationDate;
        this.isPaid = isPaid;
    }
}
