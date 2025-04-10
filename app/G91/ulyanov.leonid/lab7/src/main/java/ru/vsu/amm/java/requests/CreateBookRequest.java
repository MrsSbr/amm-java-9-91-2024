package ru.vsu.amm.java.requests;

import ru.vsu.amm.java.enums.BookType;

public record CreateBookRequest(String title,
                                String author,
                                String publisher,
                                Integer publishedYear,
                                Integer numberOfPages,
                                BookType bookType) {
}
