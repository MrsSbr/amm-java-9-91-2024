package ru.vsu.amm.java.services;

import lombok.extern.slf4j.Slf4j;
import ru.vsu.amm.java.entities.Book;
import ru.vsu.amm.java.entities.BookUpdate;
import ru.vsu.amm.java.entities.User;
import ru.vsu.amm.java.enums.UpdateType;
import ru.vsu.amm.java.repos.BookRepository;
import ru.vsu.amm.java.repos.BookUpdatesRepository;
import ru.vsu.amm.java.repos.UserRepository;
import ru.vsu.amm.java.requests.BookUpdate.ChangeBookUpdateRequest;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.Optional;

@Slf4j
public class UpdateService {
    private final UserRepository users;
    private final BookRepository books;
    private final BookUpdatesRepository bookUpdates;

    public UpdateService() {
        this.users = new UserRepository();
        this.books = new BookRepository();
        this.bookUpdates = new BookUpdatesRepository();
    }

    public void updateBook(ChangeBookUpdateRequest request) {
        BookUpdate bookUpdate = new BookUpdate();

        try {
            bookUpdate.setUpdateTime(LocalDateTime.now());
            bookUpdate.setUpdateType(UpdateType.CHANGE);
            Optional<User> user = users.getById(request.userId());

            if (user.isPresent()) {
                bookUpdate.setUserId(user.get().getUserId());
            } else {
                log.error("Update failed: User not found");
                throw new NoSuchElementException("User not found.");
            }

            Book book = new Book(request.changeBookRequest());
            Optional<Book> bookOptional = books.getByInfo(book);

            if (bookOptional.isPresent()) {
                Integer id = request.bookId();
                book.setBookId(id);
                books.update(book);
            } else {
                log.error("Update failed: Book not found");
                throw new NoSuchElementException("Book not found.");
            }
        } catch (IllegalArgumentException e) {
            log.error("Update failed: {}", e.getMessage());
            throw new IllegalArgumentException(e.getMessage());
        }

        bookUpdates.create(bookUpdate);
    }
}
