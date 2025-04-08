package ru.vsu.amm.java.requests;

import ru.vsu.amm.java.enums.BookType;

public record AddBookRequest(String title,
                             String author,
                             String publisher,
                             Integer publishedYear,
                             Integer numberOfPages,
                             BookType bookType) {
}
