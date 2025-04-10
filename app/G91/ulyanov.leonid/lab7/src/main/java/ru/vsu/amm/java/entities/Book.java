package ru.vsu.amm.java.entities;

import lombok.Data;
import ru.vsu.amm.java.enums.BookType;
import ru.vsu.amm.java.requests.Book.DownloadBookRequest;

@Data
public class Book {
    private Integer bookId;
    private String title;
    private String author;
    private String publisher;
    private Integer publishedYear;
    private Integer numberOfPages;
    private BookType bookType;

    public Book() {
    }

    public Book(DownloadBookRequest request) {
        this.title = request.title();
        this.author = request.author();
        this.publisher = request.publisher();
        this.publishedYear = request.publishedYear();
        this.numberOfPages = request.numberOfPages();
        this.bookType = request.bookType();
    }
}
