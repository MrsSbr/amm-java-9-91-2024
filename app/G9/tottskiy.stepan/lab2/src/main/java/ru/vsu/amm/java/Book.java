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

    public Book() {}

    public String getTitle() {return title;}
    public String getAuthor() {return author;}
    public double getPrice() {return price;}

    @Override
    public String getDescription() {
        return "Title: " + this.title + ", Author: " + this.author;
    }

    public abstract String getGenre();

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

    @Override
    public String toString() {
        return "Book {" +
                "title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", price=" + price;

    }

}
