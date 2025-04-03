package ru.vsu.amm.java.entities;

public class Category {
    private long categoryId;
    private String title;

    public Category(long categoryId, String title) {
        this.categoryId = categoryId;
        this.title = title;
    }

    public long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}