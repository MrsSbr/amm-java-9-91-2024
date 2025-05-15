package ru.vsu.amm.java.requests.Book;

import ru.vsu.amm.java.enums.BookType;

public record DownloadBookRequest(String title,
                                  String author,
                                  String publisher,
                                  Integer publishedYear,
                                  Integer numberOfPages,
                                  BookType bookType) {
}
