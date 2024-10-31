package ru.vsu.amm.java;

import java.util.Objects;

public class FictionBook extends Book {

    private int countOfPages;

    public FictionBook(String title, String author, double price, int countOfPages) {
        super(title, author, price);
        this.countOfPages = countOfPages;
    }

    @Override
    public String getGenre() {
        return "Fiction";
    }

    @Override
    public String getDescription() {
        return super.getDescription() + " (Genre: " + getGenre() + ")";
    }

    @Override
    public String toString() {
        return super.toString() + "\nCount=" + countOfPages + "}";
    }

    @Override
    public boolean equals(Object obj) {
        FictionBook other = (FictionBook) obj;
        return other.countOfPages == countOfPages && super.equals(obj);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTitle(), getPrice(), getAuthor(), countOfPages);
    }
}
