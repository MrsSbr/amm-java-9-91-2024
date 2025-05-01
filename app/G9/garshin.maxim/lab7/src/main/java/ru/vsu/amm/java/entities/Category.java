package ru.vsu.amm.java.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@AllArgsConstructor
public class Category {
    private long categoryId;
    private long userId;
    private String title;

    public Category(long categoryId, String title) {
        this.categoryId = categoryId;
        this.title = title;
    }

    public Category(long categoryId, long userId) {
        this.categoryId = categoryId;
        this.userId = userId;
    }

    public Category(String title, long userId) {
        this.title = title;
        this.userId = userId;
    }
}