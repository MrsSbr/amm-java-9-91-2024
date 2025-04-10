package ru.vsu.amm.java.services;

import lombok.extern.slf4j.Slf4j;
import ru.vsu.amm.java.entities.Book;
import ru.vsu.amm.java.entities.BookUpdate;
import ru.vsu.amm.java.entities.User;
import ru.vsu.amm.java.enums.UpdateType;
import ru.vsu.amm.java.repos.BookRepository;
import ru.vsu.amm.java.repos.BookUpdatesRepository;
import ru.vsu.amm.java.repos.UserRepository;
import ru.vsu.amm.java.requests.CreateBookUpdateRequest;

import javax.management.InstanceAlreadyExistsException;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.Optional;

@Slf4j
public class CreateService {
    private final UserRepository users;
    private final BookRepository books;
    private final BookUpdatesRepository bookUpdates;

    public CreateService(UserRepository users, BookRepository books,
                         BookUpdatesRepository bookUpdates) {
        this.users = users;
        this.books = books;
        this.bookUpdates = bookUpdates;
    }

    public void createBook(CreateBookUpdateRequest request) throws InstanceAlreadyExistsException {
        BookUpdate bookUpdate = new BookUpdate();

        try {
            bookUpdate.setUpdateTime(LocalDateTime.now());
            bookUpdate.setUpdateType(UpdateType.DOWNLOAD);
            Optional<User> user = users.getById(request.userId());

            if (user.isPresent()) {
                bookUpdate.setUserId(user.get().getUserId());
            } else {
                log.error("Creation failed: User not found");
                throw new NoSuchElementException("User not found.");
            }

            Book book = new Book(request.createBookRequest());
            Optional<Book> bookOptional = books.getByInfo(book);

            if (bookOptional.isPresent()) {
                log.error("Creation failed: Book already exists");
                throw new InstanceAlreadyExistsException("Book already exists.");
            } else {
                Integer id = books.create(book);
                book.setBookId(id);
                bookUpdate.setBookId(id);
            }

        } catch (IllegalArgumentException e) {
            log.error("Creation failed: {}", e.getMessage());
            throw new IllegalArgumentException(e.getMessage());
        }

        bookUpdates.create(bookUpdate);
    }

}
