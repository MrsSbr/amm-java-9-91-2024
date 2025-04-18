package ru.vsu.amm.java.entities;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Category {
    private long categoryId;
    private String title;

    public Category(long categoryId, String title) {
        this.categoryId = categoryId;
        this.title = title;
    }
}