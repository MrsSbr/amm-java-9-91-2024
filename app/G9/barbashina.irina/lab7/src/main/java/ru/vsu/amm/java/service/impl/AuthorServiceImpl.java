package ru.vsu.amm.java.service.impl;

import ru.vsu.amm.java.entities.Article;
import ru.vsu.amm.java.entities.Author;
import ru.vsu.amm.java.repository.AuthorRepository;
import ru.vsu.amm.java.service.AuthorService;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AuthorServiceImpl implements AuthorService {
    private static final Logger log = Logger.getLogger(AuthorServiceImpl.class.getName());

    private final AuthorRepository authorRepository;

    public AuthorServiceImpl() {
        log.info("Call AuthorServiceImpl constructor");
        authorRepository = new AuthorRepository();
    }

    @Override
    public List<Author> getAllAuthors() {
        log.info("Call getAllAuthors");
        try {
            return authorRepository.findAll();
        } catch (SQLException e) {
            String errorMsg = "Ошибка при получении списка авторов: " + e.getMessage();
            log.log(Level.SEVERE, errorMsg, e);
            throw new RuntimeException(errorMsg, e);
        }
    }

    @Override
    public Author getAuthorById(Long id) {
        log.info("Call getAuthorById");
        try {
            return authorRepository.findById(id).orElseThrow(
                    () -> new RuntimeException("Автор с id " + id + " не найден")
            );
        } catch (SQLException e) {
            log.log(Level.SEVERE, "Ошибка доступа к базе данных", e);
            throw new RuntimeException("Ошибка при получении автора из базы", e);
        }
    }

    @Override
    public void createAuthor(Author author) {
        log.info("Call createAuthor");
        try {
            authorRepository.save(author);
        } catch (SQLException e) {
            log.log(Level.SEVERE, "Ошибка при создании автора", e);
            throw new RuntimeException("Не удалось создать автора: " + e.getMessage(), e);
        }
    }

    @Override
    public void updateAuthor(Author author) {
        log.info("Call updateAuthor");
        try {
            authorRepository.update(author);
        } catch (SQLException e) {
            log.log(Level.SEVERE, "Ошибка при обновлении автора", e);
            throw new RuntimeException("Не удалось обновить автора: " + e.getMessage(), e);
        }
    }

    @Override
    public void deleteAuthor(Long id) {
        log.info("Call deleteAuthor");
        try {
            Author author = getAuthorById(id);
            authorRepository.delete(author);
        } catch (RuntimeException e) {
            log.log(Level.SEVERE, "Ошибка при удалении автора", e);
            throw new RuntimeException("Не удалось удалить автора с ID " + id + ": " + e.getMessage(), e);
        } catch (SQLException e) {
            log.log(Level.SEVERE, "Ошибка базы данных при удалении автора", e);
            throw new RuntimeException("Ошибка базы данных при удалении автора: " + e.getMessage(), e);
        }
    }
}
