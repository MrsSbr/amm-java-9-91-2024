package ru.vsu.amm.java.entity;

import ru.vsu.amm.java.enums.Category;

import java.time.LocalDateTime;

public class Transaction {

    private  Long id;

    private Long userId;

    private Integer amount;

    private Boolean type;

    private LocalDateTime date;

    private Category category;
}
