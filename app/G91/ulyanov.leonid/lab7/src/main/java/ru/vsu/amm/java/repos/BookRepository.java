package ru.vsu.amm.java.repos;

import ru.vsu.amm.java.entities.Book;
import ru.vsu.amm.java.mappers.BookMapper;
import lombok.extern.slf4j.Slf4j;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static ru.vsu.amm.java.config.DbConfig.getDataSource;

@Slf4j
public class BookRepository implements Repository<Book> {
    private final DataSource dataSource;

    public BookRepository() {
        this.dataSource = getDataSource();
    }

    public Optional<Book> getByInfo(Book book) {
        String query = """
                SELECT Title, Author, Publisher, NumberOfPages, PublishedYear, BookType
                FROM Book WHERE Title = ? AND Author = ? AND Publisher = ?
                            AND NumberOfPages = ? AND PublishedYear = ? AND BookType = ?;
                """;

        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, book.getTitle());
            ps.setString(2, book.getAuthor());
            ps.setString(3, book.getPublisher());
            ps.setInt(4, book.getNumberOfPages());
            ps.setInt(5, book.getPublishedYear());
            ps.setString(6, book.getBookType().name());
            ResultSet resultSet = ps.executeQuery();
            BookMapper bookMapper = new BookMapper();

            if (resultSet.next()) {
                return Optional.of(bookMapper.mapRowToObject(resultSet));
            }
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
        }

        return Optional.empty();
    }

    @Override
    public Optional<Book> getById(int id) {
        String query = """
                SELECT Title, Author, Publisher, NumberOfPages, PublishedYear, BookType
                FROM Book WHERE Id_book = ?;
                """;

        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, id);
            ResultSet resultSet = ps.executeQuery();
            BookMapper bookMapper = new BookMapper();

            if (resultSet.next()) {
                return Optional.of(bookMapper.mapRowToObject(resultSet));
            }
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
        }

        return Optional.empty();
    }

    @Override
    public List<Book> getAll() {
        String query = """
                SELECT Title, Author, Publisher, NumberOfPages, PublishedYear, BookType
                FROM Book;
                """;

        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {
            ResultSet resultSet = ps.executeQuery();
            BookMapper bookMapper = new BookMapper();

            List<Book> books = new ArrayList<>();

            while (resultSet.next()) {
                books.add(bookMapper.mapRowToObject(resultSet));
            }

            return books;
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
        }

        return new ArrayList<>();
    }

    @Override
    public Integer create(Book book) {
        String query = """
                INSERT INTO Book(Title, Author, Publisher, NumberOfPages, PublishedYear, BookType)
                VALUES (?, ?, ?, ?, ?, ?);
                """;

        try (Connection connection = dataSource.getConnection()) {

            BookMapper bookMapper = new BookMapper();
            PreparedStatement ps = bookMapper.mapObjectToRow(book, connection, query);
            ps.execute();

            ResultSet resultSet = ps.getGeneratedKeys();
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }

        } catch (SQLException e) {
            log.error(e.getMessage(), e);
        }
        return 0;
    }

    @Override
    public void update(Book book) {
        String query = """
                UPDATE Book
                SET Title = ?, Author = ?, Publisher = ?, NumberOfPages = ?, PublishedYear = ?, BookType = ?
                    WHERE Id_book = ?;
                """;

        try (Connection connection = dataSource.getConnection()) {

            BookMapper bookMapper = new BookMapper();
            PreparedStatement ps = bookMapper.mapObjectToRow(book, connection, query);
            ps.setInt(1, book.getBookId());
            ps.execute();

        } catch (SQLException e) {
            log.error(e.getMessage(), e);
        }
    }

    @Override
    public void delete(Book book) {
        String query = """
                DELETE FROM Book
                WHERE Id_book = ?;
                """;

        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, book.getBookId());
            ps.execute();

        } catch (SQLException e) {
            log.error(e.getMessage(), e);
        }
    }
}
