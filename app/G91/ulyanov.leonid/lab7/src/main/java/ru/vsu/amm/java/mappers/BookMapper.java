package ru.vsu.amm.java.mappers;

import ru.vsu.amm.java.entities.Book;
import ru.vsu.amm.java.enums.BookType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BookMapper implements Mapper<Book> {
    public BookMapper() {}

    @Override
    public Book mapRowToObject(ResultSet resultSet) throws SQLException {
        Book book = new Book();

        book.setBookId(resultSet.getInt("Id_book"));
        book.setTitle(resultSet.getString("Title"));
        book.setAuthor(resultSet.getString("Author"));
        book.setPublisher(resultSet.getString("Publisher"));
        book.setNumberOfPages(resultSet.getInt("NumberOfPages"));
        book.setPublishedYear(resultSet.getInt("PublishedYear"));
        book.setBookType(BookType.valueOf(resultSet.getString("BookType")));

        return book;
    }

    @Override
    public PreparedStatement mapObjectToRow(Book book,
                                            Connection connection,
                                            String query) throws SQLException {
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, book.getTitle());
        ps.setString(2, book.getAuthor());
        ps.setString(3, book.getPublisher());
        ps.setInt(4, book.getNumberOfPages());
        ps.setInt(5, book.getPublishedYear());
        ps.setString(6, book.getBookType().name());

        return ps;
    }
}
