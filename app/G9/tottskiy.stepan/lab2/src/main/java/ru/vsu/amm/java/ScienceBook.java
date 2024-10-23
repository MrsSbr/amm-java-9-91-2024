package ru.vsu.amm.java;

import java.util.Objects;

public class ScienceBook extends Book {
    private int numberOfIllustrations; // Количество иллюстраций

    public ScienceBook(String title, String author, double price, int numberOfIllustrations) {
        super(title, author, price);
        this.numberOfIllustrations = this.numberOfIllustrations;
    }

    @Override
    public String getGenre() {
        return "Science";
    }

    // Переопределение метода getDescription
    @Override
    public String getDescription() {
        return super.getDescription() + " (Genre: " + getGenre() + ")";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        ScienceBook other = (ScienceBook) obj;
        return numberOfIllustrations == other.numberOfIllustrations && super.equals(obj);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTitle(), getPrice(), getAuthor(), numberOfIllustrations);
    }

    @Override
    public String toString() {
        return super.toString() + "\nIllustrations=" + numberOfIllustrations + "}";
    }
}
