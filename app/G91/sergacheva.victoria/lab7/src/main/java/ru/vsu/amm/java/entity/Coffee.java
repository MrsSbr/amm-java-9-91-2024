package ru.vsu.amm.java.entity;

import java.util.Objects;

public class Coffee {
    private Long id;
    private String title;
    private String description;
    private User author;

    public Coffee(String title, String description, User author) {
        this.title = title;
        this.description = description;
        this.author = author;
    }

    public Coffee(Long id, String title, String description, User author) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.author = author;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coffee coffee = (Coffee) o;
        return Objects.equals(id, coffee.id) && Objects.equals(title, coffee.title) && Objects.equals(description, coffee.description) && Objects.equals(author, coffee.author);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, description, author);
    }
}
