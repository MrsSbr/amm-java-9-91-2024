package ru.vsu.amm.java.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.vsu.amm.java.enums.BookType;
import ru.vsu.amm.java.requests.Book.DownloadBookRequest;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class Book {
    private final static Integer FIRST_PRINTED = 1454;
    private final static Integer MIN_PAGES = 1;
    private final static String ENG_REGEX = "^[a-zA-Z]+$";
    @Setter
    private Integer bookId;
    @Setter
    private String title;
    private String author;
    @Setter
    private String publisher;
    private Integer publishedYear;
    private Integer numberOfPages;
    @Setter
    private BookType bookType;

    public Book() {
    }

    public Book(DownloadBookRequest request) {
        setTitle(request.title());
        setAuthor(request.author());
        setPublisher(request.publisher());
        setPublishedYear(request.publishedYear());
        setNumberOfPages(request.numberOfPages());
        this.bookType = request.bookType();
    }

    public void setAuthor(String author) {
        if (!author.matches(ENG_REGEX)) {
            throw new IllegalArgumentException("Invalid author, latin letters only");
        }
        this.author = author;
    }

    public void setPublishedYear(int publishedYear) {
        int currentYear = LocalDate.now().getYear();
        if (publishedYear < FIRST_PRINTED || publishedYear > currentYear) {
            throw new IllegalArgumentException(String.format("Invalid published year (should be between %d and %d))",
                    FIRST_PRINTED, currentYear));
        }
        this.publishedYear = publishedYear;
    }

    public void setNumberOfPages(Integer numberOfPages) {
        if (numberOfPages < MIN_PAGES) {
            throw new IllegalArgumentException(String.format("Invalid number of pages (should be at least %d)",
                    MIN_PAGES));
        }
        this.numberOfPages = numberOfPages;
    }
}
