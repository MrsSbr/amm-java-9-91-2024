package ru.vsu.amm.java.repos;

import ru.vsu.amm.java.entities.Book;
import ru.vsu.amm.java.mappers.BookMapper;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BookRepository implements Repository<Book> {
    private final DataSource dataSource;

    public BookRepository(final DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Optional<Book> getById(int id) throws SQLException {
        String query = """
                SELECT *
                FROM Book WHERE Id_book = ?;
                """;

        Connection connection = dataSource.getConnection();
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setInt(1, id);
        ResultSet resultSet = ps.executeQuery();
        BookMapper bookMapper = new BookMapper();

        if (resultSet.next()) {
            return Optional.of(bookMapper.mapRowToObject(resultSet));
        }

        return Optional.empty();
    }

    @Override
    public List<Book> getAll() throws SQLException {
        String query = """
                SELECT *
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
            throw new SQLException(e.getMessage());
        }
    }

    @Override
    public void create(Book book) throws SQLException {
        String query = """
                INSERT INTO Book(Title, Author, Publisher, NumberOfPages, PublishedYear, BookType)
                VALUES (?, ?, ?, ?, ?, ?);
                """;

        try (Connection connection = dataSource.getConnection()) {

            BookMapper bookMapper = new BookMapper();
            PreparedStatement ps = bookMapper.mapObjectToRow(book, connection, query);
            ps.execute();

        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }

    @Override
    public void update(Book book) throws SQLException {
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
            throw new SQLException(e.getMessage());
        }
    }

    @Override
    public void delete(Book book) throws SQLException {
        String query = """
                DELETE FROM Book
                WHERE Id_book = ?;
                """;

        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, book.getBookId());
            ps.execute();

        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }
}
