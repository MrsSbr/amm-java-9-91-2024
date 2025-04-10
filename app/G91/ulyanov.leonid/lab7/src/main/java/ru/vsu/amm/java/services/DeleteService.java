package ru.vsu.amm.java.services;

import lombok.extern.slf4j.Slf4j;
import ru.vsu.amm.java.entities.Book;
import ru.vsu.amm.java.entities.BookUpdate;
import ru.vsu.amm.java.entities.User;
import ru.vsu.amm.java.enums.UpdateType;
import ru.vsu.amm.java.repos.BookRepository;
import ru.vsu.amm.java.repos.BookUpdatesRepository;
import ru.vsu.amm.java.repos.UserRepository;
import ru.vsu.amm.java.requests.BookUpdate.DeleteBookUpdateRequest;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.Optional;

@Slf4j
public class DeleteService {
    private final UserRepository users;
    private final BookRepository books;
    private final BookUpdatesRepository bookUpdates;

    public DeleteService(UserRepository users, BookRepository books,
                         BookUpdatesRepository bookUpdates) {
        this.users = users;
        this.books = books;
        this.bookUpdates = bookUpdates;
    }

    public void deleteBook(DeleteBookUpdateRequest request) {
        BookUpdate bookUpdate = new BookUpdate();

        try {
            bookUpdate.setUpdateTime(LocalDateTime.now());
            bookUpdate.setUpdateType(UpdateType.CHANGE);
            Optional<User> user = users.getById(request.userId());

            if (user.isPresent()) {
                bookUpdate.setUserId(user.get().getUserId());
            } else {
                log.error("Delete failed: User not found");
                throw new NoSuchElementException("Delete not found.");
            }

            Integer id = request.bookId();
            Optional<Book> bookOptional = books.getById(id);

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

        bookUpdates.create(bookUpdate);
    }
}
