package java.ru.vsu.amm.java.entities;

import java.ru.vsu.amm.java.enums.BookType;

public class Book {
    private Integer bookId;
    private String title;
    private String author;
    private String publisher;
    private Integer publishedYear;
    private Integer numberOfPages;
    private BookType bookType;

    public Book() {}

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public void setPublishedYear(Integer publishedYear) {
        this.publishedYear = publishedYear;
    }

    public void setNumberOfPages(Integer numberOfPages) {
        this.numberOfPages = numberOfPages;
    }

    public void setBookType(BookType bookType) {
        this.bookType = bookType;
    }

    public Integer getBookId() {
        return bookId;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getPublisher() {
        return publisher;
    }

    public Integer getPublishedYear() {
        return publishedYear;
    }

    public Integer getNumberOfPages() {
        return numberOfPages;
    }

    public BookType getBookType() {
        return bookType;
    }
}
