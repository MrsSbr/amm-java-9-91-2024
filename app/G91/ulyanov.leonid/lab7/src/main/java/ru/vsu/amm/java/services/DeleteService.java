package ru.vsu.amm.java.services;

import lombok.extern.slf4j.Slf4j;
import ru.vsu.amm.java.entities.Book;
import ru.vsu.amm.java.entities.BookUpdate;
import ru.vsu.amm.java.entities.User;
import ru.vsu.amm.java.repos.BookRepository;
import ru.vsu.amm.java.repos.UserRepository;

import java.util.NoSuchElementException;
import java.util.Optional;

@Slf4j
public class DeleteService {
    private final UserRepository users;
    private final BookRepository books;

    public DeleteService() {
        this.users = new UserRepository();
        this.books = new BookRepository();
    }

    public void deleteBook(Integer bookId, Integer userId) {
        BookUpdate bookUpdate = new BookUpdate();

        try {
            Optional<User> user = users.getById(userId);

            if (user.isPresent()) {
                bookUpdate.setUserId(user.get().getUserId());
            } else {
                log.error("Delete failed: User not found");
                throw new NoSuchElementException("Delete not found.");
            }
            Optional<Book> bookOptional = books.getById(bookId);

            if (bookOptional.isPresent()) {
                books.delete(bookOptional.get());
            } else {
                log.error("Delete failed: Book not found");
                throw new NoSuchElementException("Book not found.");
            }
        } catch (IllegalArgumentException e) {
            log.error("Delete failed: {}", e.getMessage());
            throw new IllegalArgumentException(e.getMessage());
        }
    }
}
