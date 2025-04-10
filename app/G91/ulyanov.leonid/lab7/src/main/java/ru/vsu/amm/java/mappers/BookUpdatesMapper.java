package ru.vsu.amm.java.mappers;

import ru.vsu.amm.java.entities.BookUpdate;
import ru.vsu.amm.java.enums.UpdateType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class BookUpdatesMapper implements Mapper<BookUpdate> {
    public BookUpdatesMapper() {
    }

    @Override
    public BookUpdate mapRowToObject(ResultSet resultSet) throws SQLException {
        BookUpdate bookUpdate = new BookUpdate();

        bookUpdate.setUpdateId(resultSet.getInt("Id_update"));
        bookUpdate.setBookId(resultSet.getInt("Id_book"));
        bookUpdate.setUserId(resultSet.getInt("Id_user"));
        bookUpdate.setUpdateTime(resultSet.getTimestamp("UpdateTime").toLocalDateTime());
        bookUpdate.setUpdateType(UpdateType.valueOf(resultSet.getString("UpdateType")));

        return bookUpdate;
    }

    @Override
    public PreparedStatement mapObjectToRow(BookUpdate bookUpdate,
                                            Connection connection,
                                            String query) throws SQLException {
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setInt(1, bookUpdate.getBookId());
        ps.setInt(2, bookUpdate.getUserId());
        ps.setTimestamp(3, Timestamp.valueOf(bookUpdate.getUpdateTime()));
        ps.setString(4, bookUpdate.getUpdateType().name());

        return ps;
    }
}
