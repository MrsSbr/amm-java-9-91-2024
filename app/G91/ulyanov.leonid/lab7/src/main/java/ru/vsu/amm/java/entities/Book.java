package ru.vsu.amm.java.entities;

import lombok.Data;
import ru.vsu.amm.java.enums.BookType;

@Data
public class Book {
    private Integer bookId;
    private String title;
    private String author;
    private String publisher;
    private Integer publishedYear;
    private Integer numberOfPages;
    private BookType bookType;

    public Book() {}
}
