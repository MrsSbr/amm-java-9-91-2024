package ru.vsu.amm.java.service;

import ru.vsu.amm.java.entities.Author;

import java.util.List;

public interface AuthorService {
    List<Author> getAllAuthors();
    Author getAuthorById(Long id);
    void createAuthor(Author author);
    void updateAuthor(Author author);
    void deleteAuthor(Long id);
}
