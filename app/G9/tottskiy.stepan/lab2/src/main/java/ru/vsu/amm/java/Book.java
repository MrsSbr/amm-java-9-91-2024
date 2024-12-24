package ru.vsu.amm.java;


import java.util.Objects;

public abstract class Book implements Item {

    private String title;
    private String author;
    private double price;

    public Book(String title, String author, double price) {
        this.title = title;
        this.author = author;
        this.price = price;
    }

    public Book() {
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String getDescription() {
        return "Title: " + this.title + ", Author: " + this.author;
    }

    public abstract String getGenre();

    @Override
    public String toString() {
        return "Book {" +
                "title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", price=" + price;

    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Book book = (Book) obj;
        return Double.compare(book.price, price) == 0 &&
                title.equals(book.title) &&
                author.equals(book.author);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, author, price);
    }



}
